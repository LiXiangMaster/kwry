package hwd.kuworuye.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
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
import hwd.kuworuye.adapter.PicUpLoadAdapter;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.base.InitApplication;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageData;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.ActionCameraEvent;
import hwd.kuworuye.event.ActionMultiPhotoEvent;
import hwd.kuworuye.event.BackPic2ListEvent;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyGridView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/13.
 */

public class SitePicUpLoadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_site_pic_update)
    RelativeLayout mLlSitePicUpdate;
    @BindView(R.id.iv_activity_back)
    ImageView mIvActivityBack;
    @BindView(R.id.tv_comm_title_bar_three)
    TextView mTvCommTitleBarThree;
    @BindView(R.id.bt_save_pic)
    Button mBtSavePic;
    @BindView(R.id.et_pic_describe)
    EditText mEtPicDescribe;
    @BindView(R.id.mg_site_pic_upload)
    MyGridView mMgSitePicUpload;
    private Dialog dialog;
    private Button btn_picture, btn_photo, btn_cancle;
    public static final int IMAGE_REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST_CODE = 3;
    //相册图片上传集合；
    private ArrayList<ImageData> urls = new ArrayList<>();
    private PicUpLoadAdapter mAdapter;
    //上传到后台的集合；
    private List<ImageDataUrlBean> urlAndDescribleList = new ArrayList<>();
    private int mJoinAddPicWay;
    private String mPathUrl;
    protected Handler mHandler = new Handler();
    private static String path = "/sdcard/joinsite/";// sd路径
    private String etPicDescribe;
    private String mType;
    private String mProcessId;
    private Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_site_pic_upload);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        Intent intent = getIntent();
        if (intent != null) {
            mJoinAddPicWay = intent.getIntExtra(Constant.JOIN_ADD_PIC_WAY, 0);
            mProcessId = intent.getStringExtra(Constant.PROCESSID);
        }
        initData();
        initAdapter();
    }


    public void initAdapter() {
        mAdapter = new PicUpLoadAdapter(this, urls);
        mMgSitePicUpload.setAdapter(mAdapter);
    }

    private void initData() {
        switch (mJoinAddPicWay) {
            case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                mTvCommTitleBarThree.setText("进场明细照片上传");
                break;
            case Constant.DISPLAY_APPLY_EDIT:
                mTvCommTitleBarThree.setText("超市明细照片上传");
                break;
            case Constant.OTHER_COST_HEXIAO_UPDATE:
                mType = "10";
                mTvCommTitleBarThree.setText("其他费用核销票据上传");
                break;
            case Constant.JOIN_SITE_HEXIAO_SITE_UPDATE:
                mType = "8";
                mTvCommTitleBarThree.setText("进场费用核销现场照片上传");
                break;
            case Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE:
                mType = "9";
                mTvCommTitleBarThree.setText("进场费用核销票据照片上传");
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_MARK:
                mTvCommTitleBarThree.setText("产品陈列核销超市明细照片上传");
                mType = "4";
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY:
                mTvCommTitleBarThree.setText("产品陈列核销照片上传");
                mType = "5";
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_TICKET:
                mTvCommTitleBarThree.setText("产品陈列核销票据照片上传");
                mType = "6";
                break;
            case Constant.DISPLAY_APPLY_MARK_UPDATE:
                mType = "3";
                mTvCommTitleBarThree.setText("产品陈列申请超市照片上传");
                break;
            case Constant.DISPLAY_DETAIL_HEXIAO_TICKET:
                mType = "3";
                mTvCommTitleBarThree.setText("产品陈列申请票据照片上传");
                break;
            case Constant.JOIN_SITE_COST_APPLY_UPDATE:
                mType = "7";
                mTvCommTitleBarThree.setText("进场申请明细照片上传");
                break;
            case Constant.SALE_HEXIAO_SITE_UPDATE:
                mType = "1";
                mTvCommTitleBarThree.setText("促销活动核销现场照片上传");
                break;
            case Constant.SALE_HEXIAO_TICKET_UPDATE:
                mType = "2";
                mTvCommTitleBarThree.setText("促销活动核销票据照片上传");
                break;
        }
        mBtSavePic.setOnClickListener(this);
        mLlSitePicUpdate.setOnClickListener(this);
        mIvActivityBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_site_pic_update:
                showDialog();
                break;
            case R.id.iv_activity_back:
                finish();
                break;
            case R.id.bt_save_pic:
                etPicDescribe = mEtPicDescribe.getText().toString().trim();
                if (TextUtils.isEmpty(etPicDescribe)) {
                    Util.toast(SitePicUpLoadActivity.this, "请填写照片描述");
                    return;
                }
                if (urls.size() == 0) {
                    Util.toast(SitePicUpLoadActivity.this, "请选择图片");
                    return;
                }

                //进场费用保存的图片
                if (Constant.SUBMIT_JOIN_SITE_COST_APPLY == mJoinAddPicWay) {
                    //上传图片到阿里云服务器；
                    forMethod();
                } else if (Constant.DISPLAY_APPLY_EDIT == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.JOIN_SITE_HEXIAO_SITE_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_MARK == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_TICKET == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_APPLY_MARK_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_APPLY_MARK_SUBMIT == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.DISPLAY_DETAIL_HEXIAO_TICKET == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.JOIN_SITE_COST_APPLY_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.OTHER_COST_HEXIAO_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.SALE_HEXIAO_SITE_UPDATE == mJoinAddPicWay) {
                    forMethod();
                } else if (Constant.SALE_HEXIAO_TICKET_UPDATE == mJoinAddPicWay) {
                    forMethod();
                }

            default:
                break;
        }
    }

    private void forMethod() {
//        Util.toast(SitePicUpLoadActivity.this, "正在上传，请稍等...");
        showLoading("正在上传图片，请稍等片刻...");
        for (int i = 0; i < urls.size(); i++) {
            uploadPic2Oss(urls.get(i).getImgs(), etPicDescribe, urls.size(), mJoinAddPicWay);
        }

    }

    //全局标记；
    private int count;


    private void uploadPic2Oss(String pahtUrl, final String picDescribe, final int listSize, final int joinType) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //显示个等待页面
                //这个位置的showloading有可能是个bug
