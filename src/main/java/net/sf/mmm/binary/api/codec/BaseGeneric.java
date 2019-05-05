package net.sf.mmm.binary.api.codec;

/**
 * Implementation of {@link BinaryCodec} based on {@link #getAlphabet() alphabet} of unique and visible ASCII
 * characters.
 *
 * @since 1.0.0
 */
public class BaseGeneric extends Base {

  static final char NUL = '\0';

  private final CoderConfig config;

  /**
   * The constructor.
   *
   * @param alphabet the {@link #getAlphabet() alphabet}.
   */
  public BaseGeneric(String alphabet) {

    this(alphabet, '=');
  }

  /**
   * The constructor.
   *
   * @param alphabet the {@link #getAlphabet() alphabet}.
   * @param paddingChar the padding character or {@code -1} for no padding.
   */
  public BaseGeneric(String alphabet, char paddingChar) {

    super(alphabet);
    this.config = createConfig(alphabet, paddingChar);
  }

  /**
   * @param alphabet the {@link #getAlphabet() alphabet}.
   * @param paddingChar the optional padding character.
   * @return the CoderConfig instance.
   */
  protected CoderConfig createConfig(String alphabet, char paddingChar) {

    int length = alphabet.length();
    if ((length < 2) || (length > 107)) {
      throw new IllegalArgumentException(alphabet);
    }
    BitConfig bitConfig = BitConfig.of(length);
    if (bitConfig == null) {
      return new CoderConfigDivMod(alphabet);
    } else {
      return new CoderConfigPowerOfTwo(alphabet, bitConfig, paddingChar);
    }
  }

  @Override
  public boolean isCaseSensitive() {

    return this.config.isCaseSensitive();
  }

  @Override
  protected String doEncode(byte[] data, BaseFormat format) {

    return this.config.encode(data, format);
  }

  @Override
  protected byte[] doDecode(String encodedData, BaseFormat format) {

    return this.config.decode(encodedData, format);
  }

}
