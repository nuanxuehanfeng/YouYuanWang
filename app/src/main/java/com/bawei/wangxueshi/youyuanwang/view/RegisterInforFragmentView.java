package com.bawei.wangxueshi.youyuanwang.view;

import com.bawei.wangxueshi.youyuanwang.bean.RegisterBean;

/**
 * Created by Administrator on 2017/7/5.
 */

public interface RegisterInforFragmentView  {

    public void registerSuccess(RegisterBean registerBean);
    public void registerFailed(int code);
}
