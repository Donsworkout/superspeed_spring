package me.donsdev.web;

import javax.servlet.http.HttpSession;

import me.donsdev.domain.User;

public class HttpSessionUtils {
	public static final String CURRENT_USER = "sessionUser";
	
	public static boolean isLoginUser(HttpSession session) {
		if(session.getAttribute(CURRENT_USER) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public static User currentUser(HttpSession session) {
		if(!isLoginUser(session)) {
			return null;
		}
		return (User)session.getAttribute(CURRENT_USER);
	}
}
