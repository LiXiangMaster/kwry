package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.MainActivity;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.base.InitApplication;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ShowPersionInfo;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.LinePathView;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/6.
 */
public class MySignFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.lp_singn)
    LinePathView mLpSingn;
    Unbinder unbinder;
    @BindView(R.id.bt_sign_submit)
    Button mBtSignSubmit;
    @BindView(R.id.iv_show_sign)
    ImageView mIvShowSign;
    private View mTopBarView;
    private Topbar mTopbar;
    //用户信息id
    private String mUserInfoId;
    private boolean isFirst = false;
    private boolean isShowPic = false;
    protected Handler mHandler = new Handler();
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_my_sign;
    }

    public static Fragment newInstance(Bundle bundle) {
        MySignFragment signFragment = new MySignFragment();
        signFragment.setArguments(bundle);
        return signFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null) {
            isFirst = (boolean) arguments.get(Constant.FIRSTCERTIFICATION);
        }
        mGson = new Gson();
        initData();
        return rootView;
    }

    private void initData() {
        int visibility = mIvShowSign.getVisibility();
        if (View.VISIBLE == visibility) {
            isShowPic = true;
        }
        //根据用户id获取用户基本数据；
        getUserInfoId();
        initTopbar();
        mBtSignSubmit.setOnClickListener(this);

    }

    private void getUserInfoId() {
        final String userId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        showLoading();
        OkHttpUtils.post(HttpConstant.SHOW_PERSION_DATAS + "userId=" + userId).tag(getActivity()).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ShowPersionInfo showPersionInfo = mGson.fromJson(s, ShowPersionInfo.class);
                boolean success = showPersionInfo.isSuccess();
                if (success) {
                    SharedPreferencesUtil.getInstance(getContext()).save("loginsuccessed", "loginsuccessed");
                    ShowPersionInfo.DataBean data = showPersionInfo.getData();
                    String autograph = data.getAutograph();
                    mUserInfoId = data.getId();
                    if (TextUtils.isEmpty(autograph)) {
                        mIvShowSign.setVisibility(View.INVISIBLE);
                        mLpSingn.setVisibility(View.VISIBLE);
                    } else {
                        mLpSingn.setVisibility(View.INVISIBLE);
                        mIvShowSign.setVisibility(View.VISIBLE);
                        mBtSignSubmit.setVisibility(View.INVISIBLE);
                        Glide.with(getContext()).load(autograph).placeholder(R.drawable.place).into(mIvShowSign);
                    }
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    private void initTopbar() {
        mTopBarView = getActivity().findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(getContext(), mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void rightClick() {
                int btVisibility = mBtSignSubmit.getVisibility();
                if (btVisibility == View.INVISIBLE) {
                    mBtSignSubmit.setVisibility(View.VISIBLE);
                }
                int visibility = mIvShowSign.getVisibility();
                if (visibility == View.VISIBLE) {
                    mIvShowSign.setVisibility(View.INVISIBLE);
                    mLpSingn.setVisibility(View.VISIBLE);
                }
                mLpSingn.clear();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sign_submit:
                if (mLpSingn.getTouched()) {
                    try {
                        if (Util.hasSdcard()) {
                            mLpSingn.save("/sdcard/sign.png", true, 10);
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sign.png";
                            //上传图片到阿里云；
                            uploadingPic2Oss(path);
                        } else {
                            Util.toast(getContext(), "sd卡不存在");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Util.toast(getContext(), "您还没签名，请签名");
                }
                break;
            default:
                break;
        }
    }

    private void uploadingPic2Oss(String pathUrl) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //显示个等待页面
                showLoading();
            }
        });


        PutObjectRequest put = new PutObjectRequest(InitApplication.OSS_BUCKET, "persistence/allImg/" + getImageObjectKey("123456789"), pathUrl);

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
                final String keyUrl = Constant.OSSPATH + request.getObjectKey();
                //调用后台修改接口；
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestNetSavaPic(keyUrl);
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, final ClientException clientExcepion, ServiceException serviceException) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //如果上传失败了，通过mHandler ，发出失败的消息到主线程中。处理异常。
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

    private void requestNetSavaPic(String picUrl) {
        showLoading();
        OkHttpUtils.post(HttpConstant.UPDATE_SIGN + "id=" + mUserInfoId + "&autograph=" + picUrl).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    if (isFirst) {
                        Util.startActivity(getContext(), MainActivity.class);
                        getActivity().finish();
                    } else {
                        getUserInfoId();
                    }
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

}
