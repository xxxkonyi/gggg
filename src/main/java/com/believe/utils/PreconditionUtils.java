package com.believe.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.*;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 9:37
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PreconditionUtils {

  public static void checkNotNull(Object target) {
    checkArgument(null == target, target + "Must be not null");
  }

  public static void checkNotBlank(String target) {
    checkArgument(StringUtils.isBlank(target), target + "Must be not null");
  }

}
