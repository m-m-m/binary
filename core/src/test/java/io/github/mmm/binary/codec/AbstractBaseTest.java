package io.github.mmm.binary.codec;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.assertj.core.api.Assertions;

import io.github.mmm.binary.BinaryType;
import io.github.mmm.binary.codec.Base;
import io.github.mmm.binary.codec.BinaryCodec;

/**
 * Abstract base class for tests of {@link Base}.
 */
public class AbstractBaseTest extends Assertions {

  void check(BinaryCodec codec) {

    if (codec instanceof Base) {
      Base base = (Base) codec;
      String name = codec.getClass().getSimpleName();
      assertThat(name.startsWith("Base"));
      String suffix = name.substring(4);
      if (!"Generic".equals(suffix)) {
        int basis = Integer.parseInt(suffix);
        assertThat(base.getAlphabet().length()).isEqualTo(basis);
      }
    }
    try {
      SecureRandom random = SecureRandom.getInstanceStrong();
      int length = random.nextInt(4096);
      byte[] data = new byte[length];
      random.nextBytes(data);
      String encoded = codec.encode(data);
      byte[] decoded = codec.decode(encoded);
      assertThat(decoded).as(codec.toString() + " encoded as " + encoded).isEqualTo(data);
      assertThat(codec.decodeInt(codec.encodeInt(length))).isEqualTo(length);
      long l = random.nextLong();
      assertThat(codec.decodeLong(codec.encodeLong(l))).isEqualTo(l);
      short s = (short) l;
      assertThat(codec.decodeShort(codec.encodeShort(s))).isEqualTo(s);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  void checkHex(Base base, String hexData, String encodedData) {

    byte[] data = BinaryType.parseHex(hexData);
    assertThat(base.encode(data)).isEqualTo(encodedData);
    assertThat(BinaryType.formatHex(base.decode(encodedData))).isEqualTo(hexData);
    if (!base.isCaseSensitive()) {
      assertThat(BinaryType.formatHex(base.decode(encodedData.toLowerCase()))).isEqualTo(hexData);
      assertThat(BinaryType.formatHex(base.decode(encodedData.toUpperCase()))).isEqualTo(hexData);
    }
  }

  void checkText(BinaryCodec codec, String text, String encodedData) {

    byte[] data = text.getBytes(StandardCharsets.UTF_8);
    assertThat(codec.encode(data)).isEqualTo(encodedData);
    assertThat(new String(codec.decode(encodedData), StandardCharsets.UTF_8)).isEqualTo(text);
  }

  void checkText(Base base, String text, String encodedData) {

    checkText((BinaryCodec) base, text, encodedData);
    if (!base.isCaseSensitive()) {
      String lower = encodedData.toLowerCase();
      if (!lower.equals(encodedData)) {
        assertThat(new String(base.decode(lower), StandardCharsets.UTF_8)).isEqualTo(text);
      }
      String upper = encodedData.toUpperCase();
      if (!upper.equals(encodedData)) {
        assertThat(new String(base.decode(upper), StandardCharsets.UTF_8)).isEqualTo(text);
      }
    }
  }

  void check(Base base, byte[] data, String encodedData) {

    assertThat(base.encode(data)).isEqualTo(encodedData);
    String hexData = BinaryType.formatHex(data);
    assertThat(BinaryType.formatHex(base.decode(encodedData))).isEqualTo(hexData);
    if (!base.isCaseSensitive()) {
      assertThat(BinaryType.formatHex(base.decode(encodedData.toLowerCase()))).isEqualTo(hexData);
      assertThat(BinaryType.formatHex(base.decode(encodedData.toUpperCase()))).isEqualTo(hexData);
    }
  }

}
