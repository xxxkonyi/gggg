package com.believe.utils;

import com.believe.core.domain.Admin;
import com.believe.core.domain.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/6 22:23
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {

  private static final String CURRENT_USER_KEY = "currentUser";
  private static final String CURRENT_ADMIN_KEY = "currentAdmin";

  public static Customer getCurrentUser() {
    Object obj = RequestContextHolder.getRequestAttributes().getAttribute(CURRENT_USER_KEY, 1);
    if (obj instanceof Customer) {
      return (Customer) obj;
    }
    return null;
  }

  public static Admin getCurrentAdmin() {
    Object obj = RequestContextHolder.getRequestAttributes().getAttribute(CURRENT_ADMIN_KEY, 1);
    if (obj instanceof Admin) {
      return (Admin) obj;
    }
    return null;
  }

  public static void setCurrentUser(Customer customer) {
    RequestContextHolder.getRequestAttributes().setAttribute(CURRENT_USER_KEY, customer, 1);
  }

  public static void setCurrentAdmin(Admin admin) {
    RequestContextHolder.getRequestAttributes().setAttribute(CURRENT_ADMIN_KEY, admin, 1);
  }

  public static boolean isAuthenticated() {
    Object obj = RequestContextHolder.getRequestAttributes().getAttribute(CURRENT_USER_KEY, 1);
    return null != obj && obj instanceof Customer;
  }

  public static void remove(Class clazz) {
    if (Admin.class.isAssignableFrom(clazz)) {
      RequestContextHolder.getRequestAttributes().removeAttribute(CURRENT_ADMIN_KEY, 1);
    }
    if (Admin.class.isAssignableFrom(clazz)) {
      RequestContextHolder.getRequestAttributes().removeAttribute(CURRENT_USER_KEY, 1);
    }
  }

}
