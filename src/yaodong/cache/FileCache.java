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
//1.�ֻ��ڲ��洢CacheĿ¼�����ֻ��ڲ��洢����֮��Androidϵͳ���Զ����Cache Ŀ¼���ļ���
//2.�����Ŀ¼����ʹ�ô洢���洢����Ϊ�ռ��  mnt/storage
//3.Android ϵͳ��Ӧ�ó����ڴ洢���� ��Ҳ������һ�׳���˽�е�Ŀ¼  Ҳ���������ļ���Cache��Ҫ������
public final class FileCache {


    private static FileCache ourInstance;
    public static FileCache newInstance(Context context){
        if(context!=null){

//            if(s == null){
//                //ͬ��������õ����ǵ������ֽ����ļ�������ֻ���������
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


        //���ջ�ȡ�������ļ�����Ŀ¼ Context.getExternalCacheDir()���Ի�ȡ���洢����  Ӧ�ó���˽�еĻ���Ŀ¼
        //Context.getCacheDir()  ��ȡ�ֻ��ڲ��洢��˽�е� ����Ŀ¼
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
            //���ջ�ȡ�������ļ�����Ŀ¼ Context.getExternalCacheDir()���Ի�ȡ���洢����  Ӧ�ó���˽�еĻ���Ŀ¼
            //Context.getCacheDir()  ��ȡ�ֻ��ڲ��洢��˽�е� ����Ŀ¼
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
