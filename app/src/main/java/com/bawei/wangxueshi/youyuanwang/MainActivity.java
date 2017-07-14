package com.bawei.wangxueshi.youyuanwang;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bawei.wangxueshi.youyuanwang.activity.LoadActivity;
import com.bawei.wangxueshi.youyuanwang.activity.Main2Activity;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;

public class MainActivity extends IActivity {

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(MainActivity.this, Main2Activity.class));



        ImageView tv= (ImageView) findViewById(R.id.main_dong);
        animation = new TranslateAnimation(0, 0, 0, -100);
        animation.setDuration(3000);
        animation.setRepeatCount(0);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        tv.startAnimation(animation);//开始动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(MainActivity.this, LoadActivity.class));
              //  AppManager.getAppManager().finishActivity(MainActivity.class);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        animation.cancel();
    }
}





//{"result_message":"success","data":{"area":"安徽省  安庆市   枞阳县","createtime":1499990563319,"gender":"男","lng":116.299801,"introduce":"太","imagePath":"http://qhb.2dyt.com/MyInterface/images/bba4062c-78d1-4985-bb93-a9b8119c3034.jpg","userId":7,"yxpassword":"09A681W0","password":"b59c67bf196a4758191e42f76670ceba","lasttime":1500002644092,"phone":"15600932535","nickname":"我是我","lat":40.040444},"result_code":200}
