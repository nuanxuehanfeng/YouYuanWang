package com.bawei.wangxueshi.youyuanwang.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.bawei.wangxueshi.youyuanwang.base.IFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterIntroduceFragment extends IFragment {


    @BindView(R.id.register_des)
    EditText registerDes;
    @BindView(R.id.register_des_save)
    Button registerDesSave;
    Unbinder unbinder;
    RegisterActivity activity ;
    private InputMethodManager imm;
    private TextView tv;

    public RegisterIntroduceFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (RegisterActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_register_introduce, container, false);
        unbinder = ButterKnife.bind(this, view);
        tv = (TextView) view.findViewById(R.id.register_des1);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        registerDes.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;
            int sheng=100;
            //在变化之前用的
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            temp = s;

            }
            //变化时
            //count>0  加字    count=0 为减字 before减了多少
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChangeds = " + s);
                System.out.println("+ onTextChangeds = start" + start);
                System.out.println("+ onTextChangeds =before " + before);
                System.out.println("+ onTextChangeds =count " + count);//变化的数量，若为0表示减一个
                if(count>0){
                    sheng-=count;
                    tv.setText("还可以输入"+(sheng)+"字");
                }
                else if(count==0){
                    sheng+=before;
                    tv.setText("还可以输入"+(sheng)+"字");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                editStart = registerDes.getSelectionStart();
                editEnd = registerDes.getSelectionEnd();
                if (temp.length() >100) {//限制长度
                    Toast.makeText(getActivity(),
                            "输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
                            .show();
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    registerDes.setText(s);
                    registerDes.setSelection(tempSelection);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.register_des_save)
    public void onClick() {


        // 关闭软键盘 如果 显示 则隐藏  同理
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        //强制隐藏键盘
        imm.hideSoftInputFromWindow(registerDes.getWindowToken(), 0);
//        强制显示键盘
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);

        //隐藏键盘
//        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开

        String des =  registerDes.getText().toString().trim() ;
        activity.setDes(des);
        //跳转第二个fragment
        activity.toPos(1);
    }
}
