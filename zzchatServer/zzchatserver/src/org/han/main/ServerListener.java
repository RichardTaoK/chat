package org.han.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//ʵ�ֶ��߳̾�Ҫ��socket���ݸ����̣߳��ö��߳�ȥִ�о���Ĳ�����ʵ�ֶ��߳̾�Ҫ�̳�thread�࣬Ҫôʵ��runnable�ӿ�
public class ServerListener extends Thread{
	private ServerSocket serverSocket;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//1.����serversocket
			serverSocket =new ServerSocket(27777,27);
			while(true) {
				//2.��������
				Socket socket=serverSocket.accept();
				//��scoket����Ļ��������һֱ������һ����socket����ͻ᷵��һ��socket
				//����һ����������Ȼ���ж���ͻ������ӣ����Ը�ÿ��socket���ӷ���һ���̲߳��ҿ����̣߳�����ѫ�����������ֱ������һ�����ӽ������ظ���������
				
				System.out.println("������������.....");
				System.out.println("socket"+socket);
				ChatSocket chatSocket=new ChatSocket(socket);
				
				chatSocket.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}