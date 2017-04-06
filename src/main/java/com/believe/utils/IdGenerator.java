package com.believe.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 10:27
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdGenerator {

  public static String generateId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
