package com.believe.core.web;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:53
 * @since 1.0
 */
public interface TokenManger<K extends Serializable> {

  Token<K> get(K key);

  Token<K> put(Token<K> token);

  boolean remove(K key);

}
