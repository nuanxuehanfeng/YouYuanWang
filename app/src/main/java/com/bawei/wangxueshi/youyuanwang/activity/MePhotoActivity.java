package com.bawei.wangxueshi.youyuanwang.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.adapter.MePhotoRecyclerviewAdapter;
import com.bawei.wangxueshi.youyuanwang.base.AppManager;
import com.bawei.wangxueshi.youyuanwang.base.IActivity;
import com.bawei.wangxueshi.youyuanwang.bean.UploadPhotoBean;
import com.bawei.wangxueshi.youyuanwang.core.JNICore;
import com.bawei.wangxueshi.youyuanwang.core.SortUtils;
import com.bawei.wangxueshi.youyuanwang.network.BaseObserver;
import com.bawei.wangxueshi.youyuanwang.network.RetrofitManager;
import com.bawei.wangxueshi.youyuanwang.utils.Constants;
import com.bawei.wangxueshi.youyuanwang.utils.ImageResizeUtils;
import com.bawei.wangxueshi.youyuanwang.utils.SDCardUtils;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bawei.wangxueshi.youyuanwang.activity.UploadPhotoActivity.INTENTFORPHOTO;
import static com.bawei.wangxueshi.youyuanwang.utils.ImageResizeUtils.copyStream;

public class MePhotoActivity extends IActivity {

    @BindView(R.id.mephoto_bt_fanhui)
    Button mephotoBtFanhui;
    @BindView(R.id.mephoto_recyclerview)
    RecyclerView mephotoRecyclerview;
    private Unbinder bind;
 String LocalPhotoName;
    private int bitmapheight;
    private int bitmapwidth;
    private List<String> list;
    private MePhotoRecyclerviewAdapter mePhotoRecyclerviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_photo);
        bind = ButterKnife.bind(this);
        mephotoBtFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mephotoRecyclerview.setLayoutManager(manager);
        list = new ArrayList<>();
         list.add("a");

        mePhotoRecyclerviewAdapter = new MePhotoRecyclerviewAdapter(list, this);
        mephotoRecyclerview.setAdapter(mePhotoRecyclerviewAdapter);

        mePhotoRecyclerviewAdapter.setAddimage(new MePhotoRecyclerviewAdapter.addImage() {
            @Override
            public void addimage() {
                System.out.println("\"图片的点击事件\" = " + "图片的点击事件");
                toPhoto();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        //防止内存泄漏
        AppManager.getAppManager().finishActivity(MePhotoActivity.class);
    }
    /**
     * 打开相册
     */
    public void toPhoto() {
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (INTENTFORPHOTO == requestCode)
            //相册
            try {
                // 必须这样处理，不然在4.4.2手机上会出问题
                Uri originalUri = data.getData();
                File f = null;
                if (originalUri != null) {
                    f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                    if (null == actualimagecursor) {
                        if (originalUri.toString().startsWith("file:")) {
                            File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                            if (!file.exists()) {
                                //地址包含中文编码的地址做utf-8编码
                                originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                            }
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        }
                    } else {
                        // 系统图库
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(actual_image_column_index);
                        if (img_path == null) {
                            InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        } else {
                            File file = new File(img_path);
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        }
                    }
                    Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);

                    bitmapwidth = bitmap.getWidth();
                    bitmapheight = bitmap.getHeight();

                    FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            bitmap.isRecycled();
                        }
                        uploadFile(f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void uploadFile(File file) {
        if (!file.exists()) {
            MyToast.makeText(this, " 照片不存在", Toast.LENGTH_SHORT);
            return;
        }
        String[] arr = file.getAbsolutePath().split("/");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        long ctimer = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", ctimer + "");
        map.put("user.picWidth", bitmapwidth+"");
        map.put("user.picHeight", bitmapheight+"");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("image", arr[arr.length - 1], requestFile)
                .build();
        RetrofitManager.uploadPhotoToXiangce(body, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                try {
                    System.out.println("上传图片result = " + result);

                    Gson gson = new Gson();
                    UploadPhotoBean uploadPhotoBean = gson.fromJson(result, UploadPhotoBean.class);

                   list.add(uploadPhotoBean.getHeadImagePath());
                    mePhotoRecyclerviewAdapter.notifyDataSetChanged();
                    // Gson gson = new Gson();
                   // UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
                   // if (bean.getResult_code() == 200) {
                   //     MyToast.makeText(IApplication.getApplication(), "上传成功", Toast.LENGTH_SHORT);
                   //     AppManager.getAppManager().finishActivity(UploadPhotoActivity.class);
                  //  }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "上传图片失败" + code, Toast.LENGTH_SHORT);
            }
        });

    }
}
