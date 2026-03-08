/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.mmm.base.exception.RuntimeIoException;

/**
 * Interface for an abstract BLOB that can be streamed.
 *
 * @see Binary
 * @see WritableBinaryStream
 * @since 1.0.0
 */
public interface BinaryStream {

  /**
   * @return a new {@link InputStream} to read the underlying data.
   */
  InputStream openStream();

  /**
   * @param pos the number of bytes to {@link InputStream#skip(long) skip}.
   * @return a new {@link InputStream} to read the underlying data.
   */
  default InputStream openStream(long pos) {

    try (InputStream stream = openStream()) {
      if (pos > 0) {
        stream.skip(pos);
      }
      return stream;
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * @return the size of this BLOB in bytes.
   */
  long getSize();

  /**
   * Saves the content of this binary stream into the given {@link OutputStream}. This is not a write operation on this
   * binary stream.
   *
   * @param out the {@link OutputStream} to {@link OutputStream#write(byte[]) write} the data to.
   */
  void save(OutputStream out);

}
