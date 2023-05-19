/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * {@link Base8} is the common octal number encoding.
 *
 * @see Integer#toOctalString(int)
 * @since 1.0.0
 */
public final class Base8 extends BaseGeneric {

  /** Default instance for ocatal number encoding. */
  public static final Base8 DEFAULT = new Base8();

  private Base8() {

    super("01234567");
  }
}
