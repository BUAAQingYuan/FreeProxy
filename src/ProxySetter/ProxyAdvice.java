package ProxySetter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.HttpHost;

public class ProxyAdvice implements MethodInterceptor {
	private 	HttpHost   host;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		//before
		ProxyHandler.BeforeSetProxy(host);

		Object retVal = invocation.proceed(); 
		//after
		ProxyHandler.AfterSetProxy();
        return retVal; 
	}

	public ProxyAdvice setHost(HttpHost host) {
		this.host = host;
		return this;
	}

}
