package io.github.mmm.binary.codec;

import java.util.Objects;

import io.github.mmm.binary.Binary;

/**
 * Implementation of {@link BinaryCodec} that applies a given {@link BaseFormat} to a {@link Base} codec.
 *
 * @since 1.0.0
 */
public class BaseFormatCodec implements BinaryCodec {

  private final Base base;

  private final BaseFormat format;

  /**
   * The constructor.
   *
   * @param base the raw {@link Base} encoder.
   * @param format the {@link BaseFormat} to apply.
   */
  public BaseFormatCodec(Base base, BaseFormat format) {

    super();
    Objects.requireNonNull(base, "base");
    Objects.requireNonNull(format, "format");
    this.base = base;
    this.format = format;
  }

  @Override
  public String encode(byte[] data) {

    if (data == null) {
      return null;
    }
    if (data.length == 0) {
      return "";
    }
    return this.base.doEncode(data, this.format);
  }

  @Override
  public byte[] decode(String encodedData) {

    if (encodedData == null) {
      return null;
    }
    if (encodedData.isEmpty()) {
      return Binary.EMPTY_BYTE_ARRAY;
    }
    return this.base.doDecode(encodedData, this.format);
  }

}
