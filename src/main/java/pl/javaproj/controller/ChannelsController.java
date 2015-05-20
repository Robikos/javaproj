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

import pl.javaproj.model.Channel;
import pl.javaproj.service.ChannelService;

@Controller
public class ChannelsController {
	
	private ChannelService channelService;
	
	@Autowired(required=true)
	@Qualifier(value="channelService")
	public void setChannelService(ChannelService cs)
	{
		this.channelService = cs;
	}
	
	@RequestMapping(value = "/channels", method = RequestMethod.GET)
	public String index(Model model) {	
		model.addAttribute("channel", new Channel());
		model.addAttribute("listChannels", this.channelService.listChannels());
		//returns the view name
		return "channels_index";
 	}
	
	@RequestMapping(value="/channels/add", method = RequestMethod.POST)
	public String addChannel(@ModelAttribute("channel") Channel p)
	{
		if (p.getId() == 0)
		{
			this.channelService.addChannel(p);
		}
		else
		{
			this.channelService.updateChannel(p);
		}
		
		return "redirect:/channels";
	}
	
	@RequestMapping("/channels/remove/{id}")
	public String removeChannel(@PathVariable("id") int id)
	{
		this.channelService.removeChannel(id);
		return "redirect:/channels";
	}
	
	@RequestMapping("/channels/edit/{id}")
	public String editChannel(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("channel", this.channelService.getChannelById(id));
		model.addAttribute("listChannels", this.channelService.listChannels());
		return "channels_index";
	}
}