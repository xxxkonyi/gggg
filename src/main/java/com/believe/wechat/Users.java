package com.believe.wechat;

import com.believe.shop.utilities.json.Jsons;
import com.believe.shop.wechat.model.user.Group;
import com.believe.shop.wechat.model.user.SnsUser;
import com.believe.shop.wechat.model.user.User;
import com.believe.shop.wechat.model.user.UserList;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.believe.shop.utilities.Preconditions.checkArgument;
import static com.believe.shop.utilities.Preconditions.checkNotNullAndEmpty;

/**
 * 用户组件
 */
public final class Users extends Component {

  /**
   * 创建用户分组
   */
  private static final String CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=";

  /**
   * 获取用户分组列表
   */
  private static final String GET_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";

  /**
   * 删除分组
   */
  private static final String DELETE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=";

  /**
   * 更新分组名称
   */
  private static final String UPDATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=";

  /**
   * 获取用户所在分组
   */
  private static final String GROUP_OF_USER = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=";

  /**
   * 移动用户所在组
   */
  private static final String MOVE_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=";

  /**
   * 拉取用户信息
   */
  private static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN&access_token=";

  /**
   * 拉取用户列表信息
   */
  private static final String GET_USERS_INFO = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

  /**
   * 备注用户
   */
  private static final String REMARK_USER = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";

  private static final JavaType ARRAY_LIST_GROUP_TYPE = Jsons.DEFAULT.createCollectionType(ArrayList.class, Group.class);

  /**
   * 由AccessToken拉取用户信息
   */
  private static final String GET_SNS_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?";


  Users() {
  }

  /**
   * 创建用户分组
   *
   * @param name 名称
   * @return 分组ID，或抛WechatException
   */
  public Integer createGroup(String name) {
    return createGroup(loadAccessToken(), name);
  }

  /**
   * 创建用户分组
   *
   * @param name 名称
   * @param cb   回调
   */
  public void createGroup(final String name, Callback<Integer> cb) {
    createGroup(loadAccessToken(), name, cb);
  }

  /**
   * 创建用户分组
   *
   * @param accessToken accessToken
   * @param name        名称
   * @param cb          回调
   */
  public void createGroup(final String accessToken, final String name, Callback<Integer> cb) {
    doAsync(new AsyncFunction<Integer>(cb) {
      @Override
      public Integer execute() {
        return createGroup(accessToken, name);
      }
    });
  }

  /**
   * 创建用户分组
   *
   * @param accessToken accessToken
   * @param name        名称
   * @return 分组ID，或抛WechatException
   */
  public Integer createGroup(String accessToken, String name) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(name, "name");

    String url = CREATE_GROUP + accessToken;
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
    Group g = new Group();
    g.setName(name);
    params.put("group", g);

