package com.bawei.wangxueshi.youyuanwang.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.BaseMvpActivity;
import com.bawei.wangxueshi.youyuanwang.bean.LoadBean;
import com.bawei.wangxueshi.youyuanwang.presenter.LoadActivityPresent;
import com.bawei.wangxueshi.youyuanwang.utils.KeyBoardHelper;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.bawei.wangxueshi.youyuanwang.view.LoadActivityView;
import com.bawei.wangxueshi.youyuanwang.widget.EditTextWithDel;
import com.bawei.wangxueshi.youyuanwang.widget.EditTextWithPassWord;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoadActivity extends BaseMvpActivity<LoadActivityView, LoadActivityPresent> implements LoadActivityView {

    @BindView(R.id.load_iv_fanhui)
    Button loadIvFanhui;
    @BindView(R.id.load_tv_register)
    TextView loadTvRegister;
    @BindView(R.id.load_et_name)
    EditTextWithDel loadEtName;
    @BindView(R.id.load_et_password)
    EditTextWithPassWord loadEtPassword;
    @BindView(R.id.load_bt_load)
    Button loadBtLoad;
    @BindView(R.id.load_top)
    LinearLayout activityLoad;
    @BindView(R.id.a_load_scro)
    ScrollView aLoadScro;
    @BindView(R.id.activity_load1)
    LinearLayout activityLoad1;
    @BindView(R.id.load_tv_youke)
    TextView loadTvYouke;
    private Unbinder bind;

    @Override
    public LoadActivityPresent initPresenter() {
        return new LoadActivityPresent();
    }

    private int bottomHeight;
    private KeyBoardHelper boardHelper;
    private View layoutBottom = loadBtLoad;
    private View layoutContent = activityLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
        bind = ButterKnife.bind(this);
        loadBtLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.vaildInfor(loadEtName.getText().toString().trim(), loadEtPassword.getText().toString().trim(), "", "", latitude, longitude);
            }
        });
        loadTvYouke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoadActivity.this,Main2Activity.class));
                finish();
            }
        });
        boardHelper = new KeyBoardHelper(this);
        boardHelper.onCreate();
        loadBtLoad.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = loadBtLoad.getHeight() + activityLoad.getHeight();
            }
        });
        boardHelper.setOnKeyBoardStatusChangeListener(new KeyBoardHelper.OnKeyBoardStatusChangeListener() {
            @Override
            public void OnKeyBoardPop(int keyBoardheight) {
                final int height = keyBoardheight;
                System.out.println("height = " + height);
                System.out.println("按钮的高度+以上布局的高度 = " + bottomHeight);
                if (bottomHeight < height) {
                    loadBtLoad.setVisibility(View.GONE);

                } else {
                    int offset = bottomHeight - height;
                    System.out.println("按钮的高度-屏幕的高减去可视区域的底部 = " + offset);
//                    final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) activityLoad
//                            .getLayoutParams();
                    //距离顶部多少
                    //lp.topMargin = -offset;
                    aLoadScro.scrollTo(0, -offset);
                    //  activityLoad.setLayoutParams(lp);
                }
            }
            @Override
            public void OnKeyBoardClose(int oldKeyBoardheight) {
                if (View.VISIBLE != loadBtLoad.getVisibility()) {
                    loadBtLoad.setVisibility(View.VISIBLE);
                }
                activityLoad1.setVisibility(View.VISIBLE);
//                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) activityLoad
//                        .getLayoutParams();
//                if (lp.topMargin != 0) {
//                    lp.topMargin = 0;
//                    activityLoad.setLayoutParams(lp);
//                }
            }
        });
        if (loadBtLoad == null) {
            System.out.println("\"loadBtLoad\" = " + "kong");
        }
    }

    @OnClick({R.id.load_iv_fanhui, R.id.load_tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.load_iv_fanhui:
                finish();
                break;
            case R.id.load_tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        //防止内存泄漏
        AppManager.getAppManager().finishActivity(LoadActivity.class);
    }

    @Override
    public void LoadSuccess(String registerBean) {
        System.out.println("\"登录\" = " + registerBean);
        if (registerBean.contains("200")) {
            Gson gson = new Gson();
            PreferencesUtils.addConfigInfo(LoadActivity.this, "isLoad", true);
            LoadBean loadBean1 = gson.fromJson(registerBean, LoadBean.class);

            startActivity(new Intent(LoadActivity.this, Main2Activity.class));
        } else {
            MyToast.makeText(LoadActivity.this, "输入有误", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void LoadFailed(int code) {
        System.out.println("失败code = " + code);
        //   startActivity(new Intent(LoadActivity.this, Main2Activity.class));
    }

    protected void setImmerseLayout(View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int statusBarHeight = getStatusBarHeight(this.getBaseContext());
            System.out.println("statusBarHeight = " + statusBarHeight);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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


    @OnClick(R.id.load_tv_youke)
    public void onViewClicked() {
    }
}
