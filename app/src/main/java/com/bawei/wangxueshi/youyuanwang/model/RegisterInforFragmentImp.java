package com.bawei.wangxueshi.youyuanwang.model;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.bean.RegisterBean;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.utils.Constants;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterInforFragmentImp implements  RegisterInforFragmentModel {


    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final String latitude, final String longitude, final RegisterInforFragmentDataListener listener){
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.phone", phone);
        map.put("user.nickname", nickname);
        map.put("user.password", password);
        map.put("user.gender", sex);
        map.put("user.area", area);
        map.put("user.age", age);
        map.put("user.introduce", introduce);

        map.put("user.lat",latitude );
        map.put("user.lng",longitude);

        System.out.println("注册phone = " + phone);
        System.out.println("注册password = " + password);
        RetrofitManager.post(Constants.REGISTER_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("success=========================注册成功对象" + result);
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);
                if (registerBean.getResult_code() == 200) {
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "phone", registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "password", registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "yxpassword", registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "uid", registerBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "nickname", registerBean.getData().getNickname());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "latitude", latitude);
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(), "longitude",longitude);
                }
                listener.onSuccess(registerBean);
            }
            @Override
            public void onFailed(int code) {
                System.out.println("error=========================");
                listener.onFailed(code);
            }
        });
    }


}
