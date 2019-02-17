package com.throttle.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class AppMultipartService {

	private AppMultipartServiceListener listener = null;

	private String mURL = null;
	private Map<String, String> mParams = new HashMap<String, String>();
	private Bitmap thePic;
	private String picName;

	public AppMultipartService(AppMultipartServiceListener mListener) {
		this.listener = mListener;
	}

	public void callService(String url, Map<String, String> params,
			Bitmap mThePic, String mPicName) {
		mURL = url;
		mParams = params;
		thePic = mThePic;
		picName = mPicName;
		new serviceAsyncTask().execute("Excecute");
	}

	public static interface AppMultipartServiceListener {
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
			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				thePic.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] imageBytes = baos.toByteArray();

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(mURL);

				httpPost.setHeader("enctype", "multipart/form-data");
				ByteArrayBody bab = new ByteArrayBody(imageBytes, picName);

				MultipartEntity entity = new MultipartEntity();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				JSONObject json = new JSONObject();
				for (Map.Entry<String, String> entry : mParams.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
							entry.getValue()));
					entity.addPart(entry.getKey(),
							new StringBody(entry.getValue()));
					// json.put(entry.getKey(), entry.getValue());
				}
				entity.addPart("a", bab);

				httpPost.setEntity(entity);
				HttpResponse response = httpclient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				// InputStream is = entity.getContent();
				// serviceResult = convertStreamToString(is);
				if (statusCode == HttpStatus.SC_OK) {
					InputStream is = entity.getContent();
					serviceResult = convertStreamToString(is);
				} else {
					serviceResult = "Opps!..Something went wrong";
				}
				return serviceResult;
			} catch (Exception e) {
				// TODO Auto-generated catch block;
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
