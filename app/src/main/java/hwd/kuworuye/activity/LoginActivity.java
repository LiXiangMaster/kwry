package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.UserLoginBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.StatusBarUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.iv_login_input_phonenum)
    ImageView mIvLoginInputPhonenum;
    @BindView(R.id.iv_login_input_phonepwd)
    ImageView mIvLoginInputPhonepwd;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.cb_login_show)
    CheckBox mCbLoginShow;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.et_input_phone_num)
    EditText mEtInputPhoneNum;
    @BindView(R.id.cb_deal_agree)
    CheckBox mCbDealAgree;
    @BindView(R.id.tv_deal)
    TextView mTvDeal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_bg));
        initData();
    }

    private void initData() {
        mCbLoginShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //设置为可见的密码
                    mEtLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //将鼠标放在最后
                    mEtLoginPwd.setSelection(mEtLoginPwd.getText().toString().length());
                } else {
                    //默认状态显示密码
                    mEtLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //将鼠标放在最后
                    mEtLoginPwd.setSelection(mEtLoginPwd.getText().toString().length());
                }
            }
        });

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = mEtInputPhoneNum.getText().toString().trim();
                String phonePwd = mEtLoginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(phonePwd)) {
                    Util.toast(LoginActivity.this, "请输入账号或密码");
                    return;
                }
                if (!Util.checkPhone(phoneNum)) {
                    Util.toast(LoginActivity.this, "您输入的手机号格式错误");
                    return;
                }
                if (!mCbDealAgree.isChecked()) {
                    Util.toast(LoginActivity.this, "请点击同意服务条款");
                    return;
                }
                requestNetForLoginData(phoneNum, phonePwd);

            }
        });


        mTvDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.toast(LoginActivity.this,"请稍后...");
                Util.startActivity(LoginActivity.this,DealActivity.class);
            }
        });
    }

    private void requestNetForLoginData(String phoneNum, String phonePwd) {
        Util.showProgressDialog(this, "请稍后...", 3);
        OkHttpUtils.post(HttpConstant.LOGIN_USER + "loginName=" + phoneNum + "&password=" + phonePwd).tag(this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                Util.dismissProgressDialog();
                Gson gson = new Gson();
                UserLoginBean userLoginBean = gson.fromJson(s, UserLoginBean.class);
                boolean isSuccess = userLoginBean.isSuccess();
                if (isSuccess) {
                    UserLoginBean.DataBean data = userLoginBean.getData();
                    String id = data.getId();
                    String userName = data.getUserName();
                    String areaName = data.getAreaName();
                    SharedPreferencesUtil.getInstance(LoginActivity.this).save(Constant.USERID, id);
                    String isCertification = data.getIsCertification();
                    if (TextUtils.isEmpty(isCertification)) {
                        Util.toast(LoginActivity.this, "认证异常");
                        return;
                    }
                    String isInit = data.getIsInit();
                    SharedPreferencesUtil.getInstance(LoginActivity.this).save(Constant.ISINIT, isInit);
                    String userType = data.getUserType();
                    SharedPreferencesUtil.getInstance(LoginActivity.this).save(Constant.USERTYPE, userType);
                    SharedPreferencesUtil.getInstance(LoginActivity.this).save(Constant.USERNAME, userName);
                    SharedPreferencesUtil.getInstance(LoginActivity.this).save(Constant.SALE_AREA, areaName);
                    if ("0".equals(isCertification)) {
                        boolean signCode = data.isSignCode();
                        Intent intent = new Intent(LoginActivity.this, MyDatasActivity.class);
                        intent.putExtra(Constant.FIRSTCERTIFICATION, "firstcertification");
                        startActivity(intent);
                    } else if ("1".equals(isCertification)) {
                        if (data.isSignCode()) {
                            SharedPreferencesUtil.getInstance(LoginActivity.this).save("loginsuccessed", "loginsuccessed");
                            Util.startActivity(LoginActivity.this, MainActivity.class);
                        } else {
                            Util.openContentActivity(LoginActivity.this, ContentActivity.class, Constant.MY_SIGN);
                        }
                    }
                    finish();

                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    String msg = failMsgBean.getMsg();
                    Util.toast(LoginActivity.this, msg);
                }

            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.dismissProgressDialog();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
