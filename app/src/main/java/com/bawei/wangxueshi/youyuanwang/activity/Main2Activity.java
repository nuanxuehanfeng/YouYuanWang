package com.bawei.wangxueshi.youyuanwang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom.ActionFragment;
import com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom.FindFragment;
import com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom.MeFragment;
import com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends IActivity {
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fragmentManager = getSupportFragmentManager();
        createragment(savedInstanceState);
        switchFragment(0);
        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.main2_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case  R.id.main2_rb_me:
                        switchFragment(3);
                        break;
                    case  R.id.main2_rb_message:
                        switchFragment(2);
                        break;
                    case  R.id.main2_rb_find:
                        switchFragment(0);
                        break;
                    case  R.id.main2_rb_dynamic:
                        switchFragment(1);
                        break;
                }
            }
        });
    }
    public void createragment(Bundle savedInstanceState){
        FindFragment firstFragment = (FindFragment) fragmentManager.findFragmentByTag("FindFragment");
        ActionFragment secondFragment = (ActionFragment) fragmentManager.findFragmentByTag("ActionFragment");
        MessageFragment thirdFragment = (MessageFragment) fragmentManager.findFragmentByTag("MessageFragment");
        MeFragment fourthFragment = (MeFragment) fragmentManager.findFragmentByTag("MeFragment");
        if(firstFragment == null){
            firstFragment = new FindFragment();
        }
        if(secondFragment == null){
            secondFragment = new ActionFragment();
        }
        if(thirdFragment == null){
            thirdFragment = new MessageFragment();
        }
        if(fourthFragment == null){
            fourthFragment = new MeFragment();
        }
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fourthFragment);
    }

    public void switchFragment(int pos){
        switchIFragment(pos,fragments,R.id.main2_zhanwei);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AlertDialog.Builder builder=new   AlertDialog.Builder(Main2Activity.this);
//        System.out.println("\"返回键\" = " + "返回键");
//        builder.setTitle("确定离开");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                AppManager.getAppManager().finishAllActivity();
//            }
//        });
//        builder.show();
//
//
//        return super.onKeyDown(keyCode, event);

       // if (keyCode == KeyEvent.KEYCODE_BACK )
      //  {
            // 创建退出对话框
            AlertDialog.Builder isExit = new AlertDialog.Builder(this);
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
        isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("\"取消\" = " + "取消");
            }
        });
            isExit.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("\"确认\" = " + "确认");
                    AppManager.getAppManager().finishAllActivity();
                }
            });
            // 显示对话框
            isExit.show();
      //  }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(Main2Activity.this);
    }
}
