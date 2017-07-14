package com.bawei.wangxueshi.youyuanwang.presenter;

import com.bawei.wangxueshi.youyuanwang.base.BasePresenter;
import com.bawei.wangxueshi.youyuanwang.bean.RegisterBean;
import com.bawei.wangxueshi.youyuanwang.model.RegisterInforFragmentImp;
import com.bawei.wangxueshi.youyuanwang.model.RegisterInforFragmentModel;
import com.bawei.wangxueshi.youyuanwang.view.RegisterInforFragmentView;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterInforFragmentPresenter extends BasePresenter<RegisterInforFragmentView> {


    private RegisterInforFragmentImp registerInforFragmentModel ;
    public RegisterInforFragmentPresenter(){
        registerInforFragmentModel = new RegisterInforFragmentImp();
    }
    public void vaildInfor(String phone,String nickname,String sex,String age,String area,String introduce,String latitude,String longitude,String password){
        //非空判断

        registerInforFragmentModel.getData(phone, nickname, sex, age, area, introduce, password,latitude,longitude, new RegisterInforFragmentModel.RegisterInforFragmentDataListener() {
            @Override
            public void onSuccess(RegisterBean registerBean) {

                view.registerSuccess(registerBean);

            }

            @Override
            public void onFailed(int code) {

                view.registerFailed(code);
            }
        });






    }

}
