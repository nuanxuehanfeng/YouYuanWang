package com.bawei.wangxueshi.youyuanwang.bean;

import com.bawei.wangxueshi.youyuanwang.bean.findbean.DataBean;

/**
 * Created by Administrator on 2017/7/11.
 */

public class LoadBean {

    /**
     * result_message : success
     * data : {"area":"安徽省  安庆市   枞阳县","password":"b59c67bf196a4758191e42f76670ceba","lasttime":1499755640666,"createtime":1499690390391,"gender":"男","lng":0,"phone":"15611111111","introduce":"我看见了","imagePath":"http://qhb.2dyt.com/MyInterface/images/d829187e-7e9a-4c4b-bab1-26e89a1fc97e.jpg","nickname":"我是我","userId":182,"lat":0}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }


}
