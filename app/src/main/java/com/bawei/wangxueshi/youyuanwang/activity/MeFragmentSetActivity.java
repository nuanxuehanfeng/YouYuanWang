package com.bawei.wangxueshi.youyuanwang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragmentSetActivity extends IActivity {

    @BindView(R.id.a_mefragment_set_bt_zhuxiao)
    Button aMefragmentSetBtZhuxiao;
    private Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_fragment_set);
        bind = ButterKnife.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    @OnClick(R.id.a_mefragment_set_bt_zhuxiao)
    public void onViewClicked() {
        // 创建退出对话框
        AlertDialog.Builder isExit = new AlertDialog.Builder(MeFragmentSetActivity.this);
        // 设置对话框标题
        isExit.setTitle("切换账号");
        // 设置对话框消息
        isExit.setMessage("当前账号退出后，将无法收到消息推送");
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
                PreferencesUtils.addConfigInfo(MeFragmentSetActivity.this,"isLoad",false);
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(MeFragmentSetActivity.this,LoadActivity.class));
                finish();
            }
        });
        // 显示对话框
        isExit.show();
        //  }

    }
}
