package com.xy.rcs.goeasy.utils;

import android.content.Context;

import android.content.Intent;
import android.widget.Toast;


import com.xy.rcs.goeasy.RepertoryActivity;

import org.json.JSONArray;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



import static android.support.v4.content.ContextCompat.startActivity;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/2/002<p>
 * <p>更改时间：2018/12/2/002<p>
 * <p>版本号：1<p>
 */
public class Utils {

    int Rseult;

    public Utils() {

        final int[] i = {0};
        new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                  new thread(new ThreadCall() {
                      @Override
                      public void call(int result) {
                          Rseult=result;
                      }
                  }).start();

                    System.out.println(Rseult);
                }
        }).start();
    }


    public static void startFragment(Context context,int productFragment,String title) {
        Intent mIntent= new Intent(context, RepertoryActivity.class);
        mIntent.putExtra("fragment_code", productFragment);
        mIntent.putExtra("title", title);
        startActivity(context,mIntent,null);
    }


    JSONObject temp;
    List<JSONObject> mAppliances=new ArrayList<>();

    private static final String TAG = "Utils";

    public static final void Toast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static int dip2px(Context context,float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale+0.5f);
    }

    public static List<JSONArray> jsonObject2jsonArray( List<JSONObject> jsonObjects){
        List<JSONArray> jsonArrays=new ArrayList<>();
        JSONArray jsonArray;
        jsonArray=new JSONArray();
        for (JSONObject js:jsonObjects) {
            if (jsonArray.length()<2){
                jsonArray.put(js);
            }else {
                jsonArrays.add(jsonArray);
                jsonArray=new JSONArray();
                jsonArray.put(js);
            }
        }
        return  jsonArrays;

    }

    class  thread extends Thread{
        ThreadCall call;
        public thread(ThreadCall call) {
            this.call=call;
        }

        @Override
        public void run() {
            super.run();
            int i=0;
            while (i++<10){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (call!=null){
                    call.call(i);
                }
                System.out.println(Thread.currentThread().getName()+":i");
            }
        }
    }

    interface ThreadCall{
        void call(int result);
    }

}
