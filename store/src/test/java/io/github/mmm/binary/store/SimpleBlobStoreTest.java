package io.github.mmm.binary.store;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.github.mmm.binary.BinaryStream;

/**
 * Test of {@link SimpleBlobStore}.
 */
class SimpleBlobStoreTest extends BaseBlobStoreTest {

  @Test
  void testStore(@TempDir Path tmp) {

    // arrange
    SimpleBlobStore store = new SimpleBlobStore(tmp);

    BlobId id1 = store.save(blob(TEST_DATA1));
    assertThat(id1.isDuplicate()).isFalse();

    BlobId id2 = store.save(blob(TEST_DATA1));
    assertThat(id2.isDuplicate()).isFalse();

    BinaryStream blob1 = store.load(id1.getId());
    assertThat(blob1.getSize()).isEqualTo(TEST_DATA1.length());
    verifyBlob(blob1, TEST_DATA1);

    BinaryStream blob2 = store.load(id2.getId());
    assertThat(blob2.getSize()).isEqualTo(TEST_DATA1.length());
    verifyBlob(blob2, TEST_DATA1);

    boolean deleted1 = store.delete(id1.getId());
    assertThat(deleted1).isTrue();
    assertThat(blob1.getSize()).isEqualTo(0);
    assertThat(store.load(id1.getId())).isNull();
    assertThat(store.delete(id1.getId())).isFalse();

    verifyBlob(blob2, TEST_DATA1);

    boolean deleted2 = store.delete(id2.getId());
    assertThat(deleted2).isTrue();
    assertThat(blob2.getSize()).isEqualTo(0);
    assertThat(store.load(id2.getId())).isNull();
    assertThat(store.delete(id2.getId())).isFalse();
  }

}
