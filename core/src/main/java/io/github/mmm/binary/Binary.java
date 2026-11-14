/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import io.github.mmm.binary.codec.Base16;
import io.github.mmm.binary.codec.Base64;
import io.github.mmm.binary.codec.BinaryCodec;

/// Interface for smaller binary data (e.g. a cryptographic signature). Implementations shall be immutable.
/// **Attention:** This API is designed for smaller binary data and will cause it to be fully loaded into memory (Java
/// heap). For binary large objects (BLOB) (e.g. for generic files or documents) use other solutions like e.g.
/// [java.sql.Blob].
///
/// @since 1.0.0
public interface Binary extends BinaryStream {

  /// An empty byte array (no data).
  byte[] EMPTY_BYTE_ARRAY = new byte[0];

  /// @param index the index of the requested byte starting from `1`.
  /// @return the requested byte. If the given index exceeds the [data][#getData()] (is greater or equal to
  ///         [length][#getLength()]) `0` is returned.
  byte getDataByte(int index);

  /// **ATTENTION**: Sub-types often represent sensible data. Be careful to pass these data in raw form (unencrypted).
  ///
  /// @return the raw encoded data. Array will be copied to prevent manipulation. Hence, this method is expensive and
  ///         subsequent calls shall be avoided.
  byte[] getData();

  /// Copies the [data][#getData()] into the given buffer.
  ///
  /// @param buffer the byte array to copy the data to. Has to have enough capacity.
  /// @param bufferOffset the index where to start copying to `buffer`.
  default void getData(byte[] buffer, int bufferOffset) {

    getData(0, buffer, bufferOffset, getLength());
  }

  /// Copies a chunk of the [data][#getData()] into the given buffer.
  ///
  /// @param binaryOffset the index where to start copying from this [Binary].
  /// @param buffer the byte array to copy the data to. Has to have enough capacity.
  /// @param bufferOffset the index where to start copying to `buffer`.
  /// @param length the number of bytes to copy. Should not exceed the [length][#getLength()] of this [Binary] or the
  ///        given `buffer` (satisfy `offset + length < buffer.length`).
  void getData(int binaryOffset, byte[] buffer, int bufferOffset, int length);

  /// @return the length of this BLOB in bytes (array length of [#getData()]).
  /// @see #getSize()
  int getLength();

  @Override
  default long getSize() {

    return getLength();
  }

  /// This method avoids an [array-copy][System#arraycopy(Object, int, Object, int, int)] of [#getData()] and is
  /// therefore more efficient than using
  /// [BinaryCodec].[encode][BinaryCodec#encode(byte\[\])]([#getData()]). **ATTENTION**: Sub-types often represent
  /// sensible data. Be careful to pass the data in raw form (unencrypted).
  ///
  /// @param codec the [BinaryCodec].
  /// @return the [encoded][BinaryCodec#encode(byte\[\])] [data][#getData()].
  String format(BinaryCodec codec);

  /// **ATTENTION**: Sub-types often represent sensible data. Be careful to pass the data in raw form (unencrypted).
  ///
  /// @return a hex-dump of this BLOB. This form is more transparent but less compact to [base 64
  ///         representation][#formatBase64()].
  default String formatHex() {

    return format(Base16.DEFAULT);
  }

  /// **ATTENTION**: Sub-types often represent sensible data. Be careful to pass the data in raw form (unencrypted).
  ///
  /// @return a [java.util.Base64] encoded [String] representation of this BLOB. Base64 representation is more compact
  ///         and therefore useful for larger BLOBs while [hex][#formatHex()] is more transparent to read for humans.
  default String formatBase64() {

    return format(Base64.DEFAULT);
  }

  /// @return `true` if the [data][#getData()] contains only zeros (`0` values), `false` otherwise.
  boolean isZeros();

}
