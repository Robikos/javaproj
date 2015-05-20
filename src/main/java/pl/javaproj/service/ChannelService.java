package pl.javaproj.service;

import java.util.List;
import pl.javaproj.model.Channel;

public interface ChannelService {
	public void addChannel(Channel p);
	public void updateChannel(Channel p);
	public List<Channel> listChannels();
	public Channel getChannelById(int id);
	public void removeChannel(int id);
}
