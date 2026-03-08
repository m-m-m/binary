/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

import io.github.mmm.base.exception.RuntimeIoException;

/**
 * Implementation of {@link WritableBinaryStream} from a given {@link Blob}.
 *
 * @see #of(Blob)
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public class BlobAsBinaryStream implements WritableBinaryStream {

  private final Blob blob;

  private BlobAsBinaryStream(Blob blob) {

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

    Objects.requireNonNull(out);
    try (InputStream in = this.blob.getBinaryStream()) {
      byte[] buffer = new byte[4096];
      for (int bytesRead; (bytesRead = in.read(buffer)) != -1;) {
        out.write(buffer, 0, bytesRead);
      }
    } catch (Exception e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public InputStream openStream() {

    try {
      return this.blob.getBinaryStream();
    } catch (SQLException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public InputStream openStream(long pos) {

    try {
      return this.blob.getBinaryStream(pos, Long.MAX_VALUE);
    } catch (SQLException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public long getSize() {

    try {
      return this.blob.length();
    } catch (SQLException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public void truncate(long maxLength) {

    try {
      long length = this.blob.length();
      if (maxLength > length) {
        return;
      }
      this.blob.truncate(maxLength);
    } catch (SQLException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public void setBytes(long pos, byte[] bytes, int offset, int length) {

    try {
      this.blob.setBytes(pos, bytes, offset, length);
    } catch (SQLException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * @param blob the {@link Blob} to convert.
   * @return the given {@link Blob} converted to {@link BinaryStream}.
   */
  public static BinaryStream of(Blob blob) {

    if (blob == null) {
      return null;
    } else if (blob instanceof BlobFromBinaryStream wrapper) {
      return wrapper.getStream();
    }
    return new BlobAsBinaryStream(blob);
  }

}
