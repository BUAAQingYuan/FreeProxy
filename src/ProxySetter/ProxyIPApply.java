package ProxySetter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ProxyIPApply {
	private 	HttpHost   host;
	private    Class        classname;
	private    String        methodname;
	private  List<HttpHost>   proxypool=new ArrayList<HttpHost>();
	
	//advisor类型
	private    int       type;      
	
	
	//type 1
	public ProxyIPApply setHost(HttpHost host) {
		this.host = host;
		if(type==2)
		{
			type=2;
		}else{
			type=1;
		}
		return this;
	}
	
	//type 2
	public ProxyIPApply setProxypool(List<HttpHost> proxypool) {
		this.proxypool = proxypool;
		if(type==1)
		{
			type=1;
		}else{
			type=2;
		}
		return this;
	}

	public ProxyIPApply setClassname(Class classname) {
		this.classname = classname;
		return this;
	}

	public ProxyIPApply setMethodname(String methodname) {
		this.methodname = methodname;
		return this;
	}
	
	public  Object  Config(Object  target)
	{
        // 创建通知者 
        Advisor advisor =null;
        if(type==1)
        	advisor= new DefaultPointcutAdvisor(new ProxyDynamicPointcut(classname,methodname), new ProxyAdvice().setHost(host));
        else if(type==2)
        	advisor= new DefaultPointcutAdvisor(new ProxyDynamicPointcut(classname,methodname), new ProxyScheduleAdvice().setProxypool(proxypool));
        //创建代理工厂 
        ProxyFactory pf = new ProxyFactory(); 
        //将目标加入工厂 
        pf.setTarget(target); 
        //创建通知者 
        pf.addAdvisor(advisor); 
        //获取代理实例(产品) 
        Object proxy = pf.getProxy(); 
        return proxy;
	}


}
