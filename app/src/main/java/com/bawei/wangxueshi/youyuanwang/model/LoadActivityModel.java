package com.bawei.wangxueshi.youyuanwang.model;

/**
 * Created by Administrator on 2017/7/7.
 */

public interface LoadActivityModel {
    public void getData(String phone,String password,String sign,String secretkey,String lat,String lng,LoadActivityImp.LoadActivityImPListener listener);

    public interface LoadActivityDataListener {
        public void onSuccess(String registerBean);
        public void onFailed(int code);

    }
}
