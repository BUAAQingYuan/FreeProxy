package ProxySetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;



public class DynamicPointcutExample {
		
	public static void main(String[] args) throws IOException { 
		
        List<HttpHost>   proxypool=new ArrayList<HttpHost>();
        HttpHost   ip1=new HttpHost("122.96.59.105",82);
        HttpHost   ip4=new HttpHost("180.153.46.112",80);
        HttpHost   ip2=new HttpHost("119.188.94.145",80);
        HttpHost   ip3=new HttpHost("180.76.135.145",80);
        proxypool.add(ip1);
        proxypool.add(ip2);
        proxypool.add(ip3);
        
       // Thread.currentThread().
        
        //创建目标对象 
        Test target = new Test(); 
        ProxyIPApply  apply=new ProxyIPApply().setClassname(Test.class).setMethodname("Dosomething").setProxypool(proxypool);
        Test proxy=(Test) apply.Config(target);
        proxy.Dosomething();
        
        
    } 
}
