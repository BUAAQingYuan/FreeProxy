package Action;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import Util.PrintProxy;
import Entity.ProxyType;
import us.codecraft.webmagic.selector.Html;

/*
   ����һ������IP������������Ϣ
 */

public class CheckProxy {
	
	
	 //����һ������IP�������̶�
	//�������(�����)
	 public  int   CheckAnonymity(String ip,int port) throws HttpException, IOException
	 {
		 String checksite="http://www.stilllistener.com/checkpoint1/test11/";
		 HttpClient client=new HttpClient();
		 client.getHostConfiguration().setProxy(ip,port);
	     HttpMethod method =new GetMethod(checksite);
	     client.executeMethod(method);
	     String  html=method.getResponseBodyAsString();
	     
	     Html   page=new Html(html);
	     String  realip=page.xpath("//font[@color='yellow']/text()").toString();
	     String  score=page.xpath("//font[@size='+2']/text()").toString();
	    // System.out.println(page.getText());
	     System.out.println(realip+"  "+score);
	     String localip=GetRealIP.getMyIPLocal();
	     System.out.println("Your IP is : "+localip);
	     boolean  proxy=false;
	     
	     if(!localip.equals(realip))
	     {
	    	 System.out.println("Your Proxy Server Name is : "+realip);
	    	 proxy=true;
	     }
	     
	     if(score==null)
	     {
	    	if(proxy)
	    		return  ProxyType.HighAnonymous;
	    	else
	    		return  ProxyType.NoUseProxy;
	     }
	     
	     if(score.equalsIgnoreCase("A"))
	    	 return  ProxyType.HighAnonymous;
	     else if(score.equalsIgnoreCase("B"))
	    	 return  ProxyType.Anonymous;
	     else if(score.equalsIgnoreCase("C"))
	    	 return  ProxyType.Transparent;
	     else
	     {
	    	 System.out.println("The Proxy IP's type is Unknown!----"+score);
	    	 return ProxyType.Unknown;
	     }
	 }
	 
	 
	 //����һ������IP����ʱ��www.baidu.com
	 //����ȡ����ʱʱ�䣬�����ó�һ��(������)
	 public  long   CheckDelay(String ip,int port) throws IOException
	 {
		 long  delay=0;
		 String remote="http://www.baidu.com/";
		 HttpClient client=new HttpClient();
		 client.getHostConfiguration().setProxy(ip,port);
	     HttpMethod method =new GetMethod(remote);
	     long startTime = System.currentTimeMillis();
	     int statusCode = client.executeMethod(method);
	     long endTime = System.currentTimeMillis();
	     if(statusCode==200)
	     {
	    	 delay=endTime-startTime;
	    	 System.out.println("delay time is "+delay+"����.");
	    	 return delay;
	     }
	     else{
	    	 System.out.println("����IP�ƺ�������.");
	    	 return 0;
	     }
	     
	 }
	 
	 
	 //����һ�������ܷ�ʹ�á�
	 //��Ҫ���ó�ʱʱ��(������)
	 public  boolean  CheckIsOk(String ip,int port) throws IOException
	 {
	        String remote="http://www.baidu.com/";
	        HttpClient client=new HttpClient();
	        client.getHostConfiguration().setProxy(ip,port);
	        HttpMethod method =new GetMethod(remote);
	        try{
	        	int statusCode = client.executeMethod(method);   
	        	System.out.println("---------"+statusCode+"------------");
	        	if(statusCode==200)
	        	{
	        		System.out.println("����IP��"+ip+":"+port+" ����ʹ�ã�");
	        		return true;
	        	}
	        	else
	        	{
	        		System.out.println("����IP��"+ip+":"+port+" ����ʹ�ã�");
	        		return false;
	        	}
	        }catch(Exception e)
	        {
	        	System.out.println("����IP��"+ip+":"+port+" ����ʹ�ã�");
	        	return false;
	        }
	 }
	 
	//����һ������IP�Ĺ�����
	 public  String   getProxyAddress(String ip)
	 {
		 return null;
	 }
	 
	 
	 public static void main(String[] args) throws IOException{
			CheckProxy check=new CheckProxy();
			String ip="117.136.234.9";
			int  port=80;
			check.CheckIsOk(ip,port);
		    //check.CheckDelay(ip, port);
		   PrintProxy.printProxyType(check.CheckAnonymity(ip, port));
		}
	 
}
