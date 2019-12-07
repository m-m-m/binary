package io.github.mmm.binary.codec;

import java.util.Arrays;

/**
 * {@link CoderConfigBase} with reverse map of alphabet.
 *
 * @since 1.0.0
 */
abstract class CoderConfigMapped extends CoderConfig {

  static final int PAD = -2;

  static final int SPACE = -3;

  private static final int[] EMPTY_MAP = new int[] { //
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, SPACE, -1, -1, SPACE, -1, -1, // 16
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 32
  SPACE, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 48
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 64
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 80
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 96
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 112
  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 // 127
  };

  final char[] chars;

  final int[] map;

  final char padding;

  private final boolean caseSensitive;

  /**
   * The constructor.
   *
   * @param alphabet the {@link Base#getAlphabet() alphabet}.
   * @param padding the padding character.
   */
  public CoderConfigMapped(String alphabet, char padding) {

    super();
    this.chars = alphabet.toCharArray();
    this.padding = padding;
    boolean lower = false;
    boolean upper = false;
    this.map = Arrays.copyOf(EMPTY_MAP, EMPTY_MAP.length);
    if (padding != BaseGeneric.NUL) {
      this.map[padding] = PAD;
    }
    for (byte i = 0; i < this.chars.length; i++) {
      char c = this.chars[i];
      if ((c < 20) || (c >= 127)) {
        Base.illegalCharacter(c, i);
      } else if ((c >= 'a') && (c <= 'z')) {
        lower = true;
      } else if ((c >= 'A') && (c <= 'Z')) {
        upper = true;
      }
      if (this.map[c] != -1) {
        if (this.map[c] >= 0) {
          BaseGeneric.illegalCharacter("Duplicate", c, i);
        } else {
          BaseGeneric.illegalCharacter(c, i);
        }
      }
      this.map[c] = i;
    }
    this.caseSensitive = (lower && upper);
    if (!this.caseSensitive) {
      byte i = 0;
      for (char c : this.chars) {
        if ((c >= 'a') && (c <= 'z')) {
          c = Character.toUpperCase(c);
        } else if ((c >= 'A') && (c <= 'Z')) {
          c = Character.toLowerCase(c);
        }
        this.map[c] = i++;
      }
    }
  }

  @Override
  protected boolean isCaseSensitive() {

    return this.caseSensitive;
  }

}
