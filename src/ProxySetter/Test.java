package ProxySetter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class Test {
	
	private static String getHtml(String address) throws IOException{
		StringBuffer html = new StringBuffer();
		String result = null;

           URL url = new URL(address);
           URLConnection conn = url.openConnection();
           conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
           BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

               String inputLine;
               byte[] buf = new byte[4096];
               int bytesRead = 0;
               while (bytesRead >= 0) {
                          inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");
                          html.append(inputLine);
                          bytesRead = in.read(buf);
                          inputLine = null;
              }
              buf = null;
              in.close();
               result = new String(html.toString().trim().getBytes("ISO-8859-1"), "gb2312").toLowerCase();
               return result;
		}
	
		public  void  Dosomething( ) throws IOException
		{
			 System.out.println(getHtml("http://www.ip138.com/ip2city.asp")); 
			 //System.out.println(getHtml("https://www.google.com.hk")); 
		}
		
		public static void main(String args[]) throws IOException{
			/*
			 ApplicationContext factory = new ClassPathXmlApplicationContext("application-config.xml");  
	         Test test= (Test)factory.getBean("Test");  

	         String ip="121.69.28.86";
	         int     port=8118;
	         */
			
			 //test.Dosomething(ip,port);
			
			HttpHost host=new HttpHost("180.153.46.112",80);
			System.getProperties().setProperty("proxySet", "true");
	        System.getProperties().setProperty("http.proxyHost", host.getHostName());
	        System.getProperties().setProperty("http.proxyPort", String.valueOf(host.getPort()));
	        
			Test test=new Test();
			test.Dosomething();
			

		}
}
