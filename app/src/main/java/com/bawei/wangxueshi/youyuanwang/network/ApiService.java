package com.bawei.wangxueshi.youyuanwang.network;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by muhanxi on 17/6/19.
 */

public interface ApiService {





    @GET
    public Observable<String> get(@Url String url);


    @GET
    public Observable<String> get(@Url String url, @QueryMap Map<String, String> map);



//    @FormUrlEncoded
//    @POST
//    public Observable<String> post(@Url String url, @FieldMap Map<String, String> map);
    @GET
    public Observable<String> post(@Url String url, @QueryMap Map<String, String> map);



    //上传图片头像
    @Multipart
    @POST("MyInterface/userAction_uploadImage.action")
    Observable<String> uploadPhoto(@Part("user.file") MultipartBody file, @PartMap Map<String,String> map);
    //上传到相册
    @Multipart
    @POST("/MyInterface/userAction_uploadPhotoAlbum.action")
    Observable<String> uploadPhotos(@Part("user.file") MultipartBody file, @PartMap Map<String, String> map);
    //请求发现的数据
    @GET("/MyInterface/userAction_selectAllUser.action")
    Observable<String> getFindData(@QueryMap Map<String, String> map);

    //请求用户的详细信息
    @GET("/MyInterface/userAction_selectUserById.action")
    Observable<String> getPersonMessage(@QueryMap Map<String, String> map);
//验证 短信 验证码
    @FormUrlEncoded
    @POST("/sms/verify")
    public Observable<String> textDuanxin(@FieldMap Map<String, String> map);


    //请求获得朋友列表
    @GET("/MyInterface/userAction_selectAllUserAndFriend.action")
    Observable<String> getFriendData(@QueryMap Map<String, String> map);

    //添加好友
    @GET("/MyInterface/userAction_addFriends.action")
    Observable<String> addFriend(@QueryMap Map<String, String> map);



}
