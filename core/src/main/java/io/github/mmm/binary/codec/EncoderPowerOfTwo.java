/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary.codec;

/**
 * Simple {@link Encoder} for alphabet with length 2^x.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
class EncoderPowerOfTwo extends Encoder {

  private final CoderConfigPowerOfTwo config;

  protected EncoderPowerOfTwo(BaseFormat format, byte[] data, CoderConfigPowerOfTwo config) {

    super(format, data);
    this.config = config;
  }

  private static int capacity(BaseFormat format, byte[] data, CoderConfigPowerOfTwo config) {

    int dataLength = data.length;
    int mod = dataLength % config.bitConfig.bytesPerChunk;
    int capacity = ((dataLength - mod) * config.bitConfig.charsPerChunk) / config.bitConfig.bytesPerChunk;
    if (mod > 0) {
      boolean doPadding = !format.isOmitPadding() || (config.padding != BaseGeneric.NUL);
      if (doPadding) {
        capacity = capacity + config.bitConfig.charsPerChunk;
      } else {
        capacity = capacity + mod + 1;
      }
    }
    String newline = format.newline;
    if (newline != null) {
      int lines = (capacity - 1) / format.charsPerLine;
      capacity = capacity + (lines * newline.length());
    }
    return capacity;
  }

  @Override
  protected String encode() {

    final char[] chars = this.config.chars;
    final int mask = this.config.bitConfig.mask;
    final int bitCount = this.config.bitConfig.bitCount;
    final int charsPerChunk = this.config.bitConfig.charsPerChunk;
    final int bytesPerChunk = this.config.bitConfig.bytesPerChunk;
    final char padding = this.config.padding;
    final boolean omitPadding = this.format.isOmitPadding() || (padding == BaseGeneric.NUL);
    final int charsPerLine = this.format.charsPerLine;
    final String newline = this.format.newline;
    int newlineLength = 0;
    char newline1 = BaseGeneric.NUL;
    char newline2 = BaseGeneric.NUL;
    if (newline != null) {
      newlineLength = newline.length();
      newline1 = newline.charAt(0);
      if (newlineLength > 1) {
        newline2 = newline.charAt(1);
      }
    }
    int paddingModulo = this.input.length % bytesPerChunk;
    int maxInputIndex = this.input.length - paddingModulo;
    int chunkOffset = charsPerChunk - 1;
    int currentCharLineCount = -charsPerChunk;

    final char[] output = new char[capacity(this.format, this.input, this.config)];
    int outputIndex = 0;
    int inputIndex = 0;
    while (inputIndex < this.input.length) {
      if (newline != null) {
        currentCharLineCount = currentCharLineCount + charsPerChunk;
        if (currentCharLineCount >= charsPerLine) {
          output[outputIndex++] = newline1;
          if (newlineLength > 1) {
            output[outputIndex++] = newline2;
            for (int i = 2; i < newlineLength; i++) {
              output[outputIndex++] = newline.charAt(i);
            }
          }
          currentCharLineCount = 0;
        }
      }

      long bits = 0;
      switch (bytesPerChunk) {
        case 1:
          bits = ((long) this.input[inputIndex++] & 0xff);
          break;
        case 3:
          if (inputIndex == maxInputIndex) {
            bits = ((long) this.input[inputIndex++] & 0xff);
            if (inputIndex < this.input.length) {
              bits = (bits << 8) | ((long) this.input[inputIndex++] & 0xff);
            }
            bits <<= this.config.bitConfig.getShift4Encode(paddingModulo);
            chunkOffset = this.config.bitConfig.getChunkChars4Encode(paddingModulo) - 1;
          } else {
            bits = ((this.input[inputIndex++] & 0xff) << 16) | ((this.input[inputIndex++] & 0xff) << 8)
                | (this.input[inputIndex++] & 0xff);
          }
          break;
        case 5:
          if (inputIndex == maxInputIndex) {
            bits = ((long) this.input[inputIndex++] & 0xff);
            while (inputIndex < this.input.length) {
              bits = (bits << 8) | ((long) this.input[inputIndex++] & 0xff);
            }
            bits <<= this.config.bitConfig.getShift4Encode(paddingModulo);
            chunkOffset = this.config.bitConfig.getChunkChars4Encode(paddingModulo) - 1;
          } else {
            bits = (((long) this.input[inputIndex++] & 0xff) << 32) | (((long) this.input[inputIndex++] & 0xff) << 24)
                | (((long) this.input[inputIndex++] & 0xff) << 16) | (((long) this.input[inputIndex++] & 0xff) << 8)
                | ((long) this.input[inputIndex++] & 0xff);
          }
          break;
        default:
          throw new IllegalStateException();
      }

      for (int offset = chunkOffset; offset >= 0; offset--) {
        int index = (int) bits & mask;
        output[outputIndex + offset] = chars[index];
        bits >>>= bitCount;
      }
      outputIndex = outputIndex + chunkOffset + 1;
    }
    if ((paddingModulo > 0) && !omitPadding) {
      for (int i = charsPerChunk - chunkOffset - 1; i > 0; i--) {
        output[outputIndex++] = padding;
      }
    }
    if (outputIndex == output.length) {
      return new String(output);
    } else {
      // System.out.println("Encoder (2^x) Capacity is " + output.length + " but only " + outputIndex + " was required,
      // reserved "
      // + (output.length - outputIndex) + " too much!");
      return new String(output, 0, outputIndex);
    }

  }
}