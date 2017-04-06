package com.believe.core.web;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:52
 * @since 1.0
 */
public interface Token<K extends Serializable> {

  K getKey();

}
