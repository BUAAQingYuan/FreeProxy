package ProxySetter;

import java.io.IOException;
import java.net.ConnectException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ProxyPoolAdvice  implements MethodInterceptor  {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal=null;
		//IOException
		try{
			retVal = invocation.proceed(); 
			
		}catch(ConnectException e)
		{
			System.out.println("ProxyPoolAdvice:catch!");
		}
		
		return retVal;
	}

}
