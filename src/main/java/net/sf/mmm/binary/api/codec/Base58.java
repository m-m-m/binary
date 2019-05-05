package net.sf.mmm.binary.api.codec;

/**
 * {@link Base58} is a encoding that has an alphabet with 58 letters. As 58 is not a base of 2 (like 32 or 64) this
 * encoding is based on the div-mod algorithm (big integer arithmetic). Therefore encoding and decoding is not as
 * efficient as with e.g. {@link Base64}. Therefore, it should not be used for very large binary data but is fine for
 * hashes, IDs, etc. {@link Base58} is typically a good compromise of usability and storage-efficiency:
 * <ul>
 * <li>like {@link Base32} it typically uses alphabets that only contain Latin letters and digits and can also avoid
 * similar characters (like '1' and 'l' as well as 'O' and '0'). Therefore encoded representations can be selected with
 * a simple double-click and if you have to manually read and type them there are no ambiguous or very similar looking
 * characters.</li>
 * <li>its overhead in length of the encoded representation compared to the original binary data is better than
 * {@link Base32} (that also has the above benefits) but only a little worse than {@link Base64}.</li>
 * </ul>
 *
 * @since 1.0.0
 */
public final class Base58 extends BaseGeneric {

  /** Default instance of {@link Base58} used in crypto systems such as Bitcoin or IPFS. */
  public static final Base58 DEFAULT = new Base58("123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz");

  /** Instance of {@link Base58} used for shortened URLs (e.g. Flickr). */
  public static final Base58 URL = new Base58("123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ");

  private Base58(String chars) {

    super(chars);
    assert (chars.length() == 58);
  }

}
