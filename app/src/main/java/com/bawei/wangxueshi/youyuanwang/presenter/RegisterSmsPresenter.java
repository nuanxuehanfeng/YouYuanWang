package com.bawei.wangxueshi.youyuanwang.presenter;

import android.text.TextUtils;

import com.bawei.wangxueshi.youyuanwang.base.BasePresenter;
import com.bawei.wangxueshi.youyuanwang.model.RegisterSmsImp;
import com.bawei.wangxueshi.youyuanwang.utils.PhoneCheckUtils;
import com.bawei.wangxueshi.youyuanwang.view.RegisterSmsView;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterSmsPresenter extends BasePresenter<RegisterSmsView> {

    private RegisterSmsImp registerSmsModel ;

    public RegisterSmsPresenter(){
        registerSmsModel = new RegisterSmsImp();
    }


    public void getVerificationCode(String phone){

        if(TextUtils.isEmpty(phone)){
            view.phoneError(1);
            return;
        }
        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(2);
            return;
        }
        registerSmsModel.getVerificationCode(phone);
        view.showTimer();

    }



    public void nextStep(String phone,String sms){

        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(sms)){
            view.phoneError(3);
            return;
        }

        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(4);
            return;
        }
        if(!PhoneCheckUtils.isYanZhengMaLegal(sms)){
            view.phoneError(5);
            return;
        }

        //判断验证码是否合法  验证码是4为数字 \\d{4} sms 非空
        view.toNextPage();

    }

}
