package yaodong.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import yaodong.cache.FileCache;
import yaodong.utils.HttpTools;

import java.lang.ref.WeakReference;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/12
 */
public class LoadTask extends AsyncTask<String,Integer,Bitmap> {

    /**
     * 使用弱引用来进行ImageView对象的引用当UI销毁任务不再使用ImageView
     */
    private int requestWidth;
    private int requestHeight;
    private final WeakReference<ImageView> imageViewWeakReference;

    public LoadTask(ImageView imageView,int reqWidth,int reqHeight){
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        requestWidth = reqWidth;
        requestHeight = reqHeight;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap ret = null;

        if (params != null && params.length>0) {
            String url =params[0];

            //获取url中对用的文件缓cun

            byte[] data = FileCache.getInstance().load(url);

            if (data != null) {
                //TODO  有文件数据，那么不需要下载，更新UI
            }else{
                //TODO 网址 联网下载 取数据 存文件  生成Bitmap 更新UI
                data = HttpTools.doGet(url);
                FileCache.getInstance().save(url,data);

            }
            if (data != null) {
                //TODO  有文件数据，那么不需要下载，更新UI

                //按照原始的图片尺寸 进行Bitmap 的生成

//                ret = BitmapFactory.decodeByteArray(data,0,data.length);

                //缩小图片尺寸的方式
                //1.获取原始图片的宽高 进行采样的计算
                //1.1创建Options，给BitmapFactory 的内部解码器传递参数
                BitmapFactory.Options option = new BitmapFactory.Options();
                //1.2设置inJustDecodeBounds 来控制解码器 ，只进行宽高的获取，不会加载Bitmap
                //不占用内存，当使用这个参数，代表BitmapFactory.decodeXxxx类似的方法 不会返回Bitmap
                option.inJustDecodeBounds = true;
                //解码 使用option 来设置参数 解码方式  in传参  out取参数
                BitmapFactory.decodeByteArray(data, 0, data.length, option);

                //-----------------------------------------------
                //2.步骤2 根据图片的真实尺寸和客户端的要求当前显示的尺寸，进行计算生成图片采样率
                //2.1
                int picw = option.outWidth;  //6000
                int pich = option.outHeight;//4000
                //2.2  准备显示在手机上的尺寸 256x128  128x64
                //尺寸是根据程序需要设计的
                //maxWidth  ,maxHeight
                int reqW = requestWidth;

                int reqH = requestHeight;//测试数据 实际宽高

                //2.3  计算 设置 图片采样率
                //采样的概念 ：1 1--1  2 4--1 缩小了四个像素被采样成一个像素点
                option.inSampleSize = calculateInSampleSize(option,reqW,reqH);//宽度的1/32  高度的1/32
                //2.4开放解码 实际生成Bitmap
                option.inJustDecodeBounds = false;
                //2.4.1 BitmapConfig的说明
                //要求解码器对每个采样的像素 使用 RGB_565存储方式
                //一个像素，占用两个字节，  比ARGB_8888小了一半
                //如果解码器不能够使用指定配置，就自动使用ARGB_8888
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                //2.4.2 过时的设置
                option.inPurgeable = true;
                //2.5使用设置采样的参数，来进行解码 获取Bitmap
                ret = BitmapFactory.decodeByteArray(data, 0, data.length, option);
            }
        }

        return ret;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {

            ImageView imageView = imageViewWeakReference.get();//获取弱引用包含的对象，可能为null
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 计算图片二次采样的采样率 使用获取图片宽高之后的option作为第一个参数 并且通过请求的 宽度高度尺寸进行采样率的计算
     * @param options
     * @param reqWidth 请求的宽度
     * @param reqHeight 请求的高度
     * @return int 采样率
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
