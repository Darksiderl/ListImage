package yaodong.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/13
 */



public final class ImageCache {
    private static LruCache<String,Bitmap> lruCache;//ǿ���û��棬һ�����棬�ص㣺ʹ���������ʹ�õ��㷨�����������Ƴ���Ϊ�������ṩ�ռ�
    private static Map<String,SoftReference<Bitmap>> softCache; //�����û��棬��������



}




