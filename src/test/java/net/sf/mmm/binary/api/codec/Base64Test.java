package net.sf.mmm.binary.api.codec;

import net.sf.mmm.binary.api.codec.Base64;
import net.sf.mmm.binary.api.codec.BaseFormat;
import net.sf.mmm.binary.api.codec.BinaryCodec;

import org.junit.Test;

/**
 * Test of {@link Base64}.
 */
public class Base64Test extends AbstractBaseTest {

  /** Test of {@link Base64#DEFAULT}. */
  @Test
  public void testDefault() {

    // given
    Base64 base64 = Base64.DEFAULT;

    // when+then
    // https://en.wikipedia.org/wiki/Base64#Examples
    checkText(base64, "Man is distinguished, not only by his reason, but by this singular passion from other animals, " + //
        "which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable " + //
        "generation of knowledge, exceeds the short vehemence of any carnal pleasure.",
        "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz" + //
            "IHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2Yg" + //
            "dGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGlu" + //
            "dWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRo" + //
            "ZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=");
    checkText(base64, "any carnal pleasure.", "YW55IGNhcm5hbCBwbGVhc3VyZS4=");
    checkText(base64, "any carnal pleasure", "YW55IGNhcm5hbCBwbGVhc3VyZQ==");
    checkText(base64, "any carnal pleasur", "YW55IGNhcm5hbCBwbGVhc3Vy");
    checkText(base64, "any carnal pleasu", "YW55IGNhcm5hbCBwbGVhc3U=");
    checkText(base64, "any carnal pleas", "YW55IGNhcm5hbCBwbGVhcw==");
    check(base64);
  }

  /** Test of {@link Base64#URL}. */
  @Test
  public void testUrl() {

    // given
    Base64 base64 = Base64.URL;

    // when+then
    byte[] data = new byte[] { 3, -32, 127 };
    String data64 = "A-B_";
    assertThat(base64.encode(data)).isEqualTo(data64);
    assertThat(base64.decode(data64)).isEqualTo(data);
    check(base64);
  }

  /** Test of {@link Base64#DEFAULT} with mime encoding. */
  @Test
  public void testMime() {

    BinaryCodec codec = Base64.DEFAULT.withFormat(BaseFormat.ofMime());
    checkText(codec, "Man is distinguished, not only by his reason, but by this singular passion from other animals, " + //
        "which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable " + //
        "generation of knowledge, exceeds the short vehemence of any carnal pleasure.",
        "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz\r\n" + //
            "IHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2Yg\r\n" + //
            "dGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGlu\r\n" + //
            "dWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRo\r\n" + //
            "ZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=");
    check(codec);
  }

}
