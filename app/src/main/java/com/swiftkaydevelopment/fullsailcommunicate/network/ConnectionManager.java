/*
 *      Copyright (C) 2015 Kevin Haines
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.swiftkaydevelopment.fullsailcommunicate.network;

import android.util.Log;

import com.swiftkaydevelopment.fullsailcommunicate.BuildConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private String method;
    private String uri;
    private boolean useJson;
    private String baseUrl = "http://www.swiftkay.com/fullsail/";
    private Map<String,String> params = new HashMap<>();;

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String UTF8 = "UTF-8";


    public ConnectionManager(){
        setMethod(POST);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUri(String uri) {
        this.uri = baseUrl + uri;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void addParam(String key, String val){
        log("addParam Key->" + key + " val->" + val);
        params.put(key, val);
    }

    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String val = null;
            try {
                val = URLEncoder.encode(params.get(key), UTF8);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + val);
        }

        return sb.toString();
    }

    public String sendHttpRequest(){

        addParam("version_code", Integer.toString(BuildConfig.VERSION_CODE));

        if(this.uri == null){
            err("Can't connect - missing URI");
            return null;
        }

        BufferedReader reader = null;
        HttpURLConnection con = null;

        try{
            if(getMethod().equals(GET)){
                if(params != null) {
                    uri += "?" + getEncodedParams();
                    log("getting params");
                }
                log("no params set");
            }

            log(uri);
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(getMethod());
            con.setDoInput(true);
            log("Request Method is: " + getMethod());

            //POST METHOD
            if (getMethod().equals(POST)) {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());

                if (useJson) {
                    JSONObject jobj = new JSONObject(getParams());
                    String params = "params=" + jobj.toString();
                    writer.write(params);
                } else {
                    writer.write(getEncodedParams());
                }
                writer.flush();
            }

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line = reader.readLine()) !=null){
                sb.append(line);
            }

            log("http request result: " + sb.toString());
            return  sb.toString();
        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    private void err(String error){
        Log.e("httprequest", error);
    }

    public void uploadFile(URL connectURL, String pathToFile, String uid, String text){
        String iFileName = pathToFile.replaceAll("[^a-zA-Z0-9]", "");
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag="fSnd";

        try {
            FileInputStream fileInputStream = new FileInputStream(pathToFile);
            Log.e(Tag,"Starting Http File Sending to URL");

            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uid\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(uid);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(text);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + iFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag,"Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();

            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[ ] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0,bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();

            dos.flush();

            Log.e(Tag,"File Sent, Response: " + String.valueOf(conn.getResponseCode()));

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
            Log.e(Tag, sb.toString());
            dos.close();
        } catch (MalformedURLException ex) {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
        }
    }

    private void log(String msg){
        Log.d("httpRequest", msg);
    }
}
