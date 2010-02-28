package it.jesty.gaestbook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

@Service
public class GoogleUserService implements GaestbookUserService {
	
	private final UserService userService;

	@Autowired
	public GoogleUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String getLoginUrl() {
		return userService.createLoginURL("/");
	}

	@Override
	public String getLogoutUrl() {
		return userService.createLogoutURL("/");
	}

	@Override
	public User getUser() {
		return userService.getCurrentUser();
	}
	
	@Override
	public boolean isLogged(){
		return userService.isUserLoggedIn();
	}

}
