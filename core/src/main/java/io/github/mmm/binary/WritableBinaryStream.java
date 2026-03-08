/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

/**
 * Write access for a {@link BinaryStream}.
 *
 * @since 1.0.0
 */
public interface WritableBinaryStream extends BinaryStream {

  /**
   * Truncates this binary stream to the given maximum length. That is all bytes at the end of the binary stream will be
   * discarded so that its {@link #getSize() size} does not exceed the given maximum length.
   *
   * @param maxLength the new maximum length of this binary stream.
   * @see java.sql.Blob#truncate(long)
   */
  void truncate(long maxLength);

  /**
   * @param pos the position in this binary stream where to start writing.
   * @param bytes the bytes to write.
   * @param offset the offset in the {@code bytes} where to start getting the data from.
   * @param len the number of bytes to read from {@code bytes} and write into this binary stream.
   * @see java.sql.Blob#setBytes(long, byte[])
   */
  default void setBytes(long pos, byte[] bytes) {

    setBytes(pos, bytes, 0, bytes.length);
  }

  /**
   * @param pos the position in this binary stream where to start writing.
   * @param bytes the bytes to write.
   * @param offset the offset in the {@code bytes} where to start getting the data from.
   * @param length the number of bytes to read from {@code bytes} and write into this binary stream.
   * @see java.sql.Blob#setBytes(long, byte[], int, int)
   */
  void setBytes(long pos, byte[] bytes, int offset, int length);

  /**
   * @return a read-only {@link BinaryStream} instance of this {@link WritableBinaryStream}.
   */
  default BinaryStream asReadOnly() {

    return ReadOnlyBinaryStream.readOnly(this);
  }

}
