package net.sf.mmm.binary.api.codec;

import net.sf.mmm.binary.api.codec.Base10;
import net.sf.mmm.binary.api.codec.Base16;

import org.junit.Test;

/**
 * Test of {@link Base10}.
 */
public class Base10Test extends AbstractBaseTest {

  /** Test of {@link Base16#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base10 base10 = Base10.DEFAULT;

    // when+then
    assertThat(base10.encodeShort((short) 1234)).isEqualTo("1234");
    assertThat(base10.decodeShort("1234")).isEqualTo((short) 1234);
    assertThat(base10.encodeInt(1234567890)).isEqualTo("1234567890");
    assertThat(base10.decodeInt("1234567890")).isEqualTo(1234567890);
    assertThat(base10.encodeLong(1234567890123456789L)).isEqualTo("1234567890123456789");
    assertThat(base10.decodeLong("1234567890123456789")).isEqualTo(1234567890123456789L);
    assertThat(base10.encodeInt(12345678)).isEqualTo("012345678");
    assertThat(base10.decodeInt("012345678")).isEqualTo(12345678);
    assertThat(base10.decodeInt("12345678")).isEqualTo(12345678);
    // please note that leading zeros represent zero bytes and not zero digits
    assertThat(base10.encodeLong(9L)).isEqualTo("00000009");
    assertThat(base10.decodeLong("9")).isEqualTo(9L);
    check(base10);
  }

}
