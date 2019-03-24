package net.slipp.web;

import javax.servlet.http.HttpSession;

import net.slipp.domain.User;

public class HttpSessionUtils {
	public final static String HTTP_SESSION_USER = "sessionedUser";
	
	public static boolean isLoginUser(HttpSession httpSession) {
		
		if (httpSession == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession httpSession) {
		if (!isLoginUser(httpSession)) {
			return null;
		}
		
		return (User) httpSession.getAttribute(HTTP_SESSION_USER);
	}
}
 