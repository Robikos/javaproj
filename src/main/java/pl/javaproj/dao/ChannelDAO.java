package pl.javaproj.dao;

import pl.javaproj.model.Channel;
import java.util.List;

public interface ChannelDAO {
	public void addChannel(Channel p);
	public void updateChannel(Channel p);
	public List<Channel> listChannels();
	public Channel getChannelById(int id);
	public void removeChannel(int id);
}