    Map<String, Object> resp = doPost(url, params);
    return (Integer) ((Map) resp.get("group")).get("id");
  }

  /**
   * 获取所有分组列表
   *
   * @return 分组列表，或抛WechatException
   */
  public List<Group> getGroup() {
    return getGroup(loadAccessToken());
  }

  /**
   * 获取所有分组列表
   *
   * @param accessToken accessToken
   * @param cb          回调
   */
  public void getGroup(final String accessToken, Callback<List<Group>> cb) {
    doAsync(new AsyncFunction<List<Group>>(cb) {
      @Override
      public List<Group> execute() {
        return getGroup(accessToken);
      }
    });
  }

  /**
   * 获取所有分组列表
   *
   * @param accessToken accessToken
   * @return 分组列表，或抛WechatException
   */
  public List<Group> getGroup(String accessToken) {
    checkNotNullAndEmpty(accessToken, "accessToken");

    String url = GET_GROUP + accessToken;
    Map<String, Object> resp = doGet(url);
    return Jsons.EXCLUDE_DEFAULT
      .fromJson(Jsons.DEFAULT.toJson(resp.get("groups")), ARRAY_LIST_GROUP_TYPE);
  }

  /**
   * 删除分组
   *
   * @param id 分组ID
   * @return 删除成功返回true，或抛WechatException
   */
  public Boolean deleteGroup(Integer id) {
    return deleteGroup(loadAccessToken(), id);
  }

  /**
   * 删除分组
   *
   * @param id 分组ID
   * @param cb 回调
   */
  public void deleteGroup(final Integer id, Callback<Boolean> cb) {
    deleteGroup(loadAccessToken(), id, cb);
  }

  /**
   * 删除分组
   *
   * @param accessToken accessToken
   * @param id          分组ID
   * @param cb          回调
   */
  public void deleteGroup(final String accessToken, final Integer id, Callback<Boolean> cb) {
    doAsync(new AsyncFunction<Boolean>(cb) {
      @Override
      public Boolean execute() {
        return deleteGroup(accessToken, id);
      }
    });
  }

  /**
   * 删除分组
   *
   * @param accessToken accessToken
   * @param id          分组ID
   * @return 删除成功返回true，或抛WechatException
   */
  public Boolean deleteGroup(String accessToken, Integer id) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkArgument(id != null && id > 0, "id must > 0");

    String url = DELETE_GROUP + accessToken;
    Group g = new Group();
    g.setId(id);
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
    params.put("group", g);

    doPost(url, params);
    return Boolean.TRUE;
  }

  /**
   * 更新分组名称
   *
   * @param id      分组ID
   * @param newName 分组新名称
   * @return 更新成功返回true，或抛WechatException
   */
  public Boolean updateGroup(Integer id, String newName) {
    return updateGroup(loadAccessToken(), id, newName);
  }

  /**
   * 更新分组名称
   *
   * @param id      分组ID
   * @param newName 分组新名称
   * @param cb      回调
   */
  public void updateGroup(final Integer id, final String newName, Callback<Boolean> cb) {
    updateGroup(loadAccessToken(), id, newName, cb);
  }

  /**
   * 更新分组名称
   *
   * @param accessToken accessToken
   * @param id          分组ID
   * @param newName     分组新名称
   * @param cb          回调
   */
  public void updateGroup(final String accessToken, final Integer id, final String newName, Callback<Boolean> cb) {
    doAsync(new AsyncFunction<Boolean>(cb) {
      @Override
      public Boolean execute() {
        return updateGroup(accessToken, id, newName);
      }
    });
  }

  /**
   * 更新分组名称
   *
   * @param accessToken accessToken
   * @param id          分组ID
   * @param newName     分组新名称
   * @return 更新成功返回true，或抛WechatException
   */
  public Boolean updateGroup(String accessToken, Integer id, String newName) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkArgument(id != null && id > 0, "id must > 0");
    checkNotNullAndEmpty(newName, "group name");

    String url = UPDATE_GROUP + accessToken;
    Group g = new Group();
    g.setId(id);
    g.setName(newName);
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
    params.put("group", g);

    doPost(url, params);
    return Boolean.TRUE;
  }

  /**
   * 获取用户所在组
   *
   * @param openId 用户openId
   * @return 组ID，或抛WechatException
   */
  public Integer getUserGroup(String openId) {
    return getUserGroup(loadAccessToken(), openId);
  }

  /**
   * 获取用户所在组
   *
   * @param openId 用户openId
   * @param cb     回调
   */
  public void getUserGroup(final String openId, Callback<Integer> cb) {
    getUserGroup(loadAccessToken(), openId, cb);
  }

  /**
   * 获取用户所在组
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param cb          回调
   */
  public void getUserGroup(final String accessToken, final String openId, Callback<Integer> cb) {
    doAsync(new AsyncFunction<Integer>(cb) {
      @Override
      public Integer execute() {
        return getUserGroup(accessToken, openId);
      }
    });
  }

  /**
   * 获取用户所在组
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @return 组ID，或抛WechatException
   */
  public Integer getUserGroup(String accessToken, String openId) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(openId, "openId");

    String url = GROUP_OF_USER + accessToken;
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
    params.put("openid", openId);

    Map<String, Object> resp = doPost(url, params);
    return (Integer) resp.get("groupid");
  }

  /**
   * 移动用户所在组
   *
   * @param openId  用户openId
   * @param groupId 新组ID
   * @return 移动成功返回true，或抛WechatException
   */
  public Boolean mvUserGroup(String openId, Integer groupId) {
    return mvUserGroup(loadAccessToken(), openId, groupId);
  }

  /**
   * 移动用户所在组
   *
   * @param openId  用户openId
   * @param groupId 新组ID
   * @param cb      回调
   */
  public void mvUserGroup(final String openId, final Integer groupId, Callback<Boolean> cb) {
    mvUserGroup(loadAccessToken(), openId, groupId, cb);
  }

  /**
   * 移动用户所在组
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param groupId     新组ID
   * @param cb          回调
   */
  public void mvUserGroup(final String accessToken, final String openId, final Integer groupId, Callback<Boolean> cb) {
    doAsync(new AsyncFunction<Boolean>(cb) {
      @Override
      public Boolean execute() {
        return mvUserGroup(accessToken, openId, groupId);
      }
    });
  }

  /**
   * 移动用户所在组
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param groupId     新组ID
   * @return 移动成功返回true，或抛WechatException
   */
  public Boolean mvUserGroup(String accessToken, String openId, Integer groupId) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(openId, "openId");
    checkArgument(groupId != null && groupId > 0, "groupId must > 0");

    String url = MOVE_USER_GROUP + accessToken;
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
    params.put("openid", openId);
    params.put("to_groupid", groupId);

    doPost(url, params);
    return Boolean.TRUE;
  }

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param openId 用户openId
   * @return 用户信息，或抛WechatException
   */
  public User getUser(String openId) {
    return getUser(loadAccessToken(), openId);
  }

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param openId 用户openId
   * @param cb     回调
   */
  public void getUser(final String openId, Callback<User> cb) {
    getUser(loadAccessToken(), openId, cb);
  }

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param cb          回调
   */
  public void getUser(final String accessToken, final String openId, Callback<User> cb) {
    doAsync(new AsyncFunction<User>(cb) {
      @Override
      public User execute() {
        return getUser(accessToken, openId);
      }
    });
  }

  /**
   * 拉取用户信息(若用户未关注，且未授权，将拉取不了信息)
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @return 用户信息，或抛WechatException
   */
  public User getUser(String accessToken, String openId) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(openId, "openId");

    String url = GET_USER_INFO + accessToken + "&openid=" + openId;
    Map<String, Object> resp = doGet(url);

    return Jsons.DEFAULT.fromJson(Jsons.DEFAULT.toJson(resp), User.class);
  }

  /**
   * 拉取用户信息(进入网页授权时)
   */
  public SnsUser getUserBySns(String accessToken, String openId) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(openId, "openId");
    String url = GET_SNS_USER_INFO + accessToken + "&openid=" + openId + "&lang=zh_CN";
    Map<String, Object> resp = doGet(url);

    return Jsons.DEFAULT.fromJson(Jsons.DEFAULT.toJson(resp), SnsUser.class);
  }

  /**
   * 拉取用户列表信息
   *
   * @param nextOpenId nextOpenId
   * @return 用户列表
   */
  public UserList getUsers(String nextOpenId) {
    return getUsers(loadAccessToken(), nextOpenId);
  }

  /**
   * 拉取用户列表信息
   *
   * @param nextOpenId nextOpenId
   * @param cb         回调
   */
  public void getUsers(String nextOpenId, Callback<UserList> cb) {
    getUsers(loadAccessToken(), nextOpenId, cb);
  }

  /**
   * 拉取用户列表信息
   *
   * @param accessToken accessToken
   * @param nextOpenId  nextOpenId
   * @param cb          回调
   */
  public void getUsers(final String accessToken, final String nextOpenId, Callback<UserList> cb) {
    doAsync(new AsyncFunction<UserList>(cb) {
      @Override
      public UserList execute() throws Exception {
        return getUsers(accessToken, nextOpenId);
      }
    });
  }

  /**
   * 拉取用户列表信息
   *
   * @param accessToken accessToken
   * @param nextOpenId  第一个拉取的OPENID，不填默认从头开始拉取
   * @return 用户列表，或抛WechatExeption
   */
  public UserList getUsers(String accessToken, String nextOpenId) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    String url = GET_USERS_INFO + accessToken;

    if (!Strings.isNullOrEmpty(nextOpenId))
      url += ("&next_openid=" + nextOpenId);

    Map<String, Object> resp = doGet(url);

    return Jsons.DEFAULT.fromJson(Jsons.DEFAULT.toJson(resp), UserList.class);
  }

  /**
   * 备注用户
   *
   * @param openId 用户openId
   * @param remark 备注
   * @return 备注成功返回true，或抛WechatException
   */
  public Boolean remarkUser(String openId, String remark) {
    return remarkUser(loadAccessToken(), openId, remark);
  }

  /**
   * 备注用户
   *
   * @param openId 用户openId
   * @param remark 备注
   * @param cb     回调
   */
  public void remarkUser(final String openId, final String remark, Callback<Boolean> cb) {
    remarkUser(loadAccessToken(), openId, remark, cb);
  }

  /**
   * 备注用户
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param remark      备注
   * @param cb          回调
   */
  public void remarkUser(final String accessToken, final String openId, final String remark, Callback<Boolean> cb) {
    doAsync(new AsyncFunction<Boolean>(cb) {
      @Override
      public Boolean execute() {
        return remarkUser(accessToken, openId, remark);
      }
    });
  }

  /**
   * 备注用户
   *
   * @param accessToken accessToken
   * @param openId      用户openId
   * @param remark      备注
   * @return 备注成功返回true，或抛WechatException
   */
  public Boolean remarkUser(String accessToken, String openId, String remark) {
    checkNotNullAndEmpty(accessToken, "accessToken");
    checkNotNullAndEmpty(openId, "openId");
    checkNotNullAndEmpty(remark, "remark");

    String url = REMARK_USER + accessToken;

    Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
    params.put("openid", openId);
    params.put("remark", remark);

    doPost(url, params);
    return Boolean.TRUE;
  }
}
