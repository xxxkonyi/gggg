package com.believe.utils;

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

  public static Customer getCurrentUser() {
    Object obj = RequestContextHolder.getRequestAttributes().getAttribute(CURRENT_USER_KEY, 1);
    if (obj instanceof Customer) {
      return (Customer) obj;
    }
    return null;
  }

  public static void setCurrentUser(Customer customer) {
    RequestContextHolder.getRequestAttributes().setAttribute(CURRENT_USER_KEY, customer, 1);
  }

  public static boolean isAuthenticated() {
    Object obj = RequestContextHolder.getRequestAttributes().getAttribute(CURRENT_USER_KEY, 1);
    return null != obj && obj instanceof Customer;
  }

}
