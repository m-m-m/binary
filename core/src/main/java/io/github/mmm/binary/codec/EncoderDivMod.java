package io.github.mmm.binary.codec;

/**
 * Div-mod {@link Encoder} for alphabet with length other than 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class EncoderDivMod extends Encoder {

  private final CoderConfigDivMod config;

  public EncoderDivMod(BaseFormat format, byte[] data, CoderConfigDivMod config) {

    super(format, data);
    this.config = config;
  }
  //
  // private static int capacity(BaseFormat format, byte[] data, CoderConfigDivMod config) {
  //
  // // TODO
  // if (config.chars.length == 58) {
  // // return ((data.length * 8) / 11);
  // }
  // return data.length * 2;
  // }

  @Override
  protected String encode() {

    int leadingZeros = 0;
    while ((leadingZeros < this.input.length) && (this.input[leadingZeros] == 0)) {
      leadingZeros++;
    }

    byte[] in = new byte[this.input.length - leadingZeros];
    System.arraycopy(this.input, leadingZeros, in, 0, in.length);

    int inIndex = 0;

    char[] chars = this.config.chars;
    int alphabetLength = chars.length;
    int capacity;
    if (alphabetLength == 10) {
      capacity = (int) (2.41 * this.input.length) + 1;
    } else if (alphabetLength == 56) {
      capacity = (int) (1.377 * this.input.length) + 1;
    } else if (alphabetLength == 58) {
      capacity = (int) (1.367 * this.input.length) + 1;
    } else {
      capacity = this.input.length * 2;
      if (alphabetLength < 16) {
        capacity = capacity + this.input.length;
      }
    }
    final char[] output = new char[capacity];

    int outputStart = output.length;

    while (inIndex < in.length) {
      byte mod = divMod(in, inIndex, alphabetLength);
      if (in[inIndex] == 0) {
        inIndex++;
      }
      output[--outputStart] = chars[mod];
    }

    char zeroChar = chars[0];
    while (outputStart < output.length && output[outputStart] == zeroChar) {
      outputStart++;
    }

    for (int i = 0; i < leadingZeros; i++) {
      output[--outputStart] = zeroChar;
    }
    if (outputStart == 0) {
      return new String(output);
    } else {
      return new String(output, outputStart, output.length - outputStart);
    }
  }

  private static byte divMod(byte[] number, int offset, int base) {

    int remainder = 0;
    for (int i = offset; i < number.length; i++) {
      int digit = number[i] & 0xff;
      int temp = remainder * 256 + digit;
      number[i] = (byte) (temp / base);
      remainder = temp % base;
    }
    return (byte) remainder;
  }

}
