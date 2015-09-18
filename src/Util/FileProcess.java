package Util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import Entity.ProxyConfig;

public class FileProcess {
	
	//read config-file ,and return  thhe ProxyConfig 
	public static  ProxyConfig getProxyConfig(String configfile) throws ConfigurationException
	{
		ProxyConfig   config=new ProxyConfig();
		XMLConfiguration xml=new XMLConfiguration(configfile);
		
		if(xml.getString("ProxySite")!=null)
		{
			config.setSite(xml.getString("ProxySite"));
		}
		
		if(xml.getString("Port")!=null)
		{
			config.setPortXpath(xml.getString("Port"));
		}
		
		if(xml.getString("IP")!=null)
		{
			config.setIpXpath(xml.getString("IP"));
		}
		if(xml.getString("Anonymity")!=null)
		{
			config.setAnonymityXpath(xml.getString("Anonymity"));
		}
		
		if(xml.getString("Protocol")!=null)
		{
			config.setProtocolXpath(xml.getString("Protocol"));
		}
		return  config;
	}
	
	
	
	
}
