package net.sf.mmm.binary.api;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * TODO hohwille This type ...
 *
 * @since 7.6.1
 */
public interface Streamable {

  /**
   * @param out the {@link OutputStream} to {@link OutputStream#write(byte[]) write} the data to.
   */
  void save(OutputStream out);

  /**
   * @return a new {@link InputStream} to read the underlying data.
   */
  InputStream asStream();

}
