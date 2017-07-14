package com.bawei.wangxueshi.youyuanwang.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;

import java.util.List;


/**
 * 所有的 activity 都继承
 */
public class IActivity extends FragmentActivity implements View.OnClickListener {


    private TextView textViewTitle;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_i);


        System.loadLibrary("core");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        fragmentManager = getSupportFragmentManager();
        AppManager.getAppManager().addActivity(this);

    }

    public void setPubTitle(String title) {
        textViewTitle = (TextView) findViewById(R.id.pub_title_title);
        textViewTitle.setText(title);
    }

    public void setLeftBtn() {
        Button btnRight = (Button) findViewById(R.id.pub_title_leftbtn);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void toActivity(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 切换fragment
     *
     * @param pos
     * @param list
     */
    public void switchIFragment(int pos, List<Fragment> list, int containerId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!list.get(pos).isAdded()) {
            fragmentTransaction.add(containerId, list.get(pos), list.get(pos).getClass().getSimpleName());
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == pos) {
                fragmentTransaction.show(list.get(pos));
            } else {
                fragmentTransaction.hide(list.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy = " + this);

        AppManager.getAppManager().finishActivity(this);
    }


}
