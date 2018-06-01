package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.OtherCostApplyDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/3/6.
 */
public class OtherCostApplySubmitFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.bt_other_cost_submit)
    Button mBtOtherCostSubmit;
    Unbinder unbinder;
    @BindView(R.id.et_purport)
    EditText mEtPurport;
    @BindView(R.id.et_explain)
    EditText mEtExplain;
    @BindView(R.id.et_cost)
    EditText mEtCost;
    private String mJoinWay;
    private String mUserId;
    private Bundle mArguments;
    private String mOrderId;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_other_cost_submit;
    }

    public static Fragment newInstance(Bundle bundle) {
        OtherCostApplySubmitFragment siteFragment = new OtherCostApplySubmitFragment();
        siteFragment.setArguments(bundle);
        return siteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mArguments = getArguments();
        if (mArguments != null) {
            mJoinWay = mArguments.getString(Constant.APPLY_ACTIVITY_EDIT_JOIN_WAY);
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initData();
        return rootView;
    }

    private void initData() {
        if (mJoinWay.equals(Constant.MODIFIER)) {
            requestNetShowView();
        }
        mBtOtherCostSubmit.setOnClickListener(this);
    }

    private void requestNetShowView() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                OtherCostApplyDetailBean costApplyDetailBean = gson.fromJson(s, OtherCostApplyDetailBean.class);
                if (costApplyDetailBean.isSuccess()) {
                    OtherCostApplyDetailBean.DataBean data = costApplyDetailBean.getData();
                    showData2View(data);
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
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

    /**
     * 显示数据到控件上面；
     *
     * @param data
     */
    private void showData2View(OtherCostApplyDetailBean.DataBean data) {
        mEtPurport.setText(data.getTitle());
        mEtExplain.setText(data.getExplains());
        mEtCost.setText(data.getCosts());

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
            case R.id.bt_other_cost_submit:
                String etPurPort = mEtPurport.getText().toString().trim();
                String etExplain = mEtExplain.getText().toString().trim();
                String etCost = mEtCost.getText().toString().trim();

                boolean emptyPurPort = etPurPort.isEmpty();
                boolean emptyExplain = etExplain.isEmpty();
                boolean emptyEtCost = etCost.isEmpty();
                if (emptyPurPort || emptyExplain || emptyEtCost) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }
                if (mJoinWay.equals("其他费用申请")) {
                    submitData2Net(etPurPort, etExplain, etCost);
                } else if (mJoinWay.equals(Constant.MODIFIER)) {
                    upDateData2Net(etPurPort, etExplain, etCost);
                }
                break;
        }
    }

    /**
     * 修改其他费用；
     *
     * @param etPurPort
     * @param etExplain
     * @param etCost
     */
    private void upDateData2Net(String etPurPort, String etExplain, String etCost) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.OTHER_COST_APPLY_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&title=" + etPurPort + "&explains=" +
                etExplain + "&costs=" + etCost + "&approvalStatus=" + "1" + "&approvalType=" + "0");
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "提交成功");
                    getActivity().finish();
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
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

    /**
     * 提交数据到后台
     *
     * @param etPurPort
     * @param etExplain
     * @param etCost
     */
    private void submitData2Net(String etPurPort, String etExplain, String etCost) {
        showLoading();
        String replaceStr = HttpConstant.OTHER_COST_APPLY_SUBMIT + "user.id=" + mUserId + "&title=" + etPurPort + "&explains=" +
                etExplain + "&costs=" + etCost + "&approvalStatus=" + "1" + "&approvalType=" + "0";
        String noPrecentUrl = Util.removePrecent(replaceStr);
        OkHttpUtils.post(noPrecentUrl).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "提交成功");
                    getActivity().finish();
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
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
