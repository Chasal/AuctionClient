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
 * ���巽��getRequest()��postRequest()������������������������󣬷������ط���������Ӧ
 * @author Administrator
 *
 */
public class HttpUtil {
	
	//����HttpClient����
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "http://192.168.12.130:8080/auction/android/";
	
	/**
	 * ����get����
	 * @param url	���������URL
	 * @return		��������Ӧ�ַ���
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String getRequest(final String url) throws InterruptedException, ExecutionException{
		
		FutureTask<String> task = new  FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				//����HttpGet����
				HttpGet get = new HttpGet(url);
				//����get����
				HttpResponse httpResponse = httpClient.execute(get);
				//����������ɹ����򷵻���Ӧ
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					//��ȡ��������Ӧ�ַ���
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
	 * ����post����
	 * @param url		���������URL
	 * @param rawParams	�������
	 * @return			��������Ӧ�ַ���
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String postRequest(final String url,final Map<String,String> rawParams) throws InterruptedException, ExecutionException{
		FutureTask<String> task  = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				//����HttpPost����
				HttpPost post = new HttpPost(url);
				//������ݲ��������Ƚ϶�Ļ������ԶԴ��ݵĲ������з�װ
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for(String key:rawParams.keySet()){
					//��װ�������
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				
				//�����������
				post.setEntity(new UrlEncodedFormEntity(params,"gbk"));
				//����POST����
				HttpResponse httpResponse = httpClient.execute(post);
				//�����������Ӧ�ַ���
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					//��ȡ����������Ӧ���ַ���
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






























