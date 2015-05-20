package pl.javaproj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.IOException;

@Entity
@Table(name="users")
public abstract class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String login;
	private Date registrationDate;
	private String avatarFilename;
	
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
	public Date getRegistrationDate() {
		return this.registrationDate;
	}
	public void setRegistrationDate(Date date) {
		this.registrationDate = date;
	}
	public String getAvatarFilename() {
		return this.avatarFilename;
	}
	public void setAvatarFilename(String avatarFilename) {
		this.avatarFilename = avatarFilename;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + "]";
	}
	
	public abstract void receiveMessage(String target, String source, String value) throws IOException;
	public abstract void receiveNotice(String target, String source, String value) throws IOException;
	public abstract void receiveTopic(String author, String channel, String newTopic) throws IOException;
	public abstract void receiveMode(String author, String channel, String modeChange) throws IOException;
	public abstract void receiveJoin(String channel, String who) throws IOException;
	public abstract void receiveQuit(String who, String message) throws IOException;
}
