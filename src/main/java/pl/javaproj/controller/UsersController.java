package pl.javaproj.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.javaproj.model.Channel;
import pl.javaproj.model.User;
import pl.javaproj.service.UserService;

@Controller
@SessionAttributes("currentUser")
public class UsersController {
	
	private static int workload = 12;
	
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService cs)
	{
		this.userService = cs;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String index(Model model) {	
		model.addAttribute("listUsers", this.userService.listUsers());
		//returns the view name
		return "users_index";
	}
	
	@RequestMapping(value = "/users/me", method = RequestMethod.GET)
	public String me(Model model) {	
		//returns the view name
		return "users_show";
	}
	
	@RequestMapping(value = "/users/login", method = RequestMethod.GET)
	public String loginGet(Model model) {
		model.addAttribute("user", new User());
		//returns the view name
		return "users_login";
	}
	
	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public String loginPost(@ModelAttribute("user") User p, Model model) {
		User user = this.userService.getUserByName(p.getLogin());
		if (user == null) return "users_fail";
		
		boolean verified = BCrypt.checkpw(p.getPassword(), user.getEncrypted_password());
		//returns the view name
		if (verified == true)
		{
			model.addAttribute("currentUser", user);
			return "users_success";
		}
		else
		{
			return "users_fail";
		}
	}
	
	@RequestMapping(value="/users/add", method = RequestMethod.GET)
	public String addUserGet(Model model)
	{
		model.addAttribute("user", new User());
		return "users_add";
	}
	
	@RequestMapping(value="/users/add", method = RequestMethod.POST)
	public String addUserPost(@ModelAttribute("user") User p)
	{
		String password = p.getPassword();
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password, salt);
		p.setEncrypted_password(hashed_password);
		if (p.getId() == 0)
		{
			this.userService.addUser(p);
		}
		else
		{
			this.userService.updateUser(p);
		}
		
		return "redirect:/users";
	}
	
	@RequestMapping("/users/remove/{id}")
	public String removeUser(@PathVariable("id") int id)
	{
		this.userService.removeUser(id);
		return "redirect:/users";
	}
	
	@RequestMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("user", this.userService.getUserById(id));
		model.addAttribute("listUsers", this.userService.listUsers());
		return "users_index";
	}
}
