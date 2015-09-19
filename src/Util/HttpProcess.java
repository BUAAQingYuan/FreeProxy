package Util;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import Entity.FactoryParameter;

public class HttpProcess {

	//获取一个URL返回的json数据
	public   static  String   getJson(String url) throws HttpException, IOException
	{
		 HttpClient client=new HttpClient();
		 
		  //设置超时
	     client.getHttpConnectionManager().getParams().setConnectionTimeout(FactoryParameter.connectiontimeout);
	     client.getHttpConnectionManager().getParams().setSoTimeout(FactoryParameter.sotimeout);
	     
	     HttpMethod method =new GetMethod(url);
	     client.executeMethod(method);
	     String  location=method.getResponseBodyAsString();
	     return location;
	}
	
	public static void main(String[] args) throws HttpException, IOException
	{
		String url="http://ip.taobao.com/service/getIpInfo.php?ip=49.93.222.18";
		HttpProcess.getJson(url);
	}
	
}
