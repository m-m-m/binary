package io.github.mmm.binary.store;

import java.io.InputStream;

import io.github.mmm.binary.BinaryStream;

/**
 * Interface for a simple {@link BinaryStream BLOB} store.
 */
public interface BlobStore {

  /**
   * Saves the BLOB given as {@link InputStream} in this store. Implementations may support de-duplication and hence can
   * return {@link BlobId#isDuplicate()} being {@code true} to indicate that the given BLOB already exists in this
   * store. In case the de-duplication is based on finger-printing where two different files may have the same ID and
   * you want to override the file, you can then call {@link #delete(String)} on the given ID and then
   * {@link #save(InputStream)} again to override the file. Please note that this in not transaction save.
   *
   * @param blob the {@link InputStream} with the BLOB to save in this store.
   * @return the {@link BlobId} of the stored BLOB. Use {@link BlobId#getId()} to get the raw ID (for later
   *         {@link #load(String)} or {@link #delete(String)} operation).
   */
  BlobId save(InputStream blob);

  /**
   * @param id the {@link #save(InputStream) unique ID} of the requested BLOB.
   * @return the {@link BinaryStream} or {@code null} if no such BLOB exists in this store.
   */
  BinaryStream load(String id);

  /**
   * Deletes the binary with the given {@code id}.
   *
   * @param id the {@link #save(InputStream) unique ID} of the requested BLOB.
   * @return {@code true} if the BLOB was deleted successfully, {@code false} otherwise (no such BLOB exists).
   */
  boolean delete(String id);

}
