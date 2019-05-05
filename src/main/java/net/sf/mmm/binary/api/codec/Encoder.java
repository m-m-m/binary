package net.sf.mmm.binary.api.codec;

/**
 * Stateful class to {@link Base#encode(byte[]) encode}.
 */
abstract class Encoder {

  protected final byte[] input;

  protected final BaseFormat format;

  public Encoder(BaseFormat format, byte[] input) {

    super();
    this.format = format;
    this.input = input;
  }

  /**
   * @return the {@link BaseGeneric#encode(byte[]) encoded} {@link String}.
   */
  protected abstract String encode();

}