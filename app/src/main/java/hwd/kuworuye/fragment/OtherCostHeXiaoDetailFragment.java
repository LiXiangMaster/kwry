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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.CommBackBigAdapter;
import hwd.kuworuye.adapter.OtherCostHxDetailStateAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.BackPicBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.OtherCostHeXiaoDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;
import hwd.kuworuye.weight.MyGridView;
import hwd.kuworuye.weight.MyListView;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/6.
 */
public class OtherCostHeXiaoDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_purport)
    TextView mTvPurport;
    @BindView(R.id.tv_explain)
    TextView mTvExplain;
    @BindView(R.id.tv_cost)
    TextView mTvCost;
    @BindView(R.id.bt_refuse)
    Button mBtRefuse;
    @BindView(R.id.bt_pass)
    Button mBtPass;
    @BindView(R.id.ll_refuse_pass)
    LinearLayout mLlRefusePass;
    Unbinder unbinder;
    @BindView(R.id.myl_apply)
    MyListView mMylApply;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;
    @BindView(R.id.rl_join_site_look)
    RelativeLayout mRlJoinSiteLook;

    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;

    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.ll_other_ticket_look)
    LinearLayout mLlOtherTicketLook;
    @BindView(R.id.bt_sale_hexiao_submit)
    Button mBtSaleHexiaoSubmit;

    private Bundle mArguments;
    private String mUserId;
    private String mOrderId;


    private Topbar mTopbar;
    private View mTopBarView;
    private String mApprovalType;
    private String mExamineStatus;
    private String mApproverUser;
    private String mTitle;
    private String mExplains;
    private String mCosts;
    private RelativeLayout mRlTicketPlace;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_other_cost_hexiao_detail;
    }


    public static Fragment newInstance(Bundle bundle) {
        OtherCostHeXiaoDetailFragment detailFragment = new OtherCostHeXiaoDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mArguments = getArguments();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mGson = new Gson();
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_ticket_place));
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        initView();
        initTopbar();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        requestNet2Data();
        requsetNet2TicketPic();
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
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.OTHER_COST_HEXIAO_UPDATE);
                startActivity(intent);
            }
        });
    }

    /**
     * 得到票据图片
     */
    private void requsetNet2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "10").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                BackPicBean backPicBean = mGson.fromJson(s, BackPicBean.class);
                BackPicBean.PageListBean pageList = backPicBean.getPageList();
                List<BackPicBean.PageListBean.ListBean> list = pageList.getList();
                initPic(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 初始化图片显示到控件上；
     *
     * @param list
     */
    private void initPic(List<BackPicBean.PageListBean.ListBean> list) {
        List<ImageDataUrlBean> picUrlTitle = new ArrayList<>();
        if (list != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                String imgs = list.get(i).getImgs();
                String title = list.get(i).getTitle();
                picUrlTitle.add(new ImageDataUrlBean(imgs, title));
            }
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter ticketSitAdapter = new CommBackBigAdapter(getContext(), picUrlTitle);
        mMgvJoinSiteDetail.setAdapter(ticketSitAdapter);
    }

    private void initView() {
        mBtRefuse.setOnClickListener(this);
        mBtPass.setOnClickListener(this);
        mRlJoinSiteLook.setOnClickListener(this);
        mBtSaleHexiaoSubmit.setOnClickListener(this);
    }

    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHeXiaoDetailBean xiaoDetailBean = mGson.fromJson(s, OtherCostHeXiaoDetailBean.class);
                if (xiaoDetailBean.isSuccess()) {
                    OtherCostHeXiaoDetailBean.DataBean data = xiaoDetailBean.getData();
                    initData(data);
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
     * 其他费用详情展示
     *
     * @param data
     */
    private void initData(OtherCostHeXiaoDetailBean.DataBean data) {
        String userId = data.getUserId();

        mApproverUser = data.getApproverUser();
        mExamineStatus = data.getExamineStatus();
        mApprovalType = data.getApprovalType();

        String hxApproveState = Util.getHxApproveState(mExamineStatus, mApprovalType);
        List<OtherCostHeXiaoDetailBean.DataBean.ApprovalListBean> approvalList = data.getApprovalList();

        boolean isRefuse = "1".equals(mApprovalType);
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        if (((mUserId.equals(userId)) && (isShowMdBt || isRefuse))) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }
        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(data.getRemarks());
        }

        if (approvalList == null) {
            mLlApproveInfo.setVisibility(View.GONE);
        } else {
            if (approvalList.size() == 0) {
                mLlApproveInfo.setVisibility(View.GONE);
            }
        }

        if ("待审批".equals(hxApproveState)) {
            mLlApproveInfo.setVisibility(View.INVISIBLE);
        }

        if ((mUserId.equals(userId)) && ("待核销".equals(hxApproveState) || isRefuse)) {
            mBtSaleHexiaoSubmit.setVisibility(View.VISIBLE);
        }
        if (("待审批".equals(hxApproveState) || "审批中".equals(hxApproveState)) && (mUserId.equals(mApproverUser))) {
            mLlRefusePass.setVisibility(View.VISIBLE);
        }
        mTitle = data.getTitle();
        mExplains = data.getExplains();
        mCosts = data.getCosts();

        mTvPurport.setText(mTitle);
        mTvExplain.setText(mExplains);
        mTvCost.setText(mCosts);

        OtherCostHxDetailStateAdapter stateAdapter = new OtherCostHxDetailStateAdapter(getContext(), approvalList);
        mMylApply.setAdapter(stateAdapter);

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
            case R.id.bt_refuse:
                popupRefusePassTip();
                break;
            case R.id.bt_pass:
                popupConfirmPassTip();
                break;
            case R.id.rl_join_site_look:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.OTHER_COST_HEXIAO_DETAIL);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_sale_hexiao_submit:
                //调用修改接口；
                submitApprove();
                break;

        }
    }

    /**
     * 调用修改接口，其实就是 提交；
     */
    private void submitApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&title=" + mTitle + "&explains=" + mExplains + "&costs=" + mCosts +
                "&examineStatus=" + "1" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "提交成功");
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
                passApprove();
                dialog.dismiss();
            }
        });
    }

    private void passApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
                "&examineStatus=" + mExamineStatus + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "已通过");
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
                refuseApprove(refuseReason, "1");
                dialog.dismiss();
            }
        });
    }


    private void refuseApprove(String refuseReason, String s) {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
                "&examineStatus=" + mExamineStatus + "&approvalType=" + s + "&remarks=" + refuseReason).tag(getContext()).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "已拒绝");
                    getActivity().finish();
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
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

}
