/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * {@link Base32} is a encoding that has an alphabet with 32 letters. Typical alphabets shall only use Latin letters and
 * digits. It such case representations can be selected with a double click. However, the padding character '=' will not
 * be selected.
 *
 * @see Base58
 * @since 1.0.0
 */
public final class Base32 extends BaseGeneric {

  /** Default instance for {@link Base32} according to RFC 4648 (Table 1). */
  public static final Base32 DEFAULT = new Base32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", '=');

  /** Instance for {@link Base32} for z-base-32. */
  public static final Base32 ZB32 = new Base32("ybndrfg8ejkmcpqxot1uwisza345h769", NUL);

  private Base32(String alphabet, char padding) {

    super(alphabet, padding);
    assert (alphabet.length() == 32);
  }

}
