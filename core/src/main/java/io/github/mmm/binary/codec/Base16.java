/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * {@link Base16} is the common octal number encoding.
 *
 * @see Integer#toOctalString(int)
 * @since 1.0.0
 */
public final class Base16 extends BaseGeneric {

  /** Default instance for hexadecimal number encoding. */
  public static final Base16 DEFAULT = new Base16("0123456789abcdef");

  /** Default instance for hexadecimal number encoding (formatted in UPPER case). */
  public static final Base16 UPPER = new Base16("0123456789ABCDEF");

  private Base16(String alphabet) {

    super(alphabet);
    assert (alphabet.length() == 16);
  }
}
