package com.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/8
 */
public class ImageStorage {
    public static final String CACHEDIR = Environment.getExternalStorageDirectory()+"/images";
    public static final int FORMAT_PNG = 1;
    public static final int FORMAT_JPEG = 2;

    public static boolean isMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static void saveImage(String url,Bitmap bitmap) throws IOException {
        if (!isMounted()) {
            return;
        }
        File dir = new File(CACHEDIR);
        if(!dir.exists()){
            dir.mkdirs();
        }

        byte[] bytes = bitmap.toString().getBytes();
        FileOutputStream fos = new FileOutputStream(new File(dir,getName(url)));
        fos.write(bytes);
        fos.close();

    }
    public static Bitmap getImg(String url) throws FileNotFoundException{
        if (!isMounted()) {
            return null;
        }
        String filename = getName(url);
        File file = new File(CACHEDIR, filename);
        Bitmap bitmap = null;
        if(file.exists())
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }
    public static String getName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

    public static String getName(String url,int end){
        return url.substring(url.lastIndexOf("/")+1,end);
    }
}
