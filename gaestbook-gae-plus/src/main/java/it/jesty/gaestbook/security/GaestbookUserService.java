package it.jesty.gaestbook.security;

import com.google.appengine.api.users.User;

public interface GaestbookUserService {
	
	User getUser();
	
	String getLoginUrl();
	
	String getLogoutUrl();

	boolean isLogged();
	
}
