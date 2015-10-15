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
 * ���ܽ��ܹ�����
 */
public final class EncryptUtils {
    private EncryptUtils(){

    }

    /**
     * ����ַ����ΪMD5���ܺ�Ӱ��Ϊ���ļ���
     * @param url
     * @return
     */
    public static String mapFile(String url){
        String ret = null;
        if(url!=null){
            try {
                //������ϢժҪ����ʹ��MD5�㷨
                MessageDigest digest = MessageDigest.getInstance("MD5");//MessageDigest.getInstance("SHA1");

                //����url��Ӧ��MD5�����ɵ��������ֽ����� �ڲ�����������ʾ���ֽڣ���Ҫ���б��룬����ת��Ϊ�ַ���������new
                //��Ҫʹ�� new String��byte[]��!!!��Ҫת����16��������
                byte[] data = digest.digest(url.getBytes());
                //byte[]  ÿһ���ֽ� ת��Ϊ16���Ʊ�ʾ��������ƴ�ӳ�һ���ַ��� Hex����
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
     * ���ֽ�����ת��Ϊ16���Ʊ�����ַ���
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
