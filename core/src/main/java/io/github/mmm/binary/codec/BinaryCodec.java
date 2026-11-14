/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

import io.github.mmm.base.number.NumberCodec;
import io.github.mmm.binary.Binary;

/// Interface for a coded that can [encode][#encode(byte[])] [binary data][Binary] to a [String] and
/// [decode][#decode(String)] it back.
///
/// @since 7.6.1
public interface BinaryCodec {

  /// @param data the binary data to encode.
  /// @return the given `data` encoded as [String].
  String encode(byte[] data);

  /// @param value the short value to encode.
  /// @return the given `value` encoded as [String] using BigEndian.
  default String encodeShort(short value) {

    byte[] data = new byte[2];
    data[0] = (byte) (value >> 8);
    data[1] = (byte) value;
    return encode(data);
  }

  /// @param value the int value to encode.
  /// @return the given `value` encoded as [String] using BigEndian.
  default String encodeInt(int value) {

    byte[] data = new byte[4];
    data[0] = (byte) (value >> 24);
    data[1] = (byte) (value >> 16);
    data[2] = (byte) (value >> 8);
    data[3] = (byte) value;
    return encode(data);
  }

  /// @param value the long value to encode.
  /// @return the given `value` encoded as [String] using BigEndian.
  default String encodeLong(long value) {

    byte[] data = new byte[8];
    data[0] = (byte) (value >> 56);
    data[1] = (byte) (value >> 48);
    data[2] = (byte) (value >> 40);
    data[3] = (byte) (value >> 32);
    data[4] = (byte) (value >> 24);
    data[5] = (byte) (value >> 16);
    data[6] = (byte) (value >> 8);
    data[7] = (byte) value;
    return encode(data);
  }

  /// @param encodedData the [encoded][#encode(byte[])] [String].
  /// @return the decoded data as `byte` array.
  byte[] decode(String encodedData);

  /// @param encodedData the [encoded][#encodeShort(short)] [String].
  /// @return the decoded value.
  default short decodeShort(String encodedData) {

    byte[] data = decode(encodedData);
    if ((data.length == 1) || (data.length == 2)) {
      return (short) NumberCodec.readU2(data, 0, true);
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

  /// @param encodedData the [encoded][#encodeInt(int)] [String].
  /// @return the decoded value.
  default int decodeInt(String encodedData) {

    byte[] data = decode(encodedData);
    if ((data.length > 0) && (data.length <= 4)) {
      return NumberCodec.readU4(data, 0, true);
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

  /// @param encodedData the [encoded][#encodeLong(long)] [String].
  /// @return the decoded value.
  default long decodeLong(String encodedData) {

    byte[] data = decode(encodedData);
    if ((data.length > 0) && (data.length <= 8)) {
      return NumberCodec.readU8(data, 0, true);
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

}
