package io.github.mmm.binary.store;

import java.util.Objects;

/**
 * Implementation of {@link BlobId}.
 */
public class BlobIdType implements BlobId {

  private final String id;

  private final boolean duplicate;

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   * @param duplicate the {@link #isDuplicate() duplicate} flag.
   */
  public BlobIdType(String id, boolean duplicate) {

    super();
    Objects.requireNonNull(id);
    this.id = id;
    this.duplicate = duplicate;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public boolean isDuplicate() {

    return this.duplicate;
  }

  @Override
  public final int hashCode() {

    return this.id.hashCode();
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if (obj instanceof BlobIdType other) {
      return Objects.equals(this.id, other.id);
    }
    return false;
  }

  @Override
  public String toString() {

    return this.id;
  }

}
