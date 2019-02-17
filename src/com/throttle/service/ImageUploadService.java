package com.throttle.service;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageUploadService {

	private Bitmap uploadThisPic;
	private String uploadURL;

	public ImageUploadService(Bitmap picToUpload,String urlToUpload) {
		uploadThisPic = picToUpload;
		uploadURL = urlToUpload;
	}

	public class serviceTempAsyncTask extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = postData(params[0]);
			return result;
		}

		protected void onPostExecute(String result) {
			try {

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
				uploadThisPic.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] imageBytes = baos.toByteArray();

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://throttledev.talentick.com/FileUploader.aspx");

				httpPost.setHeader("enctype", "multipart/form-data");

				ByteArrayBody bab = new ByteArrayBody(imageBytes, "pic.png");

				MultipartEntity entity = new MultipartEntity();
				entity.addPart("data", bab);
				httpPost.setEntity(entity);
				HttpResponse response = httpclient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				// InputStream is = entity.getContent();
				// serviceResult = convertStreamToString(is);
				System.out.println("Service Respone : " + statusCode + ""
						+ serviceResult);
				return serviceResult;
			} catch (Exception e) {
				// TODO Auto-generated catch block;
				e.printStackTrace();
			}
			return serviceResult;
		}
	}
}
