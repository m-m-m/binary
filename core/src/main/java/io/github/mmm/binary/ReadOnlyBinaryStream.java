/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 */
public final class ReadOnlyBinaryStream implements BinaryStream {

  private final WritableBinaryStream stream;

  private ReadOnlyBinaryStream(WritableBinaryStream stream) {

    super();
    this.stream = stream;
  }

  @Override
  public InputStream openStream() {

    return this.stream.openStream();
  }

  @Override
  public long getSize() {

    return this.stream.getSize();
  }

  @Override
  public void save(OutputStream out) {

    this.stream.save(out);
  }

  /**
   * @param stream the {@link BinaryStream}.
   * @return a {@link BinaryStream} guaranteed to be read-only.
   */
  public static BinaryStream readOnly(BinaryStream stream) {

    if (stream instanceof WritableBinaryStream wbs) {
      return new ReadOnlyBinaryStream(wbs);
    }
    return stream;
  }

}
