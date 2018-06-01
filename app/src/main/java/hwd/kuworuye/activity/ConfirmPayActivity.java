package hwd.kuworuye.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.base.InitApplication;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageData;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.ActionCameraEvent;
import hwd.kuworuye.event.ActionMultiPhotoEvent;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.TipUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConfirmPayActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_activity_back)
    ImageView mIvActivityBack;
    @BindView(R.id.iv_loading_camera)
    ImageView mIvLoadingCamera;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.iv_show_pay)
    ImageView mIvShowPay;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;
    private Dialog dialog;
    private Button btn_picture, btn_photo, btn_cancle;
    private static String path = "/sdcard/joinsite/";// sd路径
    public static final int IMAGE_REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST_CODE = 3;
    //相册图片上传集合；
    private ArrayList<ImageData> urls = new ArrayList<>();
    private String confirmPayUrl = "";
    private String mOrderId;
    private String mOrderStatus;
    private String mApprovalType;
    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        if (intent != null) {
            mOrderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
            mOrderStatus = intent.getStringExtra(Constant.ORDER_STATE);
            mApprovalType = intent.getStringExtra(Constant.ORDER_ISREFUSE);
        }
        initData();
    }

    private void initData() {
        mIvLoadingCamera.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
        mIvActivityBack.setOnClickListener(this);
        mIvShowPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_loading_camera:
                showDialog();
                break;
            case R.id.bt_submit:
                int visibility = mIvLoadingCamera.getVisibility();
                if (visibility != View.GONE) {
                    Util.toast(ConfirmPayActivity.this, "请上传支付成功照片截图");
                    return;
                }
                loadingPic2OOS();
                break;
            case R.id.iv_activity_back:
                finish();
                break;
            case R.id.iv_show_pay:
                Intent intent = new Intent(ConfirmPayActivity.this, PhotoViewPagerActivity.class);
                intent.putExtra(Constant.COMM_SOLA_PIC_URL, confirmPayUrl);
                startActivity(intent);
                break;
        }
    }

    private void loadingPic2OOS() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //显示个等待页面
//                showLoading();
                Util.toast(ConfirmPayActivity.this, "正在上传，请稍后...");
            }
        });


        PutObjectRequest put = new PutObjectRequest(InitApplication.OSS_BUCKET, "persistence/allImg/" + getImageObjectKey("123456789"), confirmPayUrl);

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        InitApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                dismissLoading();
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                String keyUrl = request.getObjectKey();

                final String pathUrl = Constant.OSSPATH + keyUrl;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingPic2Net(pathUrl);
                    }
                });

                Log.i("test", "请求id： " + keyUrl);
            }

            @Override
            public void onFailure(PutObjectRequest request, final ClientException clientExcepion, ServiceException serviceException) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //如果上传失败了，通过mHandler ，发出失败的消息到主线程中。处理异常。
                        String s = clientExcepion.toString();
                        dismissLoading();
                    }
                });
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }

            }
        });
    }


    //通过UserCode 加上日期组装 OSS路径
    private String getImageObjectKey(String strUserCode) {

        Date date = new Date();
        return strUserCode + new SimpleDateFormat("yyyyMMddssSSS").format(date) + ".jpg";
    }

    private void loadingPic2Net(String url) {
        String userId = (String) SharedPreferencesUtil.getInstance(this).read(Constant.USERID);
        showLoading();
        OkHttpUtils.post(HttpConstant.ORDER_APPROVE_SUBMIT + "id=" + mOrderId + "&user.id=" + userId + "&orderStatus=" + mOrderStatus + "&approvalType=" + mApprovalType + "&paymentProof=" + url).tag(this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    TipUtil.operationSuccessTip(ConfirmPayActivity.this, true);
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(ConfirmPayActivity.this, failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();

        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        btn_picture = (Button) window.findViewById(R.id.bt_picture);
        btn_photo = (Button) window.findViewById(R.id.bt_photo);
        btn_cancle = (Button) window.findViewById(R.id.bt_cancle);

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最多一次只能显示6张；
                int selectPhotoNum = 1;
                GalleryActivity.openActivity(ConfirmPayActivity.this, false, selectPhotoNum, 12);
                dialog.dismiss();
            }
        });


        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断存储卡是否可以用，可用进行存储
                if (Util.hasSdcard()) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                } else {
                    Util.toast(ConfirmPayActivity.this, "未找到存储卡，无法存储照片！");
                    dialog.dismiss();

                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode != ConfirmPayActivity.this.RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    //这里图片没有进行压缩处理待解决；
                    ArrayList<String> images = data.getStringArrayListExtra(Util.EXTRA_PHOTO_PATHS);
                    sendMultiPhotoUrisToView(images);
                    break;
                case CAMERA_REQUEST_CODE:
                    //生成输入文件路径，进行压缩
                    bitmap = (Bitmap) data.getExtras().get("data");
                    sendCameraUriToView(bitmap);
                    break;
            }
        } else {
            dialog.dismiss();
        }
        if (12 == requestCode && resultCode == Activity.RESULT_OK) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            sendMultiPhotoUrisToView(images);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 相册；
     */
    private void sendMultiPhotoUrisToView(List<String> uris) {
        try {
            EventBus.getDefault().post(new ActionMultiPhotoEvent(uris, 0));
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * .
     * 相机拍照返回的
     */
    private void sendCameraUriToView(Bitmap bitmap) {

        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        try {
            String takePhotoUrl = path + "takephoto" + getRandomNum() + ".jpg";// 图片名字
            b = new FileOutputStream(takePhotoUrl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            EventBus.getDefault().post(new ActionCameraEvent(takePhotoUrl, 0));
            dialog.dismiss();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回一个0-1000的随机数
     *
     * @return
     */
    public double getRandomNum() {
        return Math.random() * 1000;
    }

    @Subscribe
    public void onEventName(ActionMultiPhotoEvent event) {
        List<String> uris = event.getUris();
        for (String u : uris) {
            ImageData data = new ImageData();
            data.setImgs(u);
            urls.add(data);
        }
        changeView(uris.get(0));
    }

    private void changeView(String s) {
        mIvLoadingCamera.setVisibility(View.GONE);
        mTvConfirm.setVisibility(View.GONE);
        mIvShowPay.setVisibility(View.VISIBLE);
        confirmPayUrl = s;
        Glide.with(this).load(s).placeholder(R.drawable.place).into(mIvShowPay);
    }

    @Subscribe
    public void onEventPhoto(ActionCameraEvent event) {
        ImageData data = new ImageData();
        data.setImgs(event.getUri());
        urls.add(data);
        changeView(data.getImgs());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
