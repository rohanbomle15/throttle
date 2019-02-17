package com.throttle.utils;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



public class ContentDownloader {

    public static String getHttpPutContent(String url, String token, MultipartEntity multipartEntity) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(url);
//            put.setHeader("token", token);
//            Log.i(TAG, "Token=> " + token);
            

            MultipartEntity reqEntity = multipartEntity;
            put.setEntity(reqEntity);

            HttpResponse httpResponse = httpClient.execute(put);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            

            HttpEntity entity = httpResponse.getEntity();
            String serverResponse = EntityUtils.toString(entity);
            

            if(!isStatusOk(statusCode))
                return null;

            return serverResponse;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    } 

    /**
     *
     * @param statusCode
     * @return True if connection code is 2xx, False otherwise.
     */
    private static boolean isStatusOk(int statusCode) {
        if((statusCode >= HttpURLConnection.HTTP_OK)  &&  (statusCode <= HttpURLConnection.HTTP_PARTIAL))
            return true;

        return false;
    }
}