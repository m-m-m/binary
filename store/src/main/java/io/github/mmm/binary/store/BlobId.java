package io.github.mmm.binary.store;

/**
 * Interface for the result of {@link BlobStore#save(java.io.InputStream)}. Acts as a simple container of
 * {@link #getId() ID} and {@link #isDuplicate() duplicate} flag. Advanced implementations of {@link BlobStore} may
 * define dedicated sub-classes to pass further values resulting from expensive computations within this object.
 *
 * @see BlobIdType
 */
public interface BlobId {

  /**
   * @return the ID of the saved BLOB.
   */
  String getId();

  /**
   * @return {@code true} if the BLOB already existed before and the save operation did not cause any change,
   *         {@code false} otherwise (BLOB was saved regularly).
   */
  boolean isDuplicate();

}
