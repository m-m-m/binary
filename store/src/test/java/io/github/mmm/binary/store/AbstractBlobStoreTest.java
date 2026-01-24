package io.github.mmm.binary.store;

import java.nio.file.Path;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link AbstractBlobStore}.
 */
class AbstractBlobStoreTest extends Assertions {

  @Test
  void testGetFilePath() {

    // arrange
    Path data = Path.of("root/data");
    String[] ids = { "e4c80c23-438e-4c26-99cb-16d32ed14dc2", "e4c80c23438e4c2699cb16d32ed14dc2",
    "-e--4-c-8-0-c-2-3-4-3-8-e-4-c-2-6-9-9-c-b-1-6-d-3-2-e-d-1-4-d-c-2--" }; // from JavaDoc example
    int segmentLength = 3;
    int[] segmentOffsets = { 0, 2, -1 };

    for (String id : ids) {
      // act
      Path filePath = AbstractBlobStore.getFilePath(data, id, segmentLength, segmentOffsets);

      // assert
      assertThat(filePath).isEqualTo(data.resolve("e4c/c23/dc2").resolve(id));
    }
  }

  @Test
  void testGetFilePathPreventsPathTraversal() {

    // arrange
    Path data = Path.of("root/data");
    String id = "../80bin-438e-4c26-99cb-16d32ed14/./"; // from JavaDoc example
    int segmentLength = 3;
    int[] segmentOffsets = { 0, 2, -1 };

    // act
    Path filePath = AbstractBlobStore.getFilePath(data, id, segmentLength, segmentOffsets);

    // assert
    assertThat(filePath).isNull();
  }

}
