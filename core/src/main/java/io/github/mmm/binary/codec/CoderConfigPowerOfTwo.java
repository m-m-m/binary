/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * {@link CoderConfig} for alphabet with length 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class CoderConfigPowerOfTwo extends CoderConfigMapped {

  final BitConfig bitConfig;

  /**
   * The constructor.
   *
   * @param alphabet the {@link Base#getAlphabet() alphabet}.
   * @param bitConfig the {@link BitConfig}.
   * @param padding the padding character.
   */
  public CoderConfigPowerOfTwo(String alphabet, BitConfig bitConfig, char padding) {

    super(alphabet, padding);
    this.bitConfig = bitConfig;
  }

  @Override
  protected Encoder encoder(BaseFormat format, byte[] data) {

    return new EncoderPowerOfTwo(format, data, this);
  }

  @Override
  protected Decoder decoder(BaseFormat format, byte[] input) {

    return new DecoderPowerOfTwo(format, input, this);
  }

}
