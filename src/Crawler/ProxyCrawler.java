package Crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.http.HttpHost;

import Util.FileProcess;
import Entity.ProxyConfig;
import Entity.ProxyHost;
import Entity.ProxyProtocol;
import Entity.ProxyType;
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
	
	
	//read one config
	//异常处理(待添加)
	public  ProxyCrawler(String  configfile) throws ConfigurationException
	{
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
	
	
	@Override
	public void run() {
		List<String> url = new ArrayList<String>();
		url.clear();
		for(ProxyConfig config:configlist)
		{
			url.add(config.getSite());
		}
		
		Spider.create(this).startUrls(url).run(); 
	}

	@Override
	public Site getSite() {
		site = Site.me().setSleepTime(0).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setHttpProxy(new HttpHost("117.136.234.9",80));
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
			//ProxyHost  host=new ProxyHost(ip.get(i),Integer.valueOf(port.get(i)),true,ProxyType.HighAnonymous,ProxyProtocol.HTTP);
			//hosts.add(host);
			System.out.println(ip.get(i)+" "+port.get(i)+" "+type.get(i)+" "+protocol.get(i));
		}
		
		next=true;
		//下一个site
		if(next==true)
		{
			counter++;
		}
		
	}
	
	public static void main(String[] args) throws ConfigurationException{
		List<String>  confs=new ArrayList<String>();
		confs.add("ProxySiteout.xml");
		confs.add("ProxySite2.xml");
		confs.add("ProxySite.xml");
		new ProxyCrawler(confs).run();
	}
	

}
