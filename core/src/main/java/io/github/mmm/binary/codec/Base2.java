/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

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
