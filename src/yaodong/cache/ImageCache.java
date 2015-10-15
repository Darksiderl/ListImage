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
    private static LruCache<String,Bitmap> lruCache;//强引用缓存，一级缓存，特点：使用最近最少使用的算法，将旧数据移除，为新数据提供空间
    private static Map<String,SoftReference<Bitmap>> softCache; //软引用缓存，二级缓存



}




