package com.bawei.wangxueshi.youyuanwang.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.wangxueshi.youyuanwang.IApplication;
import com.bawei.wangxueshi.youyuanwang.R;
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
import com.bawei.wangxueshi.youyuanwang.widget.BitmapToRound_Util;
import com.bawei.wangxueshi.youyuanwang.widget.MyToast;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.bawei.wangxueshi.youyuanwang.utils.ImageResizeUtils.copyStream;

@RuntimePermissions
public class UploadPhotoActivity extends IActivity {
    @BindView(R.id.uploadphone_bt_fanhui)
    Button uploadphoneBtFanhui;
    @BindView(R.id.uploadphone_bt_camera)
    Button uploadphoneBtCamera;
    @BindView(R.id.uploadphone_bt_up)
    Button uploadphoneBtUp;
    @BindView(R.id.uploadphoto_iv_face)
    ImageView uploadphotoIvFace;
    @BindView(R.id.uploadphone_bt_mephone)
    Button uploadphoneBtMephone;
    private Unbinder bind;
    static final int INTENTFORCAMERA = 1;
    static final int INTENTFORPHOTO = 2;
    //文件名称
    public String LocalPhotoName;



    private BitmapToRound_Util round_Util = new BitmapToRound_Util();
    private File file;
    private int bitmapheight;
    private int bitmapwidth;

    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        bind = ButterKnife.bind(this);
        createLocalPhotoName();
        uploadphoneBtFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                AppManager.getAppManager().finishActivity(UploadPhotoActivity.class);
            }
        });
        uploadphoneBtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCheckPermissionCamera();
            }
        });
        uploadphoneBtUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPhoto();
            }
        });
        uploadphoneBtMephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调转我的相册的Acticity
                startActivity(new Intent(UploadPhotoActivity.this,MePhotoActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         bind.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 打开系统相机
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void toCamera() {
        try {
//           Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
//            startActivityForResult(intentNow, INTENTFORCAMERA);
            Intent intent = new Intent();
            file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
            if (file.exists()) {
            } else {
                file.createNewFile();
            }
            Uri uri;
            //  File file = new FileStorage().createIconFile();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
                uri = FileProvider.getUriForFile(UploadPhotoActivity.this, "com.ddz.demo", file);//通过FileProvider创建一个content类型的Uri，进行封装
            }
            else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
                uri = Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName()));
            }
            //   SDCardUtils.getMyFaceFile(createLocalPhotoName())=file;
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定URI
            startActivityForResult(intent, INTENTFORCAMERA);//启动拍照
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("需要打开您的相机来上传照片并保存照片")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void onDenied() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

    }


    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void onNeverAsyAgain() {
        Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
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

    public void toCheckPermissionCamera() {
        UploadPhotoActivityPermissionsDispatcher.toCameraWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UploadPhotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENTFORPHOTO:
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
                        //设置头像
                        bitmap = round_Util.toRoundBitmap(bitmap);
                        uploadphotoIvFace.setImageBitmap(bitmap);
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
                break;
            case INTENTFORCAMERA:
//                相机
                try {
                    //file 就是拍照完 得到的原始照片
                    // File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), Constants.RESIZE_PIC);
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            //设置头像
                            bitmap = round_Util.toRoundBitmap(bitmap);
                            uploadphotoIvFace.setImageBitmap(bitmap);

                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                        uploadFile(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
        RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                try {
                    Gson gson = new Gson();
                    System.out.println("上传图片result = " + result);
                    UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
                    if (bean.getResult_code() == 200) {
                        MyToast.makeText(IApplication.getApplication(), "上传成功", Toast.LENGTH_SHORT);

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "" + code, Toast.LENGTH_SHORT);
            }
        });
    }

}
