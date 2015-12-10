package Proxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.httpclient.HttpException;

import Action.CheckProxy;
import Crawler.ProxyCrawler;
import Entity.FactoryParameter;
import Entity.ProxyHost;
import Entity.ProxyType;

public class ProxyIPFactory {
	
	private  static  List<ProxyHost> hosts=new ArrayList<ProxyHost>();
	
	private static  List<ProxyHost>   results=new ArrayList<ProxyHost>();
	private static  int  index=0;
	
	   /*
	 
	  构造器
	  
	  */
	
	public  ProxyIPFactory(String configfile) throws ConfigurationException
	{
		ProxyCrawler crawler=new ProxyCrawler(configfile);
		crawler.run();
		hosts=crawler.getHosts();
		System.out.println("The number of ProxyIP  is "+hosts.size());
	}
	
	public  ProxyIPFactory(List<String>  configs) throws ConfigurationException
	{
		ProxyCrawler crawler=new ProxyCrawler(configs);
		crawler.run();
		hosts=crawler.getHosts();
		System.out.println("The number of ProxyIP  is "+hosts.size());
	}
	
	public  ProxyIPFactory(File configdir) throws ConfigurationException
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
	public static  ProxyHost   getProxyHost() throws HttpException, IOException, InterruptedException 
	{	
		final int number=1;
		 //index lock
		final Semaphore semp = new Semaphore(1);
		final Semaphore proxy= new Semaphore(1);
		final CountDownLatch  down=new CountDownLatch(FactoryParameter.FactorySeekthread);
		ExecutorService executor = Executors.newFixedThreadPool(FactoryParameter.FactorySeekthread);
		for(int i=0;i<FactoryParameter.FactorySeekthread ; i++)
		{
			Runnable task=new Runnable(){
				@Override
				public void run() {
				try{
					while(true)
					{	
						  proxy.acquire();
						  if(results.size()>=number)
						  {
						    	down.countDown();
						    	proxy.release();
						    	return;
						  }
						  proxy.release();
		
							semp.acquire();
							if(index>=hosts.size())
							{
								down.countDown();
						    	semp.release();
								return;
							}
							ProxyHost  current=hosts.get(index);
							index++;
							semp.release();
	
							if(!CheckProxy.isProxyForeign(current.getIp())&&CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
							{
								 proxy.acquire();
								 if(results.size()>=number)
								 {
								    	down.countDown();
								    	proxy.release();
								    	return;
								 }
								 results.add(current);
								 proxy.release();
							}
							
					 }
				 }
					catch(Exception e){}
					}
				};
	
			executor.submit(task);
		}
		
		down.await();
		executor.shutdown();
		return results.get(0);
	}
	
	
	
	//
	public  static  ProxyHost  getProxyHost(boolean  pos)
	{
		return null;
	}
	
	
	//默认获取10个，国内
	public  static   List<ProxyHost>  getProxyHosts() throws HttpException, IOException, InterruptedException 
	{
		final int number=10;
		 //index lock
		final Semaphore semp = new Semaphore(1);
		final Semaphore proxy= new Semaphore(1);
		final CountDownLatch  down=new CountDownLatch(FactoryParameter.FactorySeekthread);
		ExecutorService executor = Executors.newFixedThreadPool(FactoryParameter.FactorySeekthread);
		for(int i=0;i<FactoryParameter.FactorySeekthread ; i++)
		{
			Runnable task=new Runnable(){
				@Override
				public void run() {
				try{
					while(true)
					{	
						  proxy.acquire();
						  if(results.size()>=number)
						  {
						    	down.countDown();
						    	proxy.release();
						    	return;
						  }
						  proxy.release();
		
							semp.acquire();
							if(index>=hosts.size())
							{
								down.countDown();
						    	semp.release();
								return;
							}
							ProxyHost  current=hosts.get(index);
							index++;
							semp.release();
	
							if(!CheckProxy.isProxyForeign(current.getIp())&&CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
							{
								 proxy.acquire();
								 if(results.size()>=number)
								 {
								    	down.countDown();
								    	proxy.release();
								    	return;
								 }
								 results.add(current);
								 proxy.release();
							}
							
					 }
				 }
					catch(Exception e){}
					}
				};
	
			executor.submit(task);
		}
		
		down.await();
		executor.shutdown();
		return results ;
	}
	
	
	//指定数量
	public  static   List<ProxyHost>  getProxyHosts(final int number) throws HttpException, IOException, InterruptedException
	{
		        //index lock
				final Semaphore semp = new Semaphore(1);
				final Semaphore proxy= new Semaphore(1);
				final CountDownLatch  down=new CountDownLatch(FactoryParameter.FactorySeekthread);
				ExecutorService executor = Executors.newFixedThreadPool(FactoryParameter.FactorySeekthread);
				for(int i=0;i<FactoryParameter.FactorySeekthread ; i++)
				{
					Runnable task=new Runnable(){
						@Override
						public void run() {
						try{
							while(true)
							{	
								  proxy.acquire();
								  if(results.size()>=number)
								  {
								    	down.countDown();
								    	proxy.release();
								    	return;
								  }
								  proxy.release();
				
									semp.acquire();
									if(index>=hosts.size())
									{
										down.countDown();
								    	semp.release();
										return;
									}
									ProxyHost  current=hosts.get(index);
									index++;
									semp.release();
			
									if(CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
									{
										 proxy.acquire();
										 if(results.size()>=number)
										 {
										    	down.countDown();
										    	proxy.release();
										    	return;
										 }
										 results.add(current);
										 proxy.release();
									}
									
							 }
						 }
							catch(Exception e){}
							}
						};
			
					executor.submit(task);
				}
				
				down.await();
				executor.shutdown();
				return results ;
	}
	
	
	
	//指定数量、匿名程度
	public  static  List<ProxyHost>  getProxyHosts(final int number,int type) throws IOException, InterruptedException
	{
		
		//index lock
		final Semaphore semp = new Semaphore(1);
		
		final Semaphore proxy= new Semaphore(1);
		
		final CountDownLatch  down=new CountDownLatch(FactoryParameter.FactorySeekthread);
		
		ExecutorService executor = Executors.newFixedThreadPool(FactoryParameter.FactorySeekthread);
		for(int i=0;i<FactoryParameter.FactorySeekthread ; i++)
		{
			Runnable task=new Runnable(){
				@Override
				public void run() {
				try{
					while(true)
					{	
						  proxy.acquire();
						  if(results.size()>=number)
						  {
						    	down.countDown();
						    	proxy.release();
						    	return;
						  }
						  proxy.release();
		
							semp.acquire();
							if(index>=hosts.size())
							{
								down.countDown();
						    	semp.release();
								return;
							}
							ProxyHost  current=hosts.get(index);
							index++;
							semp.release();
	
							if(current.getType()==ProxyType.HighAnonymous&&CheckProxy.CheckIsOk(current.getIp(),current.getPort()))
							{
								 proxy.acquire();
								 if(results.size()>=number)
								 {
								    	down.countDown();
								    	proxy.release();
								    	return;
								 }
								 results.add(current);
								 proxy.release();
							}
							
					 }
				 }
					catch(Exception e){}
					}
				};
	
			executor.submit(task);
		}
		down.await();
		executor.shutdown();
		return results ;
	}
	
	
	
	
	public static void main(String[] args) throws ConfigurationException, HttpException, IOException, InterruptedException 
	{
        File  confdir=new File("E:\\MyEclipse 2015 CI\\FreeProxy\\src\\Configs");
		ProxyIPFactory  factory=new ProxyIPFactory(confdir);
		
		//List<ProxyHost> host=new ArrayList<ProxyHost>();
		ProxyHost host;
		long startTime=System.currentTimeMillis(); 
		host=ProxyIPFactory.getProxyHost();
		long endTime=System.currentTimeMillis();
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
		System.out.println("####"+host.getIp());
		/*
		for(ProxyHost  one:host)
		{
			System.out.println("####"+one.getIp());
		}
		*/
		
	}
	
	
}