//                showLoading();
//                Util.toast(SitePicUpLoadActivity.this, "正在上传，请稍后...");
            }
        });


        PutObjectRequest put = new PutObjectRequest(InitApplication.OSS_BUCKET, "persistence/allImg/" + getImageObjectKey("123456789"), pahtUrl);

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        InitApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
//                dismissLoading();
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                String keyUrl = request.getObjectKey();

                mPathUrl = Constant.OSSPATH + keyUrl;
                urlAndDescribleList.add(new ImageDataUrlBean(mPathUrl, picDescribe));

                switch (mJoinAddPicWay) {
                    case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                        sendMessage2Front();
                        break;
                    case Constant.DISPLAY_APPLY_MARK_SUBMIT:
                        sendMessage2Front();
                        break;
                    default:
                        count++;
                        if (count >= listSize) {
                            loadingPic2Net();
                        }
                        break;

                }

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

    private void loadingPic2Net() {
        String picJson = mGson.toJson(urlAndDescribleList);
        final String picSingleJson = Util.replaceSingleMark(picJson);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingToRealNet(picSingleJson);
            }
        });
    }

    /**
     * 所有的提交接口，不需要再这个界面就上传到后台；
     */
    private void sendMessage2Front() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                EventBus.getDefault().post(new BackPic2ListEvent(urlAndDescribleList, mJoinAddPicWay));
                finish();
            }
        });

    }

    /**
     * 上传图片到后台
     *
     * @param picSingleJson
     */
    private void loadingToRealNet(String picSingleJson) {
//        showLoading();
        OkHttpUtils.post(HttpConstant.SINGLE_ADD_PIC + "processId=" + mProcessId + "&type=" + mType + "&kwPhotoList=" + picSingleJson).tag(this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(SitePicUpLoadActivity.this, "保存成功");
                    finish();
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(SitePicUpLoadActivity.this, failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();

            }
        });
    }

    //通过UserCode 加上日期组装 OSS路径
    private String getImageObjectKey(String strUserCode) {
        Date date = new Date();
        return strUserCode + new SimpleDateFormat("yyyyMMddssSSS").format(date) + ".jpg";

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
                //最多一次只能显示50张；
                int selectPhotoNum = 50;
                GalleryActivity.openActivity(SitePicUpLoadActivity.this, false, selectPhotoNum, 12);
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
                    Util.toast(SitePicUpLoadActivity.this, "未找到存储卡，无法存储照片！");

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
        if (resultCode != SitePicUpLoadActivity.this.RESULT_CANCELED) {
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
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Subscribe
    public void onEventName(ActionMultiPhotoEvent event) {
        List<String> uris = event.getUris();
        for (String u : uris) {
            ImageData data = new ImageData();
            data.setImgs(u);
            urls.add(data);
        }

        mLlSitePicUpdate.setVisibility(View.GONE);

        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventPhoto(ActionCameraEvent event) {
        ImageData data = new ImageData();
        data.setImgs(event.getUri());
        urls.add(data);

        mLlSitePicUpdate.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }


    private void deleteSourceFile(String photoName) {
        //删除压缩前的照相文件
        File temp = new File(photoName);
        if (temp.exists()) {
            temp.delete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
