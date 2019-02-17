package com.throttle.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AppService {

	private AppServiceListener listener = null;

	private String mURL = null;
	private Map<String, String> mParams = new HashMap<String, String>();

	public AppService(AppServiceListener mListener) {
		this.listener = mListener;
	}

	public void callService(String url, Map<String, String> params) {
		mURL = url;
		mParams = params;
		new serviceAsyncTask().execute("Excecute");
	}

	public static interface AppServiceListener {
		public void onServiceComplete(Boolean success, String jsonObj);

		public void onServiceError(String error);
	}

	private class serviceAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = postData(params[0]);
			return result;
		}

		protected void onPostExecute(String result) {
			try{
			listener.onServiceComplete(true, result);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			String serviceResult = "";
			HttpParams httpParameters = new BasicHttpParams(); 
			int timeoutConnection = 10000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			int timeoutSocket = 5000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(mURL);
			
			httppost.setHeader("Content-Type", "application/json");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				JSONObject json = new JSONObject();
				for (Map.Entry<String, String> entry : mParams.entrySet()) {
					System.out.println("Key : " + entry.getKey() + " Value : "
							+ entry.getValue());
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
							entry.getValue()));
					json.put(entry.getKey(), entry.getValue());
				}
				// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				StringEntity se = new StringEntity(json.toString());
				httppost.setEntity(se);

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					InputStream is = entity.getContent();
					serviceResult = convertStreamToString(is);
				} else {
					InputStream is = entity.getContent();
					serviceResult = convertStreamToString(is);
					serviceResult = "Opps!..Something went wrong";
				}
				return serviceResult;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return serviceResult;
		}

	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
