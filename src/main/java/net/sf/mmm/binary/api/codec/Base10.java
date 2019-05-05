package net.sf.mmm.binary.api.codec;

/**
 * {@link Base10} is the common decimal number encoding.
 *
 * @see Integer#toString(int)
 * @since 1.0.0
 */
public final class Base10 extends BaseGeneric {

  /** Default instance for decimal number encoding. */
  public static final Base10 DEFAULT = new Base10();

  private Base10() {

    super("0123456789");
  }
}
