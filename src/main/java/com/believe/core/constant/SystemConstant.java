package com.believe.core.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:43
 * @since 1.0
 */
public final class SystemConstant {

  // todo 修改配置常量为测试
  public static final Integer CUSTOMER_PRAISE_QUOTA = 1;
  public static final Integer MANIFESTO_QUOTA = 1;

  public static final Set<String> AUTH_URL = Sets.newHashSet("index", "declaration", "person", "address");
}
