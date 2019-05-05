package net.sf.mmm.binary.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Implementation of {@link Streamable} for {@link Blob}.
 *
 * @since 1.0.0
 */
public class StreamableBlob implements Streamable {

  private final Blob blob;

  /**
   * The constructor.
   *
   * @param blob the {@link Blob} to wrap.
   */
  public StreamableBlob(Blob blob) {

    super();
    this.blob = blob;
  }

  /**
   * @return the contained {@link Blob}.
   */
  public Blob getBlob() {

    return this.blob;
  }

  @Override
  public void save(OutputStream out) {

    try {
      long bytesRemaining = this.blob.length();
      long bytesStreamed = 0;
      int capacity = 4096;
      while (bytesRemaining > 0) {
        if (bytesRemaining < capacity) {
          capacity = (int) bytesRemaining;
        }
        byte[] data = this.blob.getBytes(bytesStreamed, capacity);
        out.write(data);
        bytesStreamed += capacity;
        bytesRemaining -= capacity;
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public InputStream asStream() {

    try {
      return this.blob.getBinaryStream();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

}
