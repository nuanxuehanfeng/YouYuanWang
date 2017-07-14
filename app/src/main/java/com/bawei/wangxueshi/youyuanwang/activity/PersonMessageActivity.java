package com.bawei.wangxueshi.youyuanwang.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.adapter.AlertDialogViewPageAdapter;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.bean.personmessagebean.PersonBean;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.bawei.wangxueshi.youyuanwang.utils.image.GlideRoundTransform;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonMessageActivity extends IActivity {

    @BindView(R.id.activity_person_message_iv_head)
    ImageView activityPersonMessageIvHead;
    @BindView(R.id.activity_person_message_tv_fanhui)
    Button activityPersonMessageTvFanhui;
    @BindView(R.id.activity_person_message_linear_photos_one)
    ImageView activityPersonMessageLinearPhotosOne;
    @BindView(R.id.activity_person_message_linear_photos)
    LinearLayout activityPersonMessageLinearPhotos;
    @BindView(R.id.activity_person_message_tv_nickname)
    TextView activityPersonMessageTvNickname;
    @BindView(R.id.activity_person_message_tv_age)
    TextView activityPersonMessageTvAge;
    @BindView(R.id.activity_person_message_tv_sex)
    TextView activityPersonMessageTvSex;
    @BindView(R.id.activity_person_message_tv_area)
    TextView activityPersonMessageTvArea;
    @BindView(R.id.activity_person_message_tv_desc)
    TextView activityPersonMessageTvDesc;
    @BindView(R.id.activity_person_message_bt_message)
    Button activityPersonMessageBtMessage;
    @BindView(R.id.activity_person_message_bt_hello)
    Button activityPersonMessageBtHello;
    @BindView(R.id.activity_person_message_bt_gonext)
    Button activityPersonMessageBtGonext;
    private Gson gson;
    private Unbinder bind;


    PersonBean.DataBean person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        bind = ButterKnife.bind(this);
        gson = new Gson();
        final Intent intent = getIntent();
        int userid = intent.getIntExtra("userid", 0);
        if(userid==0){
            MyToast.makeText(this,"没有此人",Toast.LENGTH_SHORT);
            return;
        }
        Map<String, String> map = new HashMap<>();
        System.out.println("userid = " + userid);
        map.put("user.userId", userid + "");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);
        RetrofitManager.getPersonMessage(map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("请求个人的信息result = " + result);
                PersonBean personBean = gson.fromJson(result, PersonBean.class);
                if (personBean.getResult_code() == 200) {
                    PersonBean.DataBean data = personBean.getData();

                    person=data;

                    int userId = data.getUserId();
                    String area = data.getArea();
                    String imagePath = data.getImagePath();
                    String introduce = data.getIntroduce();
                    String nickname = data.getNickname();
                    final List<PersonBean.DataBean.PhotolistBean> photolist = data.getPhotolist();
                    String gender = data.getGender();
                    if (!TextUtils.isEmpty(imagePath)) {
                        Glide.with(PersonMessageActivity.this).load(imagePath).into(activityPersonMessageIvHead);
                    }
                    if (!TextUtils.isEmpty(area)) {
                        activityPersonMessageTvArea.setText(area);
                    }
                    if (!TextUtils.isEmpty(introduce)) {
                        if (introduce.length() > 10) {
                            activityPersonMessageTvDesc.setText(introduce.substring(0, 10) + "...");
                        }
                    }
                    if (!TextUtils.isEmpty(nickname)) {
                        activityPersonMessageTvNickname.setText(nickname);
                    }
                    if (!TextUtils.isEmpty(gender)) {
                        activityPersonMessageTvSex.setText(gender);
                    }
                    if (photolist != null && photolist.size() > 0) {

                        for (int i = 0; i < photolist.size(); i++) {

                            if (photolist.get(i).getImagePath().endsWith(".jpg")) {
                                final int a = i;
                                ImageView imageView = new ImageView(PersonMessageActivity.this);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                imageView.setPadding(0, 0, 30, 0);
                                int heightImage = activityPersonMessageLinearPhotosOne.getHeight();
                                int widthImage = activityPersonMessageLinearPhotosOne.getWidth();
                                ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(widthImage, heightImage);
                                imageView.setLayoutParams(layout);
                                Glide.with(PersonMessageActivity.this).load(photolist.get(i).getImagePath()).transform(new GlideRoundTransform(PersonMessageActivity.this,10)).into(imageView);
                                activityPersonMessageLinearPhotos.addView(imageView);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PersonMessageActivity.this);
                                        View view = View.inflate(PersonMessageActivity.this, R.layout.alertdialog_person_photo, null);

                                        ViewPager viewPager = (ViewPager) view.findViewById(R.id.alertdialog_person_photo_viewpager);
                                        AlertDialogViewPageAdapter alertDialogViewPageAdapter = new AlertDialogViewPageAdapter(photolist, PersonMessageActivity.this);
                                        viewPager.setAdapter(alertDialogViewPageAdapter);
                                        viewPager.setCurrentItem(a);
                                        builder.setView(view);

                                        builder.show();

                                    }
                                });
                            }
                        }
                    }
                } else {
                    MyToast.makeText(PersonMessageActivity.this, "请求发送错误", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailed(int code) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        bind.unbind();
        //解决内存泄漏的
        AppManager.getAppManager().finishActivity(PersonMessageActivity.this);
    }

    @OnClick({R.id.activity_person_message_tv_fanhui, R.id.activity_person_message_linear_photos_one})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.activity_person_message_tv_fanhui:
                AppManager.getAppManager().finishActivity(this);

                break;
            case R.id.activity_person_message_linear_photos_one:
                break;
        }
    }
    @OnClick({R.id.activity_person_message_bt_message, R.id.activity_person_message_bt_hello, R.id.activity_person_message_bt_gonext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_person_message_bt_message:
                System.out.println("\"发送消息\" = " + "发送消息");
                break;
            case R.id.activity_person_message_bt_hello:
                System.out.println("\"打招呼\" = " + "打招呼");
                Boolean isLoad = PreferencesUtils.getValueByKey(this, "isLoad", false);
                System.out.println("是否登录isLoad = " + isLoad);
                if(PreferencesUtils.getValueByKey(this,"isLoad",false)==false){
                    MyToast.makeText(PersonMessageActivity.this,"您还没有登录，请登录",Toast.LENGTH_SHORT);
                    return;
                }
                if(person==null){
                    MyToast.makeText(PersonMessageActivity.this,"没有此人",Toast.LENGTH_SHORT);
                    return;
                }
                Map<String,String> map=new HashMap<>();
                map.put("relationship.friendId", person.getUserId() + "");
                String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
                map.put("user.sign", sign);
                RetrofitManager.addFriend(map, new BaseObserver() {
                    @Override
                    public void onSuccess(String result) {
                  MyToast.makeText(PersonMessageActivity.this,"添加成功",Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void onFailed(int code) {
                     //   System.out.println("添加朋友result = " + code);
                    }
                });




                break;
            case R.id.activity_person_message_bt_gonext:
                System.out.println("\"下一个\" = " + "下一个");
                break;
        }
    }
}
