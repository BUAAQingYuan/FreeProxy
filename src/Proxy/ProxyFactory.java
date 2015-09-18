package Proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

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
	public static  ProxyHost   getProxyHost()
	{
		System.out.println("Size is "+hosts.size());
		return hosts.get(0);
	}
	
	//国外
	public  static ProxyHost  getProxyHost(boolean  pos)
	{
		return null;
	}
	
	
	//默认获取10个，国内
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
	
	public static void main(String[] args) throws ConfigurationException 
	{
		List<String>  confs=new ArrayList<String>();
		confs.add("ProxySite2.xml");
		confs.add("ProxySite.xml");
		confs.add("Proxydaili.xml");
		ProxyFactory  factory=new ProxyFactory(confs);
		ProxyHost  host=factory.getProxyHost();
		System.out.println("IP : "+host.getIp());
		System.out.println("Port: "+host.getPort());
		System.out.println("Anonymity: "+host.getType());
		System.out.println("Procotol: "+host.getProtocol());
	}
	
	
}
