package net.sf.mmm.binary.api.codec;

import net.sf.mmm.binary.api.codec.Base16;

import org.junit.Test;

/**
 * Test of {@link Base16}.
 */
public class Base16Test extends AbstractBaseTest {

  /** Test of {@link Base16#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base16 base16 = Base16.DEFAULT;

    // when+then
    assertThat(base16.encodeShort((short) 0x7f80)).isEqualTo("7f80");
    assertThat(base16.decodeShort("7f80")).isEqualTo((short) 0x7f80);
    assertThat(base16.decodeShort("ACEF")).isEqualTo((short) 0xacef);
    assertThat(base16.encodeInt(0xf7e6d5c4)).isEqualTo("f7e6d5c4");
    assertThat(base16.decodeInt("f7e6d5c4")).isEqualTo(0xf7e6d5c4);
    assertThat(base16.encodeLong(0x08192a3b4c5d6e7fL)).isEqualTo("08192a3b4c5d6e7f");
    assertThat(base16.decodeLong("08192a3b4c5d6e7f")).isEqualTo(0x08192a3b4c5d6e7fL);
    check(base16);
  }

}
