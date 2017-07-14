package com.bawei.wangxueshi.youyuanwang.network;


import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.cookie.CookiesManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by muhanxi on 17/6/19.
 */

public class RetrofitManager {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookiesManager(IApplication.application))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .addNetworkInterceptor(new LoggingInterceptor())
            .build();


    private   static ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://qhb.2dyt.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            //把 以前的 call 转化成 Observable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiService.class);
    public  static void get(String url,Observer<String> observer){
         apiService.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void get(String url, Map<String,String> map, Observer<String> observer){

         apiService.get(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(observer);
    }
    public static void post(String url,Map<String,String> map, Observer<String> observer){
        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        System.out.println("登录sign = " + sign);
        map.put("user.sign",sign);
        apiService.post(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public static void uploadPhoto(MultipartBody multipartBody,Map<String,String> map, Observer<String> observer){
        apiService.uploadPhoto(multipartBody,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void uploadPhotoToXiangce(MultipartBody multipartBody,Map<String,String> map, Observer<String> observer){
        apiService.uploadPhotos(multipartBody,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public static void getFindData(Map<String,String> map, Observer<String> observer){

        apiService.getFindData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

//获得好友
    public static void getFriendData(Map<String,String> map, Observer<String> observer){

        apiService.getFriendData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    //添加好友
    public static void addFriend(Map<String,String> map, Observer<String> observer){

        apiService.addFriend(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    public static void getPersonMessage(Map<String,String> map, Observer<String> observer){
        apiService.getPersonMessage(map )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
    public static void getTextDuanxi(Map<String,String> map, Observer<String> observer){
        ApiService    apiService1 = new Retrofit.Builder()
                .baseUrl("https://webapi.sms.mob.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                //把 以前的 call 转化成 Observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiService.class);
        apiService1.getPersonMessage(map )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
