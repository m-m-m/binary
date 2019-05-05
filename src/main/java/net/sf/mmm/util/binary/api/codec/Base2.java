package net.sf.mmm.util.binary.api.codec;

/**
 * {@link Base2} is the common binary number encoding.
 *
 * @since 1.0.0
 */
public final class Base2 extends BaseGeneric {

  /** Default instance for binary number encoding. */
  public static final Base2 DEFAULT = new Base2();

  private Base2() {

    super("01");
  }

}
