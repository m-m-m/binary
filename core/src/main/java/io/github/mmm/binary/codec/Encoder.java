/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * Stateful class to {@link Base#encode(byte[]) encode}.
 *
 * @since 1.0.0
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