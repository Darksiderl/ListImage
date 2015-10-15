package com.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.lidroid.xutils.BitmapUtils;
import com.yaodong.listimage.R;
import yaodong.asynctask.AsyncDrawble;
import yaodong.asynctask.LoadTask;

import java.util.List;

/**
 * Created
 * Author:lvyaodong[Darksider]
 * Email:1126220529@qq.com
 * Date:2015/10/8
 */
public class ImageListAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageUrls;
    private static BitmapUtils bitmapUtils;

    public ImageListAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        if(bitmapUtils==null){
            bitmapUtils = new BitmapUtils(context);
            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
            bitmapUtils.configDefaultBitmapMaxSize(128,64);
        }
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (imageUrls != null) {
            ret = imageUrls.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder holder = null;
//        if(view == null){
//            view = LayoutInflater.from(context).inflate(R.layout.image_item,viewGroup,false);
//            holder = new ViewHolder();
//            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
//            view.setTag(holder);
//        }else{
//            holder = (ViewHolder) view.getTag();
//            holder.imageView.setImageResource(R.drawable.ic_launcher);
//        }
//        String url = imageUrls.get(i);
        //////////////////////////////////
        //视图复用
        View ret = null;
        if(view!=null){
            ret = view;
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            ret = layoutInflater.inflate(R.layout.image_item,viewGroup,false);
        }

        /////////////////////////////
        //viewholder的处理
        ViewHolder holder = (ViewHolder) ret.getTag();
        if(holder==null){
            holder = new ViewHolder();
            holder.imageView = (ImageView) ret.findViewById(R.id.image_item_icon);
            ret.setTag(holder);
        }
        ////////////////////////////
        //设置内容
        String url = imageUrls.get(i);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
        LoadTask loadTask = new LoadTask(holder.imageView,256,128);
        AsyncDrawble drawble = new AsyncDrawble(
                context.getResources(),
                b,
                loadTask);
        //先设置后执行
        holder.imageView.setImageDrawable(drawble);
        loadTask.execute(url);
        return ret;
    }



    private static class ViewHolder{//在adapter加载时直接加载了 ViewHolder ，直接访问
        public ImageView imageView;
    }
}
