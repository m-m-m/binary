package io.github.mmm.binary.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.UUID;

import io.github.mmm.binary.codec.Base64;

/**
 * Implementation of {@link BlobStore} that just saves any file using a {@link UUID}.
 */
public class HashDeduplicatingBlobStore extends AbstractBlobStore {

  private final String algorithm;

  /**
   * The constructor.
   *
   * @param root the root directory where all BLOBs are stored. Needs to exist.
   */
  public HashDeduplicatingBlobStore(Path root) {

    this(root, "sha256");
  }

  /**
   * The constructor.
   *
   * @param root the root directory where all BLOBs are stored. Needs to exist.
   * @param algorithm the hash algorithm to use. E.g. "sha256".
   */
  public HashDeduplicatingBlobStore(Path root, String algorithm) {

    super(root);
    this.algorithm = algorithm;
    newDigest(); // force error if algorithm is not supported
  }

  private MessageDigest newDigest() {

    try {
      return MessageDigest.getInstance(this.algorithm);
    } catch (Exception e) {
      throw new IllegalArgumentException(this.algorithm, e);
    }
  }

  @Override
  protected String saveTemporary(InputStream blob, String tempId, Path tempFile) {

    MessageDigest digest = newDigest();
    try (OutputStream out = Files.newOutputStream(tempFile);
        DigestOutputStream digestOut = new DigestOutputStream(out, digest)) {
      blob.transferTo(digestOut);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save blob to temporary file " + tempFile, e);
    }
    String id = Base64.DEFAULT.encode(digest.digest());
    return id;
  }

}
