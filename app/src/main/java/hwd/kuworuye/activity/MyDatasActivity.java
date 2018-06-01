package hwd.kuworuye.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.base.InitApplication;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ShowPersionInfo;
import hwd.kuworuye.bean.UpdateMyInfoBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class MyDatasActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_transport_car)
    EditText mEtTransportCar;
    @BindView(R.id.et_team_persons)
    EditText mEtTeamPersons;
    @BindView(R.id.et_sale_area)
    EditText mEtSaleArea;
    @BindView(R.id.rl_sale_area)
    LinearLayout mRlSaleArea;
    @BindView(R.id.et_sale_channel)
    EditText mEtSaleChannel;
    @BindView(R.id.et_brand)
    EditText mEtBrand;
    @BindView(R.id.et_suffer)
    EditText mEtSuffer;
    @BindView(R.id.et_registered_capital)
    EditText mEtRegisteredCapital;
    @BindView(R.id.et_bank_name)
    EditText mEtBankName;
    @BindView(R.id.et_bank_num)
    EditText mEtBankNum;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.rl_take_yinye_photo)
    RelativeLayout mRlTakeYinyePhoto;
    @BindView(R.id.rl_shiping_card)
    RelativeLayout mRlShipingCard;
    @BindView(R.id.iv_temp_yinye)
    ImageView mIvTempYinye;
    @BindView(R.id.iv_temp_shipin)
    ImageView mIvTempShipin;
    @BindView(R.id.bt_submit_mydatas)
    Button mBtSubmitMydatas;
    @BindView(R.id.et_house_areas)
    EditText mEtHouseAreas;
    Unbinder unbinder;
    @BindView(R.id.tv_store_area)
    TextView mTvStoreArea;
    @BindView(R.id.comm_three_back)
    ImageView mCommThreeBack;
    @BindView(R.id.tv_comm_title_bar_three)
    TextView mTvCommTitleBarThree;
    @BindView(R.id.comm_right_three)
    ImageView mCommRightThree;
    private Dialog dialog;
    private static String path = "/sdcard/mydatas/";// sd路径
    private Button btn_picture, btn_photo, btn_cancle;
    protected Handler mHandler = new Handler();
    private String mUserId;
    private String mUserInfoId;
    private String mBusinessLicenseUrl;
    private String mFoodCertificateUrl;
    private Gson mGson;
    private boolean mSingle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_datas);
        ButterKnife.bind(this);
        mGson = new Gson();
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra(Constant.FIRSTCERTIFICATION);
            if ("firstcertification".equals(stringExtra)) {
                mSingle = true;
            }
        }
        initData();
    }


    private void initData() {
        mCommRightThree.setVisibility(View.INVISIBLE);
        mTvCommTitleBarThree.setText(getResources().getString(R.string.my_datas));
        mUserId = (String) SharedPreferencesUtil.getInstance(this).read(Constant.USERID);
        //两种途径 进来 都是先调用 获取控件上的数据接口；
        requestNet2UserInfoId();
        mCommThreeBack.setOnClickListener(this);
        mRlTakeYinyePhoto.setOnClickListener(this);
        mRlShipingCard.setOnClickListener(this);
        mBtSubmitMydatas.setOnClickListener(this);
        mIvTempYinye.setOnClickListener(this);
        mIvTempShipin.setOnClickListener(this);

    }

    private void initView(ShowPersionInfo.DataBean data) {
        if (data != null) {
            mEtTransportCar.setText(data.getCar());
            mEtTeamPersons.setText(data.getTeams());
            mEtSaleArea.setText(data.getSalesArea());
            mEtSaleChannel.setText(data.getSalesChannel());
            mEtSuffer.setText(data.getExperience());
            mEtRegisteredCapital.setText(data.getRegisteredCapital());
            mEtBankName.setText(data.getBankName());
            mEtBankNum.setText(data.getBankCard());
            mEtUserName.setText(data.getRealName());
            mEtHouseAreas.setText(data.getAcreage());
            mEtBrand.setText(data.getBrand());

            mBusinessLicenseUrl = data.getBusinessLicense();
            mFoodCertificateUrl = data.getFoodCertificate();
            if (!TextUtils.isEmpty(mBusinessLicenseUrl)) {
                mIvTempYinye.setVisibility(View.VISIBLE);
                Glide.with(this).load(mBusinessLicenseUrl).placeholder(R.drawable.place).into(mIvTempYinye);
            } else {
                mIvTempYinye.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mFoodCertificateUrl)) {
                mIvTempShipin.setVisibility(View.VISIBLE);
                Glide.with(this).load(mFoodCertificateUrl).placeholder(R.drawable.place).into(mIvTempShipin);
            } else {
                mIvTempShipin.setVisibility(View.GONE);
            }
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MyDatasActivity.this, PhotoViewPagerActivity.class);
        switch (view.getId()) {
            case R.id.rl_take_yinye_photo:
                showBuisnessDialog(Constant.YINGYE);
                break;
            case R.id.rl_shiping_card:
                showBuisnessDialog(Constant.SHIPIN);
                break;
            case R.id.bt_submit_mydatas:
                //提交数据到后台
                submitData();
                break;
            case R.id.comm_three_back:
                finish();
                break;
            case R.id.iv_temp_yinye:
                intent.putExtra(Constant.COMM_SOLA_PIC_URL, mBusinessLicenseUrl);
                startActivity(intent);
                break;
            case R.id.iv_temp_shipin:
                intent.putExtra(Constant.COMM_SOLA_PIC_URL, mFoodCertificateUrl);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void submitData() {
        String houseArea = mEtHouseAreas.getText().toString().trim();
        String transportNum = mEtTransportCar.getText().toString().trim();
        String teamNum = mEtTeamPersons.getText().toString().trim();
        String saleArea = mEtSaleArea.getText().toString().trim();
        String saleChannel = mEtSaleChannel.getText().toString().trim();
        String brand = mEtBrand.getText().toString().trim();
        String suffer = mEtSuffer.getText().toString().trim();
        String registeredCapital = mEtRegisteredCapital.getText().toString().trim();
        String bankName = mEtBankName.getText().toString().trim();
        String bankNum = mEtBankNum.getText().toString().trim();
        String userName = mEtUserName.getText().toString().trim();

        boolean emptyHouseArea = TextUtils.isEmpty(houseArea);
        boolean emptyTransportNum = TextUtils.isEmpty(transportNum);
        boolean emptyTeamNum = TextUtils.isEmpty(teamNum);
        boolean emptySaleArea = TextUtils.isEmpty(saleArea);
        boolean emptySaleChannel = TextUtils.isEmpty(saleChannel);
        boolean emptyBrand = TextUtils.isEmpty(brand);
        boolean emptySuffer = TextUtils.isEmpty(suffer);
        boolean emptyRegistered = TextUtils.isEmpty(registeredCapital);
        boolean emptyBankName = TextUtils.isEmpty(bankName);
        boolean emptyBankNum = TextUtils.isEmpty(bankNum);
        boolean emptyUserName = TextUtils.isEmpty(userName);
        if (emptyHouseArea || emptyTransportNum || emptyTeamNum || emptySaleArea || emptySaleChannel || emptyBrand || emptySuffer || emptyRegistered || emptyBankName || emptyBankNum || emptyUserName) {
            Util.toast(this, "资料填写不完整");
            return;
        }
        if (TextUtils.isEmpty(mBusinessLicenseUrl) && TextUtils.isEmpty(mFoodCertificateUrl)) {
            Util.toast(this, "请选择上传营业执照和合格证");
            return;
        }
        //调用 后台 修改接口；
        submitMyInfo2Net(houseArea, transportNum, teamNum, saleArea, saleChannel, brand, suffer, registeredCapital, bankName, bankNum, userName);
    }

    private void requestNet2UserInfoId() {
        showLoading();
        OkHttpUtils.post(HttpConstant.SHOW_PERSION_DATAS + "userId=" + mUserId).tag(MyDatasActivity.this).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                ShowPersionInfo showPersionInfo = mGson.fromJson(s, ShowPersionInfo.class);
                boolean success = showPersionInfo.isSuccess();
                if (success) {
                    ShowPersionInfo.DataBean data = showPersionInfo.getData();
                    mUserInfoId = data.getId();
                    SharedPreferencesUtil.getInstance(MyDatasActivity.this).save(Constant.USERINFO_ID, mUserId);
                    //展示数据到控件上
                    initView(data);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(MyDatasActivity.this, failMsgBean.getMsg());
                }

            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    private void submitMyInfo2Net(String houseArea, String transportNum, String teamNum, String saleArea, String saleChannel, String brand, String suffer, String registeredCapital, String bankName, String bankNum, final String userName) {

        showLoading();
        OkHttpUtils.post(HttpConstant.UPDATE_MY_DATA + "id=" + mUserInfoId + "&realName=" + userName + "&acreage="
                + houseArea + "&car=" + transportNum + "&teams=" + teamNum + "&salesArea=" + saleArea + "&salesChannel=" + saleChannel + "&brand=" + brand + "&experience=" + suffer
                + "&registeredCapital=" + registeredCapital + "&bankName=" + bankName + "&bankCard=" + bankNum + "&businessLicense=" + mBusinessLicenseUrl + "&foodCertificate="
                + mFoodCertificateUrl).tag(MyDatasActivity.this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                UpdateMyInfoBean updateMyInfoBean = mGson.fromJson(s, UpdateMyInfoBean.class);
                if (updateMyInfoBean.isSuccess()) {
                    Util.toast(MyDatasActivity.this, "资料提交成功");
                    SharedPreferencesUtil.getInstance(MyDatasActivity.this).save("loginsuccessed", "loginsuccessed");

                    if (mSingle) {
                        Intent intent = new Intent(MyDatasActivity.this, ContentActivity.class);
                        intent.putExtra(Constant.CONTENT_TYPE, Constant.MY_SIGN);
                        intent.putExtra(Constant.FIRSTCERTIFICATION, mSingle);
                        startActivity(intent);
                    } else {
                        Util.toast(MyDatasActivity.this, "资料更新成功");
                    }
                    SharedPreferencesUtil.getInstance(MyDatasActivity.this).save(Constant.USERNAME, userName);
                    finish();
                } else {
                    Util.toast(MyDatasActivity.this, "资料更新失败");
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();

            }
        });
    }


    private void showBuisnessDialog(final String str) {
        View view = LayoutInflater.from(MyDatasActivity.this).inflate(R.layout.photo_choose_dialog, null);
        dialog = new Dialog(MyDatasActivity.this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = MyDatasActivity.this.getWindowManager().getDefaultDisplay().getHeight();
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
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                if (Constant.YINGYE.equals(str)) {
                    startActivityForResult(intent, 0);
                } else if (Constant.SHIPIN.equals(str)) {
                    startActivityForResult(intent, 10);
                }
                dialog.dismiss();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                if (Constant.YINGYE.equals(str)) {
                    startActivityForResult(intent, 1);
                } else if (Constant.SHIPIN.equals(str)) {
                    startActivityForResult(intent, 11);
                }
                dialog.dismiss();
            }
        });


        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 获取选择的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;//当data为空的时候，不做任何处理
        }
        Bitmap bitmap = null;
        switch (requestCode) {
            case 0:
                //营业 相册
                //获取从相册界面返回的缩略图
                bitmap = data.getParcelableExtra("data");
                if (bitmap == null) {//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                    try {
                        //通过URI得到输入流
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        //通过输入流得到bitmap对象
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        setPicToView(bitmap, Constant.YINGYE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                //营业拍照
                bitmap = (Bitmap) data.getExtras().get("data");
                setPicToView(bitmap, Constant.YINGYE);
                break;
            case 10:
                bitmap = data.getParcelableExtra("data");
                if (bitmap == null) {//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                    try {
                        //通过URI得到输入流
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        //通过输入流得到bitmap对象
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        setPicToView(bitmap, Constant.SHIPIN);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 11:
                bitmap = (Bitmap) data.getExtras().get("data");
                setPicToView(bitmap, Constant.SHIPIN);
                break;
            default:
                break;
        }

    }


    private void setPicToView(Bitmap mBitmap, String string) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Util.toast(MyDatasActivity.this, "sd卡不存在");
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹

        try {

            if (string.equals(Constant.YINGYE)) {
                mIvTempYinye.setImageBitmap(mBitmap);
                final String yingyeUrl = path + "yingye.jpg";// 图片名字
                b = new FileOutputStream(yingyeUrl);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                urlUpload2Oss(yingyeUrl, Constant.YINGYE);
            } else if (string.equals(Constant.SHIPIN)) {
                mIvTempShipin.setImageBitmap(mBitmap);
                String shipingUrl = path + "shiping.jpg";
                b = new FileOutputStream(shipingUrl);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                urlUpload2Oss(shipingUrl, Constant.SHIPIN);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    // 关闭流
                    b.flush();
                    b.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void urlUpload2Oss(final String pathUrl, final String isWhichPic) {
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
                String keyUrl = request.getObjectKey();
                if (Constant.YINGYE.equals(isWhichPic)) {
                    mBusinessLicenseUrl = Constant.OSSPATH + keyUrl;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIvTempYinye.setVisibility(View.VISIBLE);
                        }
                    });
                    Glide.with(MyDatasActivity.this).load(mBusinessLicenseUrl).placeholder(R.drawable.place).into(mIvTempYinye);
                } else if (Constant.SHIPIN.equals(isWhichPic)) {
                    mFoodCertificateUrl = Constant.OSSPATH + keyUrl;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIvTempShipin.setVisibility(View.VISIBLE);
                        }
                    });
                    Glide.with(MyDatasActivity.this).load(mFoodCertificateUrl).placeholder(R.drawable.place).into(mIvTempShipin);
                }
                Log.i("test", "请求id： " + keyUrl);
                //将上传成功的图片地址传给自己的服务器后台，比如修改用户数据库中，用户头像的url。
                //修改后台url成功后，再利用glide 下载最新的照片，修改本地头像图片。
                //request.getObjectKey() 是图片地址，但是不包含，OSS 图片域名
//	            uploadImage(request.getObjectKey());
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
}
