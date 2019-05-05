package net.sf.mmm.util.binary.api.codec;

import org.junit.Test;

/**
 * Test of {@link Base2}.
 */
public class Base2Test extends AbstractBaseTest {

  /** Test of {@link Base2#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base2 base2 = Base2.DEFAULT;

    // when+then
    assertThat(base2.encodeShort((short) 0b0111111111111111)).isEqualTo("0111111111111111");
    assertThat(base2.decodeShort("0111111111111111")).isEqualTo((short) 0b0111111111111111);
    assertThat(base2.encodeShort((short) 0b1000000000000000)).isEqualTo("1000000000000000");
    assertThat(base2.decodeShort("1000000000000000")).isEqualTo((short) 0b1000000000000000);
    assertThat(base2.encodeInt(-2)).isEqualTo("11111111111111111111111111111110");
    assertThat(base2.decodeInt("11111111111111111111111111111110")).isEqualTo(-2);
    check(base2, new byte[] { (byte) 0b10101010, 0b01010101, -1, 0 }, "10101010010101011111111100000000");
    check(base2);
  }

}
