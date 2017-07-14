package com.bawei.wangxueshi.youyuanwang.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.bean.findbean.DataBean;
import com.bawei.wangxueshi.youyuanwang.bean.findbean.FFindBean;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mob.MobSDK.getContext;

public class PhoneLoadActivity extends IActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_load);

        long l = System.currentTimeMillis();
        getData(l,0);
    }
    private void getData(long time1 , final int type) {

        List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();
      //  list.addAll(dataBeen);
     //   fFindRecyclerviewAdapter.notifyDataSetChanged();
        Map<String,String> map=new HashMap<>();
        map.put("user.currenttimer",time1+"");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign",sign);
        RetrofitManager.getFindData(map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("请求的数据result = " + result);
                Gson gson = new Gson();
                FFindBean fFindBean = gson.fromJson(result, FFindBean.class);
                List<DataBean> data = fFindBean.getData();
                ;if(data!=null&&data.size()!=0){
                    //清楚数据库
                    IApplication.daoSession.getDataBeanDao().deleteAll();
                    //向数据库中添加内容
                    IApplication.daoSession.getDataBeanDao().insertInTx(data);
                    //查找数据库
                    List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();
                    System.out.println("保存了的多少dataBeen.size() = " + dataBeen.size());
                    if(type==0){
                //        list.clear();
                //        list.addAll(dataBeen);
                        ;//刷新适配器

                        System.out.println("请求数据的数量data.size() = " + data.size());
                    }
                    else if(type==1){
                 //       list.clear();
                 //       list.addAll(dataBeen);
                        ;//刷新适配器
                    }
                    else if(type==2){
               //         list.addAll(list.size(),dataBeen);
                    }
             //       fFindRecyclerviewAdapter.notifyDataSetChanged();
                }
                else{
                    //请求的数据集合为空
                    MyToast.makeText(getContext(),"请求数据为空", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailed(int code) {
            }
        });
    }

}
