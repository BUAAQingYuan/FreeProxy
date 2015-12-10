package Proxy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.httpclient.HttpException;

import Action.CheckProxy;
import Entity.FactoryParameter;
import Entity.ProxyHost;

public class ProxyOptimize {
	  private  ProxyIPFactory   factory;
	  
	  public  ProxyOptimize(ProxyIPFactory factory)
	  {
		  this.factory=factory;
	  }
	  
	  //获得指定targeturl访问最快的代理IP
	  public   ProxyHost   getOptimalProxyPartly(String targeturl) throws HttpException, IOException, InterruptedException
	  {
		  List<ProxyHost>  host=factory.getProxyHosts(FactoryParameter.optimizenumber);
		  ProxyHost    remain=new ProxyHost();
		  long   delay=FactoryParameter.maxtimeout;
		  for(ProxyHost  one:host)
		  {
			  long temp=CheckProxy.CheckDelayUsetarget(one.getIp(), one.getPort(), targeturl);
			  if(temp<delay)
			  {
				  remain=one;
				  delay=temp;
				  System.out.println("optimize----host : "+remain.getIp()+"  delay="+delay);
			  }
		  }
		return remain;
	  }
	  
	  public static void main(String[] args) throws ConfigurationException, HttpException, IOException, InterruptedException
	  {
		  File  confdir=new File("E:\\MyEclipse 2015 CI\\FreeProxy\\src\\Configs");
		  ProxyIPFactory  factory=new ProxyIPFactory(confdir);
		  
		  ProxyOptimize opt=new ProxyOptimize(factory);
		  
		  opt.getOptimalProxyPartly("http://git.oschina.net/buaalining/FreeProxy");
	  }
}
