package pl.javaproj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.javaproj.model.User;
import pl.javaproj.service.UserService;

@Controller
public class UsersController {
	
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService cs)
	{
		this.userService = cs;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String index(Model model) {	
		model.addAttribute("user", new User());
		model.addAttribute("listusers", this.userService.listUsers());
		//returns the view name
		return "users_index";
	}
	
	@RequestMapping(value="/users/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User p)
	{
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
		model.addAttribute("listusers", this.userService.listUsers());
		return "users_index";
	}
}
