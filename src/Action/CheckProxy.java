package Action;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import Util.HttpProcess;
import Util.PrintProxy;
import Entity.FactoryParameter;
import Entity.ProxyType;
import us.codecraft.webmagic.selector.Html;

/*
  测试一个代理IP，获得其相关信息
 */

public class CheckProxy {
	
	
	 //测试一个代理IP的匿名程度
	//输入过滤(待添加)
	 public  static  int   CheckAnonymity(String ip,int port) throws HttpException, IOException
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
	     else if(score.equalsIgnoreCase("C")||score.equalsIgnoreCase("D"))
	    	 return  ProxyType.Transparent;
	     else
	     {
	    	 System.out.println("The Proxy IP's type is Unknown!----"+score);
	    	 return ProxyType.Unknown;
	     }
	 }
	 
	 
	 //测试一个代理IP的延时，www.baidu.com
	 //设置取消超时时间，或设置长一点(待处理)
	 public  static long   CheckDelay(String ip,int port) throws IOException
	 {
		 long  delay=0;
		 String remote="http://www.baidu.com/";
		 HttpClient client=new HttpClient();
		 client.getHostConfiguration().setProxy(ip,port);
		 
		  //设置超时
	     client.getHttpConnectionManager().getParams().setConnectionTimeout(FactoryParameter.connectiontimeout);
	     client.getHttpConnectionManager().getParams().setSoTimeout(FactoryParameter.sotimeout);
	     
	     HttpMethod method =new GetMethod(remote);
	     long startTime = System.currentTimeMillis();
	     int statusCode = client.executeMethod(method);
	     long endTime = System.currentTimeMillis();
	     if(statusCode==200)
	     {
	    	 delay=endTime-startTime;
	    	 System.out.println("delay time is "+delay+"毫秒.");
	    	 return delay;
	     }
	     else{
	    	 System.out.println("代理IP似乎不可用.");
	    	 return 0;
	     }
	     
	 }
	 
	 
	 //测试一个代理能否使用
	 //还要设置超时时间(待补充)
	 public static  boolean  CheckIsOk(String ip,int port) throws IOException
	 {
	        String remote="http://www.baidu.com/";
	        HttpClient client=new HttpClient();
	        client.getHostConfiguration().setProxy(ip,port);
	        
	        //设置超时
	        client.getHttpConnectionManager().getParams().setConnectionTimeout(FactoryParameter.connectiontimeout);
	        client.getHttpConnectionManager().getParams().setSoTimeout(FactoryParameter.sotimeout);
	        
	        HttpMethod method =new GetMethod(remote);
	        try{
	        	int statusCode = client.executeMethod(method);   
	        	System.out.println("---------"+statusCode+"------------");
	        	if(statusCode==200)
	        	{
	        		System.out.println("代理IP： "+ip+":"+port+" 可以使用!");
	        		return true;
	        	}
	        	else
	        	{
	        		System.out.println("statusCode : "+statusCode);
	        		System.out.println("代理IP: "+ip+":"+port+" 不能使用!");
	        		return false;
	        	}
	        }catch(Exception e)
	        {
	        	System.out.println("代理IP: "+ip+":"+port+" 不能使用!");
	        	return false;
	        }
	 }
	 
	 
	//测试一个代理IP的归属地
	 //异常情况处理(待添加)
	 public  String   getProxyAddress(String ip) throws HttpException, IOException
	 {
		 String  url="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="; 
		 String  location = null;
		 JSONObject  jb=JSONObject.fromObject(HttpProcess.getJson(url+ip));
		// JSONObject  data=JSONObject.fromObject(jb.get("data"));
		 if(jb.getString("country").equals("中国"))
		 {
			 location=jb.getString("province")+" "+jb.getString("city");
		 }else
		 {
			 location=jb.getString("country");
		 }
		 return location;
	 }
	 
	 //测试一个代理IP归属地是国内还是国外
	 //true 国外  ；  false 国内
	 public static  boolean   isProxyForeign(String  ip) throws HttpException, IOException
	 {
		 String  url="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="; 
		 JSONObject  jb=JSONObject.fromObject(HttpProcess.getJson(url+ip));
		// JSONObject  data=JSONObject.fromObject(jb.get("data"));
		 
		 if(jb.getString("country").equals("中国"))
			 return false;
		else
			 return true;
	 }
	 
	 
	 public static void main(String[] args) throws IOException{
			CheckProxy check=new CheckProxy();
			String ip="218.189.26.20";
			int  port=8080;
			check.CheckIsOk(ip,port);
		    check.CheckDelay(ip, port);
		   // PrintProxy.printProxyType(check.CheckAnonymity(ip, port));
			//System.out.println(check.getProxyAddress(ip));
			//System.out.println("IP locations in foreign country :"+check.isProxyForeign(ip));
		}
	 
}
