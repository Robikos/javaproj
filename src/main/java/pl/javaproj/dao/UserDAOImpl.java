package pl.javaproj.dao;

import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import pl.javaproj.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf)
	{
		this.sessionFactory = sf;
	}

	public void addUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		//User user = (User)p;
		session.persist(p);
	}

	public void updateUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		//User user = (User)p;
		session.update(p);
	}

	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User").list();
		return usersList;
	}

	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.get(User.class, new Integer(id));
		return p;
	}
	
	@Override
	public User getUserByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query= session.
		        createQuery("from User where login=:name");
		query.setParameter("name", name);
		User p = (User) query.uniqueResult();
		return p;
	}

	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.get(User.class, new Integer(id));
		if (p != null) session.delete(p);
	}
}
