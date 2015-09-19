package Util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import Entity.ProxyConfig;

public class FileProcess {
	
	private static  List<String> type1=Arrays.asList("透明");
	private static  List<String> type2=Arrays.asList("匿名","普匿");
	private static  List<String> type3=Arrays.asList("高匿名","高匿");
	
	//read config-file ,and return  thhe ProxyConfig 
	public static  ProxyConfig getProxyConfig(String configfile) throws ConfigurationException
	{
		ProxyConfig   config=new ProxyConfig();
		XMLConfiguration xml=new XMLConfiguration(configfile);
		
		if(xml.containsKey("ProxySite"))
		{
			config.setSite(xml.getString("ProxySite"));
		}
		
		if(xml.containsKey("Port"))
		{
			config.setPortXpath(xml.getString("Port"));
		}
		
		if(xml.containsKey("IP"))
		{
			config.setIpXpath(xml.getString("IP"));
		}
		if(xml.containsKey("Anonymity"))
		{
			config.setAnonymityXpath(xml.getString("Anonymity"));
		}
		
		if(xml.containsKey("Protocol"))
		{
			config.setProtocolXpath(xml.getString("Protocol"));
		}
        
		if(xml.containsKey("PageEnable"))
		{
			config.setPageEnable(xml.getBoolean("PageEnable"));
		}
		
		if(xml.containsKey("PageStart"))
		{
			config.setPageStart(xml.getInt("PageStart"));
		}
		
		if(xml.containsKey("PageEnd"))
		{
			config.setPageEnd(xml.getInt("PageEnd"));
		}
		return  config;
	}
	
	
	public  static  int   getAnonymityType(String  type)
	{
		if(type1.contains(type))
			return 1;
		else if(type2.contains(type))
			return 2;
		else if(type3.contains(type))
			return 3;
		
		return 0;
	}
	
	public  static int  getProtocolType(String type)
	{
		if(type.equalsIgnoreCase("http"))
			return 1;
		else if(type.equalsIgnoreCase("https"))
			return 2;
		else 
			return 3;
	}
	
	public static void main(String[] args) throws ConfigurationException
	{
		System.out.println(FileProcess.getProtocolType("HTTPS"));
	}
	
	
	
}
