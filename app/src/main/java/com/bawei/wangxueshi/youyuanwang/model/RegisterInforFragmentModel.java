package com.bawei.wangxueshi.youyuanwang.model;

import com.bawei.wangxueshi.youyuanwang.bean.RegisterBean;

/**
 * Created by Administrator on 2017/7/5.
 */

public interface RegisterInforFragmentModel {



    public void getData(String phone,String nickname,String sex,String age,String area,String introduce,String password,String latitude,String longitude,RegisterInforFragmentDataListener listener);


    public interface RegisterInforFragmentDataListener {


        public void onSuccess(RegisterBean registerBean);

        public void onFailed(int code);

    }

}
