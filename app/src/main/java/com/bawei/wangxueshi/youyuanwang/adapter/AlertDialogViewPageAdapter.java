package com.bawei.wangxueshi.youyuanwang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bawei.wangxueshi.youyuanwang.bean.personmessagebean.PersonBean;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class AlertDialogViewPageAdapter extends PagerAdapter {
    List<PersonBean.DataBean.PhotolistBean> list;
    Context context;

    public AlertDialogViewPageAdapter(List<PersonBean.DataBean.PhotolistBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size()==0?0:list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int picWidth = list.get(position).getPicWidth();
        int picHeight = list.get(position).getPicHeight();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        int height = wm.getDefaultDisplay().getHeight();

        float scale = (float) width / (float) picWidth;
        ViewGroup.LayoutParams layoutParams =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.height=(int)((float)scale*(float)picHeight);
        layoutParams.width=width;


        PhotoView photoView = new PhotoView(context);
        photoView.setLayoutParams(layoutParams);
    //    photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(list.get(position).getImagePath()).into(photoView);

        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
