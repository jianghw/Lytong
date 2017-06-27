package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.FileUploadApi;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.UserInfoUpdateView;
import com.zantong.mobilecttx.presenter.UserInfoUpdatePresenter;
import com.zantong.mobilecttx.utils.StringUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.imagetools.ImageLoad;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.IOSpopwindow;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.log.LogUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 个人中心页面
 */
public class UserInfoUpdate extends BaseMvpActivity<UserInfoUpdateView, UserInfoUpdatePresenter>
        implements UserInfoUpdateView {
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

    private UserInfoUpdatePresenter mUserInfoUpdatePresenter;
    HashMap<String, String> mHashMap = new HashMap<>();

    private static final int REQ_TAKE_PHOTO = 100;// 拍照
    private static final int REQ_ALBUM_1 = 101;
    private static final int REQ_ALBUM_2 = 102;
    private static final int REQ_ZOOM = 103;

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
    public void initData() {
        File file = getHeadImageFile();
        if (file == null)
            ImageLoad.loadHead(
                    PublicData.getInstance().mLoginInfoBean.getPortrait(),
                    user_head_image
            );
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
                ImageLoad.loadHead(mapData().get("portrait"), user_head_image);
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
            case R.id.user_info_head_rl://设置头像
                new IOSpopwindow(this, content_all,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                takePhoto();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openAlbum();
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


    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    //READ_EXTERNAL_STORAGE
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        openCamera();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
    }

    /**
     * 拍照
     */
    private void openCamera() {
        // 指定调用相机拍照后照片的储存路径
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CAMERA_DIR);
        File mCameraFile = new File(ImgPath);
        Uri cameraUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //如果是7.0或以上
            cameraUri = getUriForFileByN(mCameraFile);

            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            cameraUri = Uri.fromFile(mCameraFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);

        startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    /**
     * N 7.0系统封装
     *
     * @param mCameraFile
     * @return
     */
    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(getApplicationContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//打开最近图片
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");

        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.GALLERY_DIR);
        File mGalleryFile = new File(ImgPath);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = getUriForFileByN(mGalleryFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, REQ_ALBUM_1);
        } else {
            startActivityForResult(intent, REQ_ALBUM_2);
        }
    }

    /**
     * 照相回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK://调用图片选择处理成功
                switch (requestCode) {
                    case REQ_TAKE_PHOTO:// 拍照后在这里回调
                        reqTakePhoto();
                        break;
                    case REQ_ALBUM_1:// 选择相册中的图片>7.0
                        if (data != null) {
                            startPhotoZoomByN(data);
                        }
                        break;
                    case REQ_ALBUM_2:// 选择相册中的图片
                        if (data != null) {
                            Uri uri = data.getData();
                            FileUtils.startPhotoZoom(uri, this, REQ_ZOOM);
                        }
                        break;
                    case REQ_ZOOM://裁剪后回调
                        if (data != null) {
                            reqZoom();
                        } else {
                            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 拍照回调
     */
    private void reqTakePhoto() {
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CAMERA_DIR);
        File mCameraFile = new File(ImgPath);
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = getUriForFileByN(mCameraFile);
        } else {
            inputUri = Uri.fromFile(mCameraFile);
        }

        FileUtils.startPhotoZoom(inputUri, this, REQ_ZOOM);
    }

    /**
     * 选择相册中的图片>7.0
     *
     * @param data
     */
    private void startPhotoZoomByN(Intent data) {

        String url = FileUtils.getPath(getApplicationContext(), data.getData());
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
            return;
        }
        File imgUri = new File(url);
        Uri dataUri = getUriForFileByN(imgUri);

        if (dataUri == null) {
            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
        } else {
            FileUtils.startPhotoZoom(dataUri, this, REQ_ZOOM);
        }
    }

    /**
     * 选择照片回调
     */
    private void reqAlbum(Intent data) {
//        Uri sourceUri = data.getData();
//        String[] proj = {MediaStore.Images.Media.DATA};
//        // 好像是android多媒体数据库的封装接口，具体的看Android文档
//        Cursor cursor = managedQuery(sourceUri, proj, null, null, null);
//        // 按我个人理解 这个是获得用户选择的图片的索引值
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
//        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
//        String imgPath = cursor.getString(column_index);

        Uri uri = data.getData();
        String imgPath = FileUtils.getPath(getApplicationContext(), uri);
        if (TextUtils.isEmpty(imgPath)) {
            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
        } else {
            File srcFile = new File(imgPath);
            File outPutFile = new File(FileUtils.generateImgePath(getApplicationContext()));
            Uri outputUri = Uri.fromFile(outPutFile);
            FileUtils.startPhotoZoom(this, srcFile, outPutFile, REQ_ZOOM);// 发起裁剪请求
        }
    }

    /**
     * 剪切回调
     */
    private void reqZoom() {
        //TODO 如果是拍照的,删除临时文件
        File temFile = getHeadImageFile();
        if (temFile == null) return;
//            String scaleImgPath = FileUtils.saveBitmapByQuality(bitmap, 80, getApplicationContext());//进行压缩
//            进行上传，上传成功后显示新图片,上传的逻辑就是将scaleImgPath这个路径下的图片上传，此处不做演示，这里只是显示到iv上
//                                ivPhoto.setImageBitmap(bitmap);

        Map<String, RequestBody> params = new HashMap<>();
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), temFile);
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
                if (response != null && response.isSuccessful()) {
                    ResponseBody mBody = response.body();
                    if (mBody != null) {
                        String url = null;
                        try {
                            JSONObject json = new JSONObject(mBody.string());
                            url = json.get("url").toString();

                            mapData().put("portrait", url);
                            if (Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getPortrait())) {
                                mUserInfoUpdatePresenter.loadView(1);
                            } else {
                                ImageLoad.loadHead(PublicData.getInstance().mLoginInfoBean.getPortrait(), user_head_image);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (response != null) {
                    ToastUtils.showShort(getApplicationContext(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShort(getApplicationContext(), Config.getErrMsg("1"));
            }
        });
    }

    /**
     * 获取头像显示
     */
    @Nullable
    private File getHeadImageFile() {
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CROP_DIR);

        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
            ToastUtils.showShort(getApplicationContext(), "头像图片可能已经移位或删除");
            return null;
        }
        Uri outputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            outputUri = getUriForFileByN(mCropFile);
            LogUtils.e("2==" + outputUri.toString());
        } else {
            outputUri = Uri.fromFile(mCropFile);
            LogUtils.e("3==" + outputUri.toString());
        }

        Bitmap bitmap = FileUtils.decodeUriAsBitmap(outputUri, getApplicationContext());
        if (bitmap != null) user_head_image.setImageBitmap(bitmap);

        return mCropFile;
    }

    public HashMap<String, String> mapData() {
        return mHashMap;
    }

    public void setUserHeadImage() {
        user_head_image.setImageResource(R.mipmap.icon_portrai);
    }

}
