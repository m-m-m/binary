package io.github.mmm.binary.store;

import java.nio.file.Path;
import java.util.UUID;

/**
 * Implementation of {@link BlobStore} that just saves any file using a {@link UUID} as primary key.
 */
public class SimpleBlobStore extends AbstractBlobStore {

  /**
   * The constructor.
   *
   * @param root the root directory where all BLOBs are stored. Needs to exist.
   */
  protected SimpleBlobStore(Path root) {

    super(root);
  }

}
