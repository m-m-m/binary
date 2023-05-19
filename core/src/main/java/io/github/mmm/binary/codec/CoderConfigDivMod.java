/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * {@link CoderConfig} for alphabet with length other than 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class CoderConfigDivMod extends CoderConfigMapped {

  /**
   * The constructor.
   *
   * @param alphabet the {@link Base#getAlphabet() alphabet}.
   */
  public CoderConfigDivMod(String alphabet) {

    super(alphabet, BaseGeneric.NUL);
  }

  @Override
  protected Encoder encoder(BaseFormat format, byte[] data) {

    return new EncoderDivMod(format, data, this);
  }

  @Override
  protected Decoder decoder(BaseFormat format, byte[] input) {

    return new DecoderDivMod(format, input, this);
  }

}
