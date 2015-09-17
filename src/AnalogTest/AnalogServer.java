package AnalogTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class AnalogServer {
	public  void   Server()
	{
		ServerSocket serverSocket = null;  
        Socket socket = null;  
        try {  
              
            serverSocket = new ServerSocket(8888);  
            socket = serverSocket.accept();  
          
            BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream ps =new PrintStream(socket.getOutputStream());        
            String msg;
            //如果输入流不为空,将接受到的信息打印到相应的文本框中并反馈回收到的信息
            if ((msg =br.readLine())!=null)  
            {
                System.out.println("Server:"+msg);
                System.out.println("Server:InetAddress is"+serverSocket.getInetAddress().getHostAddress());
                System.out.println("Server:LocalAddress is"+serverSocket.getLocalSocketAddress().toString());
            }
            System.out.println("Server:"+"server exit!");
            ps.close();
            br.close();
            socket.close();
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	public static void main(String[] args){
		new AnalogServer().Server();
	}
	
}
