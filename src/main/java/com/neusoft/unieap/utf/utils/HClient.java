package com.neusoft.unieap.utf.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jim on 2017/7/5.
 * This class is ...
 */
public class HClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(HClient.class);

	public static int post(String baseURL, String URI, Map<String, String> header, String entity) {
		String apiURL = baseURL + URI;
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(apiURL);

		for (String key : header.keySet()) {
			post.addHeader(key, header.get(key));
		}

		int statusCode = 500;
		try {
			StringEntity stringEntity = new StringEntity(entity, "application/json", "UTF-8");

			post.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(post);
			statusCode = response.getStatusLine().getStatusCode();
			LOGGER.debug(String.valueOf(statusCode));
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			LOGGER.debug(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//httpClient.getConnectionManager().shutdown();
		}

		return statusCode;
	}

	public static int get(String baseURL, String URI, Map<String, String> args, Boolean isQueryString) {
		String apiURL = getAPIURL(baseURL, URI, args, isQueryString);
		int statusCode = 500;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(apiURL);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			while ((output = br.readLine()) != null) {
				LOGGER.debug(output);
			}

			//httpClient.getConnectionManager().shutdown();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return statusCode;
	}

	private static String parseArgs(Map<String, String> args, Boolean isQueryString) {
		StringBuilder stringBuilder = new StringBuilder("/");
		if (isQueryString) stringBuilder.append("?");
		for (String key : args.keySet()) {
			if (isQueryString) {
				stringBuilder.append(key).append("=").append(args.get(key)).append("&");
			} else {
				stringBuilder.append(key).append("/").append(args.get(key));
			}
		}

		return stringBuilder.toString();
	}

	private static String getAPIURL(String baseURL, String URI, Map<String, String> args, Boolean isQueryString) {
		StringBuilder apiURL = new StringBuilder();
		apiURL.append(baseURL).append(URI);
		apiURL.append(parseArgs(args, isQueryString));
		return apiURL.toString();
	}

	public static Map<String, String> getRequestHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		return headers;
	}
}
