/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

import java.nio.charset.StandardCharsets;

/**
 * Stateless config and factory for {@link Decoder} and {@link Encoder}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
abstract class CoderConfig {

  protected abstract Encoder encoder(BaseFormat format, byte[] data);

  /**
   * @return {@link Base#isCaseSensitive()}.
   */
  protected abstract boolean isCaseSensitive();

  protected String encode(byte[] data, BaseFormat format) {

    Encoder encoder = encoder(format, data);
    return encoder.encode();
  }

  protected byte[] decode(String encodedData, BaseFormat format) {

    return decode(encodedData.getBytes(StandardCharsets.US_ASCII), format);
  }

  protected byte[] decode(byte[] encodedData, BaseFormat format) {

    Decoder decoder = decoder(format, encodedData);
    return decoder.decode();
  }

  protected abstract Decoder decoder(BaseFormat format, byte[] input);

}