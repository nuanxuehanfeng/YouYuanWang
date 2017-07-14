package com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.activity.PersonMessageActivity;
import com.bawei.wangxueshi.youyuanwang.adapter.FFindRecyclerviewAdapter;
import com.bawei.wangxueshi.youyuanwang.bean.LoadBean;
import com.bawei.wangxueshi.youyuanwang.bean.findbean.DataBean;
import com.bawei.wangxueshi.youyuanwang.bean.findbean.FFindBean;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.NetTools;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
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
 * Created by Administrator on 2017/7/10.       fragment 重叠
 */

public class FindFragment extends Fragment {

    private ImageView zhuan;
    private RecyclerView recyclerView;
    List<DataBean> list=new ArrayList<>();
    private FFindRecyclerviewAdapter fFindRecyclerviewAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int goodsType=0;
   private  int page=1;
    private SpringView springView;
    private LoadBean loadBeana;
    private long time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.fragment_find,null);


        time = System.currentTimeMillis();
        springView = (SpringView) view.findViewById(R.id.f_find_springview);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        zhuan = (ImageView) view.findViewById(R.id.f_find_tv_zhuan);
        recyclerView = (RecyclerView) view.findViewById(R.id.f_find_recyclerview);
        fFindRecyclerviewAdapter = new FFindRecyclerviewAdapter(list, getActivity());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(fFindRecyclerviewAdapter);
        zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsType==0){
                    zhuan.setImageResource(R.mipmap.good_type_grid);
                    //1：设置布局类型
                    fFindRecyclerviewAdapter.setType(1);
                    //2：设置对应的布局管理器
                    staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    //3：刷新adapter
                    fFindRecyclerviewAdapter.notifyDataSetChanged();
                    goodsType=1;
                }else {
                    zhuan.setImageResource(R.mipmap.good_type_linear);
                    fFindRecyclerviewAdapter.setType(0);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    fFindRecyclerviewAdapter.notifyDataSetChanged();
                    goodsType=0;
                }
            }
        });
        //item点击事件
        fFindRecyclerviewAdapter.setOnClickAdapterItem(new FFindRecyclerviewAdapter.FFindRecyclerviewAdapterItem() {
            @Override
            public void onClickAdapterItem(DataBean dataBean) {
                Intent intent = new Intent(getActivity(), PersonMessageActivity.class);

                int userId = dataBean.getUserId();
                intent.putExtra("userid",userId);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action?pageIndex=1&pageSize=10&user.sign=1

        boolean networkAvailable = NetTools.isNetworkAvailable(getActivity());
        if(networkAvailable==true){
        getData(time,0);}
        else{
            List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();
            list.addAll(dataBeen);
            fFindRecyclerviewAdapter.notifyDataSetChanged();
        }
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                long lin = System.currentTimeMillis();
                getData(lin,1);
                springView.onFinishFreshAndLoad();

            }
            @Override
            public void onLoadmore() {
                long lasttime = list.get(list.size()-1).getLasttime();
                if(lasttime!=0){
                getData(lasttime,2);
                springView.onFinishFreshAndLoad();
                }
            }
        });

    }

    private void getData(long time1 , final int type) {


        List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();
        list.addAll(dataBeen);
        fFindRecyclerviewAdapter.notifyDataSetChanged();
        Map<String,String> map=new HashMap<>();
        map.put("user.currenttimer",time1+"");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign",sign);

        RetrofitManager.getFindData(map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
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
                 list.clear();
                list.addAll(dataBeen);
                ;//刷新适配器
                }
                else if(type==1){
                    list.clear();
                    list.addAll(dataBeen);
                    ;//刷新适配器
                }
                else if(type==2){
                    list.addAll(list.size(),dataBeen);
                }
                fFindRecyclerviewAdapter.notifyDataSetChanged();
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
