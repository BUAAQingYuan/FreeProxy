package ProxySetter;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

public class ProxyDynamicPointcut extends DynamicMethodMatcherPointcut {
	
	private String  pointmethod;
	private Class    classname;
	
	public  ProxyDynamicPointcut(Class classname,String pointmethod)
	{
		this.pointmethod=pointmethod;
		this.classname=classname;
	}
	
	
	public boolean matches(Method method ,Class cls)
	{
		if(pointmethod!=null)
				return (pointmethod.equals(method.getName()));
		else
			   return  false;
	}
	
	@Override
	public boolean matches(Method method, Class<?> cls, Object[] args) {
		//int x=((Integer)args[0]).intValue();
		//return (x!=100);
		return true;
	}
	
	public  ClassFilter getClassFilter()
	{
		return new ClassFilter() { 
            public boolean matches(Class cls) { 
                return (cls == classname); 
            } 
        }; 
    }


	public ProxyDynamicPointcut setPointmethod(String pointmethod) {
		this.pointmethod = pointmethod;
		return this;
	}

	public ProxyDynamicPointcut setClassname(Class classname) {
		this.classname = classname;
		return this;
	} 
	
}
