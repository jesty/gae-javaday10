package it.jesty.gaestbook.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.users.User;

@Entity(name="Message")
public class Message implements Serializable {
	
	//The class need to be serializable to be saved in cache!
	private static final long serialVersionUID = -3486079471673437678L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column private String text;
	@Column private String email;
	@Column private String name;
	@Column private Date created;
	@Column private String uuid;
	@Column private Boolean confirmed = false;
	@Column private Boolean anonymous = true;
	@Column private User user;

		
	public Message(String text, String email, String name, Date created, Boolean anonymous) {
		this.text = text;
		this.email = email;
		this.name = name;
		this.created = created;
		this.anonymous = anonymous;
	}
	
	public Message() {/*NECESSARY!!!*/}
	
	public Long getId() {
		return id;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Boolean isConfirmed() {
		return confirmed;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Boolean getAnonymous() {
		return anonymous;
	}
	
}
