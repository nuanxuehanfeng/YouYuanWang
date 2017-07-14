package com.bawei.wangxueshi.youyuanwang.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.bean.friend.DataBean1;
import com.bawei.wangxueshi.youyuanwang.utils.DisanceUtils;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class FActionRecyclerviewAdapter extends RecyclerView.Adapter {
    List<DataBean1> list;
    Context context;
    public FActionRecyclerviewAdapter(List<DataBean1> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=View.inflate(context,R.layout.ffind_recycler_adapter,null);
            Holder1 holder1=new Holder1(view);
            return holder1;


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DataBean1 dataBean = list.get(position);
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

            Holder1 holder1 = (Holder1) holder;
            int picHeight = list.get(position).getPicHeight();
            int picWidth = list.get(position).getPicWidth();
            ViewGroup.LayoutParams layoutParams = holder1.iv.getLayoutParams();
            layoutParams.height = 300;
            layoutParams.width = 300;


         //   Glide.with(context).load(list.get(position).getImagePath()).into(holder1.iv);

        holder1.iv.setImageURI(list.get(position).getImagePath());
            holder1.nicheng.setText(list.get(position).getNickname());
           holder1.age.setText(list.get(position).getAge());
           holder1.desc.setText(list.get(position).getIntroduce());
           holder1.sex.setText(list.get(position).getGender());
            holder1.juli.setText(juli);
           double lat = list.get(position).getLat();
           double lng = list.get(position).getLng();
        if(fActiondRecyclerviewAdapterItem!=null){
            holder1.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fActiondRecyclerviewAdapterItem.onClickAdapterItem(list.get(position));
                }
            });
        }
    }
    @Override
    public int getItemCount() {

        return list.size()==0?0:list.size();
    }

    class Holder1 extends  RecyclerView.ViewHolder{
        SimpleDraweeView iv;
        TextView nicheng;
        TextView age;
        TextView sex;
        TextView juli;
        TextView desc;
         LinearLayout linearLayout;
        public Holder1(View itemView) {
            super(itemView);
            iv= (SimpleDraweeView) itemView.findViewById(R.id.adapter_f_find_recycler_iv_photo);
            nicheng= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_nicheng);
            age= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_age);
            sex= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_sex);
            juli= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_juli);
            desc= (TextView) itemView.findViewById(R.id.adapter_f_find_recycler_tv_desc);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.adapter_f_find_recycler_linearlayout);
        }
    }
 public    interface  FAtionRecyclerviewAdapterItem{
        void onClickAdapterItem(DataBean1 dataBean);
    }
    FAtionRecyclerviewAdapterItem  fActiondRecyclerviewAdapterItem;
    public void setOnClickAdapterItem(FAtionRecyclerviewAdapterItem  fActiondRecyclerviewAdapterItem){
        this.fActiondRecyclerviewAdapterItem=fActiondRecyclerviewAdapterItem;
    }
}
