package io.github.mmm.binary.codec;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link Base58}.
 */
public class Base58Test extends AbstractBaseTest {

  /** Test of {@link Base58#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base58 base58 = Base58.DEFAULT;

    // when+then
    checkHex(base58, "000111d38e5fc9071ffcd20b4a763cc9ae4f252bb4e48fd66a835e252ada93ff480d6dd43dc62a641155a5",
        base58.getAlphabet());
    checkHex(base58, "00000000", "1111");
    // https://github.com/stephen-hill/base58php
    checkText(base58, "Hello World", "JxF12TrwUP45BMd");
    check(base58);
  }

  /** Test of {@link Base58#URL}. */
  @Test
  public void testUrl() {

    // given
    Base58 base58 = Base58.URL;

    // when+then
    // https://github.com/stephen-hill/base58php
    checkText(base58, "Hello World", "iXf12sRWto45bmC");
    checkHex(base58, "000111d38e5fc9071ffcd20b4a763cc9ae4f252bb4e48fd66a835e252ada93ff480d6dd43dc62a641155a5",
        base58.getAlphabet());
    check(base58);
  }

}
