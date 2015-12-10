package Proxy;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProxyAspceJ {
	
	  @Pointcut("execution(public * Proxy.Test.Do*(..))")  
       private void aspectjMethod(){};  
	
       @Before("aspectjMethod()") 
		public  void  beforeProxy(JoinPoint joinpoint)
		{
			System.out.println("before");
			String ip=joinpoint.getArgs()[0].toString();
			String port=joinpoint.getArgs()[1].toString();
			//如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("proxySet", "true"); 
            System.getProperties().setProperty("http.proxyHost", ip);
            System.getProperties().setProperty("http.proxyPort", port);
		}
		
       @After("aspectjMethod()")  
		public  void  afterProxy(JoinPoint joinpoint)
		{
			System.out.println("after");
			System.clearProperty("proxySet");
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
		}
}
