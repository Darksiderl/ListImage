package yaodong.asynctask;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
public class AsyncDrawble extends BitmapDrawable {
    //真正下载图片的部分
    private final WeakReference<LoadTask> taskWeakReference;

    public AsyncDrawble(Resources res,Bitmap bitmap,LoadTask task){
        super(res,bitmap);
        taskWeakReference = new WeakReference<LoadTask>(task);
    }

    /**
     * 获取当前的Drawable包含的异步任务
     * @return
     */
    public LoadTask getLoadTask(){
        LoadTask ret = null;
        ret = taskWeakReference.get();
        return ret;
    }
}
