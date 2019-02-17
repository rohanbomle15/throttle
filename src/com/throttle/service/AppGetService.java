package com.throttle.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.throttle.utils.Util;

public class AppGetService {

	private AppGetServiceListener listener = null;

	private String mURL = null;
	private Map<String, String> mParams = new HashMap<String, String>();

	public AppGetService(AppGetServiceListener mListener) {
		this.listener = mListener;
	}

	public void callService(String url, Map<String, String> params) {
		mURL = url;
		mParams = params;
		new serviceAsyncTask().execute("Excecute");
	}

	public static interface AppGetServiceListener {
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
			try {
				listener.onServiceComplete(true, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			String serviceResult = "";
			HttpClient Client = new DefaultHttpClient();
			try {
				HttpGet httpget = new HttpGet(mURL);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				serviceResult = Client.execute(httpget, responseHandler);
				System.out.println(serviceResult);
				return serviceResult;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Util.closeProgressDialog();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Util.closeProgressDialog();
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
