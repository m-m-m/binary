package io.github.mmm.binary.codec;

import java.util.Objects;

/**
 * Format for {@link Base}.
 *
 * @since 1.0.0
 * @see Base#withFormat(BaseFormat)
 */
public class BaseFormat {

  private static final String CRLF = "\r\n";

  private static final char NUL = '\0';

  static final BaseFormat DEFAULT = new BaseFormat(null, 0, false, null);

  static final BaseFormat MIME = new BaseFormat(CRLF, 76, false, null);

  static final BaseFormat STRICT = new BaseFormat(null, 0, true, Boolean.TRUE);

  final String newline;

  final int charsPerLine;

  final boolean failOnWhitespace;

  final Boolean padding;

  private final char newline1;

  private final char newline2;

  private final int newlineLength;

  /**
   * The constructor.
   *
   * @param newline - see {@link #getNewline()}.
   * @param charsPerLine - see {@link #getCharsPerLine()}.
   * @param failOnWhitespace - see {@link #isFailOnMissingPadding()}
   * @param padding - see {@link #isFailOnMissingPadding()} and {@link #isOmitPadding()}.
   */
  protected BaseFormat(String newline, int charsPerLine, boolean failOnWhitespace, Boolean padding) {

    super();
    char nl1 = NUL;
    char nl2 = NUL;
    if (newline == null) {
      assert (charsPerLine == 0);
      this.newline = null;
      this.charsPerLine = 0;
      this.newlineLength = 0;
    } else {
      if (charsPerLine <= 1) {
        throw new IllegalArgumentException("charsPerLine=" + charsPerLine);
      }
      assert (charsPerLine > 10);
      this.newline = newline;
      this.charsPerLine = charsPerLine;
      this.newlineLength = newline.length();
      if (this.newlineLength == 1) {
        nl1 = newline.charAt(0);
      } else if (this.newlineLength == 2) {
        nl1 = newline.charAt(0);
        nl2 = newline.charAt(1);
      }
    }
    this.newline1 = nl1;
    this.newline2 = nl2;
    this.failOnWhitespace = failOnWhitespace;
    this.padding = padding;
  }

  /**
   * @return newline the newline sequence (e.g. "\r\n") or {@code null} for none (encoded {@link String} is a single
   *         line).
   */
  public String getNewline() {

    return this.newline;
  }

  /**
   * @return the number of characters until another {@link #getNewline() newline} will be appended. Will be {@code 0} if
   *         {@link #getNewline() newline} is {@code null}.
   */
  public int getCharsPerLine() {

    return this.charsPerLine;
  }

  /**
   * @return {@code true} to fail on whitespace (' ', '\r', or '\n'), {@code false} to silently ignore whitespaces.
   */
  public boolean isFailOnWhitespace() {

    return this.failOnWhitespace;
  }

  /**
   * @return {@code true} to fail if padding (e.g. on {@link Base64}) is omitted where applicable, {@code false} to
   *         silently ignore missing padding.
   */
  public boolean isFailOnMissingPadding() {

    return Boolean.TRUE.equals(this.padding);
  }

  /**
   * @return {@code true} if padding should be omitted, {@code false} otherwise (add padding where applicable).
   */
  public boolean isOmitPadding() {

    return Boolean.FALSE.equals(this.padding);
  }

  /**
   * @param chars the char-array to append the newline to. Has to have a remaining capacity of at least the
   *        {@link String#length() length} of {@link #getNewline() newline}.
   * @param index the offset in {@code chars} where to append.
   */
  void appendNewline(char[] chars, int index) {

    if (this.newlineLength > 0) {
      if (this.newlineLength > 2) {
        for (int i = 0; i < this.newlineLength; i++) {
          chars[index + i] = this.newline.charAt(i);
        }
      } else {
        chars[index] = this.newline1;
        if (this.newlineLength == 2) {
          chars[index + 1] = this.newline2;
        }
      }
    }
  }

  /**
   * @return the default {@link BaseFormat} (tolerant, no {@link #getNewline() newline} on encoding).
   */
  public static BaseFormat ofDefault() {

    return DEFAULT;
  }

  /**
   * @return instance of {@link BaseFormat} with MIME compatible {@link #getNewline() newline} settings for encoding.
   */
  public static BaseFormat ofMime() {

    return MIME;
  }

  /**
   * @return instance of {@link BaseFormat} with strict decoding and without {@link #getNewline() newlines} on encoding.
   */
  public static BaseFormat ofStrict() {

    return STRICT;
  }

  /**
   * @param newline the {@link #getNewline() newline} sequence (e.g. "\n", "\r\n").
   * @param charsPerLine the number of characters per line until a {@code newline} is appended.
   * @return instance of {@link BaseFormat} for the given arguments.
   */
  public static BaseFormat ofNewline(String newline, int charsPerLine) {

    Objects.requireNonNull(newline, "newline");
    if (!newline.trim().isEmpty()) {
      throw new IllegalArgumentException("Illegal newline '" + newline + "' - shall only contain whitespace characters.");
    }
    return new BaseFormat(newline, charsPerLine, true, null);
  }

}
