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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.CommBackBigAdapter;
import hwd.kuworuye.adapter.JoinSiteHxDetailStateAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ImageTypeTitleUrlBean;
import hwd.kuworuye.bean.JoinSiteHxDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
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
 * Created by Administrator on 2017/4/2.
 */
public class JoinSiteCostHeXiaoDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_hx_mark_name)
    TextView mTvHxMarkName;
    @BindView(R.id.tv_hx_address)
    TextView mTvHxAddress;
    @BindView(R.id.tv_hx_phone_number)
    TextView mTvHxPhoneNumber;
    @BindView(R.id.tv_hx_shopping_people)
    TextView mTvHxShoppingPeople;
    @BindView(R.id.tv_hx_store_num)
    TextView mTvHxStoreNum;
    @BindView(R.id.tv_hx_year_managecost)
    TextView mTvHxYearManagecost;
    @BindView(R.id.tv_hx_same_brand)
    TextView mTvHxSameBrand;
    @BindView(R.id.tv_year_salecost)
    TextView mTvYearSalecost;
    @BindView(R.id.tv_hx_cost_total)
    TextView mTvHxCostTotal;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.myl_apply)
    MyListView mMylApply;
    @BindView(R.id.rl_join_site_look)
    RelativeLayout mRlJoinSiteLook;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;
    @BindView(R.id.rl_ticket_look)
    RelativeLayout mRlTicketLook;
    @BindView(R.id.mgv_ticket_detail)
    MyGridView mMgvTicketDetail;
    Unbinder unbinder;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;

    @BindView(R.id.bt_sale_hexiao_submit)
    Button mBtSaleHexiaoSubmit;
    @BindView(R.id.bt_refuse)
    Button mBtRefuse;
    @BindView(R.id.bt_pass)
    Button mBtPass;
    @BindView(R.id.ll_refuse_pass)
    LinearLayout mLlRefusePass;
    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;

    private String mOrderId;
    private Bundle mArguments;
    private String mUserId;
    private Topbar mTopbar;
    private View mTopBarView;
    private RelativeLayout mRlSitePlace;
    private RelativeLayout mRlTicketPlace;
    private Gson mGson;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mPurchase;
    private int mStoreNumber;
    private String mSimilarBrands;
    private double mTotalExpenses;
    private double mAnnualOperation;
    private double mAnnualSales;
    private String mApproverUser;
    private String mExamineStatus;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_joinsite_hexiao_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        JoinSiteCostHeXiaoDetailFragment detailFragment = new JoinSiteCostHeXiaoDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);

        mRlSitePlace = ((RelativeLayout) rootView.findViewById(R.id.rl_site_place));
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_ticket_place));
        mGson = new Gson();
        mArguments = getArguments();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initView();
        initTopbar();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNet2Data();
        joinSitePicShow();
        joinSiteTicketShow();
    }

    private void initView() {
        mRlJoinSiteLook.setOnClickListener(this);
        mRlTicketLook.setOnClickListener(this);
        mBtSaleHexiaoSubmit.setOnClickListener(this);
        mBtRefuse.setOnClickListener(this);
        mBtPass.setOnClickListener(this);
    }

    /**
     * 进场票据照片查看；
     */
    private void joinSiteTicketShow() {
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "9").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                CommPicListBean commPicListBean = gson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
                initJoinSiteTicket(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 进场现场照片查看；
     */
    private void joinSitePicShow() {
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "8").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                CommPicListBean commPicListBean = gson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
                initJoinSitePhoto(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 进场票据查看
     *
     * @param list
     */
    private void initJoinSiteTicket(List<CommPicListBean.PageListBean.ListBean> list) {
        final List<ImageDataUrlBean> joinSiteTicketList = new ArrayList<>();
        List<ImageTypeTitleUrlBean> joinJsonList = new ArrayList<>();
        if (list != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                joinSiteTicketList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));

            }
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }
        //票据照片图片；
        CommBackBigAdapter mJoinSitePicSite = new CommBackBigAdapter(getContext(), joinSiteTicketList);
        mMgvTicketDetail.setAdapter(mJoinSitePicSite);
    }

    /**
     * 进场照片显示；
     *
     * @param list
     */
    private void initJoinSitePhoto(List<CommPicListBean.PageListBean.ListBean> list) {
        List<ImageDataUrlBean> joinSiteList = new ArrayList<>();
        final List<ImageDataUrlBean> newList = new ArrayList<>();
        if (list != null) {
            mRlSitePlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                joinSiteList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
                newList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
            }
        } else {
            mRlSitePlace.setVisibility(View.VISIBLE);
        }

        CommBackBigAdapter mJoinSitePicSite = new CommBackBigAdapter(getContext(), newList);
        mMgvJoinSiteDetail.setAdapter(mJoinSitePicSite);


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
                intent.putExtra(Constant.CONTENT_TYPE, Constant.JOIN_SITE_HEXIAO_UPDATE);
                startActivity(intent);
            }
        });
    }


    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHxDetailBean joinSiteHxDetailBean = mGson.fromJson(s, JoinSiteHxDetailBean.class);
                boolean success = joinSiteHxDetailBean.isSuccess();
                if (success) {
                    JoinSiteHxDetailBean.DataBean data = joinSiteHxDetailBean.getData();
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

    private void initData(JoinSiteHxDetailBean.DataBean data) {
        mApproverUser = data.getApproverUser();
        mExamineStatus = data.getExamineStatus();

        String approveState = Util.getHxApproveState(mExamineStatus, data.getApprovalType());
        List<JoinSiteHxDetailBean.DataBean.ApprovalListBean> approvalList = data.getApprovalList();

        String userId = data.getUserId();
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        String approvalType = data.getApprovalType();
        boolean isRefuse = "1".equals(approvalType);
        if ((mUserId.equals(userId)) && (isShowMdBt || isRefuse)) {
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

        if ((mUserId.equals(userId)) && ("待核销".equals(approveState) || isRefuse)) {
            mBtSaleHexiaoSubmit.setVisibility(View.VISIBLE);
        }

        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }
        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUserId.equals(mApproverUser))) {
            mLlRefusePass.setVisibility(View.VISIBLE);
        }
        mName = data.getName();
        mAddress = data.getAddress();
        mPhone = data.getPhone();
        mPurchase = data.getPurchase();
        mStoreNumber = data.getStoreNumber();
        mSimilarBrands = data.getSimilarBrands();
        mTotalExpenses = data.getTotalExpenses();
        mAnnualOperation = data.getAnnualOperation();
        mAnnualSales = data.getAnnualSales();

        mTvHxMarkName.setText(mName);
        mTvHxAddress.setText(mAddress);
        mTvHxPhoneNumber.setText(mPhone);
        mTvHxShoppingPeople.setText(mPurchase);
        mTvHxStoreNum.setText(mStoreNumber + "家");
        mTvHxSameBrand.setText(mSimilarBrands);
        mTvHxCostTotal.setText(mTotalExpenses + "元");
        mTvHxYearManagecost.setText(mAnnualOperation + "万元");
        mTvYearSalecost.setText(mAnnualSales + "万元");

        JoinSiteHxDetailStateAdapter stateAdapter = new JoinSiteHxDetailStateAdapter(getContext(), approvalList);
        mMylApply.setAdapter(stateAdapter);
        mMylApply.setEmptyView(mLlEmpty);

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
            case R.id.rl_join_site_look:
                Intent intentSite = new Intent(getContext(), ContentActivity.class);
                intentSite.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intentSite.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOIN_SITE_HEXIAO_DETAIL_SITE);
                //processid 其实就是mOrderId;
                intentSite.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intentSite);
                break;
            case R.id.rl_ticket_look:
                Intent intentTicket = new Intent(getContext(), ContentActivity.class);
                intentTicket.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intentTicket.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOIN_SITE_HEXIAO_DETAIL_TICKET);
                //processid 其实就是mOrderId;
                intentTicket.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intentTicket);
                break;

            case R.id.bt_sale_hexiao_submit:
                //调用审批接口；
                submitApprove();
                break;
            case R.id.bt_refuse:
                popupRefusePassTip();
                break;
            case R.id.bt_pass:
                popupConfirmPassTip();
                break;

        }
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
        OkHttpUtils.post(HttpConstant.JOIN_SITE_HEXIAO_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
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
        OkHttpUtils.post(HttpConstant.JOIN_SITE_HEXIAO_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
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


    /**
     * 调用修改接口，其实就是 提交；
     */
    private void submitApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_HEXIAO_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&name=" + mName +
                "&address=" + mAddress + "&phone=" + mPhone + "&purchase=" + mPurchase + "&storeNumber=" + mStoreNumber + "&annualOperation=" + mAnnualOperation + "&similarBrands=" + mSimilarBrands + "&annualSales=" + mAnnualSales + "&totalExpenses=" +
                mTotalExpenses + "&examineStatus=" + "1" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "修改成功");
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


}
