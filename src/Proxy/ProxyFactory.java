package Proxy;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import Crawler.ProxyCrawler;
import Entity.ProxyHost;
import Entity.ProxyType;

public class ProxyFactory {
	
	//默认获取一个国内
	public static  ProxyHost   getProxyHost()
	{
		
		return null;
	}
	
	//国外
	public  static ProxyHost  getProxyHost(boolean  pos)
	{
		return null;
	}
	
	
	//默认获取10个,国内
	public  static   List<ProxyHost>  getProxyHosts() throws ConfigurationException
	{
		ProxyCrawler  crawler=new ProxyCrawler("ProxySite.xml");
		crawler.run();
		if(crawler.hosts.size()>10)
			return crawler.hosts.subList(0, 9);
		else
			return crawler.hosts;
	}
	
	//指定数量
	public  static   List<ProxyHost>  getProxyHosts(int number)
	{
		return null;
	}
	
	//指定参数
	public  static  List<ProxyHost>  getProxyHosts(int number,ProxyType type,boolean position)
	{
		return null;
	}
	
	
}
