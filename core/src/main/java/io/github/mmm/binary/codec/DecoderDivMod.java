package io.github.mmm.binary.codec;

import java.util.Arrays;

/**
 * Simple {@link Decoder} for alphabet with length other than 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class DecoderDivMod extends Decoder {

  private final CoderConfigDivMod config;

  protected DecoderDivMod(BaseFormat format, byte[] encodedData, CoderConfigDivMod config) {

    super(format, encodedData, capactiy(format, encodedData, config));
    this.config = config;
  }

  private static int capactiy(BaseFormat format, byte[] encodedData, CoderConfigDivMod config) {

    // TODO
    return encodedData.length;
  }

  @Override
  protected byte[] decode() {

    int[] map = this.config.map;
    byte[] in = new byte[this.input.length];
    int inIndex = 0;
    for (int inputIndex = 0; inputIndex < in.length; inputIndex++) {
      byte c = this.input[inputIndex];
      int code = -1;
      if ((c > 0) && (c < 127)) {
        code = map[c];
      }
      if (code == CoderConfigMapped.SPACE) {
        // ignore spaces
      } else if (code < 0) {
        BaseGeneric.illegalCharacter((char) c, inputIndex);
      } else {
        in[inIndex++] = (byte) code;
      }
    }

    int zeroCount = 0;
    while (zeroCount < inIndex && in[zeroCount] == 0) {
      zeroCount++;
    }

    byte[] output = new byte[inIndex];
    int outputIndex = output.length;
    int offset = zeroCount;
    while (offset < inIndex) {
      byte mod = divMod(in, offset, this.config.chars.length);
      if (in[offset] == 0) {
        offset++;
      }
      output[--outputIndex] = mod;
    }

    while (outputIndex < output.length && output[outputIndex] == 0) {
      outputIndex++;
    }
    int outputStart = outputIndex - zeroCount;
    if (outputStart == 0) {
      return output;
    } else {
      return Arrays.copyOfRange(output, outputStart, output.length);
    }
  }

  private static byte divMod(byte[] number, int offset, int modulo) {

    int remainder = 0;
    for (int i = offset; i < number.length; i++) {
      int digit = number[i] & 0xff;
      int temp = remainder * modulo + digit;
      number[i] = (byte) (temp / 256);
      remainder = temp % 256;
    }
    return (byte) remainder;
  }

}