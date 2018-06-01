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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.ApplyProductListHeXiaoAdapter;
import hwd.kuworuye.adapter.CommBackBigAdapter;
import hwd.kuworuye.adapter.CommHeXiaoInfoAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.BackPicBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.SaleActivityXiaoHeDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommHeXiaoRefrestEvent;
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
 * Created by Administrator on 2017/3/7.
 */
public class SaleAcHeXiaoDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.bt_xiaohe_pass)
    Button mBtPass;
    Unbinder unbinder;
    @BindView(R.id.bt_xiaohe_refuse)
    Button mBtXiaoheRefuse;

    @BindView(R.id.rl_activity_site_pic_manager)
    RelativeLayout mRlActivitySitePicManager;
    @BindView(R.id.rl_ticket_manager)
    RelativeLayout mRlTicketManager;
    @BindView(R.id.ll_refuse_pass_xiaohe)
    LinearLayout mLlRefusePassXiaohe;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.mlv_approve_info)
    MyListView mMlvApproveInfo;
    @BindView(R.id.tv_activity_name)
    TextView mTvActivityName;
    @BindView(R.id.tv_activity_goal)
    TextView mTvActivityGoal;
    @BindView(R.id.myl_product_list)
    MyListView mMylProductList;
    @BindView(R.id.tv_execute_city)
    TextView mTvExecuteCity;
    @BindView(R.id.tv_execute_time)
    TextView mTvExecuteTime;
    @BindView(R.id.tv_content_shape)
    TextView mTvContentShape;
    @BindView(R.id.tv_xiaohe_material)
    TextView mTvXiaoheMaterial;
    @BindView(R.id.tv_xiaohe_bilv)
    TextView mTvXiaoheBilv;
    @BindView(R.id.tv_sale_cost)
    TextView mTvSaleCost;
    @BindView(R.id.tv_execute_range)
    TextView mTvRanges;

    @BindView(R.id.mgv_activity_site)
    MyGridView mMgvActivitySite;
    @BindView(R.id.mgv_ticket_manage)
    MyGridView mMgvTicketManage;

    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;

    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.bt_sale_hexiao_submit)
    Button mBtSaleHexiaoSubmit;

    @BindView(R.id.tv_ac_sale_cost)
    TextView mTvAcSaleCost;
    @BindView(R.id.tv_ac_bilv)
    TextView mTvAcBilv;

    private String mOrderId;
    private Topbar mTopbar;
    private View mTopBarView;
    private String mUserId;
    private Bundle mArguments;
    private RelativeLayout mRlSitePlace;
    private RelativeLayout mRlTicketPlace;
    private String mExamineStatus;
    private String mApprovalType;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_apply_for_xiaohe_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        SaleAcHeXiaoDetailFragment fragmentg = new SaleAcHeXiaoDetailFragment();
        fragmentg.setArguments(bundle);
        return fragmentg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        mRlSitePlace = ((RelativeLayout) rootView.findViewById(R.id.rl_ac_site_place));
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_ac_ticket_place));

        mArguments = getArguments();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
            requestNet2Detail(mOrderId);
        }

        initTopbar();
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //请求图片接口；
        request2ActivitySitPic();
        request2TicketPic();
    }

    /**
     * 促销活动核销 返回 票据图片
     */
    private void request2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "2").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                BackPicBean backPicBean = mGson.fromJson(s, BackPicBean.class);
                BackPicBean.PageListBean ticketPicBean = backPicBean.getPageList();
                //核销票据集合
                List<BackPicBean.PageListBean.ListBean> ticketPicList = ticketPicBean.getList();
                initTicketPic(ticketPicList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 显示核销 票据图片mangli；
     *
     * @param ticketPicList
     */
    private void initTicketPic(List<BackPicBean.PageListBean.ListBean> ticketPicList) {

        List<ImageDataUrlBean> list = new ArrayList<>();
        if (ticketPicList != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < ticketPicList.size(); i++) {
                list.add(new ImageDataUrlBean(ticketPicList.get(i).getImgs(), ticketPicList.get(i).getTitle()));
            }
        }
        CommBackBigAdapter adapter = new CommBackBigAdapter(getContext(), list);
        mMgvTicketManage.setAdapter(adapter);
    }

    /**
     * 促销活动核销请求后台返回 现场管理图片
     */
    private void request2ActivitySitPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                BackPicBean backPicBean = mGson.fromJson(s, BackPicBean.class);
                BackPicBean.PageListBean activityMangePic = backPicBean.getPageList();
                //核销现场图片集合；
                List<BackPicBean.PageListBean.ListBean> activityList = activityMangePic.getList();
                initActivityPic(activityList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 核销 活动现场图片；
     *
     * @param activityList
     */
    private void initActivityPic(List<BackPicBean.PageListBean.ListBean> activityList) {
        List<ImageDataUrlBean> list = new ArrayList<>();
        if (activityList != null) {
            mRlSitePlace.setVisibility(View.GONE);
            for (int i = 0; i < activityList.size(); i++) {
                list.add(new ImageDataUrlBean(activityList.get(i).getImgs(), activityList.get(i).getTitle()));
            }
        }
        CommBackBigAdapter adapter = new CommBackBigAdapter(getContext(), list);
        mMgvActivitySite.setAdapter(adapter);
    }

    /**
     * 请求接口获取详情数据；
     *
     * @param orderId
     */
    private void requestNet2Detail(String orderId) {
        showLoading();
        OkHttpUtils.get(HttpConstant.SALE_XIAOHE_DETAIL + "id=" + orderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SaleActivityXiaoHeDetailBean saleActivityXiaoHeDetailBean = mGson.fromJson(s, SaleActivityXiaoHeDetailBean.class);
                boolean success = saleActivityXiaoHeDetailBean.isSuccess();
                if (success) {
                    initData(saleActivityXiaoHeDetailBean);
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
     * 刷新列表；
     *
     * @param event
     */
    @Subscribe
    public void refreshApplyActivityList(CommHeXiaoRefrestEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            requestNet2Detail(mOrderId);
            //请求图片接口；
            request2ActivitySitPic();
            request2TicketPic();
        }
    }

    private void initData(SaleActivityXiaoHeDetailBean saleActivityXiaoHeDetailBean) {
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        SaleActivityXiaoHeDetailBean.KwPromotionExamineBean kwPromotionExamine = saleActivityXiaoHeDetailBean.getKwPromotionExamine();
        String approverUser = saleActivityXiaoHeDetailBean.getKwPromotionExamine().getApproverUser();
        mExamineStatus = saleActivityXiaoHeDetailBean.getKwPromotionExamine().getExamineStatus();
        mApprovalType = saleActivityXiaoHeDetailBean.getKwPromotionExamine().getApprovalType();
        List<SaleActivityXiaoHeDetailBean.ItemListBean> itemList = saleActivityXiaoHeDetailBean.getItemList();
        String approveState = Util.getHxApproveState(mExamineStatus, mApprovalType);
        String userId = saleActivityXiaoHeDetailBean.getKwPromotionExamine().getUser().getId();
        List<SaleActivityXiaoHeDetailBean.RecordListBean> recordList = saleActivityXiaoHeDetailBean.getRecordList();


        boolean isRefuse = "1".equals(mApprovalType);
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        if ((mUserId.equals(userId)) && (isShowMdBt || isRefuse)) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }

        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(kwPromotionExamine.getApprovalMemo());
        }

        if (recordList == null) {
            mLlApproveInfo.setVisibility(View.GONE);
        } else {
            if (recordList.size() == 0) {
                mLlApproveInfo.setVisibility(View.GONE);
            }
        }

        if ((mUserId.equals(userId)) && ("待核销".equals(approveState) || isRefuse)) {
            mBtSaleHexiaoSubmit.setVisibility(View.VISIBLE);
        }

        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }
        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUserId.equals(approverUser))) {
            mLlRefusePassXiaohe.setVisibility(View.VISIBLE);
        }

        mMlvApproveInfo.setEmptyView(mLlEmpty);
        CommHeXiaoInfoAdapter commApproveInfoAdapter = new CommHeXiaoInfoAdapter(getActivity(), recordList);
        mMlvApproveInfo.setAdapter(commApproveInfoAdapter);

        List<String> objectiveList = kwPromotionExamine.getObjectiveList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objectiveList.size(); i++) {
            String activityName = Util.getActivityName(objectiveList.get(i));
            if (i == objectiveList.size() - 1) {
                stringBuffer.append(activityName);
            } else {
                stringBuffer.append(activityName).append(",");
            }
        }
        mTvActivityGoal.setText(stringBuffer.toString());
        mTvRanges.setText(kwPromotionExamine.getRanges());
        mTvActivityName.setText(kwPromotionExamine.getName());
        mTvExecuteCity.setText(kwPromotionExamine.getCity());
        mTvExecuteTime.setText(kwPromotionExamine.getExecutionTime());
        mTvContentShape.setText(kwPromotionExamine.getContent());
        mTvXiaoheMaterial.setText(kwPromotionExamine.getMateriel());
        mTvXiaoheBilv.setText(kwPromotionExamine.getRatio() + "%");
        mTvSaleCost.setText(kwPromotionExamine.getSalesVolume() + "元");
        mTvAcSaleCost.setText(kwPromotionExamine.getActivitySales() + "元");
        mTvAcBilv.setText(kwPromotionExamine.getActivityRatio() + "%");

        ApplyProductListHeXiaoAdapter applyProductListAdapter = new ApplyProductListHeXiaoAdapter(getContext(), itemList);
        mMylProductList.setAdapter(applyProductListAdapter);


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
                intent.putExtra(Constant.CONTENT_TYPE, Constant.ACTIVITY_HEXIAO_EDIT);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBtPass.setOnClickListener(this);
        mBtXiaoheRefuse.setOnClickListener(this);
        mRlActivitySitePicManager.setOnClickListener(this);
        mRlTicketManager.setOnClickListener(this);
        mBtSaleHexiaoSubmit.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (view.getId()) {
            case R.id.bt_xiaohe_pass:
                popupConfirmSuccessTip();
                break;
            case R.id.bt_xiaohe_refuse:
                popupRefuseTip();
                break;
            case R.id.rl_activity_site_pic_manager:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.SALE_ACTIVITY_SITE_HEXIAO_PIC);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_ticket_manager:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.SALE_ACTIVITY_SITE_TICKET_PIC);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_sale_hexiao_submit:
                //调用审批接口；
                submitApprove();
                break;
            default:
                break;
        }
    }

    /**
     * 调用  通过 审批接口；
     */
    private void submitApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.SALE_HEXIAO_APPROVE + "id=" + mOrderId + "&examineStatus=" + mExamineStatus + "&user.id=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "提交成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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

    private void popupRefuseTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.refuse_pass_tip, null);
        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_refuse_pass_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_refuse_pass_confirm);
        final EditText etInput = (EditText) dialog.findViewById(R.id.et_refuse_reason);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputContent = etInput.getText().toString();
                if (TextUtils.isEmpty(inputContent)) {
                    Util.toast(getContext(), "请输入拒绝理由");
                    return;
                }
                refuseApprove(inputContent);
                dialog.dismiss();
            }
        });
    }

    /**
     * 拒绝审批；
     */
    private void refuseApprove(String inputContent) {
        showLoading();
        OkHttpUtils.post(HttpConstant.SALE_HEXIAO_APPROVE + "id=" + mOrderId + "&examineStatus=" + mExamineStatus + "&user.id=" + mUserId + "&approvalType=" + "1" + "&approvalMemo=" + inputContent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "操作成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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

    private void popupConfirmSuccessTip() {
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
                submitApprove();
                dialog.dismiss();
            }
        });
    }
}
