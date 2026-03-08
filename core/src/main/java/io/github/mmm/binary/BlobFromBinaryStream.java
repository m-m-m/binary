/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import io.github.mmm.base.exception.ReadOnlyException;

/**
 * Implementation of {@link Blob} that wraps a {@link BinaryStream}.
 *
 * @see #of(BinaryStream)
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public class BlobFromBinaryStream implements Blob {

  private BinaryStream stream;

  private WritableBinaryStream writableStream;

  private BlobFromBinaryStream(BinaryStream stream) {

    super();
    Objects.requireNonNull(stream);
    this.stream = stream;
    if (stream instanceof WritableBinaryStream wbs) {
      this.writableStream = wbs;
    }
  }

  /**
   * @return the wrapped {@link BinaryStream}.
   */
  public BinaryStream getStream() {

    return this.stream;
  }

  /**
   * @return writableStream
   */
  public WritableBinaryStream getWritableStream() {

    if (this.writableStream == null) {
      throw new ReadOnlyException("BinaryStream");
    }
    return this.writableStream;
  }

  @Override
  public long length() throws SQLException {

    return this.stream.getSize();
  }

  @Override
  public byte[] getBytes(long pos, int length) throws SQLException {

    try (InputStream in = this.stream.openStream(pos)) {
      byte[] data = new byte[length];
      int remain = length;
      int offset = 0;
      while (remain > 0) {
        int bytesRead = in.read(data, offset, remain);
        if (bytesRead < 0) {
          if (offset < length) {
            return Arrays.copyOf(data, offset);
          }
          break; // can actually never happen since remain must be 0 then...
        }
        offset += bytesRead;
        remain -= bytesRead;
      }
      return data;
    } catch (IOException e) {
      throw new SQLException(e);
    }
  }

  @Override
  public InputStream getBinaryStream() throws SQLException {

    return this.stream.openStream();
  }

  @Override
  public long position(byte[] pattern, long start) throws SQLException {

    throw new UnsupportedOperationException();
  }

  @Override
  public long position(Blob pattern, long start) throws SQLException {

    throw new UnsupportedOperationException();
  }

  @Override
  public int setBytes(long pos, byte[] bytes) throws SQLException {

    return setBytes(pos, bytes, 0, bytes.length);
  }

  @Override
  public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {

    getWritableStream().setBytes(pos, bytes, offset, len);
    return len;
  }

  @Override
  public OutputStream setBinaryStream(long pos) throws SQLException {

    throw new UnsupportedOperationException();
  }

  @Override
  public void truncate(long len) throws SQLException {

    getWritableStream().truncate(len);
  }

  @Override
  public void free() throws SQLException {

    // this.streamable.close();
    this.stream = null;
  }

  @Override
  public InputStream getBinaryStream(long pos, long length) throws SQLException {

    return this.stream.openStream(pos);
  }

  /**
   * @param streamable the {@link BinaryStream} to convert.
   * @return the given {@link BinaryStream} converted to {@link Blob}.
   */
  public static Blob of(BinaryStream streamable) {

    if (streamable == null) {
      return null;
    } else if (streamable instanceof BlobAsBinaryStream blob) {
      return blob.getBlob();
    }
    return new BlobFromBinaryStream(streamable);
  }

}
