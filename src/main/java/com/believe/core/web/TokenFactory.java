package com.believe.core.web;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:56
 * @since 1.0
 */
public interface TokenFactory<K extends Serializable> {

  Token<K> createToken(K key);

  Token<K> get(K key);

  boolean remove(K key);

}
