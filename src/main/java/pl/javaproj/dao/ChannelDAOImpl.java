package pl.javaproj.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import pl.javaproj.model.Channel;

@Repository
public class ChannelDAOImpl implements ChannelDAO {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf)
	{
		this.sessionFactory = sf;
	}

	public void addChannel(Channel p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
	}

	public void updateChannel(Channel p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
	}

	@SuppressWarnings("unchecked")
	public List<Channel> listChannels() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Channel> channelsList = session.createQuery("from Channel").list();
		return channelsList;
	}

	public Channel getChannelById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Channel p = (Channel) session.get(Channel.class, new Integer(id));
		return p;
	}

	public void removeChannel(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Channel p = (Channel) session.get(Channel.class, new Integer(id));
		if (p != null) session.delete(p);
	}
}
