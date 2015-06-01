package pl.javaproj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.javaproj.model.User;
import pl.javaproj.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService{
	private UserDAO userDAO;
	
	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Transactional
	public void addUser(User p) {
		this.userDAO.addUser(p);
	}

	@Transactional
	public void updateUser(User p) {
		this.userDAO.updateUser(p);
	}

	@Transactional
	public List<User> listUsers() {
		return this.userDAO.listUsers();
	}

	@Transactional
	public User getUserById(int id) {
		return this.userDAO.getUserById(id);
	}

	@Transactional
	public void removeUser(int id) {
		this.userDAO.removeUser(id);
	}
	
	
}
