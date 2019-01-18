package com.example.jayhu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import java.util.concurrent.ConcurrentHashMap;

public class JayHuLoader {
    private static Context mContext;

    private static JayHuLoader jayHuLoader;

    public static synchronized JayHuLoader getInstence(){
        if (jayHuLoader!=null){
            return jayHuLoader;
        }
        jayHuLoader= new JayHuLoader();
        return jayHuLoader;
    }


    private JayHuLoader() {

    }

    public static JayHuLoader with(Context context){
        mContext = context;
        return getInstence();
    }


    //一级缓存的容量
    private static final int MAX_CAPACITY=20;

    //一级缓存，强引用缓存（内存）内存溢出异常
    //20张
    /**
     * loadFactor 加载因子
     * accessOrder 排序
     * true 访问排序
     * false 插入排序
     * LRU近期最少使用算法
     */
    private static LinkedHashMap<String,Bitmap> firstCacheMap=new LinkedHashMap<String,Bitmap>(MAX_CAPACITY,0.75f,true ){
        //根据返回值，移除map中最老的值
        @Override
        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if (this.size()>MAX_CAPACITY){
                //加入二级缓存
                secondCaceMap.put(eldest.getKey(),new SoftReference<Bitmap>(eldest.getValue()));
                //加入本地
                addDiskCaChe(eldest.getKey(),eldest.getValue());
                //移除一级缓存
                return true;
            }

            return false;
        }
    };

    /**
     * 本地缓存
     * @param key
     * @param value
     */
    protected static void addDiskCaChe(String key, Bitmap value) {

        String fileName=MD5Utils.md5(key);
        String path=mContext.getCacheDir().getAbsolutePath()+ File.separator+fileName;
        OutputStream os= null;
        try {
            os = new FileOutputStream(path);
            value.compress(Bitmap.CompressFormat.JPEG,100,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //二级缓存，软引用（内存）不足
    //超过20张
    private static ConcurrentHashMap<String ,SoftReference<Bitmap>> secondCaceMap=new ConcurrentHashMap<>();

    private DefualtImage defualtImage = new DefualtImage();

    //三级缓存：本地缓存（硬盘）
    //离线缓存
    //写入内部存储


    public void loadInmage(String key, ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        //读取缓存
        Bitmap bitmap=getFromCache(key);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }else {
            //访问网络

            imageView.setImageDrawable(defualtImage);

            //执行异步任务
            AsynImageLoadTask asynImageLoadTask=new AsynImageLoadTask(imageView);
            asynImageLoadTask.execute(key);
        }


    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    private Bitmap getFromCache(String key) {
        //从一级缓存中加载
        Bitmap bitmap=firstCacheMap.get(key);
        if (bitmap!=null){
            firstCacheMap.remove(bitmap);
            firstCacheMap.put(key,bitmap);
            return bitmap;
        }
        //从二级缓存中加载
        SoftReference<Bitmap> softReference=secondCaceMap.get(key);
        if (softReference!=null){
            firstCacheMap.put(key,bitmap);
            return bitmap;
        }else {
            //软引用被回收，则剔除
            secondCaceMap.remove(key);
        }
        //从本地中读取
        Bitmap local_bitmap=getFromDisk(key);
        if (local_bitmap!=null){
            firstCacheMap.put(key,bitmap);
            return local_bitmap;
        }

        return null;
    }

    private Bitmap getFromDisk(String key) {
        String fileName=MD5Utils.md5(key);
        if (fileName==null){
            return null;
        }
        String path=mContext.getCacheDir().getAbsolutePath()+ File.separator+fileName;

        Log.e("path", "getFromDisk: "+path );

        FileInputStream fis=null;
        try {
            fis=new FileInputStream(path);

            Bitmap bitmap=BitmapFactory.decodeStream(fis);

            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Bitmap downLoad(String key)  {
         InputStream is = null;
        try {
            is = HttpUtil.getInstences().downLoad(key);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    class DefualtImage extends ColorDrawable{
        public DefualtImage() {
            super(Color.GRAY);
        }
    }


    class AsynImageLoadTask extends AsyncTask<String,Void,Bitmap>{

        private String key;
        private ImageView imageView;

        public AsynImageLoadTask(ImageView imageView) {
            super();
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            key=strings[0];
            return downLoad(key);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //添加到一级缓存
            addFirstCache( key,bitmap );
            //放入disk

            imageView.setImageBitmap(bitmap);

        }
    }

    /**
     * 添加到一级缓存
     * @param key
     * @param bitmap
     */
    private void addFirstCache(String key, Bitmap bitmap) {
        if (key!=null){
            synchronized (firstCacheMap){
                firstCacheMap.put(key,bitmap);
            }
        }
    }
}
