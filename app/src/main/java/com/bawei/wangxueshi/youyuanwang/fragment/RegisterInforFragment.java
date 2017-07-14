package com.bawei.wangxueshi.youyuanwang.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.activity.RegisterActivity;
import com.bawei.wangxueshi.youyuanwang.activity.UploadPhotoActivity;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.BaseMvpFragment;
import com.bawei.wangxueshi.youyuanwang.bean.RegisterBean;
import com.bawei.wangxueshi.youyuanwang.cipher.Md5Utils;
import com.bawei.wangxueshi.youyuanwang.presenter.RegisterInforFragmentPresenter;
import com.bawei.wangxueshi.youyuanwang.utils.PreferencesUtils;
import com.bawei.wangxueshi.youyuanwang.view.RegisterInforFragmentView;
import com.bawei.wangxueshi.youyuanwang.widget.EditTextWithPassWord;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RegisterInforFragment extends BaseMvpFragment<RegisterInforFragmentView, RegisterInforFragmentPresenter> implements  RegisterInforFragmentView{
    @BindView(R.id.registerinfor_et_nickname)
    EditText registerinforEtNickname;
    @BindView(R.id.registerinfor_tv_sex)
    TextView registerinforTvSex;
    @BindView(R.id.registerinfor_tv_age)
    TextView registerinforTvAge;
    @BindView(R.id.registerinfor_tv_area)
    TextView registerinforTvArea;
    @BindView(R.id.registerinfor_tv_introduce)
    TextView registerinforTvIntroduce;
    @BindView(R.id.registerinfor_bt_next)
    Button registerinforBtNext;
    @BindView(R.id.registerinfor_et_password)
    EditTextWithPassWord registerinforEtPassword;
    private Unbinder bind;
    private RegisterActivity activity;
    private InputMethodManager imm;

    @Override
    public RegisterInforFragmentPresenter initPresenter() {
        return new RegisterInforFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registerinfor, container, false);
        activity = (RegisterActivity) getActivity();
        bind = ButterKnife.bind(this, view);
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        String latitude = PreferencesUtils.getValueByKey(getActivity(), "latitude", "");
        String longitude = PreferencesUtils.getValueByKey(getActivity(), "longitude", "");


        RxView.clicks(registerinforBtNext).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                presenter.vaildInfor(
                        phone,
                        registerinforEtNickname.getText().toString().trim(),
                        registerinforTvSex.getText().toString().trim(),
                        registerinforTvAge.getText().toString().trim(),
                        registerinforTvArea.getText().toString().trim()
                        , registerinforTvIntroduce.getText().toString().trim()
                        , PreferencesUtils.getValueByKey(getActivity(), "latitude", "")
                        ,PreferencesUtils.getValueByKey(getActivity(), "longitude", ""),
                        Md5Utils.getMD5(registerinforEtPassword.getText().toString().trim()));
            }
        });










        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
    @OnClick({R.id.registerinfor_tv_sex, R.id.registerinfor_tv_age, R.id.registerinfor_tv_area, R.id.registerinfor_tv_introduce, R.id.registerinfor_bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registerinfor_tv_sex:
                showSexChooseDialog();
                break;
            case R.id.registerinfor_tv_age:
                showAgeDialog();
                break;
            case R.id.registerinfor_tv_area:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                CityPickerView cityPickerView = new CityPickerView(getActivity());
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                       // Toast.makeText(getActivity(),province+"-"+city+"-"+district,Toast.LENGTH_LONG).show();
                        registerinforTvArea.setText(province+"  "+city+"   "+district);
                    }
                });
                cityPickerView.show();
                break;
            case R.id.registerinfor_tv_introduce:
                activity.toPos(2);
                break;
            case R.id.registerinfor_bt_next:

                toData();
                break;
        }
    }
    private String[] sexArry = new String[]{"女", "男"};
    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                registerinforTvSex.setText(sexArry[which]);
            }
        });
        builder.show();
    }
    private String phone;
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDes(String des) {
        if (!TextUtils.isEmpty(des)) {
            if(des.length()>20){
                des = des.substring(0, 20) + "...";
                registerinforTvIntroduce.setText(des);
            }
            else{
            registerinforTvIntroduce.setText(des);
            }


        }
    }
    AlertDialog.Builder builder;
    private void showAgeDialog() {
        if (builder == null) {
            final String[] ages = getResources().getStringArray(R.array.age);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请选择年龄");
            builder.setItems(ages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    registerinforTvAge.setText(ages[which]);
                }
            });
        }
        builder.show();
    }

    /**
     * 判断所有的参数 非空
     * 注册 添加 草稿功能
     */
    private void toData() {
              //还可以输入多少字
//        TextWatcher textWatcher=new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        //给这个控件，添加textWatcher
//        registerinforEtNickname.addTextChangedListener(textWatcher);
      //在两秒内取第一次点击

    }
    @Override
    public void registerSuccess(RegisterBean registerBean) {
        //调到上传照片

        if(registerBean.getResult_code()==200){
            System.out.println("\"注册成功\" = " + "注册成功");
        startActivity(new Intent(getActivity(), UploadPhotoActivity.class));
            AppManager.getAppManager().finishActivity(getActivity());


        }
        else{
            MyToast.makeText(getActivity(),"参数不可为空或者已经注册过了", Toast.LENGTH_SHORT);
           // startActivity(new Intent(getActivity(), UploadPhotoActivity.class));
        }
    }
    @Override
    public void registerFailed(int code) {
        System.out.println("\"注册失败\" = " + "注册失败");
    }
}
