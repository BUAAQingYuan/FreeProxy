package Proxy;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import Crawler.ProxyCrawler;
import Entity.ProxyHost;
import Entity.ProxyType;

public class ProxyFactory {
	
	//Ĭ�ϻ�ȡһ������
	public static  ProxyHost   getProxyHost()
	{
		
		return null;
	}
	
	//����
	public  static ProxyHost  getProxyHost(boolean  pos)
	{
		return null;
	}
	
	
	//Ĭ�ϻ�ȡ10��,����
	public  static   List<ProxyHost>  getProxyHosts() throws ConfigurationException
	{
		ProxyCrawler  crawler=new ProxyCrawler("ProxySite.xml");
		crawler.run();
		if(crawler.hosts.size()>10)
			return crawler.hosts.subList(0, 9);
		else
			return crawler.hosts;
	}
	
	//ָ������
	public  static   List<ProxyHost>  getProxyHosts(int number)
	{
		return null;
	}
	
	//ָ������
	public  static  List<ProxyHost>  getProxyHosts(int number,ProxyType type,boolean position)
	{
		return null;
	}
	
	
}
