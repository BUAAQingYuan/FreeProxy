##FreeProxy

FreeProxy是一个给开发者提供免费、高效的代理IP以及相关服务的框架或工具。


###目标与功能

@A
第一个基本的功能就是提供给使用者全面、可靠获取代理IP的接口。这些函数定义在Proxy.ProxyFactory类中。包括但不限于：

*获取一个代理IP(默认为国内的、高匿名)
*获取一个指定参数的代理IP（国内或国外、匿名程度、支持协议等）
*获取一组代理IP（默认为国内的、高匿名），默认为10个
*获取指定数量的代理IP
*获取指定参数(国内或国外、匿名程度、最高延时、数量等)的一组代理IP
*获取一个给定地址段内的代理IP
...


@B
第二个功能是提供对代理IP的条件测试。这些函数定义在Action.CheckProxy类中。包括但不限于:

*测试一个(一组)代理IP的匿名度
*测试一个(一组)代理IP的延时
*测试一个(一组)代理IP的可用性
*测试一个(一组)代理IP的归属地
...

@c
@a和@b只是FreeProxy提供的基本功能，@c就是在@a和@b的基础上提供更高质量的服务。这些函数暂时定义在Proxy.ProxyOptimize类中。包括但不限于:

*找出访问用户指定的targeturl速度最快的代理IP
...

@D
有些想法还处于思考阶段。比如：

*定时切换代理，比如一个小时改变一次代理IP
*持续代理，当前的代理IP不再支持使用或延迟变高时，自动寻找新的代理IP
...


###使用                        
                               
每个配置文件描述一个数据源，

ProxySite 数据所在页面，如果url中包含页数，页号用#代替。一般情况下，不同页的url只有页号不同。

IP        ip在页面的位置

Port      端口在页面的位置

Anonymity 匿名度在页面的位置

Procotol  支持的协议在页面的位置

PageEnable 如果数据源有多个页面，为true

PageStart  当PageEnable=true时，抓取的起始页

PageEnd    当PageEnable=true时,抓取的结束页

每个配置文件描述一个数据源，爬虫可以同时读入多个数据源并爬取数据。


给一个类的某一个方法绑定一个代理IP，不影响其他函数
```
HttpHost   proxyHost=new HttpHost("180.76.135.145",80);
Test target = new Test(); 
ProxyIPApply  apply=new ProxyIPApply().setClassname(Test.class).setMethodname("Dosomething").setHost(proxyHost);
Test proxy=(Test) apply.Config(target);
proxy.Dosomething();

```

给一个类的某个方法绑定一个代理池，代理池根据指定的时间间隔更换代理IP，代理池自动移除无效的代理IP，不影响其他函数
```
  
  Test target = new Test(); 
  ProxyIPApply  apply=new ProxyIPApply().setClassname(Test.class).setMethodname("Dosomething").setProxypool(proxypool);
  Test proxy=(Test) apply.Config(target);
  proxy.Dosomething();

```

                    
  

                                                                                                                                    