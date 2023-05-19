/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.binary;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for an abstract BLOB that can be streamed.
 *
 * @since 1.0.0
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
