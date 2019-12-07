package io.github.mmm.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Implementation of {@link Streamable} for {@link Path}.
 *
 * @since 1.0.0
 */
public class StreamablePath implements Streamable {

  private final Path file;

  /**
   * The constructor.
   *
   * @param file the file to adapt.
   */
  public StreamablePath(Path file) {

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
      throw new IllegalStateException(e);
    }
  }

  @Override
  public InputStream asStream() {

    try {
      return Files.newInputStream(this.file);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
