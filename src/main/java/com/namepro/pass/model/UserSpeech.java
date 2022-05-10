
package com.namepro.pass.model;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name = "user_speech")
public class UserSpeech {

	@Id
	private long uid;

	@Column(name = "username")
	private String username;
	
	@Column(name = "speechtext")
	private byte[] speechtext;

	public UserSpeech() {

	}
	
	public UserSpeech(String username, byte[] speechtext) {
		this.username = username;
		this.speechtext = speechtext;
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

	public byte[] getSpeechtext() {
		return speechtext;
	}

	public void setSpeechtext(byte[] speechtext) {
		this.speechtext = speechtext;
	}


	@Override
	public String toString() {
		return "UserSpeech [uid=" + uid + ", username=" + username + ", speechtext=" + Arrays.toString(speechtext)
				+ "]";
	}

}
