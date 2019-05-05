package net.sf.mmm.util.binary.api.codec;

/**
 * Stateful class to {@link Base#decode(String) decode}.
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