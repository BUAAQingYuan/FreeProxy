package Crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.http.HttpHost;

import Util.FileProcess;
import Entity.FactoryParameter;
import Entity.ProxyConfig;
import Entity.ProxyHost;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ProxyCrawler  implements  PageProcessor,Runnable {

	public List<ProxyHost> hosts=new ArrayList<ProxyHost>();
	private Site site;
	private ProxyConfig config=new ProxyConfig();
	private List<ProxyConfig> configlist=new ArrayList<ProxyConfig>();
	
	private int   counter=0;
	//next site
	private boolean  next=false;
	private  int      currentpage=0;
	
	//read one config
	//异常处理(待添加)
	public  ProxyCrawler(String  configfile) throws ConfigurationException
	{
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
		configlist.add(config);
	}
	
	
	//read  multi-configs
	public  ProxyCrawler(List<String>  configs) throws ConfigurationException
	{
		for(String one:configs)
		{
			configlist.add(FileProcess.getProxyConfig(one));
		}
	}
	
	//指定目录
	public  ProxyCrawler(File configdir) throws ConfigurationException 
	{
		if(!configdir.isDirectory())
		{
			System.out.println("输入的不是目录!");
			return ;
		}
		
		File[] confs=configdir.listFiles();
		for(File conf:confs)
		{
			configlist.add(FileProcess.getProxyConfig(conf.getAbsolutePath()));
		}
	}
	
	
	public  List<ProxyHost>   getHosts()
	{
		return  this.hosts;
	}
	
	@Override
	public void run() {
		List<String> url = new ArrayList<String>();
		url.clear();
		System.out.println("Site number= "+configlist.size());
		for(ProxyConfig config:configlist)
		{
			String  start=config.getSite();
			if(config.isPageEnable())
			{
				int   currentpage=config.getPageStart();
				for(int i=currentpage;i<=config.getPageEnd();i++)
				{
					url.add(start.replaceAll("#", String.valueOf(i)));
				}
			}else{
				url.add(start);
			}
			
		}
		
		/*
		for(String one:url)
		{
			System.out.println(one);
		}
		*/
		currentpage=configlist.get(counter).getPageStart();
		
		Spider.create(this).startUrls(url).thread(FactoryParameter.crawlerthreadnum).run(); 
	}

	@Override
	public Site getSite() {
		String  useragent="Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
		//site = Site.me().setSleepTime(0).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setHttpProxy(new HttpHost("117.136.234.12",80));
		site = Site.me().setSleepTime(0).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(useragent);
		site.setCharset("UTF-8");
		return site;
	}

	@Override
	public void process(Page page) {
	    ProxyConfig  currentconf=new ProxyConfig();
	    currentconf=configlist.get(counter);
	    
		List<String>   ip=page.getHtml().xpath(currentconf.getIpXpath()).all();
		List<String>   port=page.getHtml().xpath(currentconf.getPortXpath()).all();
		List<String>   type=page.getHtml().xpath(currentconf.getAnonymityXpath()).all();
		List<String>   protocol=page.getHtml().xpath(currentconf.getProtocolXpath()).all();
		for(int i=0;i<ip.size();i++)
		{
			ProxyHost  host=new ProxyHost(ip.get(i),Integer.valueOf(port.get(i)),true,FileProcess.getAnonymityType(type.get(i)),FileProcess.getProtocolType(protocol.get(i)));
			hosts.add(host);
			System.out.println(ip.get(i)+" "+port.get(i)+" "+type.get(i)+" "+protocol.get(i));
		}
		
		//next page
		if(currentpage<currentconf.getPageEnd())
		{
			currentpage++;
		}
		else
		{
			next=true;
		}
		
		//next site
		if(next==true)
		{
			counter++;
			next=false;
			if(counter<configlist.size())
				currentpage=configlist.get(counter).getPageStart();
		}
		
	}
	
	public static void main(String[] args) throws ConfigurationException{
		//设置多个文件
		List<String>  confs=new ArrayList<String>();
		confs.add("Configs\\Proxy1.xml");
		confs.add("Configs\\Proxy2.xml");
		confs.add("Configs\\Proxy3.xml");
		new ProxyCrawler(confs).run();
		
		
		//设置一个文件
		//new ProxyCrawler("E:\\MyEclipse 2015 CI\\FreeProxy\\src\\Proxydaili.xml").run();
		
	}
	

}
