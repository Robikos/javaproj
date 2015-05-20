package pl.javaproj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.javaproj.model.Channel;
import pl.javaproj.dao.ChannelDAO;

@Service
public class ChannelServiceImpl implements ChannelService{
	private ChannelDAO channelDAO;
	
	public void setChannelDAO(ChannelDAO channelDAO)
	{
		this.channelDAO = channelDAO;
	}

	@Transactional
	public void addChannel(Channel p) {
		this.channelDAO.addChannel(p);
	}

	@Transactional
	public void updateChannel(Channel p) {
		this.channelDAO.updateChannel(p);
	}

	@Transactional
	public List<Channel> listChannels() {
		return this.channelDAO.listChannels();
	}

	@Transactional
	public Channel getChannelById(int id) {
		return this.channelDAO.getChannelById(id);
	}

	@Transactional
	public void removeChannel(int id) {
		this.channelDAO.removeChannel(id);
	}
	
	
}
