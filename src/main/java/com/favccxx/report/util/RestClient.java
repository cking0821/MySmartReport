package com.favccxx.report.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class RestClient {

	
	public static String rest(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String restData = "";

		HttpGet httpGet = new HttpGet(url);

		// 设置Header，处理认证、传参等信息
		httpGet.setHeader("TOKEN", "ekyoffgFN5hUxD4YPxvirrxQ1tFLJxlZu1UMSrv77d3YTDADdUdbLabHVRQNIqfe");

		CloseableHttpResponse response = null;

		try {
			response = httpclient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			restData = JSON.toJSONString(result);
		} finally {
			response.close();
		}

		return restData;
	}
	
	public static String rest(String url, Map<String, Object> headers) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String restData = "";

		HttpGet httpGet = new HttpGet(url);

		// 设置Header，处理认证、传参等信息
		for (String key : headers.keySet()) {
			httpGet.setHeader(key, (String)headers.get(key));
		}

		CloseableHttpResponse response = null;

		try {
			response = httpclient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			restData = JSON.toJSONString(result);
		} finally {
			response.close();
		}

		return restData;
	}
	
	
//	public static String rest(String url, String[] headers) throws ClientProtocolException, IOException {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		String restData = "";
//
//		HttpGet httpGet = new HttpGet(url);
//
//		// 设置Header，处理认证、传参等信息
//		for(int i=0, l=headers.length; i<l; i++) {
//			String[] params = headers[i].split(":");
//			httpGet.setHeader(params[0], params[1]);
//		}		
//
//		
//		
//		CloseableHttpResponse response = null;
//
//		try {
//			response = httpclient.execute(httpGet);
//			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
//			restData = JSON.toJSONString(result);
//		} finally {
//			response.close();
//		}
//
//		return restData;
//	}
	
	
	public static void main(String args[]) {
		String url = "http://172.17.100.130/dash/api/apps/paginate";
		
//		String[] header = {
//				"TOKEN:ekyoffgFN5hUxD4YPxvirrxQ1tFLJxlZu1UMSrv77d3YTDADdUdbLabHVRQNIqfe"
//		};
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("TOKEN", "ekyoffgFN5hUxD4YPxvirrxQ1tFLJxlZu1UMSrv77d3YTDADdUdbLabHVRQNIqfe");
		try {
			String res = RestClient.rest(url, header);
			System.out.println(res);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
