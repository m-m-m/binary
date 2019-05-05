package net.sf.mmm.util.binary.api.codec;

import org.junit.Test;

/**
 * Test of {@link Base32}.
 */
public class Base32Test extends AbstractBaseTest {

  /** Test of {@link Base32#DEFAULT}. */
  @Test
  public void testDefault() {

    Base32 base32 = Base32.DEFAULT;
    checkText(base32, "Hello World!", /**/ "JBSWY3DPEBLW64TMMQQQ====");
    checkText(base32, "Hello World", /* */ "JBSWY3DPEBLW64TMMQ======");
    checkText(base32, "Hello Worl", /*  */ "JBSWY3DPEBLW64TM");
    checkText(base32, "Hello Wor", /*   */ "JBSWY3DPEBLW64Q=");
    checkText(base32, "Hello Wo", /*    */ "JBSWY3DPEBLW6===");
    checkText(base32, "Hello W", /*     */ "JBSWY3DPEBLQ====");
    checkText(base32, "Hello ", /*      */ "JBSWY3DPEA======");
    checkText(base32, "Hello", /*       */ "JBSWY3DP");
    check(base32);
  }

}
