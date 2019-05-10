package me.donsdev.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String userId;
	private String name;
	private String password;
	private String email;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void update(User newUser) {
		this.userId = newUser.userId;
		this.name = newUser.name;
		this.password = newUser.password;
		this.email = newUser.email;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}
	
	
}
