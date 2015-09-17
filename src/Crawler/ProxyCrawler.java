package Crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.http.HttpHost;

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
	
	public  ProxyCrawler(String  configfile) throws ConfigurationException
	{
		XMLConfiguration xml=new XMLConfiguration(configfile);
		config.setSite(xml.getString("ProxySite"));
		config.setPortXpath(xml.getString("Port"));
		config.setIpXpath(xml.getString("IP"));
		config.setAnonymityXpath(xml.getString("Anonymity"));
		config.setProtocolXpath(xml.getString("Protocol"));
	}
	
	@Override
	public void run() {
		String s=config.getSite();
		List<String> url = new ArrayList<String>();
		url.clear();
		url.add(s.replaceAll("\"", "%22"));
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
		
		List<String>   ip=page.getHtml().xpath(config.getIpXpath()).all();
		List<String>   port=page.getHtml().xpath(config.getPortXpath()).all();
		List<String>   type=page.getHtml().xpath(config.getAnonymityXpath()).all();
		List<String>   protocol=page.getHtml().xpath(config.getProtocolXpath()).all();
		for(int i=0;i<ip.size();i++)
		{
			ProxyHost  host=new ProxyHost(ip.get(i),Integer.valueOf(port.get(i)),true,ProxyType.HighAnonymous,ProxyProtocol.HTTP);
			hosts.add(host);
			System.out.println(ip.get(i)+" "+port.get(i)+" "+type.get(i)+" "+protocol.get(i));
		}
		
		
	}
	
	public static void main(String[] args) throws ConfigurationException{
		new ProxyCrawler("ProxySiteout.xml").run();
	}
	

}
