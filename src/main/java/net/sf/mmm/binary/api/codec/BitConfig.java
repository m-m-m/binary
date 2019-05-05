package net.sf.mmm.binary.api.codec;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
class BitConfig {

  private static final BitConfig[] CONFIGS = new BitConfig[] {
  // Base2 : 1bit -> 8 chars (binary)
  new BitConfig(1, 1, 8),
  // Base4 : 2bit -> 4 chars
  new BitConfig(2, 1, 4),
  // Base8 : 3bit -> 8 chars with max padding 2 (octal)
  new BitConfig(3, 3, 8, new int[] { 0, 0, 1, 0, 0, 2 }, new int[] { 1, 2 }, new int[] { 3, 6 }),
  // Base16: 4bit -> 2 chars (hex)
  new BitConfig(4, 1, 2),
  // Base32: 5bit -> 2 chars with max padding 4
  new BitConfig(5, 5, 8, new int[] { 0, 1, 0, 2, 3, 0, 4 }, new int[] { 2, 4, 1, 3 }, new int[] { 2, 4, 5, 7 }),
  // Base64: 6bit -> 2 chars with max padding 2
  new BitConfig(6, 3, 4, new int[] { 0, 1, 2, 3 }, new int[] { 4, 2 }, new int[] { 2, 3 }) //
  };

  final int length;

  final int bitCount;

  final int mask;

  final int bytesPerChunk;

  final int charsPerChunk;

  private final int[] chars2decode;

  private final int[] shift4encode;

  private final int[] chunkChars4encode;

  public BitConfig(int bitCount, int bytesPerChunk, int charsPerChunk) {

    this(bitCount, bytesPerChunk, charsPerChunk, null, null, null);
  }

  public BitConfig(int bitCount, int bytesPerChunk, int charsPerChunk, int[] chars2decode, int[] shift4encode, int[] chunkChars4encode) {

    super();
    this.bitCount = bitCount;
    this.length = 1 << bitCount;
    this.mask = this.length - 1;
    this.bytesPerChunk = bytesPerChunk;
    this.charsPerChunk = charsPerChunk;
    this.chars2decode = chars2decode;
    this.shift4encode = shift4encode;
    this.chunkChars4encode = chunkChars4encode;
  }

  int getBytes2Decode(int charsInChunk) {

    int bytes = 0;
    if ((this.chars2decode != null) && (charsInChunk <= this.chars2decode.length)) {
      bytes = this.chars2decode[charsInChunk - 1];
    }
    assert (bytes != 0);
    return bytes;
  }

  int getShift4Encode(int paddingModulo) {

    int bytes = 0;
    if ((this.shift4encode != null) && (paddingModulo <= this.shift4encode.length)) {
      bytes = this.shift4encode[paddingModulo - 1];
    }
    assert (bytes != 0);
    return bytes;
  }

  int getChunkChars4Encode(int paddingModulo) {

    int chunkChars = 0;
    if ((this.chunkChars4encode != null) && (paddingModulo <= this.chunkChars4encode.length)) {
      chunkChars = this.chunkChars4encode[paddingModulo - 1];
    }
    assert (chunkChars != 0);
    return chunkChars;

  }

  public static BitConfig of(int length) {

    switch (length) {
      case 2:
        return CONFIGS[0];
      case 4:
        return CONFIGS[1];
      case 8:
        return CONFIGS[2];
      case 16:
        return CONFIGS[3];
      case 32:
        return CONFIGS[4];
      case 64:
        return CONFIGS[5];
    }
    return null;
  }

}
