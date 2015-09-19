package Proxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.httpclient.HttpException;

import Action.CheckProxy;
import Crawler.ProxyCrawler;
import Entity.ProxyHost;
import Entity.ProxyType;

public class ProxyFactory {
	
	private  static  List<ProxyHost> hosts=new ArrayList<ProxyHost>();
	
	   /*
	 
	  构造器
	  
	  */
	
	public  ProxyFactory(String configfile) throws ConfigurationException
	{
		ProxyCrawler crawler=new ProxyCrawler(configfile);
		crawler.run();
		hosts=crawler.getHosts();
		System.out.println("The number of ProxyIP  is "+hosts.size());
	}
	
	public  ProxyFactory(List<String>  configs) throws ConfigurationException
	{
		ProxyCrawler crawler=new ProxyCrawler(configs);
		crawler.run();
		hosts=crawler.getHosts();
		System.out.println("The number of ProxyIP  is "+hosts.size());
	}
	
	public  ProxyFactory(File configdir) throws ConfigurationException
	{
		ProxyCrawler crawler=new ProxyCrawler(configdir);
		crawler.run();
		hosts=crawler.getHosts();
		System.out.println("The number of ProxyIP  is "+hosts.size());
	}
	
	
	  /*
	       
	       function interface
	 
	 */
	
	//默认获取一个国内
	public static  ProxyHost   getProxyHost() throws HttpException, IOException 
	{	
		for(int i=0;i<hosts.size();i++)
		{
			ProxyHost  current=hosts.get(i);
			if(!CheckProxy.isProxyForeign(current.getIp())&&CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
			{
				return  current;
			}
		}
		
		return null;
	}
	
	
	
	//国外
	public  static ProxyHost  getProxyHost(boolean  pos)
	{
		return null;
	}
	
	
	//默认获取10个，国内
	public  static   List<ProxyHost>  getProxyHosts() throws HttpException, IOException 
	{
		int  num=0;
		List<ProxyHost>   host=new ArrayList<ProxyHost>();
		for(int i=0;i<hosts.size();i++)
		{
			ProxyHost  current=hosts.get(i);
			if(!CheckProxy.isProxyForeign(current.getIp())&&CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
			{
				host.add(current);
				num++;
				if(num==10)
					break;
			}
		}
		return  host;
	}
	
	
	//指定数量
	public  static   List<ProxyHost>  getProxyHosts(int number) throws HttpException, IOException
	{
		int  num=0;
		List<ProxyHost>   host=new ArrayList<ProxyHost>();
		for(int i=0;i<hosts.size();i++)
		{
			ProxyHost  current=hosts.get(i);
			if(CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
			{
				host.add(current);
				num++;
				if(num==number)
					break;
			}
		}
		return  host;
	}
	
	//指定数量、匿名程度
	//输入过滤(待添加)
	public  static  List<ProxyHost>  getProxyHosts(int number,int type) throws IOException
	{
		int  num=0;
		List<ProxyHost>   host=new ArrayList<ProxyHost>();
		for(int i=0;i<hosts.size();i++)
		{
			ProxyHost  current=hosts.get(i);
			if(CheckProxy.CheckIsOk(current.getIp(),current.getPort())&&CheckProxy.CheckAnonymity(current.getIp(),current.getPort())==type)
			{
				host.add(current);
				num++;
				if(num==number)
					break;
			}
		}
		return  host;
	}
	
	
	public static void main(String[] args) throws ConfigurationException, HttpException, IOException 
	{
        File  confdir=new File("E:\\MyEclipse 2015 CI\\FreeProxy\\src\\Configs");
		ProxyFactory  factory=new ProxyFactory(confdir);
		
		List<ProxyHost> host=new ArrayList<ProxyHost>();
		host=ProxyFactory.getProxyHosts(8,ProxyType.HighAnonymous);
		for(ProxyHost  one:host)
		{
			System.out.println(one.getIp());
		}
		
	}
	
	
}
