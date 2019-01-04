package com.android.net;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
 
public class AndroidService {
 
 
	public static void main(String[] args) throws IOException {
		ServerSocket serivce = new ServerSocket(30000);
		while (true) {
			//等待客户端连接
			Socket socket = serivce.accept();
			System.out.println("haha");
			new Thread(new AndroidRunnable(socket)).start();
			System.out.println("aa");
		}
	}
 
}
