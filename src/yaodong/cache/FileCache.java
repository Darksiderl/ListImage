package yaodong.cache;

import android.content.Context;
import android.os.Environment;
import yaodong.utils.EncryptUtils;
import yaodong.utils.StreamUtil;

import java.io.*;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
//1.手机内部存储Cache目录，当手机内部存储满了之后，Android系统会自动清空Cache 目录的文件；
//2.缓存的目录优先使用存储卡存储；因为空间大  mnt/storage
//3.Android 系统给应用程序在存储卡上 ，也创建了一套程序私有的目录  也包含缓存文件夹Cache需要上下文
public final class FileCache {


    private static FileCache ourInstance;
    public static FileCache newInstance(Context context){
        if(context!=null){

//            if(s == null){
//                //同步代码块用的锁是单例的字节码文件对象，且只能用这个锁
//                synchronized(SingleDemo.class){
//                    if(s == null){
//                        s = new SingleDemo();
//                    }
//                }
//            }

            if(ourInstance==null){
                synchronized (FileCache.class) {
                    if(ourInstance == null) {
                        ourInstance = new FileCache(context);
                    }
                }
            }
            else{
                throw new IllegalArgumentException("Context must be set");
            }

        }
        return ourInstance;
    }



    public static FileCache getInstance() {
        if(ourInstance == null){
            throw new IllegalStateException("newInstance invoke");
        }
        return ourInstance;
    }
    private Context context;
    private FileCache(Context context) {
        this.context = context;
    }

    /**
     *
     * @param url
     * @return
     */
    public byte[] load(String url){
        byte[] ret = null;


        //最终或取出来的文件缓存目录 Context.getExternalCacheDir()可以获取，存储卡中  应用程序私有的缓存目录
        //Context.getCacheDir()  获取手机内部存储中私有的 缓存目录
        if(url!=null) {
            File cacheDir = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                cacheDir = context.getExternalCacheDir();
            } else {
                cacheDir = context.getCacheDir();
            }

            String fileName = EncryptUtils.mapFile(url);

            File targetFile = new File(cacheDir, fileName);

            if (targetFile.exists()) {

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(targetFile);

                    ret = StreamUtil.readStream(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtil.close(fis);
                }

            }
        }
          return ret;
    }

    /**
     *
     * @param url
     * @param bytes
     */
    public void save(String url,byte[] bytes){
        if(url!=null&&bytes!=null) {
            //最终或取出来的文件缓存目录 Context.getExternalCacheDir()可以获取，存储卡中  应用程序私有的缓存目录
            //Context.getCacheDir()  获取手机内部存储中私有的 缓存目录
            File cacheDir = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                cacheDir = context.getExternalCacheDir();
            }else{
                cacheDir = context.getCacheDir();
            }

            String fileName = EncryptUtils.mapFile(url);

            File targetFile = new File(cacheDir,fileName);

            FileOutputStream fout = null;

            try {
                fout = new FileOutputStream(targetFile);
                fout.write(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(fout);
            }
        }
    }

}
