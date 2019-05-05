package net.sf.mmm.binary.api.codec;

import net.sf.mmm.binary.api.Binary;

/**
 * Interface for a coded that can {@link #encode(byte[]) encode} {@link Binary binary data} to a {@link String} and
 * {@link #decode(String) decode} it back.
 *
 * @since 7.6.1
 */
public interface BinaryCodec {

  /**
   * @param data the binary data to encode.
   * @return the given {@code data} encoded as {@link String}.
   */
  String encode(byte[] data);

  /**
   * @param value the short value to encode.
   * @return the given {@code value} encoded as {@link String} using BigEndian.
   */
  default String encodeShort(short value) {

    byte[] data = new byte[2];
    data[0] = (byte) (value >> 8);
    data[1] = (byte) value;
    return encode(data);
  }

  /**
   * @param value the int value to encode.
   * @return the given {@code value} encoded as {@link String} using BigEndian.
   */
  default String encodeInt(int value) {

    byte[] data = new byte[4];
    data[0] = (byte) (value >> 24);
    data[1] = (byte) (value >> 16);
    data[2] = (byte) (value >> 8);
    data[3] = (byte) value;
    return encode(data);
  }

  /**
   * @param value the long value to encode.
   * @return the given {@code value} encoded as {@link String} using BigEndian.
   */
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

  /**
   * @param encodedData the {@link #encode(byte[]) encoded} {@link String}.
   * @return the decoded data as {@code byte} array.
   */
  byte[] decode(String encodedData);

  /**
   * @param encodedData the {@link #encodeShort(short) encoded} {@link String}.
   * @return the decoded value.
   */
  default short decodeShort(String encodedData) {

    byte[] data = decode(encodedData);
    if (data.length == 2) {
      return (short) (((data[0] & 0xff) << 8) | (data[1] & 0xff));
    } else if (data.length == 1) {
      return data[0];
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

  /**
   * @param encodedData the {@link #encodeInt(int) encoded} {@link String}.
   * @return the decoded value.
   */
  default int decodeInt(String encodedData) {

    byte[] data = decode(encodedData);
    if (data.length == 4) {
      return (((data[0] & 0xff) << 24) | ((data[1] & 0xff) << 16) | ((data[2] & 0xff) << 8) | ((data[3] & 0xff)));
    } else if (data.length == 3) {
      return (((data[0] & 0xff) << 16) | ((data[1] & 0xff) << 8) | ((data[2] & 0xff)));
    } else if (data.length == 2) {
      return (((data[0] & 0xff) << 8) | ((data[1] & 0xff)));
    } else if (data.length == 1) {
      return data[0];
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

  /**
   * @param encodedData the {@link #encodeLong(long) encoded} {@link String}.
   * @return the decoded value.
   */
  default long decodeLong(String encodedData) {

    byte[] data = decode(encodedData);
    if (data.length == 8) {
      return ((((long) data[0]) << 56) | (((long) data[1] & 0xff) << 48) | (((long) data[2] & 0xff) << 40) | (((long) data[3] & 0xff) << 32)
          | (((long) data[4] & 0xff) << 24) | (((long) data[5] & 0xff) << 16) | (((long) data[6] & 0xff) << 8) | (((long) data[7] & 0xff)));
    } else if (data.length == 7) {
      return ((((long) data[0] & 0xff) << 48) | (((long) data[1] & 0xff) << 40) | (((long) data[2] & 0xff) << 32)
          | (((long) data[3] & 0xff) << 24) | (((long) data[4] & 0xff) << 16) | (((long) data[5] & 0xff) << 8) | (((long) data[6] & 0xff)));
    } else if (data.length == 6) {
      return ((((long) data[0] & 0xff) << 40) | (((long) data[1] & 0xff) << 32) | (((long) data[2] & 0xff) << 24)
          | (((long) data[3] & 0xff) << 16) | (((long) data[4] & 0xff) << 8) | (((long) data[5] & 0xff)));
    } else if (data.length == 5) {
      return ((((long) data[0] & 0xff) << 32) | (((long) data[1] & 0xff) << 24) | (((long) data[2] & 0xff) << 16)
          | (((long) data[3] & 0xff) << 8) | (((long) data[4] & 0xff)));
    } else if (data.length == 4) {
      return ((((long) data[0] & 0xff) << 24) | (((long) data[1] & 0xff) << 16) | (((long) data[2] & 0xff) << 8)
          | (((long) data[3] & 0xff)));
    } else if (data.length == 3) {
      return ((((long) data[0] & 0xff) << 16) | (((long) data[1] & 0xff) << 8) | (((long) data[2] & 0xff)));
    } else if (data.length == 2) {
      return ((((long) data[0] & 0xff) << 8) | (((long) data[1] & 0xff)));
    } else if (data.length == 1) {
      return data[0];
    }
    throw new IllegalArgumentException("Decoded data has invalid length " + data.length);
  }

}
