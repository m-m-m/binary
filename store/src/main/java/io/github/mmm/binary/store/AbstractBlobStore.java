package io.github.mmm.binary.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.binary.Streamable;
import io.github.mmm.binary.StreamableFile;

/**
 * Abstract base implementation of {@link BlobStore}.
 */
public class AbstractBlobStore implements BlobStore {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractBlobStore.class);

  private final Path root;

  /** The directory where the BLOBs are stored. */
  protected final Path data;

  private final Path temp;

  /**
   * The constructor.
   *
   * @param root the root directory where all BLOBs are stored. Needs to exist.
   */
  protected AbstractBlobStore(Path root) {

    super();
    if (!Files.isDirectory(root)) {
      throw new IllegalArgumentException("Root directory of blob store does not exist: " + root);
    }
    this.root = root;
    this.data = this.root.resolve("data");
    this.temp = this.root.resolve("temp");
    try {
      Files.createDirectories(this.data);
      Files.createDirectories(this.temp);
    } catch (IOException e) {
      throw new IllegalStateException("Root directory not writable " + root, e);
    }
  }

  /**
   * Computes the {@link Path} where to save and load the file with the given {@code id}. Dumping all files named after
   * their ID into a flat folder can cause problems if you want to store hundreds of thousand or even millions of files.
   * To avoid having too many direct children per folder, we extract segments from the {@code id} and add sub-folders to
   * the path. E.g. with the default implementation of this method the {@code id} "e4c80c23-438e-4c26-99cb-16d32ed14dc2"
   * would result in the path data/e4c/c23/dc2/e4c80c23-438e-4c26-99cb-16d32ed14dc2.
   *
   * @param id the ID of the BLOB.
   * @return the {@link Path} to the BLOB or {@code null} if ID is invalid. The returned {@link Path} has to be inside
   *         the {@code data} folder.
   */
  protected Path getFilePath(String id) {

    // see JavaDoc example (first 3 chars, skip 2 chars and get next 3 chars, skip 25 chars to get the last 3)
    return getFilePath(this.data, id, 3, 0, 2, -1);
  }

  /**
   * @param id the ID of the BLOB.
   * @param basePath the data folder under which the file should be located.
   * @param segmentLength the length of each segment or in other words the number of alphanumeric characters consumed
   *        from the {@code id} to build a sub-folder to partition the BLOBs.
   * @param segmentOffsets an array with the number of characters to skip after each segment. The last offset may be
   *        {@code -1} to extract the last segment from the end.
   * @return the {@link Path} to the BLOB or {@code null} if ID is invalid.
   */
  protected static final Path getFilePath(Path basePath, String id, int segmentLength, int... segmentOffsets) {

    if ((segmentLength < 2) || (segmentLength > 6)) {
      throw new IllegalArgumentException("segmentLength:" + segmentLength);
    }
    if (segmentOffsets.length == 0) {
      throw new IllegalArgumentException("segmentOffsets.length:" + segmentOffsets.length);
    }
    int max = segmentOffsets.length - 1;
    for (int i = 0; i <= max; i++) {
      int offset = segmentOffsets[i];
      if ((offset < -1) || (offset > 64) || ((offset == -1) && (i != max))) {
        throw new IllegalArgumentException("segmentOffsets[" + i + "]:" + offset);
      }
    }
    Path path = basePath;
    StringBuilder segment = new StringBuilder(segmentLength);
    int segmentCount = 0;
    int i = 0;
    int len = id.length();
    int inc = 1;
    int skipCount = segmentOffsets[0];
    while (i < len) {
      char c = id.charAt(i);
      i += inc;
      if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9'))) {
        if (skipCount > 0) {
          skipCount--;
        } else {
          if (inc == -1) {
            segment.insert(0, c);
          } else {
            segment.append(c);
          }
          if (segment.length() == segmentLength) {
            path = path.resolve(segment.toString());
            segment.setLength(0);
            segmentCount++;
            if (segmentCount >= segmentOffsets.length) {
              return path.resolve(id);
            }
            int offset = segmentOffsets[segmentCount];
            if (offset < 0) {
              if (i + segmentLength >= len) {
                return null;
              }
              i = len - 1;
              inc = -1;
            } else {
              skipCount = offset;
            }
          }
        }
      } else if ((c != '-') && (c != '=') && (c != '/') && (c != '+')) {
        return null; // reject ID if no hex, uuid, base64, etc.
      }
    }
    return null;
  }

  /**
   * @param file the {@link Path} to the file saved in a temporary folder.
   * @param tempId the temporary ID of the file.
   * @return the final ID of the file. Implementations can easily implement de-duplication by computing a unique hash or
   *         something.
   */
  protected String determineFileId(Path file, String tempId) {

    return tempId;
  }

  @Override
  public BlobId save(InputStream blob) {

    UUID uuid = UUID.randomUUID();
    String tempId = uuid.toString();
    Path tempFile = this.temp.resolve(tempId);
    String id = saveTemporary(blob, tempId, tempFile);
    Path file = getFilePath(id);
    if (file == null) {
      throw new IllegalStateException(id);
    }
    boolean duplicate = false;
    try {
      if (Files.exists(file)) {
        duplicate = true;
        LOG.debug("Ignoring duplicate BLOB that already exists at {}", file);
        Files.delete(tempFile);
      } else {
        Files.createDirectories(file.getParent());
        Files.move(tempFile, file);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to move blob to final destination: " + file, e);
    }
    return new BlobIdType(id, duplicate);
  }

  /**
   * @param blob the {@link InputStream} to save.
   * @param tempId the temporary file ID.
   * @param tempFile the temporary {@link Path} where to save the file.
   * @return the final ID of the file. See {@link #determineFileId(Path, String)} for details.
   */
  protected String saveTemporary(InputStream blob, String tempId, Path tempFile) {

    try (OutputStream out = Files.newOutputStream(tempFile)) {
      blob.transferTo(out);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save blob to temporary file " + tempFile, e);
    }
    String id = determineFileId(tempFile, tempId);
    return id;
  }

  @Override
  public Streamable load(String id) {

    Path file = getFilePath(id);
    if ((file != null) && Files.exists(file)) {
      return new StreamableFile(file);
    }
    return null;
  }

  @Override
  public boolean delete(String id) {

    Path file = getFilePath(id);
    if (Files.exists(file)) {
      try {
        Files.delete(file);
        return true;
      } catch (IOException e) {
        throw new IllegalStateException("Failed to delete BLOB: " + file);
      }
    }
    return false;
  }

}
