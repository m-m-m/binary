/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

import java.util.Objects;

import io.github.mmm.binary.Binary;

/// Implementation of [BinaryCodec] based on [alphabet][#getAlphabet()] of unique and visible ASCII
/// characters.
///
/// @since 1.0.0
public abstract class Base implements BinaryCodec {

  private final String alphabet;

  /// The constructor.
  ///
  /// @param alphabet the [alphabet][#getAlphabet()].
  public Base(String alphabet) {

    super();
    Objects.requireNonNull(alphabet, "alphabet");
    this.alphabet = alphabet;
  }

  /// @return the alphabet of characters used to encode/decode the data. It shall only contain visible and regular ASCII
  ///         characters and no duplicates. It is expected that characters are in a logical order (e.g. "0123456789" or
  ///         "abcdef" rather than "5192" or "xazb").
  public String getAlphabet() {

    return this.alphabet;
  }

  /// @return `true` if case-sensitive (e.g. both 'a' and 'A' are used with different meanings), `false`
  ///         otherwise (the case of Latin Ascii letters will be ignored).
  public abstract boolean isCaseSensitive();

  @Override
  public final String encode(byte[] data) {

    if (data == null) {
      return null;
    }
    if (data.length == 0) {
      return "";
    }
    return doEncode(data, BaseFormat.DEFAULT);
  }

  /// @param data the binary data to encode. Neither `null` nor empty.
  /// @param format the [BaseFormat] to configure the encoding.
  /// @return the given `data` encoded as [String].
  protected abstract String doEncode(byte[] data, BaseFormat format);

  @Override
  public final byte[] decode(String encodedData) {

    if (encodedData == null) {
      return null;
    }
    if (encodedData.isEmpty()) {
      return Binary.EMPTY_BYTE_ARRAY;
    }
    return doDecode(encodedData, BaseFormat.DEFAULT);
  }

  /// @param encodedData the [encoded][#encode(byte[])] [String]. Neither `null` nor
  ///        [empty][String#isEmpty()].
  /// @param format the [BaseFormat] to configure the decoding.
  /// @return the decoded data as `byte` array.
  protected abstract byte[] doDecode(String encodedData, BaseFormat format);

  /// @param c the illegal character.
  /// @return nothing. Will always throw [IllegalArgumentException]. Return type may be used to call after
  ///         `throw` statement to ensure program flow can not continue and compiler issues can be prevented.
  static RuntimeException illegalCharacter(char c) {

    return illegalCharacter(c, -1);
  }

  /// @param c the illegal character.
  /// @return nothing. Will always throw [IllegalArgumentException]. Return type may be used to call after
  ///         `throw` statement to ensure program flow can not continue and compiler issues can be prevented.
  static RuntimeException illegalCharacter(char c, int index) {

    return illegalCharacter("Illegal", c, index);
  }

  /// @param c the illegal character.
  /// @return nothing. Will always throw [IllegalArgumentException]. Return type may be used to call after
  ///         `throw` statement to ensure program flow can not continue and compiler issues can be prevented.
  static RuntimeException illegalCharacter(String errorType, char c, int index) {

    StringBuilder sb = new StringBuilder(40);
    sb.append(errorType);
    sb.append(" character '");
    sb.append(c);
    sb.append("' (#");
    sb.append(Integer.toHexString(c));
    sb.append(')');
    if (index >= 0) {
      sb.append(" at index ");
      sb.append(index);
    }
    sb.append('!');
    throw new IllegalArgumentException(sb.toString());
  }

  /// @param format the [BaseFormat].
  /// @return the new [BinaryCodec] with the given [BaseFormat] applied.
  public BinaryCodec withFormat(BaseFormat format) {

    return new BaseFormatCodec(this, format);
  }

  @Override
  public String toString() {

    return "Base" + this.alphabet.length() + "[" + this.alphabet + "]";
  }

}
