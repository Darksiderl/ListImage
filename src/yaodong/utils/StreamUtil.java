package yaodong.utils;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
public final class StreamUtil {
    //io刘的工具类
    private StreamUtil(){

    }

    /**
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readStream(InputStream in) throws IOException {
        byte[] ret = null;
        if(in != null){
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int len;
            while(true){
                len = in.read(buf);
                if(len == -1){
                    break;
                }
                bout.write(buf,0,len);
                //buf  必须要进行 = null的操作
            }
            buf = null;//用的太多之后不会释放  gc无法管理  减少内存溢出的可能性
            ret= bout.toByteArray();
            bout.close();
        }
        return ret;
    }

    public static void close(Object stream){
        if(stream!=null){
            try{
                if(stream instanceof InputStream){
                    ((InputStream) stream).close();
                }else if(stream instanceof OutputStream){
                    ((OutputStream) stream).close();
                }else if (stream instanceof Writer){
                    ((Writer) stream).close();
                }else if(stream instanceof Reader){
                    ((Reader) stream).close();
                }else if(stream instanceof HttpURLConnection){
                    ((HttpURLConnection) stream).disconnect();
                }


            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
