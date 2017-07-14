package com.bawei.wangxueshi.youyuanwang.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.bean.findbean.DataBean;
import com.bawei.wangxueshi.youyuanwang.utils.DisanceUtils;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class FFindRecyclerviewAdapter extends RecyclerView.Adapter {
    List<DataBean> list;
    Context context;
    private int type = 0;//0:LinearViewHolder  1:GridViewHolder
    private View view2;

    public FFindRecyclerviewAdapter(List<DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view=View.inflate(context,R.layout.ffind_recycler_adapter,null);
            Holder1 holder1=new Holder1(view);
            return holder1;
        }
        else {
            view2 = View.inflate(context, R.layout.ffind1_recycler_adapter,null);

            Holder2 holder2=new Holder2(view2);
            return  holder2;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
       DataBean dataBean = list.get(position);
        String juli="";
        double lat1 = dataBean.getLat();
        double lng1 = dataBean.getLng();
        String latitude = PreferencesUtils.getValueByKey(context, "latitude","");
        String longitude = PreferencesUtils.getValueByKey(context, "longitude","");
        if(lat1!=0&&lng1!=0&&!TextUtils.isEmpty(latitude)&&!TextUtils.isEmpty(longitude)){
            double  lat2=Double.valueOf(latitude);
            double  lng2=Double.valueOf(longitude);
            DPoint dPoint1=new DPoint(lat1,lng1);
            DPoint dPoint2=new DPoint(lat2,lng2);
            float v = CoordinateConverter.calculateLineDistance(dPoint1, dPoint2);
            juli = DisanceUtils.standedDistance(v);
        }
        if (type == 0) {
            Holder1 holder1 = (Holder1) holder;



            int picHeight = list.get(position).getPicHeight();
            int picWidth = list.get(position).getPicWidth();
            ViewGroup.LayoutParams layoutParams = holder1.iv.getLayoutParams();
            layoutParams.height = 300;
            layoutParams.width = 300;
        //    Glide.with(context).load(list.get(position).getImagePath()).into(holder1.iv);

         //   holder1.iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(list.get(position).getImagePath()).into(holder1.iv);

            holder1.nicheng.setText(list.get(position).getNickname());
           holder1.age.setText(list.get(position).getAge());
           holder1.desc.setText(list.get(position).getIntroduce());
           holder1.sex.setText(list.get(position).getGender());
            holder1.juli.setText(juli);
           double lat = list.get(position).getLat();
           double lng = list.get(position).getLng();
            holder1.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fFindRecyclerviewAdapterItem.onClickAdapterItem(list.get(position));
                }
            });
       }
       else if(type==1){
            Holder2 holder2 = (Holder2) holder;
            int picHeight = list.get(position).getPicHeight();
            int picWidth = list.get(position).getPicWidth();
           WindowManager wm = (WindowManager) context
                   .getSystemService(Context.WINDOW_SERVICE);
           int width = wm.getDefaultDisplay().getWidth();
           int itemWidth=width/3;
           int height = wm.getDefaultDisplay().getHeight();
           float scale = (float) itemWidth / (float) picWidth;
           ViewGroup.LayoutParams layoutParams = holder2.ivLift.getLayoutParams();
           layoutParams.height=(int)((float)scale*(float)picHeight);
           layoutParams.width=itemWidth;
            Glide.with(context).load(list.get(position).getImagePath()).into(holder2.ivLift);
          //  Glide.with(context).load(list.get(position).getImagePath()).placeholder(context.getResources().getDrawable(R.drawable.user_icon_default)).error(context.getResources().getDrawable(R.drawable.user_icon_default)).into(holder2.ivLift);
            holder2.tvJuli.setText(juli);
            holder2.ivLift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fFindRecyclerviewAdapterItem.onClickAdapterItem(list.get(position));
                }
            });
        }
    }
    @Override
    public int getItemCount() {

        return list.size()==0?0:list.size();
    }
    @Override
    //用来获取当前项Item是哪种类型的布局
    public int getItemViewType(int position) {
        return type;
    }

    //点击切换布局的时候通过这个方法设置type
    public void setType(int type) {
        this.type = type;
    }
    class Holder1 extends  RecyclerView.ViewHolder{
        ImageView iv;
        TextView nicheng;
        TextView age;
        TextView sex;
        TextView juli;
        TextView desc;
         LinearLayout linearLayout;
        public Holder1(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.adapter_f_find_recycler_iv_photo);
            nicheng= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_nicheng);
            age= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_age);
            sex= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_sex);
            juli= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_juli);
            desc= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_desc);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.adapter_f_find_recycler_linearlayout);
        }
    }
    class Holder2 extends  RecyclerView.ViewHolder{
        ImageView ivLift;
       TextView tvJuli;
        public Holder2(View itemView) {
            super(itemView);
            ivLift= (ImageView) itemView.findViewById(R.id.adapter_ff1_find_recycler_iv_left);
            tvJuli= (TextView) itemView.findViewById(R.id.adapter_ff1_find_recycler_iv_juli);
        }
    }

 public    interface  FFindRecyclerviewAdapterItem{
        void onClickAdapterItem(DataBean dataBean);
    }
    FFindRecyclerviewAdapterItem  fFindRecyclerviewAdapterItem;
    public void setOnClickAdapterItem(FFindRecyclerviewAdapterItem  fFindRecyclerviewAdapterItem){
        this.fFindRecyclerviewAdapterItem=fFindRecyclerviewAdapterItem;
    }


}
