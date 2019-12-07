package io.github.mmm.binary.codec;

/**
 * Stateful class to {@link Base#decode(String) decode}.
 * 
 * @since 1.0.0
 */
abstract class Decoder {

  protected final byte[] input;

  protected final BaseFormat format;

  protected Decoder(BaseFormat format, byte[] encodedData, int dataLength) {

    super();
    this.input = encodedData;
    this.format = format;
  }

  protected abstract byte[] decode();
}