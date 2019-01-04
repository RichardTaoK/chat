package org.han.main;

import java.util.List;
import java.util.ArrayList;

// 创建一个管理线程的类，防止服务器端的线程太多，有了管理线程的类，就可以轻松的对消息进行跨进程转发（消息
public class ChatManager {
	
	public ChatManager() {
		
	}
	private static final ChatManager chatManager=new ChatManager();
	List<SocketMsg> socketList=new ArrayList<>();
	
	public static ChatManager getChatManager(){
		return chatManager;
	}
	public void add(SocketMsg cs) {
		socketList.add(cs);
	}
	public void remove(SocketMsg cs) {
		socketList.remove(cs);
	}
}
