package ProxySetter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpHost;


public class ProxyScheduleAdvice  implements MethodInterceptor{

	private  List<HttpHost>   proxypool=new ArrayList<HttpHost>();
	private  int    index=0;
	
	private  final  static  int  ConnectionTimeout=50000;
	private  final  static  int   SoTimeout=50000;
	
	//delay
	private  int    delay=60*30;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
				HttpHost  current=proxypool.get(index%proxypool.size());
				if(!CheckIsOk(current.getHostName(),current.getPort()))
				{
					proxypool.remove(index%proxypool.size());
					//System.out.println("remove "+current.getHostName());
				}else{
					SetProxy(current);
				}
				
				index++;
			}
		};
		
		Timer timer=new Timer();
		timer.schedule(task, new Date(),1000*delay);
		Object retVal = invocation.proceed(); 
		System.out.println("proceed over");
		timer.cancel();
		return retVal;
	}



	public ProxyScheduleAdvice setProxypool(List<HttpHost> proxypool) {
		this.proxypool = proxypool;
		return  this;
	}
	
	public  void  SetProxy(HttpHost host)
	{
		if(host==null)
			return;
	
		 //如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("proxySet", "true");
        System.getProperties().setProperty("http.proxyHost", host.getHostName());
        System.getProperties().setProperty("http.proxyPort", String.valueOf(host.getPort()));
	}
	
	 //判断代理能否使用
	 public static  boolean  CheckIsOk(String ip,int port) 
	 {
	        String remote="http://www.baidu.com/";
	        HttpClient client=new HttpClient();
	        client.getHostConfiguration().setProxy(ip,port);
	        
	        //设置超时
	        client.getHttpConnectionManager().getParams().setConnectionTimeout(ConnectionTimeout);
	        client.getHttpConnectionManager().getParams().setSoTimeout(SoTimeout);
	        
	        HttpMethod method =new GetMethod(remote);
	        try{
	        	int statusCode = client.executeMethod(method);   
	        	//System.out.println("---------"+statusCode+"------------");
	        	if(statusCode==200)
	        	{
	        		//System.out.println("代理IP： "+ip+":"+port+" 可以使用!");
	        		return true;
	        	}
	        	else
	        	{
	        		//System.out.println("代理IP: "+ip+":"+port+" 不能使用!");
	        		return false;
	        	}
	        }catch(Exception e)
	        {
	        	//System.out.println("代理IP: "+ip+":"+port+" 不能使用!");
	        	return false;
	        }
	 }


	public ProxyScheduleAdvice  setDelay(int delay) {
		this.delay = delay;
		return this;
	}
	
}
