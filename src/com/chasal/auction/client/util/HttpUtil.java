package com.chasal.auction.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 定义方法getRequest()和postRequest()方法，用于向服务器发送请求，方法返回服务器的响应
 * @author Administrator
 *
 */
public class HttpUtil {
	
	//创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "http://192.168.12.130:8080/auction/android/";
	
	/**
	 * 发送get请求
	 * @param url	发送请求的URL
	 * @return		服务器响应字符串
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String getRequest(final String url) throws InterruptedException, ExecutionException{
		
		FutureTask<String> task = new  FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				//创建HttpGet对象
				HttpGet get = new HttpGet(url);
				//发送get请求
				HttpResponse httpResponse = httpClient.execute(get);
				//如果服务器成功，则返回响应
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					//获取服务器响应字符串
					String result= EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 * 发送post请求
	 * @param url		发送请求的URL
	 * @param rawParams	请求参数
	 * @return			服务器响应字符串
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String postRequest(final String url,final Map<String,String> rawParams) throws InterruptedException, ExecutionException{
		FutureTask<String> task  = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				//创建HttpPost对象
				HttpPost post = new HttpPost(url);
				//如果传递参数个数比较多的话，可以对传递的参数进行封装
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for(String key:rawParams.keySet()){
					//封装请求参数
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				
				//设置请求参数
				post.setEntity(new UrlEncodedFormEntity(params,"gbk"));
				//发送POST请求
				HttpResponse httpResponse = httpClient.execute(post);
				//如果服务器响应字符串
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					//获取服务器你响应的字符串
					String result = EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		
		});
		new Thread(task).start();
		return task.get();
		
	}
	
}






























