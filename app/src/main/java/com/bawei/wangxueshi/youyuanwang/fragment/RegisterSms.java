package com.bawei.wangxueshi.youyuanwang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.activity.RegisterActivity;
import com.bawei.wangxueshi.youyuanwang.base.BaseMvpFragment;
import com.bawei.wangxueshi.youyuanwang.presenter.RegisterSmsPresenter;
import com.bawei.wangxueshi.youyuanwang.view.RegisterSmsView;
import com.bawei.wangxueshi.youyuanwang.widget.EditTextWithDel;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterSms extends BaseMvpFragment<RegisterSmsView,RegisterSmsPresenter> implements  RegisterSmsView{

    @BindView(R.id.register_sms_et_phone)
    EditTextWithDel registerSmsEtPhone;
    @BindView(R.id.register_sms_et_yanzhema)
    EditText registerSmsEtYanzhema;
    @BindView(R.id.register_sms_bt_getyan)
    Button registerSmsBtGetyan;
    @BindView(R.id.register_sms_bt_next)
    Button registerSmsBtNext;
    private Unbinder bind;
    private RegisterActivity activity;
    EventHandler eventHandler;

    private InputMethodManager imm;

    Disposable dcancle;
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(msg.arg2 == SMSSDK.RESULT_COMPLETE){//发送成功的情况
                        if(msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//验证成功通过
                            System.out.println("\"验证成功\" = " + "验证成功");
                            //验证成功，取消 获得验证码按钮的文字改变
                   //         dcancle.dispose();
                    //        imm.hideSoftInputFromWindow(registerSmsBtNext.getWindowToken(), 0); //强制隐藏键盘
                            //保存电话号
                    //        activity.setPhone(registerSmsEtPhone.getText().toString().trim());
                            // 验证成功， 去下一个fragment ,并判断验证码和手机号是否符合格式
                    //        presenter.nextStep(registerSmsEtPhone.getText().toString().trim(),registerSmsEtYanzhema.getText().toString().trim());


                        }else if(msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE){//验证码已经从服务器发出
                    //        Toast.makeText(getActivity(), "验证码已发出,请注意查收"+registerSmsEtPhone.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                    //   Toast.makeText(getActivity(), "验证码错误a", Toast.LENGTH_SHORT).show();



                    }
                    break;
            }
        };
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_sms, container, false);
        bind = ButterKnife.bind(this,view);
        activity = (RegisterActivity) getActivity();
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//注册
        SMSSDK.initSDK(getActivity(),"1f2a06c6cca20","62183728136ab66360efc0378c10c6c4");
    //    写一个短信发送的监听，用于判断验证码是否发出，提交的验证码是否通过等信息，因为这个不是主线程中的，所以不能对UI线程进行操作，所以这里使用了消息机制
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message m = Message.obtain();
                m.what = 1;
                m.arg1 = event;//event
                m.arg2 =  result;//result

                System.out.println("验证码result = " + result);
                System.out.println("data = " + data);
               handler.sendMessage(m);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.register_sms_bt_getyan, R.id.register_sms_bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_sms_bt_getyan:
                //请求获取短信验证码，在监听中EvenHandler接口返回，str:手机号
              //  SMSSDK.getVerificationCode("86",str);
                System.out.println("提交的电话号 = " + registerSmsEtPhone.getText().toString().trim());
                presenter.getVerificationCode(registerSmsEtPhone.getText().toString().trim());
                break;
            case R.id.register_sms_bt_next:
                //提交短信验证码，在监听中返回，str :手机号 str1:收到的验证码
            //    System.out.println("验证的电话和验证码 = " +  registerSmsEtPhone.getText().toString().trim()+"吗"+registerSmsEtYanzhema.getText().toString().trim());
           //     SMSSDK.submitVerificationCode("86", registerSmsEtPhone.getText().toString().trim(), registerSmsEtYanzhema.getText().toString().trim());
                //保存电话号
                activity.setPhone(registerSmsEtPhone.getText().toString().trim());
                // 验证成功， 去下一个fragment ,并判断验证码和手机号是否符合格式
                presenter.nextStep(registerSmsEtPhone.getText().toString().trim(),registerSmsEtYanzhema.getText().toString().trim());



                break;
        }
    }

    @Override
    public RegisterSmsPresenter initPresenter() {
        return new RegisterSmsPresenter();
    }
    @Override
    public void phoneError(int type) {
        switch (type){
            case 1:
                //这里上下文要修改
                MyToast.makeText(IApplication.getApplication(),"手机号不可为空", Toast.LENGTH_SHORT);

                break;
            case 2:
                MyToast.makeText(IApplication.getApplication(),"手机号的格式不正确",Toast.LENGTH_SHORT);
                break;
            case 3:
                MyToast.makeText(IApplication.getApplication(),"手机号和验证码不可空",Toast.LENGTH_SHORT);
                break;
            case 4:
                MyToast.makeText(IApplication.getApplication(),"手机号的格式不正确",Toast.LENGTH_SHORT);
                break;
            case 5:
                MyToast.makeText(IApplication.getApplication(),"验证码的格式不正确",Toast.LENGTH_SHORT);
                break;
        }

    }

    @Override
    public void showTimer() {
        registerSmsBtGetyan.setClickable(false);
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        d.dispose();
                        dcancle=d;

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {


                        registerSmsBtGetyan.setText(aLong+" S ");
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        registerSmsBtGetyan.setClickable(true);
                        registerSmsBtGetyan.setText("获得验证码");
                    }
                });
    }
    @Override
    public void toNextPage() {
        activity.toPos(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
