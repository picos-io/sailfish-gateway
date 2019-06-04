package io.picos.sailfish.oauth2.support.security;

import io.picos.sailfish.oauth2.domain.model.User;

public class UserDetails extends org.springframework.security.core.userdetails.User {

	private User user;

	public UserDetails(User user) {
		super(user.getUsername(),
			  user.getPassword(),
			  user.isEnabled(),
			  !user.isExpired(),
			  !user.isExpired(),
			  !user.isLocked(),
			  user.getAuthorities());
		this.user = user;
	}

	public String getUserId() {
		return user.getId();
	}

	public User getUser() {
		return user;
	}

}
