package com.bawei.wangxueshi.youyuanwang.presenter;

import com.bawei.wangxueshi.youyuanwang.base.BasePresenter;
import com.bawei.wangxueshi.youyuanwang.model.LoadActivityImp;
import com.bawei.wangxueshi.youyuanwang.view.LoadActivityView;

/**
 * Created by Administrator on 2017/7/7.
 */

public class LoadActivityPresent extends BasePresenter<LoadActivityView> {


    private final LoadActivityImp loadActivityImp;

    public LoadActivityPresent() {
        loadActivityImp = new LoadActivityImp();

    }

    public void vaildInfor(String phone,String password,String sign,String secretkey,String lat,String lng){

        //非空判断

        loadActivityImp.getData(phone, password, sign, secretkey, lat, lng,  new LoadActivityImp.LoadActivityImPListener() {
            @Override
            public void LoadSuccess(String result) {
                view.LoadSuccess(result);
            }

            @Override
            public void LoadFailed(int code) {
                view.LoadFailed(code);
            }
        });






    }



}
