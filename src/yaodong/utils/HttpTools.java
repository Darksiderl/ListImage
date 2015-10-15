package yaodong.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
public final class HttpTools {
    private HttpTools(){
    }

    public static String sendByPost(String path,HashMap<String,String> params,String encode) throws IOException
    {
        //把要提交的数据组织成username=kk&psw=111格式
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, String> en:params.entrySet())
        {
            stringBuilder.append(en.getKey())
                    .append("=")
                    .append(URLEncoder.encode(en.getValue(), encode))
                    .append("&");
        }

        //删除最后的 &符号
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        //设置提交的数据的 类型
        conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");

        //设置提交的数据的长度
        byte[] b = stringBuilder.toString().getBytes("utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(b.length));

        //提交数据 ---向服务器端写入数据
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(b,0,b.length);
        outputStream.close();

        InputStream in = null;
        if(conn.getResponseCode()==200)
        {
            in = conn.getInputStream();
            return getResult(in,encode);
        }
        return null;
    }

    //读取服务器端的验证结果
    private static String getResult(InputStream in, String encode) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        int len = 0;
        while((len = in.read(arr))!=-1)
        {
            bos.write(arr,0,len);
        }
        byte[] b = bos.toByteArray();
        return new String(b,encode);
    }



    public static byte[] doGet(String url){
        byte[] ret = null;
        if(url != null){
            HttpURLConnection conn = null;
            try {
                URL u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();
                conn.connect();
                if(conn.getResponseCode() == 200){
                    //TODO 给data赋值
                    InputStream is=null;
                    try {
                        is =  conn.getInputStream();
                        ret = StreamUtil.readStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                       StreamUtil.close(is);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                StreamUtil.close(conn);
            }
        }
        return ret;
    }
}
