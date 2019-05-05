package net.sf.mmm.binary.api.codec;

/**
 * {@link Base64} is a encoding that has an alphabet with 64 letters. As all ASCII Latin letters and digits together
 * make 62 distinct characters this encoding requires some additional special ASCII characters (e.g. '+', '/', '-', or
 * '_'). As typical text selection of operating systems triggered by double clicking only marks alpha-numerical words
 * but stops at special characters a {@link Base64} encoded representation is not as user-friendly. Also typical
 * alphabets contain similar looking characters like 'O' and '0' or '1' and 'l'. However, still {@link Base64} is a very
 * common and especially efficient encoding.
 *
 * @since 1.0.0
 */
public final class Base64 extends BaseGeneric {

  /** Default instance for {@link Base64} according to RFC 4648 (Table 1). */
  public static final Base64 DEFAULT = new Base64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

  /** Instance for {@link Base64} for usage in URLs according to RFC 4648 (Table 2). */
  public static final Base64 URL = new Base64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_");

  private Base64(String alphabet) {

    super(alphabet, '=');
    assert (alphabet.length() == 64);
  }

  @Override
  public boolean isCaseSensitive() {

    return true;
  }

}
