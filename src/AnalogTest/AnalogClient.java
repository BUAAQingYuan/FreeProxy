package AnalogTest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;



public final class AnalogClient {

	public  void Client(String ip,int port)
	{
		  Socket socket = null;  
          
	        try {  
	            socket = new Socket(ip, port);  
	            BufferedReader br1=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            //从客户端输出的数据
	            PrintStream ps =new PrintStream(socket.getOutputStream());          
	            
	            String msg="message from client.";
	            
	            ps.println(msg);
	            br1.close();
                ps.close();
                socket.close();
	            /*
	            while((msg=br1.readLine())!=null){    
	                  ps.println(msg);     
	                  if(msg.equals("quit"))
	                  {
	                        br1.close();
	                        ps.close();
	                        socket.close();
	                        break;
	                  }                      
	            }    
	          */
	              
	        } catch (UnknownHostException e) {  
	            System.out.println("未知的主机异常: " + e);  
	              
	        } catch (IOException e) {  
	            System.out.println("IO异常: " + e);  
	        } 
		 
	}
	
	public static void main(String[] args){
		String ip="127.0.0.1";
		int port=8888;
		new AnalogClient().Client(ip, port);
	}
}
