package com.bawei.wangxueshi.youyuanwang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.fragment.RegisterInforFragment;
import com.bawei.wangxueshi.youyuanwang.fragment.RegisterIntroduceFragment;
import com.bawei.wangxueshi.youyuanwang.fragment.RegisterSms;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends IActivity {

    @BindView(R.id.register_bt_fanhui)
    Button registerBtFanhui;
    @BindView(R.id.register_tv_load)
    TextView registerTvLoad;
    @BindView(R.id.register_zhanwei)
    FrameLayout registerZhanwei;
    private Unbinder bind;
    private FragmentManager fragmentManager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private RegisterInforFragment inforFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bind = ButterKnife.bind(this);

       // StatusBarUtils.setWindowStatusBarColor(this,R.color.chen);
        inforFragment = new RegisterInforFragment();
        list.add(new RegisterSms());
        list.add(inforFragment);
       list.add(new RegisterIntroduceFragment());
        switchIFragment(0,list,R.id.register_zhanwei);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        //解决内存泄漏的
        AppManager.getAppManager().finishActivity(RegisterActivity.class);
    }
    /**
     * 0 获取短信验证码
     * 1 注册填写基本资料
     * 2 添加自我描述
     */
    public void toPos(int pos){
        switchIFragment(pos,list,R.id.register_zhanwei);
    }


    @OnClick({R.id.register_bt_fanhui, R.id.register_tv_load})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_bt_fanhui:
                AppManager.getAppManager().finishActivity(this);
                break;
            case R.id.register_tv_load:
                AppManager.getAppManager().finishActivity(this);
                break;
        }
    }
    public void setDes(String des){
        inforFragment.setDes(des);
    }
    public void setPhone(String phone){
        inforFragment.setPhone(phone);

    }


}
