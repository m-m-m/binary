/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

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
