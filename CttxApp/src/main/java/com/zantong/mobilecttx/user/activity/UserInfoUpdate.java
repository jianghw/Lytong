package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.api.FileUploadApi;
import com.zantong.mobilecttx.presenter.UserInfoUpdatePresenter;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.imagetools.ImageLoad;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.IOSpopwindow;
import com.zantong.mobilecttx.interf.UserInfoUpdateView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UserInfoUpdate extends BaseMvpActivity<UserInfoUpdateView, UserInfoUpdatePresenter> implements UserInfoUpdateView {
    @Bind(R.id.user_info_head_rl)
    RelativeLayout userInfoHeadRl;
    @Bind(R.id.user_info_name_rl)
    RelativeLayout userInfoNameRl;
    @Bind(R.id.user_info_phone_rl)
    RelativeLayout userInfoPhoneRl;
    @Bind(R.id.user_info_name_text)
    TextView user_info_name_text;
    @Bind(R.id.user_info_phone_text)
    TextView user_info_phone_text;
    @Bind(R.id.content_all)
    LinearLayout content_all;
    @Bind(R.id.user_head_image)
    ImageView user_head_image;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_FILE_NAME = "cttx_photo_head.jpg";
    private static final String PHOTO_CROP_FILE_NAME = "cttx_crop_photo_head.jpg";
    private File cttxFile;
    private UserInfoUpdatePresenter mUserInfoUpdatePresenter;
    HashMap<String, String> mHashMap = new HashMap<>();
    private Dialog mDialog;

    @Override
    public UserInfoUpdatePresenter initPresenter() {
        mUserInfoUpdatePresenter = new UserInfoUpdatePresenter(this);
        return mUserInfoUpdatePresenter;
    }

    @Override
    protected int getContentResId() {
        presenter.attach(this);
        return R.layout.user_info_update;
    }

    @Override
    public void initView() {
        setTitleText("个人信息");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getNickname())) {
            user_info_name_text.setText(PublicData.getInstance().mLoginInfoBean.getNickname());
        } else {
            user_info_name_text.setText(PublicData.getInstance().mLoginInfoBean.getPhoenum().substring(7));
        }
        String phone = StringUtils.getEncrypPhone(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        user_info_phone_text.setText(phone);
        ImageLoad.loadHead(this, PublicData.getInstance().mLoginInfoBean.getPortrait(), user_head_image);
    }

    @Override
    public void initData() {

    }

    @Override
    public String getOrderType() {
        return null;
    }

    @Override
    public void showProgress() {
//        mDialog = DialogUtils.showLoading(this,)
    }

    @Override
    public void updateView(Object object, int index) {
        switch (index) {
            case 1:
                ImageLoad.loadHead(UserInfoUpdate.this, mapData().get("portrait"), user_head_image);
                break;
        }
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.user_info_head_rl, R.id.user_info_name_rl, R.id.user_info_phone_rl, R.id.user_info_change_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_head_rl:
                File cacheDir;
                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "CTTXHEAD");
                else
                    cacheDir = UserInfoUpdate.this.getCacheDir();
                if (!cacheDir.exists())
                    cacheDir.mkdirs();
                IOSpopwindow menuWindow = new IOSpopwindow(UserInfoUpdate.this, content_all, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhone();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosePhone();
                    }
                });
                break;
            case R.id.user_info_name_rl:
                Act.getInstance().lauchIntent(UserInfoUpdate.this, UpdateNickName.class);
                break;
            case R.id.user_info_phone_rl:
//                IOSpopwindow menuWindow1 = new IOSpopwindow(UserInfoUpdate.this, content_all, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Act.getInstance().lauchIntent(UserInfoUpdate.this, UpdatePhoneNumber.class);
//                    }
//                });
                break;
            case R.id.user_info_change_pwd:
                Act.getInstance().lauchIntent(UserInfoUpdate.this, ChangePwdActivity.class);
                break;
        }
    }

    public void takePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
        } else {
            camera();
        }
    }

    public void choosePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
        } else {
            gallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camera();
            } else {
                Toast.makeText(this, "权限未开启", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 1002) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gallery();
            } else {
                Toast.makeText(this, "权限未开启", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public HashMap<String, String> mapData() {
        return mHashMap;
    }

    /*
     * 从相机获取
	 */

    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            Toast.makeText(UserInfoUpdate.this, "未找到存储卡，无法存储照片！1", Toast.LENGTH_SHORT).show();
            Uri mImageCaptureUri;

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果是7.0android系统
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, new File(
                        Environment.getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_FILE_NAME).getAbsolutePath());
                mImageCaptureUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            }else{
                mImageCaptureUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_FILE_NAME));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /*
     * 从相册获取
	 */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String img_path = getPath(this, uri);
                cttxFile = new File(img_path);
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                if (0 == resultCode) {
                    return;
                }
                cttxFile = new File(Environment.getExternalStorageDirectory() + "/CTTXHEAD/",
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(cttxFile));
            } else {
                Toast.makeText(UserInfoUpdate.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                Map<String, RequestBody> params = new HashMap<>();
                File imagFile = new File(Environment
                        .getExternalStorageDirectory() + "/CTTXHEAD/" + PHOTO_CROP_FILE_NAME);
                RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), imagFile);
                String imagFileName = "";
                String[] imageUrls = PublicData.getInstance().mLoginInfoBean.getPortrait().split("\\/");
                if (Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getPortrait())) {
                    imagFileName = PublicData.getInstance().userID + ".jpg";
                } else {
                    imagFileName = imageUrls[imageUrls.length - 1];
                }
                params.put("image\"; filename=\"" + imagFileName + "", body);
                Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
                FileUploadApi api = retrofit2Utils
                        .getRetrofitHttps(BuildConfig.ImageLoadUrl)
                        .create(FileUploadApi.class);
                api.uploadImage(params).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String gsonStr = response.body().string();
                            JSONObject json = new JSONObject(gsonStr);
                            String url = json.get("url").toString();
                            mapData().put("portrait", url);
                            if (Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getPortrait())) {
                                mapData().put("portrait", url);
                                mUserInfoUpdatePresenter.loadView(1);
                            } else {
                                ImageLoad.loadHead(UserInfoUpdate.this, PublicData.getInstance().mLoginInfoBean.getPortrait(), user_head_image);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        ToastUtils.showShort(UserInfoUpdate.this, Config.getErrMsg("1"));
                        Log.e("why", t.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setUserHeadImage() {
        user_head_image.setImageResource(R.mipmap.mine_head_default);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1001);
        intent.putExtra("aspectY", 1000);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("scale", true);
        // 图片格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                .getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_CROP_FILE_NAME)));
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);//人脸识别
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 4.4的系统有些bug,需要以下方法来获取文件的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }


            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
