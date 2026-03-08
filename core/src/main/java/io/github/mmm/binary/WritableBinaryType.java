/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.util.Objects;

/**
 * Extends {@link BinaryType} with write operations from {@link WritableBinaryStream}.
 *
 * @since 1.0.0
 */
public class WritableBinaryType extends BinaryType implements WritableBinaryStream {

  /**
   * The constructor.
   *
   * @param data the raw binary {@link #getData() data}.
   */
  public WritableBinaryType(byte[] data) {

    super(data);
  }

  /**
   * The constructor.
   *
   * @param base64 the {@link #getData() data} as {@link #formatBase64() base64}.
   */
  public WritableBinaryType(String base64) {

    super(base64);
  }

  @Override
  public void setBytes(long pos, byte[] bytes, int offset, int length) {

    Objects.requireNonNull(bytes);
    checkNotNegative(pos, "pos");
    checkNotNegative(offset, "offset");
    checkNotNegative(length, "length");
    checkMax(offset, bytes.length, "offset");
    checkMax(pos, this.data.length, "pos");
    if (length == 0) {
      return;
    }
    checkMax(offset, bytes.length - length, "offset");
    checkMax(pos, this.data.length - length, "pos");
    System.arraycopy(bytes, offset, this.data, (int) pos, length);
  }

  private void checkNotNegative(long value, String name) {

    if (value < 0) {
      throw new IllegalArgumentException(name + " must not be negative: " + value);
    }
  }

  private void checkMax(long value, long max, String name) {

    if (value >= max) {
      throw new IllegalArgumentException(name + " (" + value + ") exceeds " + max);
    }
  }

  @Override
  public void truncate(long maxLength) {

    throw new UnsupportedOperationException();
  }

}
