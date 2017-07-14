package com.bawei.wangxueshi.youyuanwang.model;

import android.text.TextUtils;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.base.BasePresenter;
import com.bawei.wangxueshi.youyuanwang.cipher.Md5Utils;
import com.bawei.wangxueshi.youyuanwang.cipher.aes.JNCryptorUtils;
import com.bawei.wangxueshi.youyuanwang.cipher.rsa.RsaUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.utils.Constants;
import com.bawei.wangxueshi.youyuanwang.view.LoadActivityView;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7.
 */

public class LoadActivityImp extends BasePresenter<LoadActivityView> implements  LoadActivityModel {


    @Override
    public void getData(String phone, String password, String sign, String secretkey, String lat, String lng, final LoadActivityImPListener listener) {

      if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(password)){
        Map map = new HashMap<String, String>();
        String md5 = Md5Utils.getMD5(password);
        String randomKey = RsaUtils.getStringRandom(16);
        //对电话号进行rsa与aes方式进行加密
        String cipherPhone = JNCryptorUtils.getInstance().encryptData(phone, IApplication.getApplication(), randomKey);
        String rsaRandomKey = RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(), randomKey);
        map.put("user.phone", cipherPhone);
        map.put("user.password",md5);
        map.put("user.secretkey",rsaRandomKey);
          map.put("user.lat",lat );
          map.put("user.lng",lng);
        System.out.println("登录user.phone = " + cipherPhone);
        System.out.println("登录user.password = " + md5);
        System.out.println("user.secretkey = " + rsaRandomKey);
        System.out.println("user.lat = " + lat);
        System.out.println("user.lng = " + lng);

        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                listener.LoadSuccess(result);
            }
            @Override
            public void onFailed(int code) {
                listener.LoadFailed(code);
            }
        });
      }
        else{
          MyToast.makeText(IApplication.getApplication(),"姓名或者密码为空", Toast.LENGTH_SHORT);

      }

    }

   public  interface  LoadActivityImPListener{
        void LoadSuccess(String result);
        void LoadFailed(int code);
    }
}
