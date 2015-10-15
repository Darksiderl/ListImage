package yaodong.utils;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密工具类
 */
public final class EncryptUtils {
    private EncryptUtils(){

    }

    /**
     * 将网址返回为MD5加密后影射为的文件名
     * @param url
     * @return
     */
    public static String mapFile(String url){
        String ret = null;
        if(url!=null){
            try {
                //创建消息摘要对象使用MD5算法
                MessageDigest digest = MessageDigest.getInstance("MD5");//MessageDigest.getInstance("SHA1");

                //计算url对应的MD5，生成的数据是字节数组 内部包含不可显示的字节，需要进行编码，才能转换为字符串，不能new
                //不要使用 new String（byte[]）!!!需要转换成16进制内容
                byte[] data = digest.digest(url.getBytes());
                //byte[]  每一个字节 转换为16进制表示法，并且拼接成一个字符串 Hex编码
                //0x3C - "3C"
                String hex = toHex(data);

                ret = hex;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 将字节数组转换为16进制编码的字符串
     * @param data
     * @return
     */
    public static String toHex(byte[] data){
        String ret = null;

        if(data != null && data.length>0){
            StringBuilder sb = new StringBuilder();

            for (byte b:data) {
                int v =  b & 0x0FF;
                String hexString = Integer.toHexString(v);
                if(v > 0x0F){

                    sb.append(hexString);

                }else{
                    sb.append('0').append(hexString);
                }
            }
            ret = sb.toString();
        }
        return  ret;
    }
}
