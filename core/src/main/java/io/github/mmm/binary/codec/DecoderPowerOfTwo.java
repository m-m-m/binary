package io.github.mmm.binary.codec;

import java.util.Arrays;

/**
 * Simple {@link Decoder} for alphabet with length 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class DecoderPowerOfTwo extends Decoder {

  private final CoderConfigPowerOfTwo config;

  protected DecoderPowerOfTwo(BaseFormat format, byte[] encodedData, CoderConfigPowerOfTwo config) {

    super(format, encodedData, capactiy(format, encodedData, config));
    this.config = config;
  }

  private static int capactiy(BaseFormat format, byte[] encodedData, CoderConfigPowerOfTwo config) {

    int encodedLength = encodedData.length;
    int alphabetChars = encodedLength;
    if (!format.isFailOnWhitespace()) {
      alphabetChars = 0;
      for (byte b : encodedData) {
        int code = -1;
        if ((b > 0) && (b < 127)) {
          code = config.map[b];
        }
        if (code >= 0) {
          alphabetChars++;
        }
      }
    } else if (config.padding != BaseGeneric.NUL) {
      for (int i = encodedLength - 1; i > 0; i--) {
        if (encodedData[i] == config.padding) {
          alphabetChars--;
        }
      }
    }

    int capacity = (alphabetChars * config.bitConfig.bytesPerChunk) / config.bitConfig.charsPerChunk;
    if (capacity == 0) {
      capacity = encodedLength;
    }
    return capacity;
  }

  @Override
  protected byte[] decode() {

    int inputLength = this.input.length;
    int[] map = this.config.map;
    int bitCount = this.config.bitConfig.bitCount;
    int charsPerChunk = this.config.bitConfig.charsPerChunk;
    int bytesPerChunk = this.config.bitConfig.bytesPerChunk;
    int fullShift = bitCount * (charsPerChunk - 1);
    int shift = fullShift;
    boolean failOnWhitespace = this.format.failOnWhitespace;
    int paddingCount = 0;
    long bits = 0;

    int alphabetChars = inputLength;
    if (!this.format.isFailOnWhitespace()) {
      alphabetChars = 0;
      for (byte b : this.input) {
        int code = -1;
        if ((b > 0) && (b < 127)) {
          code = this.config.map[b];
        }
        if (code >= 0) {
          alphabetChars++;
        }
      }
    } else if (this.config.padding != BaseGeneric.NUL) {
      while (inputLength > 0) {
        if (this.input[inputLength - 1] != this.config.padding) {
          break;
        }
        inputLength--;
        alphabetChars--;
        paddingCount++;
      }
    }

    int outputLength = (alphabetChars * bytesPerChunk) / charsPerChunk;
    if ((outputLength == 0) && (inputLength > 0)) {
      throw new IllegalArgumentException("Invalid input length " + inputLength);
    }
    final byte[] output = new byte[outputLength];
    int outputIndex = 0;
    int inputIndex = 0;
    while (inputIndex < inputLength) {
      byte c = this.input[inputIndex++];
      int code = -1;
      if ((c > 0) && (c < 127)) {
        code = map[c];
      }
      if (code == -1) {
        Base.illegalCharacter((char) c, inputIndex - 1);
      } else if (code == CoderConfigMapped.PAD) {
        paddingCount++;
        while (inputIndex < inputLength) {
          byte next = this.input[inputIndex++];
          if (next != c) {
            BaseGeneric.illegalCharacter((char) next);
          }
          paddingCount++;
        }
        break;
      } else if (code == CoderConfigMapped.SPACE) {
        if (failOnWhitespace) {
          Base.illegalCharacter((char) c);
        }
      } else {
        bits |= ((long) code << shift);
        shift -= bitCount;
        if (shift < 0) {
          if (bytesPerChunk == 5) {
            output[outputIndex++] = (byte) (bits >> 32);
            output[outputIndex++] = (byte) (bits >> 24);
          }
          if (bytesPerChunk >= 3) {
            output[outputIndex++] = (byte) (bits >> 16);
            output[outputIndex++] = (byte) (bits >> 8);
          }
          output[outputIndex++] = (byte) (bits);
          bits = 0;
          shift = fullShift;
        }
      }
    }
    if (shift != fullShift) {
      int charsInChunk = (fullShift - shift) / bitCount;
      int remain = this.config.bitConfig.getBytes2Decode(charsInChunk);
      if (this.format.isFailOnMissingPadding()) {
        if ((remain + paddingCount) != charsPerChunk) {
          throw new IllegalArgumentException("Invalid padding " + paddingCount + " as " + (charsPerChunk - remain) + " was expected!");
        }
      }
      if (bytesPerChunk == 5) {
        output[outputIndex++] = (byte) (bits >> 32);
        remain--;
        if (remain > 0) {
          output[outputIndex++] = (byte) (bits >> 24);
          remain--;
        }
      }
      if ((bytesPerChunk >= 3) && (remain > 0)) {
        output[outputIndex++] = (byte) (bits >> 16);
        remain--;
        if (remain > 0) {
          output[outputIndex++] = (byte) (bits >> 8);
          remain--;
        }
      }
      assert (remain == 0);
    }
    if (outputIndex >= output.length) {
      return output;
    } else {
      // System.out.println("Decoder (2^x) Capacity is " + output.length + " but only " + outputIndex + " was required,
      // reserved "
      // + (output.length - outputIndex) + " too much!");
      return Arrays.copyOfRange(output, 0, outputIndex);
    }

  }

}