package pl.javaproj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.io.IOException;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	protected String login;
	private String encrypted_password;
	private Date registration_date;
	private String avatar_filename;
	@Transient
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getAvatar_filename() {
		return avatar_filename;
	}
	public void setAvatar_filename(String avatar_filename) {
		this.avatar_filename = avatar_filename;
	}
	public String getEncrypted_password() {
		return encrypted_password;
	}
	public void setEncrypted_password(String encrypted_password) {
		this.encrypted_password = encrypted_password;
	}
	public Date getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + "]";
	}
	
//	public abstract void receiveMessage(String target, String source, String value) throws IOException;
//	public abstract void receiveNotice(String target, String source, String value) throws IOException;
//	public abstract void receiveTopic(String author, String channel, String newTopic) throws IOException;
//	public abstract void receiveMode(String author, String channel, String modeChange) throws IOException;
//	public abstract void receiveJoin(String channel, String who) throws IOException;
//	public abstract void receiveQuit(String who, String message) throws IOException;
//	public abstract void receiveNick(String who, String nick) throws IOException;
}
