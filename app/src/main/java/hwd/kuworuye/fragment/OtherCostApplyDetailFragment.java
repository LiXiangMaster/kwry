package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.OtherCostAyDetailStateAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.OtherCostApplyDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;
import hwd.kuworuye.weight.MyListView;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class OtherCostApplyDetailFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.bt_pass)
    Button mBtPass;
    Unbinder unbinder;
    @BindView(R.id.tv_other_purport)
    TextView mTvOtherPurport;
    @BindView(R.id.tv_other_explain)
    TextView mTvOtherExplain;
    @BindView(R.id.tv_other_cost)
    TextView mTvOtherCost;
    @BindView(R.id.bt_other_cost_delete)
    Button mBtOtherCostDelete;
    @BindView(R.id.ll_refuse_pass_other)
    LinearLayout mLlRefusePassOther;
    @BindView(R.id.myl_approve_info)
    MyListView mMylApproveInfo;

    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.bt_refuse)
    Button mBtRefuse;

    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;

    private Topbar mTopbar;
    private View mTopBarView;
    private String mOrderId;
    private Bundle mArguments;
    private String mUserId;
    private String mApproverUser;
    private String mApprovalStatus;
    private String mApprovalType;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_other_cost_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        OtherCostApplyDetailFragment fragment = new OtherCostApplyDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        mArguments = getArguments();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initTopbar();
        requestNet2Data();
        return rootView;
    }

    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                OtherCostApplyDetailBean costApplyDetailBean = gson.fromJson(s, OtherCostApplyDetailBean.class);
                if (costApplyDetailBean.isSuccess()) {
                    OtherCostApplyDetailBean.DataBean data = costApplyDetailBean.getData();
                    initData(data);
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

    private void initData(OtherCostApplyDetailBean.DataBean data) {
        mApprovalStatus = data.getApprovalStatus();
        String approveState = Util.getApproveState(mApprovalStatus, data.getApprovalType());
        mApproverUser = data.getApproverUser();
        mApprovalType = data.getApprovalType();
        boolean isRefuse = "1".equals(mApprovalType);
        String userId = data.getUserId();
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        if ((mUserId.equals(userId)) && (isShowMdBt || isRefuse)) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }
        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(data.getRemarks());
        }
        if ("待审批".equals(approveState)){
            mLlApproveInfo.setVisibility(View.GONE);
        }

        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUserId.equals(mApproverUser))) {
            mLlRefusePassOther.setVisibility(View.VISIBLE);
        }

        if ((mUserId.equals(userId)) && ("待审批".equals(approveState) || "待提交".equals(approveState) || isRefuse)) {
            mBtOtherCostDelete.setVisibility(View.VISIBLE);
        }

        mTvOtherPurport.setText(data.getTitle());
        mTvOtherExplain.setText(data.getExplains());
        mTvOtherCost.setText(data.getCosts()+"元");


        List<OtherCostApplyDetailBean.DataBean.ApprovalListBean> approvalList = data.getApprovalList();
        OtherCostAyDetailStateAdapter stateAdapter = new OtherCostAyDetailStateAdapter(getContext(), approvalList);
        mMylApproveInfo.setAdapter(stateAdapter);
        mMylApproveInfo.setEmptyView(mLlEmpty);

        mBtOtherCostDelete.setOnClickListener(this);
        mBtPass.setOnClickListener(this);
        mBtRefuse.setOnClickListener(this);

    }


    private void initTopbar() {
        mTopBarView = getActivity().findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(getActivity(), mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void rightClick() {
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.OTHER_COST_SUBMIT);
                intent.putExtra(Constant.APPLY_ACTIVITY_EDIT_JOIN_WAY, Constant.MODIFIER);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
                getActivity().finish();
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
            case R.id.bt_other_cost_delete:
                popupLoginOutTip("删除");
                break;
            case R.id.bt_pass:
                popupConfirmPassTip();
                break;
            case R.id.bt_refuse:
                popupRefusePassTip();
                break;
        }
    }

    private void popupRefusePassTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.refuse_pass_tip, null);
        final EditText etRefuseReason = (EditText) view.findViewById(R.id.et_refuse_reason);


        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_refuse_pass_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_refuse_pass_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refuseReason = etRefuseReason.getText().toString().trim();
                if (TextUtils.isEmpty(refuseReason)) {
                    Util.toast(getContext(), "请填写拒绝原因");
                    return;
                }
                refuseApprove(refuseReason, "1");
                dialog.dismiss();
            }
        });
    }

    private void refuseApprove(String refuseReason, String s) {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
                "&approvalStatus=" + mApprovalStatus + "&approvalType=" + s + "&remarks=" + refuseReason).tag(getContext()).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "已拒绝");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    getActivity().finish();
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                dismissLoading();
            }
        });
    }


    private void popupConfirmPassTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pass_tip, null);
        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_pass_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_pass_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passApprove();
                dialog.dismiss();
            }
        });
    }

    private void passApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
                "&approvalStatus=" + mApprovalStatus + "&approvalType=" + mApprovalType).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "已通过");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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
     * 删除提示
     *
     * @param str
     */
    private void popupLoginOutTip(final String str) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_tip, null);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText(str + "？");
        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_delete_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_delete_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str.equals("删除")) {
                    deleteOtherDetail();
                }
                dialog.dismiss();
            }
        });
    }

    private void deleteOtherDetail() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_DELETE + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "删除成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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
