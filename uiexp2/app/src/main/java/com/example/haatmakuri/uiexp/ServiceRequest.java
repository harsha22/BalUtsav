package com.example.haatmakuri.uiexp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;

/**
 * Created by haatmakuri on 11/13/15.
 */
public class ServiceRequest {


        static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";

        private String requestType = "GET";
        private HttpURLConnection conn = null;

        public ServiceRequest(String type) {
                this.requestType=type;
        }

        public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
            try {
                URL urlToRequest = new URL(url);
                conn = (HttpURLConnection)urlToRequest.openConnection();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "n");
                }
                is.close();
                json = sb.toString();
                Log.e("JSON", json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }


            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }


            return jObj;

        }
        JSONObject jobj;
        public JSONObject getJSON(String url, List<NameValuePair> params) {

            Params param = new Params(url,params);
            Request myTask = new Request();
            try{
                jobj= myTask.execute(param).get();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }catch (ExecutionException e){
                e.printStackTrace();
            }
            return jobj;
        }


        private static class Params {
            String url;
            List<NameValuePair> params;


            Params(String url, List<NameValuePair> params) {
                this.url = url;
                this.params = params;

            }
        }

        private class Request extends AsyncTask<Params, String, JSONObject> {

            @Override
            protected JSONObject doInBackground(Params... args) {

                ServiceRequest request = new ServiceRequest("");
                JSONObject json = request.getJSONFromUrl(args[0].url,args[0].params);

                return json;
            }

            @Override
            protected void onPostExecute(JSONObject json) {

                super.onPostExecute(json);

            }

        }
}

