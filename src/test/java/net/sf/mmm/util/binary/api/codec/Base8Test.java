package net.sf.mmm.util.binary.api.codec;

import org.junit.Test;

/**
 * Test of {@link Base8}.
 */
public class Base8Test extends AbstractBaseTest {

  /** Test of {@link Base8#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base8 base8 = Base8.DEFAULT;

    // when+then
    check(base8, new byte[] { -1 }, "776=====");
    checkText(base8, "Hello World!", /**/ "22062554330674402566756233062041");
    checkText(base8, "Hello World", /* */ "220625543306744025667562330620==");
    checkText(base8, "Hello Worl", /*  */ "220625543306744025667562330=====");
    checkText(base8, "Hello Wor", /*   */ "220625543306744025667562");
    checkText(base8, "Hello Wo", /*    */ "2206255433067440256674==");
    checkText(base8, "Hello W", /*     */ "2206255433067440256=====");
    checkText(base8, "Hello ", /*      */ "2206255433067440");
    checkText(base8, "Hello", /*       */ "22062554330674==");
    check(base8);
  }

}
