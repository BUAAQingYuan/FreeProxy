package ProxySetter;

import org.apache.http.HttpHost;

public class ProxyHandler {
	
		public static  void  BeforeSetProxy(HttpHost host)
		{
			 //如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("proxySet", "true");
	        System.getProperties().setProperty("http.proxyHost", host.getHostName());
	        System.getProperties().setProperty("http.proxyPort", String.valueOf(host.getPort()));
		}
		
		public static  void  AfterSetProxy()
		{
			 System.clearProperty("proxySet");
	         System.clearProperty("http.proxyHost");
	         System.clearProperty("http.proxyPort");
		}
		
}
