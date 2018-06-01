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
import hwd.kuworuye.adapter.ApplyProductListAdapter;
import hwd.kuworuye.adapter.CommApproveInfoAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.SaleApplyDetailBean;
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
 * Created by Administrator on 2017/3/7.
 */
public class SaleAcAyDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.bt_pass)
    Button mBtPass;
    Unbinder unbinder;
    @BindView(R.id.bt_refuse)
    Button mBtRefuse;
    @BindView(R.id.tv_activity_name)
    TextView mTvActivityName;
    @BindView(R.id.tv_activity_goal)
    TextView mTvActivityGoal;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_execute_time)
    TextView mTvExecuteTime;
    @BindView(R.id.tv_content_shape)
    TextView mTvContentShape;
    @BindView(R.id.tv_cost_and_material)
    TextView mTvCostAndMaterial;
    @BindView(R.id.tv_bilv)
    TextView mTvBilv;
    @BindView(R.id.tv_sale_cost)
    TextView mTvSaleCost;
    @BindView(R.id.bt_delete)
    Button mBtDelete;
    @BindView(R.id.ll_refuse_pass_apply_detail)
    LinearLayout mLlRefusePassApplyDetail;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.mlv_approve_info)
    MyListView mMlvApproveInfo;
    @BindView(R.id.tv_range)
    TextView mTvRange;
    @BindView(R.id.myl_product_list)
    MyListView mMylProductList;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;

    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;

    @BindView(R.id.tv_ac_sale_cost)
    TextView mTvAcSaleCost;
    @BindView(R.id.tv_ac_bilv)
    TextView mTvAcBilv;

    private String mOrderId;
    private Topbar mTopbar;
    private View mTopBarView;
    private String mApprovalStatus;
    private String mUserId;
    private Bundle mArguments;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_apply_for_activity_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        SaleAcAyDetailFragment fragment = new SaleAcAyDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mArguments = getArguments();
        mGson = new Gson();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        requestNet2Detail();
        initView();
        initTopbar();
        return rootView;
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
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.SALE_ACTIVITY_APPLY_UPDATE);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


    /**
     * 促销活动订单详情；
     */
    private void requestNet2Detail() {
        showLoading();
        OkHttpUtils.get(HttpConstant.SALE_APPLY_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                SaleApplyDetailBean saleApplyDetailBean = mGson.fromJson(s, SaleApplyDetailBean.class);
                boolean activityApplySuccess = saleApplyDetailBean.isSuccess();
                if (activityApplySuccess) {
                    initActivityDetailData(saleApplyDetailBean);
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


    /**
     * 促销活动详情
     *
     * @param saleApplyDetailBean
     */
    private void initActivityDetailData(SaleApplyDetailBean saleApplyDetailBean) {
        SaleApplyDetailBean.KwPromotionApplicationBean kwPromotionApplication = saleApplyDetailBean.getKwPromotionApplication();

        String approverUser = kwPromotionApplication.getApproverUser();
        mApprovalStatus = saleApplyDetailBean.getKwPromotionApplication().getApprovalStatus();
        String approvalType = saleApplyDetailBean.getKwPromotionApplication().getApprovalType();
        String approveState = Util.getApproveState(mApprovalStatus, approvalType);
        String userId = kwPromotionApplication.getUser().getId();

        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        //如果是已拒绝也要有一修改按钮
        boolean isRefuse = "1".equals(approvalType);
        if ((mUserId.equals(userId)) && (isShowMdBt || isRefuse)) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }

        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(kwPromotionApplication.getApprovalMemo());
        }

        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }

        if ((mUserId.equals(userId)) && ("待提交".equals(approveState) || "待审批".equals(approveState) || isRefuse)) {
            mBtDelete.setVisibility(View.VISIBLE);
        }
        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUserId.equals(approverUser))) {
            mLlRefusePassApplyDetail.setVisibility(View.VISIBLE);
            mBtDelete.setVisibility(View.GONE);
        }
        List<SaleApplyDetailBean.RecordListBean> recordList = saleApplyDetailBean.getRecordList();
        mMlvApproveInfo.setEmptyView(mLlEmpty);
        CommApproveInfoAdapter commApproveInfoAdapter = new CommApproveInfoAdapter(getActivity(), recordList);
        mMlvApproveInfo.setAdapter(commApproveInfoAdapter);
        mTvActivityName.setText(kwPromotionApplication.getName());

        List<String> objectiveList = kwPromotionApplication.getObjectiveList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objectiveList.size(); i++) {
            String activityName = Util.getActivityName(objectiveList.get(i));
            if (i == objectiveList.size() - 1) {
                stringBuffer.append(activityName);
            } else {
                stringBuffer.append(activityName).append(",");
            }
        }

        String activitySales = kwPromotionApplication.getActivitySales();
        mTvAcSaleCost.setText(activitySales + "元");
        String activityRatio = kwPromotionApplication.getActivityRatio();
        mTvAcBilv.setText(activityRatio + "%");

        mTvActivityGoal.setText(stringBuffer.toString());
        mTvCity.setText(kwPromotionApplication.getCity());
        mTvExecuteTime.setText(kwPromotionApplication.getExecutionTime());
        String content = kwPromotionApplication.getContent();
        mTvContentShape.setText(kwPromotionApplication.getContent());

        mTvCostAndMaterial.setText(kwPromotionApplication.getMateriel() + "");
        mTvBilv.setText(kwPromotionApplication.getRatio() + "%");
        mTvSaleCost.setText(kwPromotionApplication.getSalesVolume() + "元");
        mTvRange.setText(kwPromotionApplication.getRanges());
        List<SaleApplyDetailBean.ItemListBean> itemList = saleApplyDetailBean.getItemList();
        ApplyProductListAdapter applyProductListAdapter = new ApplyProductListAdapter(getActivity(), itemList);
        mMylProductList.setAdapter(applyProductListAdapter);

    }

    private void initView() {
        mBtPass.setOnClickListener(this);
        mBtRefuse.setOnClickListener(this);
        mBtDelete.setOnClickListener(this);
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
            case R.id.bt_pass:
                popupConfirmPassTip();
                break;
            case R.id.bt_refuse:
                popupRefusePassTip();
                break;
            case R.id.bt_delete:
                popupDeleteTip("删除");
                break;

        }
    }

    private void popupDeleteTip(final String str) {
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
                    deleteActivityApply();
                }
                dialog.dismiss();
            }
        });
    }


    /**
     * 删除促销活动申请
     */
    private void deleteActivityApply() {
        showLoading();
        OkHttpUtils.get(HttpConstant.DELETE_ACTIVITY_APPLY + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    EventBus.getDefault().post(new CommRefreshListEvent(success));
                    Util.toast(getContext(), "删除成功");
                    getActivity().finish();
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
                applyRefuse(refuseReason, "1");
                dialog.dismiss();
            }
        });
    }

    /**
     * 促销活动申请审批 拒绝接口；
     *
     * @param refuseReason
     */
    private void applyRefuse(String refuseReason, String refuseStr) {
        showLoading();
        OkHttpUtils.post(HttpConstant.APPLY_APPROVE + "id=" + mOrderId + "&approvalStatus=" + mApprovalStatus
                + "&user.id=" + mUserId + "&approvalType=" + refuseStr + "&approvalMemo=" + refuseReason).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    EventBus.getDefault().post(new CommRefreshListEvent(success));
                    Util.toast(getContext(), "操作成功");
//                    TipUtil.operationSuccessTip(getContext(), false);
                    getActivity().finish();
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

    /**
     * 促销活动申请审批 通过接口；
     */
    private void applyApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.APPLY_APPROVE + "id=" + mOrderId + "&approvalStatus=" + mApprovalStatus
                + "&user.id=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    EventBus.getDefault().post(new CommRefreshListEvent(success));
                    Util.toast(getContext(), "操作成功");
//                    TipUtil.operationSuccessTip(getContext(), false);
                    getActivity().finish();
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
                applyApprove();
                dialog.dismiss();
            }
        });
    }
}
