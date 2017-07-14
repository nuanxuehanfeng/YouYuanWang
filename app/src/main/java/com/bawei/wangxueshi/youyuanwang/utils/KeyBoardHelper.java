package com.bawei.wangxueshi.youyuanwang.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

public class KeyBoardHelper {

    private Activity activity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private int screenHeight;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    // 当 blankHeight 不为 0 即为软键盘高度。
    private int blankHeight = 0;

    public KeyBoardHelper(Activity activity) {
        this.activity = activity;
        //获得屏幕的高
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        //设置setSoftInputMode
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onDestory() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
           // 用来获取当前窗口可视区域大小的
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            //这个高度为屏幕的高度-
            int newBlankheight = screenHeight - rect.bottom;
            if (newBlankheight != blankHeight) {
                if (newBlankheight > blankHeight) {
                    if (onKeyBoardStatusChangeListener != null) {
                      //  System.out.println("可视区域的大小rect.bottom = " + rect.bottom);
                    //    System.out.println("不可视区域的= " + newBlankheight);
                    //    System.out.println("screenHeight屏幕的 = " + screenHeight);
                       onKeyBoardStatusChangeListener.OnKeyBoardPop(newBlankheight);
                       // onKeyBoardStatusChangeListener.OnKeyBoardPop(rect.bottom);
                    }
                } else { // newBlankheight < blankHeight
                    // keyboard close
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardClose(blankHeight);
                    }
                }
            }
            blankHeight = newBlankheight;
        }
    };

    public void setOnKeyBoardStatusChangeListener(
            OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void OnKeyBoardPop(int keyBoardheight);

        void OnKeyBoardClose(int oldKeyBoardheight);
    }
}