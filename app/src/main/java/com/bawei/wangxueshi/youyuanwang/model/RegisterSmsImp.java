package com.bawei.wangxueshi.youyuanwang.model;

import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterSmsImp implements  RegisterSmsModel {
    @Override
    public void getVerificationCode(String phone){
        SMSSDK.getVerificationCode("86", phone);
    }
}
