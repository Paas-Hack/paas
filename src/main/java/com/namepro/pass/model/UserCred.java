package com.namepro.pass.model;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "user_cred")
public class UserCred {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long uid;

	@Column(name = "username")
	private String username;

	@ColumnTransformer(forColumn = "password", read = "pgp_sym_decrypt(password, 'secret')", write = "pgp_sym_encrypt(?, 'secret')")
	@Column(name = "password")
	private String password;
	
	public UserCred() {

	}

	public UserCred(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserCred [uid=" + uid + ", username=" + username + ", password=" + password +  "]";
	}

}
