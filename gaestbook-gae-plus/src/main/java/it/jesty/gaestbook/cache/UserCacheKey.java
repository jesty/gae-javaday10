package it.jesty.gaestbook.cache;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.api.users.User;

public class UserCacheKey implements Serializable {
	
	private static final long serialVersionUID = -2685439180245150603L;
	
	private final String name;

	private UserCacheKey(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public static UserCacheKey build(User user){
		return build(user.getNickname());
	}
	
	public static UserCacheKey build(String nickname){
		return new UserCacheKey(nickname);
	}

}
