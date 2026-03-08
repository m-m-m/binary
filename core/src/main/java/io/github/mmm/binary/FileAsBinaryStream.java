/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import io.github.mmm.base.exception.RuntimeIoException;

/**
 * Implementation of {@link BinaryStream} for a file {@link Path}.
 *
 * @since 1.0.0
 */
public class FileAsBinaryStream implements WritableBinaryStream {

  private final Path file;

  /**
   * The constructor.
   *
   * @param file the file to adapt.
   */
  public FileAsBinaryStream(Path file) {

    super();
    Objects.requireNonNull(file, "file");
    assert (Files.isRegularFile(file));
    this.file = file;
  }

  /**
   * @return the wrapped {@link Path}.
   */
  public Path getFile() {

    return this.file;
  }

  @Override
  public void save(OutputStream out) {

    try {
      Files.copy(this.file, out);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public InputStream openStream() {

    try {
      return Files.newInputStream(this.file);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public long getSize() {

    if (!Files.exists(this.file) || Files.isDirectory(this.file)) {
      return 0;
    }
    try {
      return Files.size(this.file);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public void truncate(long maxLength) {

    if (maxLength >= getSize()) {
      return;
    }
    try (RandomAccessFile raf = new RandomAccessFile(this.file.toFile(), "rwd")) {
      raf.setLength(maxLength);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  @Override
  public void setBytes(long pos, byte[] bytes, int offset, int length) {

    Objects.requireNonNull(bytes);
    checkNotNegative(pos, "pos");
    checkNotNegative(offset, "offset");
    checkNotNegative(length, "length");
    checkOffset(offset, bytes.length);
    if (length == 0) {
      return;
    }
    checkOffset(offset, bytes.length - length);
    try (RandomAccessFile raf = new RandomAccessFile(this.file.toFile(), "rw")) {
      raf.seek(pos);
      raf.write(bytes, offset, length);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  private void checkNotNegative(long value, String name) {

    if (value < 0) {
      throw new IllegalArgumentException(name + " must not be negative: " + value);
    }
  }

  private void checkOffset(int offset, int max) {

    if (offset >= max) {
      throw new IllegalArgumentException("offset " + offset + " exceeds " + max);
    }
  }

}
