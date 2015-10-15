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
     * ʹ��������������ImageView��������õ�UI����������ʹ��ImageView
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

            //��ȡurl�ж��õ��ļ���cun

            byte[] data = FileCache.getInstance().load(url);

            if (data != null) {
                //TODO  ���ļ����ݣ���ô����Ҫ���أ�����UI
            }else{
                //TODO ��ַ �������� ȡ���� ���ļ�  ����Bitmap ����UI
                data = HttpTools.doGet(url);
                FileCache.getInstance().save(url,data);

            }
            if (data != null) {
                //TODO  ���ļ����ݣ���ô����Ҫ���أ�����UI

                //����ԭʼ��ͼƬ�ߴ� ����Bitmap ������

//                ret = BitmapFactory.decodeByteArray(data,0,data.length);

                //��СͼƬ�ߴ�ķ�ʽ
                //1.��ȡԭʼͼƬ�Ŀ�� ���в����ļ���
                //1.1����Options����BitmapFactory ���ڲ����������ݲ���
                BitmapFactory.Options option = new BitmapFactory.Options();
                //1.2����inJustDecodeBounds �����ƽ����� ��ֻ���п�ߵĻ�ȡ���������Bitmap
                //��ռ���ڴ棬��ʹ���������������BitmapFactory.decodeXxxx���Ƶķ��� ���᷵��Bitmap
                option.inJustDecodeBounds = true;
                //���� ʹ��option �����ò��� ���뷽ʽ  in����  outȡ����
                BitmapFactory.decodeByteArray(data, 0, data.length, option);

                //-----------------------------------------------
                //2.����2 ����ͼƬ����ʵ�ߴ�Ϳͻ��˵�Ҫ��ǰ��ʾ�ĳߴ磬���м�������ͼƬ������
                //2.1
                int picw = option.outWidth;  //6000
                int pich = option.outHeight;//4000
                //2.2  ׼����ʾ���ֻ��ϵĳߴ� 256x128  128x64
                //�ߴ��Ǹ��ݳ�����Ҫ��Ƶ�
                //maxWidth  ,maxHeight
                int reqW = requestWidth;

                int reqH = requestHeight;//�������� ʵ�ʿ��

                //2.3  ���� ���� ͼƬ������
                //�����ĸ��� ��1 1--1  2 4--1 ��С���ĸ����ر�������һ�����ص�
                option.inSampleSize = calculateInSampleSize(option,reqW,reqH);//��ȵ�1/32  �߶ȵ�1/32
                //2.4���Ž��� ʵ������Bitmap
                option.inJustDecodeBounds = false;
                //2.4.1 BitmapConfig��˵��
                //Ҫ���������ÿ������������ ʹ�� RGB_565�洢��ʽ
                //һ�����أ�ռ�������ֽڣ�  ��ARGB_8888С��һ��
                //������������ܹ�ʹ��ָ�����ã����Զ�ʹ��ARGB_8888
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                //2.4.2 ��ʱ������
                option.inPurgeable = true;
                //2.5ʹ�����ò����Ĳ����������н��� ��ȡBitmap
                ret = BitmapFactory.decodeByteArray(data, 0, data.length, option);
            }
        }

        return ret;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {

            ImageView imageView = imageViewWeakReference.get();//��ȡ�����ð����Ķ��󣬿���Ϊnull
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * ����ͼƬ���β����Ĳ����� ʹ�û�ȡͼƬ���֮���option��Ϊ��һ������ ����ͨ������� ��ȸ߶ȳߴ���в����ʵļ���
     * @param options
     * @param reqWidth ����Ŀ��
     * @param reqHeight ����ĸ߶�
     * @return int ������
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
