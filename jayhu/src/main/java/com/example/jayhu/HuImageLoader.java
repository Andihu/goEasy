package com.example.jayhu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HuImageLoader {

    private static HuImageLoader instences;
    private String name;
    private String path;
    private Bitmap finalBitmap;

    public static synchronized HuImageLoader getInstences() {
        if (instences == null) {
            instences = new HuImageLoader();
        }
        return instences;
    }

    private HuImageLoader() {}

    private Context mContext;

    private static int FRIST_CACHE_CAPACITY = 20;

    public HuImageLoader with(Context context) {
        this.mContext = context;
        return this;
    }

    private LinkedHashMap<String, Bitmap> firstCache = new LinkedHashMap<String, Bitmap>(FRIST_CACHE_CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if (this.size() > FRIST_CACHE_CAPACITY) {
                sencondCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                return true;
            }
            return super.removeEldestEntry(eldest);
        }
    };


    private ConcurrentHashMap<String, SoftReference<Bitmap>> sencondCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();


    public Bitmap Load(String key,ImageView imageView) {

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        //从一级缓存中查找

        finalBitmap = getFromCache(key);

        if (finalBitmap != null) {
            imageView.setImageBitmap(finalBitmap);
            return finalBitmap;
        } else {
            finalBitmap = getFromDisk(key);
            if (finalBitmap != null) {
                addFristCache(key, finalBitmap);
                imageView.setImageBitmap(finalBitmap);
                return finalBitmap;
            }
            DownLoad downLoad=new DownLoad(imageView);
            downLoad.execute(key);
        }
        return null;

    }

    private Bitmap download(String key) {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            connection = (HttpURLConnection) new URL(key).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is = connection.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {

                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    private void addFristCache(String key, Bitmap finalBitmap) {
        firstCache.put(key, finalBitmap);
    }

    private void addDisk(String key, Bitmap value) {
        FileOutputStream fos = null;
        name = MD5Utils.md5(key);
        path = mContext.getCacheDir().getAbsolutePath() + File.separator + name;
        try {
            fos = new FileOutputStream(path);
            value.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private Bitmap getFromCache(String key) {
        Bitmap bitmap = firstCache.get(key);
        if (bitmap != null) {
            firstCache.remove(key);
            firstCache.put(key, bitmap);
            return bitmap;
        }
        SoftReference<Bitmap> bitmapSoftReference = sencondCache.get(key);

        if (bitmapSoftReference != null) {
            firstCache.put(key, bitmapSoftReference.get());
            return bitmapSoftReference.get();
        } else {
            sencondCache.remove(key);
        }
        return null;
    }

    private Bitmap getFromDisk(String key) {
        name = MD5Utils.md5(key);
        path = mContext.getCacheDir().getAbsolutePath() + File.separator + name;

        File file = new File(path);
        if (!file.exists())
            return null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    class DownLoad extends AsyncTask<String,Void,Bitmap>{

        ImageView imageView;
        String key;
        public DownLoad(ImageView imageView) {
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            key=strings[0];
            return download(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            finalBitmap=bitmap;
            addFristCache(key, bitmap);
            addDisk(key,bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
