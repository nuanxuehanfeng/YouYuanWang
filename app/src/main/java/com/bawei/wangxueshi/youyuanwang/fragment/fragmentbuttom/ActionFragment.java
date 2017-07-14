package com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.activity.LoadActivity;
import com.bawei.wangxueshi.youyuanwang.adapter.FActionRecyclerviewAdapter;
import com.bawei.wangxueshi.youyuanwang.bean.friend.DataBean1;
import com.bawei.wangxueshi.youyuanwang.bean.friend.FActionFriendBean;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.NetTools;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ActionFragment extends Fragment {

    private SpringView springView;
    private ImageView zhuan;
    private RecyclerView recyclerView;
    List<DataBean1> list=new ArrayList<>();
    private FActionRecyclerviewAdapter fActionRecyclerviewAdapter;
    private boolean isLoad;
    private Button btLoad;
    private boolean networkAvailable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.fragment_action,null);
        btLoad = (Button) view.findViewById(R.id.f_action_bt_load);
        springView = (SpringView) view.findViewById(R.id.f_action_springview);


        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        zhuan = (ImageView) view.findViewById(R.id.f_action_tv_zhuan);
        recyclerView = (RecyclerView) view.findViewById(R.id.f_action_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        fActionRecyclerviewAdapter = new FActionRecyclerviewAdapter(list, getActivity());
        recyclerView.setAdapter(fActionRecyclerviewAdapter);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLoad =  PreferencesUtils.getValueByKey(getActivity(),"isLoad",false);
        networkAvailable = NetTools.isNetworkAvailable(getActivity());
        System.out.println("actionfragment是否登录 = " + isLoad);
        if(isLoad==true){
            btLoad.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
            long lin = System.currentTimeMillis();
            if(networkAvailable==true){
               getData(lin,0);
            }
            else{
                List<DataBean1> dataBean1s = IApplication.daoSession.getDataBean1Dao().loadAll();
                if(dataBean1s!=null&&dataBean1s.size()>0){
                list.addAll(dataBean1s);
                fActionRecyclerviewAdapter.notifyDataSetChanged();
                }
            }
        }
        else{
            btLoad.setVisibility(View.VISIBLE);
            springView.setVisibility(View.GONE);
        }
        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LoadActivity.class));

            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if(networkAvailable==false){
                    MyToast.makeText(getActivity(),"刷新请链接网络",Toast.LENGTH_SHORT);
                    return;
                }

                long lin = System.currentTimeMillis();
               // 刷新  当前时间
               getData(lin,1);
                springView.onFinishFreshAndLoad();
            }
            @Override
            public void onLoadmore() {
                if(networkAvailable==false){
                    MyToast.makeText(getActivity(),"加载请链接网络",Toast.LENGTH_SHORT);
                    return;
                }
                if(list!=null&&list.size()>0){
                    //最后一个时间
                long lasttime = list.get(list.size()-1).getLasttime();

                   getData(lasttime,2);

                    springView.onFinishFreshAndLoad();
                }
                else{
                    MyToast.makeText(getContext(),"请求数据为空", Toast.LENGTH_SHORT);
                    springView.onFinishFreshAndLoad();
                }
            }
        });
    }
    private void getData(long time1 , final int type) {
        Map<String,String> map=new HashMap<>();
        map.put("user.currenttimer",time1+"");
        System.out.println("时间戳time1 = " + time1);
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign",sign);
        RetrofitManager.getFriendData(map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("好友数据result = " + result);
                if(TextUtils.isEmpty(result)){
                    MyToast.makeText(getActivity(),"获取好友返回数据为空",Toast.LENGTH_SHORT);
                    return;
                }
                Gson gson = new Gson();
                FActionFriendBean fActionFriendBean = gson.fromJson(result, FActionFriendBean.class);

                List<DataBean1> data = fActionFriendBean.getData();
                ;if(data !=null&& data.size()!=0){
                    //清楚数据库
                    IApplication.daoSession.getDataBean1Dao().deleteAll();
                    IApplication.daoSession.getDataBean1Dao().insertInTx(data);
                    List<DataBean1> dataBean1s = IApplication.daoSession.getDataBean1Dao().loadAll();
                    //    IApplication.daoSession.getDataBeanDao().deleteAll();
                    //向数据库中添加内容

               //     IApplication.daoSession.getDataBeanDao().insertInTx(data);
                    //查找数据库
               //    List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();
              //     System.out.println("好友保存了的多少dataBeen.size() = " + dataBeen.size());
                    if(type==0){
                        list.addAll(dataBean1s);
                        ;//刷新适配器
                    }
                    else if(type==1){
                        list.clear();
                        list.addAll(dataBean1s);
                        ;//刷新适配器
                    }
                    else if(type==2){
                        list.addAll(list.size(),dataBean1s);
                    }
                    fActionRecyclerviewAdapter.notifyDataSetChanged();
                }
                else{
                    //请求的数据集合为空
                    MyToast.makeText(getContext(),"请求数据为空", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailed(int code) {
                System.out.println("请求好友  请求错误 = " + code);
            }
        });
    }
}
