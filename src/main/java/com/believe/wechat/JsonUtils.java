package com.believe.wechat;

import com.believe.exception.JsonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Json Util based on Jackson
 */
public final class JsonUtils {

  /**
   * 忽略对象中值为NULL或""的属性
   */
  public static final JsonUtils EXCLUDE_EMPTY = new JsonUtils(JsonInclude.Include.NON_EMPTY);

  /**
   * 忽略对象中值为默认值的属性
   */
  public static final JsonUtils EXCLUDE_DEFAULT = new JsonUtils(JsonInclude.Include.NON_DEFAULT);

  /**
   * 默认不排除任何属性
   */
  public static final JsonUtils DEFAULT = new JsonUtils();

  private ObjectMapper mapper;

  private JsonUtils() {
    mapper = new ObjectMapper();
    // ignore attributes exists in json string, but not in java object when deserialization
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  private JsonUtils(JsonInclude.Include include) {
    mapper = new ObjectMapper();
    // set serialization feature
    mapper.setSerializationInclusion(include);
    // ignore attributes exists in json string, but not in java object when deserialization
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  public String toJson(Object target) {
    try {
      return mapper.writeValueAsString(target);
    } catch (IOException e) {
      throw new JsonException(e);
    }
  }

  /**
   * deserialize a json to target class object
   *
   * @param json   json string
   * @param target target class
   * @param <T>    the generic type
   * @return target object
   * @throws JsonException the exception for json
   */
  public <T> T fromJson(String json, Class<T> target) {
    if (StringUtils.isBlank(json)) {
      return null;
    }
    try {
      return mapper.readValue(json, target);
    } catch (IOException e) {
      throw new JsonException(e);
    }
  }

  /**
   * 反序列化
   *
   * @param javaType   JavaType
   * @param jsonString json string
   * @param <T>        the generic type
   * @return the javaType's object
   * @throws JsonException the exception for json
   * @see #createCollectionType(Class, Class...)
   */
  @SuppressWarnings("unchecked")
  public <T> T fromJson(String jsonString, JavaType javaType) {
    if (StringUtils.isBlank(jsonString)) {
      return null;
    }
    try {
      return (T) mapper.readValue(jsonString, javaType);
    } catch (Exception e) {
      throw new JsonException(e);
    }
  }

  /**
   * construct collection type
   *
   * @param collectionClass collection class, such as ArrayList, HashMap, ...
   * @param elementClasses  element class
   * @return JavaType
   */
  public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }
}