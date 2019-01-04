package org.han.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

import org.han.db.DBManager;
import org.han.view.MainWindow;

public class ChatSocket extends Thread{
	
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String message;
	private String username;
	private SocketMsg socketMsg;
	private Connection connection = DBManager.getDBManager().getConnection();//连接数据库
	
	
	public ChatSocket(Socket s) {
		// TODO Auto-generated constructor stub
		//获取数据流
		this.socket=s;
		//读数据,写数据
		try {
			this.bufferedReader= new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//进行数据读写操作
		try {
			String line="";
			while((line =bufferedReader.readLine())!=null) {
				if(!line.equals("-1")) {//-1代表一条数据的结束
					message+=line;
					System.out.println("message:"+message+"\n");
					
				}else {
					delMessage(message);
					System.out.println("receive:"+message+"\n");
					line=null;
					message=null;
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				MainWindow.getMainWindow().setShowMsg(this.username+"login out!");
				MainWindow.getMainWindow().removeOfflineUsers(this.username);
				ChatManager.getChatManager().remove(socketMsg);
				bufferedWriter.close();
				bufferedReader.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private String getAction(String msg) {
		
		String p="\\[(.*)\\]:";
		Pattern pattern = Pattern.compile(p);//构建正则表达式，其中p表示所有的字符
		Matcher matcher = pattern.matcher(msg);//匹配msg
		if(matcher.find()) {//匹配msg里第一个括号里的内容
			return matcher.group(1);//返回msg
		}else {
			return "error";
		}
	}
//	数据处理的协议依赖
	private void delMessage(String msg) {
		System.out.println(msg);
		// TODO Auto-generated method stub
		if(msg!=null) {
			String action = getAction(msg);
			switch (action) {
			case "LOGIN":{dealLogin(msg);break;}
			case "REGISTER":{dealRegister(msg);break;}
			case "DRESSUP":{dealDressUp(msg);break;}
			case "GETDRESSUP":{dealGetDressUp(msg);break;}
			case "PROFILE":{dealProfile(msg);break;}
			case "GETPROFILE":{dealGetProfile(msg);break;}
			case "GETFRIENDLIST":{dealGetFriendList(msg);break;}
			case "GETGROUPLIST":{dealGetGroupList(msg);break;}
			case "GETFRIENDPROFILE":{dealGetFriendProfile(msg);break;}
			case "STATE":{dealState(msg);break;}
			case "CHATMSG":{dealChatMsg(msg);break;}
			case "USERLIST":{dealUserList(msg);break;}
			case "ADDFRIEND":{dealAddFriend(msg);break;}
			case "GROUPMEMBERLIST":{dealGroupMemberList(msg);break;}
			case "ADDGROUP":{dealAddGroup(msg);break;}
			case "GETALLGROUPLIST":{dealGetAllGroupList(msg);break;}
			default:dealError();break;
			}
		}
	}
	
//	发送消息
	private void sendMsg(String msg) {
		System.out.println(msg);
		System.out.println(socket);
		try {
			while(socket == null); 
				
				
			if(bufferedWriter!= null) {
				System.out.println("往客户端写的数据:"+msg);
				bufferedWriter.write(msg+'\n');
				bufferedWriter.flush();
				bufferedWriter.write("-1\n");
				bufferedWriter.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getMsg() {
		System.out.println("获取到的消息:"+message);
		return message;
	}
	
// 处理错误
	private void dealError() {
		
}
	private void dealGetAllGroupList(String msg) {
			String out = null;
			String sql = "select groupName from groups";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				while(resultSet.next()) {
					out+="[ACKGETALLGROUPLIST]:["+resultSet.getString(1)+"]";
				}
				sendMsg(out);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	private void dealAddGroup(String msg) {
			
			
		}
	private void dealGroupMemberList(String msg) {
			
			
		}
	private void dealAddFriend(String msg) {
			// TODO Auto-generated method stub
			
		}
	private void dealUserList(String msg) {
			// TODO Auto-generated method stub
			
		}
	private void dealChatMsg(String msg) {
			// TODO Auto-generated method stub
		String chatObj = null;//msg匹配第一个括号，表示聊天对象
		String content = null;//msg匹配的第二个括号，表示聊天内容
		String avatarID = null;//s三，表好似聊天这id
		String msgType = null;//类型
		String p="\\[CHATMSG\\]:\\[(.*), (.*), (.*), (.*)\\]"; 
		Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		chatObj = matcher.group(1);
    		content = matcher.group(2);
    		avatarID = matcher.group(3);
    		msgType = matcher.group(4);
    	} else {
    		return;
    	}
    	String out=null;
    	String sqlGroup="select * from groups where groupName='"+chatObj+"';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlGroup);
			System.out.println("resultSet:"+resultSet);
//			System.out.println(resultSet.next());
			//group chat
			if(resultSet.next()) {
				String sql="select groupMemberName from groupinfo where groupName='"+chatObj+"';";
				resultSet=statement.executeQuery(sql);
				System.out.println("usename:"+username);
				while(resultSet.next()) {
					//如果user存在，那就send msg
					
					
					for(SocketMsg SocketMsg : ChatManager.getChatManager().socketList) {
						System.out.println("resultset:"+resultSet.getString(1));
						System.out.println("getusername:"+SocketMsg.getUsername());
						
						if (SocketMsg.getUsername().equals(resultSet.getString(1)) && !SocketMsg.getUsername().equals(username)) {
							out = "[GETCHATMSG]:[" + username + ", " + content + ", " + avatarID + ", Text, " + chatObj + "]";
							System.out.println("out:"+out);
							SocketMsg.getChatSocket().sendMsg(out);
							
						}
					}
				}
				//private chat
			}else {
				for (SocketMsg socketManager : ChatManager.getChatManager().socketList) {
					System.out.println(socketManager.getUsername());
					System.out.println("chatObj:"+chatObj);
					if (socketManager.getUsername().equals(chatObj)) {
						out = "[GETCHATMSG]:[" + username + ", " + content + ", " + avatarID + ", Text,  ]";
						System.out.println("out"+out);
						socketManager.getChatSocket().sendMsg(out);
					}
				}
			}
			out = "[ACKCHATMSG]:[1]";
			sendMsg(out);
		} catch (SQLException e) {
			out = "[ACKCHATMSG]:[0]";
			sendMsg(out);
			e.printStackTrace();
		}
    }
	private void dealState(String msg) {
			// TODO Auto-generated method stub
			
		}
	private void dealGetFriendProfile(String msg) {
			String friendName=null;
			String p = "\\[GETFRIENDPROFILE\\]:\\[(.*)\\]";
			Pattern pattern = Pattern.compile(msg);
			Matcher matcher = pattern.matcher(msg);
			if(matcher.find()) {
				friendName = matcher.group(1);
			}else {
				return;
			}
			String out=null;
			String sql = "SELECT avatar, sign, background, state FROM userinfo WHERE username = '" + friendName + "';";
	    	try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					out = "[ACKGETFRIENDPROFILE]:[" + resultSet.getString(1) + ", " + resultSet.getString(2) + ", "
							+ "" + resultSet.getString(3) + ", " + resultSet.getString(4) + "]";
					sendMsg(out);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	private void dealGetGroupList(String msg) {
		String out ="";
    	String sql = "SELECT groupName FROM groupinfo WHERE groupMemberName = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				out += "[ACKGETGROUPLIST]:[" + resultSet.getString(1) + "] ";
			}
			sendMsg(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		}
	
	
	private void dealGetFriendList(String msg) {
		String out = "";
    	String sql = "SELECT friendsName FROM friends WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				out += "[ACKGETFRIENDLIST]:[" + resultSet.getString(1) + "] ";
				System.out.println(out);
			}
			sendMsg(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	
	private void dealGetProfile(String msg) {
		String out = null;
    	String sql = "SELECT sign FROM USERINFO WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				out = "[ACKGETPROFILE]:[" + resultSet.getString(1) + "]";
				sendMsg(out);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	private void dealProfile(String msg) {
			// TODO Auto-generated method stub
			
		}
	private void dealGetDressUp(String msg) {
		// TODO Auto-generated method stub
		String out = null;
    	String sql = "SELECT avatar, background FROM USERINFO WHERE username = '" + username + "';";
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				out = "[ACKGETDRESSUP]:[" + resultSet.getString(1) + ", " + resultSet.getString(2) + "]";
				sendMsg(out);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void dealDressUp(String msg) {
		String acatarID = null;
    	String backgroundID = null;
    	String p = "\\[DRESSUP\\]:\\[(.*), (.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		acatarID = matcher.group(1);
    		backgroundID = matcher.group(2);
    	}
    	System.out.println(acatarID + "   " + backgroundID);
    	String sql = "UPDATE USERINFO SET avatar =  " + acatarID + ", background = " + backgroundID +" WHERE username = '" + username + "'";
    	try {
			Statement statement = connection.createStatement();
			if (statement.executeUpdate(sql) > 0) {
				sendMsg("[ACKDRESSUP]:[1]");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	sendMsg("[ACKDRESSUP]:[0]");
			
		}
	private void dealRegister(String msg) {
			// TODO Auto-generated method stub
			
		}
	private void dealLogin(String msg) {
		String iusername = null;
    	String iPassword = null;
    	
    	String p = "\\[LOGIN\\]:\\[(.*), (.*)\\]";
    	Pattern pattern = Pattern.compile(p);
    	Matcher matcher = pattern.matcher(msg);
    	if (matcher.find()) {
    		iusername = matcher.group(1);
    		iPassword = matcher.group(2);
    		System.out.println("user:"+iusername+",password:"+iPassword);
    	}
    	String sql = "SELECT password FROM USERS WHERE username = '" + iusername + "';";
    	try {
    		System.out.println("denglu");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			System.out.println(resultSet);
//			if(resultSet.next()) {
//				System.out.println(resultSet.getString(1));
//			}
			if ( resultSet.next() && iPassword.equals(resultSet.getString(1))) {
				sendMsg("[ACKLOGIN]:[1]");
				this.username = iusername;
				MainWindow.getMainWindow().setShowMsg(this.username + " login in!");
				MainWindow.getMainWindow().addOnlineUsers(this.username);
				socketMsg = new SocketMsg(this,  this.username);
				ChatManager.getChatManager().add(socketMsg);
				return ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	sendMsg("[ACKLOGIN]:[0]");
		
	}
	
}
