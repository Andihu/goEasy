package com.example.jayhu;

import android.net.UrlQuerySanitizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    private static HttpUtil instence;
    private HttpUtil() {
    }

    public static synchronized HttpUtil getInstences(){
        if (instence==null){
            instence=new HttpUtil();
        }
        return instence;
    }


    public  InputStream downLoad(String key) throws IOException {
        HttpURLConnection connection= (HttpURLConnection) new URL(key).openConnection();
        return connection.getInputStream();
    }


    public byte[] getByteArrayFromWeb(String key)  {
        byte[] b=null;
        InputStream is=null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            URL url=new URL(key);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            if (connection.getResponseCode()== HttpURLConnection.HTTP_OK){
                byteArrayOutputStream=new ByteArrayOutputStream();
                is=connection.getInputStream();
                byte[] tempbyte=new byte[1024];
                int length=0;
                while((length=is.read(tempbyte))!=-1){
                    byteArrayOutputStream.write(tempbyte,0,length);
                }
            }
            b=byteArrayOutputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
            if (is!=null){
                    is.close();
            }
            if (byteArrayOutputStream!=null){
                byteArrayOutputStream.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return b;
    }

}
