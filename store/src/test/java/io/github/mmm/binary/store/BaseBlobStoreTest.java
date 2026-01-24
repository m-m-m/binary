package io.github.mmm.binary.store;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;

import io.github.mmm.binary.Streamable;

/**
 * Abstract base class to test {@link BlobStore}.
 */
abstract class BaseBlobStoreTest extends Assertions {

  static final String TEST_DATA1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  static final String TEST_DATA2 = "เด็กที่มีปัญหาทางการเรียนรู้่บางคนสามารถเรียนร่วมกับเด็กปกติได้";

  InputStream blob(String data) {

    return new ByteArrayInputStream(data.getBytes());
  }

  void verifyBlob(Streamable blob, String data) {

    try {
      byte[] bytes = blob.asStream().readAllBytes();
      assertThat(bytes).isEqualTo(data.getBytes());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
