package io.github.mmm.binary;

import io.github.mmm.binary.codec.Base16;
import io.github.mmm.binary.codec.Base64;
import io.github.mmm.binary.codec.BinaryCodec;

/**
 * Interface for smaller binary data (e.g. a cryptographic signature). Implementations shall be immutable. <br>
 * <b>Attention:</b><br>
 * This API is designed for smaller binary data and will cause it to be fully loaded into memory (Java heap). For binary
 * large objects (BLOB) (e.g. for generic files or documents) use other solutions like e.g. {@link java.sql.Blob}.
 *
 * @since 1.0.0
 */
public interface Binary extends Streamable {

  /** An empty byte array (no data). */
  byte[] EMPTY_BYTE_ARRAY = new byte[0];

  /**
   * @param index the index of the requested byte starting from {@code 1}.
   * @return the requested byte. If the given index exceeds the {@link #getData() data} (is greater or equal to
   *         {@link #getLength() length}) {@code 0} is returned.
   */
  byte getDataByte(int index);

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass these data in raw form (unencrypted).
   *
   * @return the raw encoded data. Array will be copied to prevent manipulation. Hence, this method is expensive and
   *         subsequent calls shall be avoided.
   */
  byte[] getData();

  /**
   * Copies the {@link #getData() data} into the given buffer.
   *
   * @param buffer the byte array to copy the data to. Has to have enough capacity left for the {@link #getLength()
   *        length} of this BLOB.
   * @param offset the index where to start copying to {@code buffer}.
   */
  void getData(byte[] buffer, int offset);

  /**
   * @return the length of this BLOB in bytes (array length of {@link #getData()}).
   */
  int getLength();

  /**
   * This method avoids an {@link System#arraycopy(Object, int, Object, int, int) array-copy} of {@link #getData()} and
   * is therefore more efficient than using {@link BinaryCodec}.{@link BinaryCodec#encode(byte[])
   * encode}({@link #getData()}).<br>
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass the data in raw form (unencrypted).
   *
   * @param codec the {@link BinaryCodec}.
   * @return the {@link BinaryCodec#encode(byte[]) encoded} {@link #getData() data}.
   */
  String format(BinaryCodec codec);

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass the data in raw form (unencrypted).
   *
   * @return a hex-dump of this BLOB. This form is more transparent but less compact to {@link #formatBase64() base 64
   *         representation}.
   */
  default String formatHex() {

    return format(Base16.DEFAULT);
  }

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass the data in raw form (unencrypted).
   *
   * @return a {@link java.util.Base64} encoded {@link String} representation of this BLOB. Base64 representation is
   *         more compact and therefore useful for larger BLOBs while {@link #formatHex() hex} is more transparent to
   *         read for humans.
   */
  default String formatBase64() {

    return format(Base64.DEFAULT);
  }

  /**
   * @return {@code true} if the {@link #getData() data} contains only zeros ({@code 0} values), {@code false}
   *         otherwise.
   */
  boolean isZeros();

}
