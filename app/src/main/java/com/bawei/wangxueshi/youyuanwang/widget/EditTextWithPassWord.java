package com.bawei.wangxueshi.youyuanwang.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.bawei.wangxueshi.youyuanwang.R;

/**
 * @author: adan
 * @description: 自定义带有删除功能的EditText
 * @projectName: EditTextWithDelDome
 * @date: 2016-02-28
 * @time: 23:34
 */
public class EditTextWithPassWord extends EditText {
    private final static String TAG = "EditTextWithPassWord";
    private Drawable imgInable;
    private Drawable imgAble;
    private Context mContext;
    //默认是密码格式的
    private boolean isTrue=true;

    public EditTextWithPassWord(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithPassWord(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }
    public EditTextWithPassWord(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        this.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    private void init() {
        imgAble = mContext.getResources().getDrawable(
                R.drawable.show_password_icon);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }
    // 设置删除图片
    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
        }
    }
    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 150;
            if (rect.contains(eventX, eventY)){
                if(isTrue==true){
                    //如果选中，显示密码
                this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isTrue=false;
                }
                else{
                    //否则隐藏密码
                    this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isTrue=true;
                }
            }
            ;
               // setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}