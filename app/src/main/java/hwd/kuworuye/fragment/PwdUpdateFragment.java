package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.SuccessBean;
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
public class PwdUpdateFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.et_old_pwd)
    EditText mEtOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText mEtNewPwd;
    @BindView(R.id.et_confrim)
    EditText mEtConfirm;
    @BindView(R.id.bt_login_commit)
    Button mBtLoginCommit;
    Unbinder unbinder;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_pwd_modifier;
    }

    public static Fragment newInstance(Bundle bundle) {
        PwdUpdateFragment pwdModifierFragment = new PwdUpdateFragment();
        pwdModifierFragment.setArguments(bundle);
        return pwdModifierFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        mGson = new Gson();
        return rootView;
    }

    private void initData() {
        mBtLoginCommit.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login_commit:
                submitUpdatePwd();
                break;
        }
    }

    private void submitUpdatePwd() {
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString();
        String confrimPwd = mEtConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confrimPwd)) {
            Util.toast(getContext(), "输入的不能为空！");
            return;
        }
        int length = newPwd.length();
        if (length < 6) {
            Util.toast(getContext(), "密码不能少于6位数");
            return;
        }
        if (!newPwd.equals(confrimPwd)) {
            Util.toast(getContext(), "确认密码与新密码不一致");
            return;
        }
        submitUpdatePwd2Net(oldPwd, newPwd);
    }

    private void submitUpdatePwd2Net(String oldPwd, String newPwd) {
        String userId = (String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERID);
        String userName = (String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERNAME);

        OkHttpUtils.post(HttpConstant.UPDATE_PWD + "id=" + userId + "&password=" + oldPwd + "&newPassword=" + newPwd + "&loginName=" + userName).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    Util.toast(getContext(), "密码修改成功");
                    getActivity().finish();
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(getActivity());
    }
}
