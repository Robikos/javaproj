package pl.javaproj.model;

import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import pl.javaproj.server.Connection;
import pl.javaproj.server.WebConnection;

@Entity
@Table(name="channels")
public class Channel implements Iterable<Connection>{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String topic;
	private String description;
	
	@Transient
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	@Transient
	private ArrayList<WebConnection> webconnections = new ArrayList<WebConnection>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Channel [id=" + id + ", name=" + name + ", topic=" + topic
				+ ", description=" + description + "]";
	}
	public Iterator<Connection> iterator() {
		return connections.iterator();
	}
	
	public ArrayList<WebConnection> getWebConnections() {
		return webconnections;
	}
	
	public void joinUser(Connection connection)
	{
		connections.add(connection);
	}
	
	public int usersCount()
	{
		return connections.size();
	}
	
	public void joinWebUser(WebConnection connection)
	{
		webconnections.add(connection);
	}
	
	public int webusersCount()
	{
		return webconnections.size();
	}
}
