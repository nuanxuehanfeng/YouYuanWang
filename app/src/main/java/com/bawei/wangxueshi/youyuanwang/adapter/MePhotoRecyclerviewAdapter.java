package com.bawei.wangxueshi.youyuanwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class MePhotoRecyclerviewAdapter extends RecyclerView.Adapter {

    List<String> list;
    Context context;

    public MePhotoRecyclerviewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=View.inflate(context,R.layout.adapter_mephoto_recyview_iv,null);

        PhotoHolder holder=new PhotoHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==0){
            PhotoHolder ho= (PhotoHolder) holder;
            ho.iv.setImageResource(R.drawable.my_photo_album_add);

            ho.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.addimage();
                }
            });
        }
        else{
            PhotoHolder ho= (PhotoHolder) holder;
            Glide.with(context).load(list.get(position)).into(ho.iv);
        }




    }
    @Override
    public int getItemCount() {
        return list.size()==0?1:list.size();
    }

    class PhotoHolder extends  RecyclerView.ViewHolder{
        ImageView iv;

        public PhotoHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.adapter_mephoto_recyclerview_iv);
        }
    }
    addImage  add;
public interface  addImage{
    void addimage();
}
  public void setAddimage( addImage  add){
      this.add=add;
  };

}
