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
import hwd.kuworuye.adapter.DispalyHeXiaoInfoAdapter;
import hwd.kuworuye.adapter.DisplayProductListHeXiaoAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.DisplayHeXiaoDetailBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
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
 * Created by Administrator on 2017/4/3.
 */
public class DisPlayHeXiaoDetailFragment extends FBaseFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.tv_display_name)
    TextView mTvDisplayName;
    @BindView(R.id.tv_hx_sale_goal)
    TextView mTvHxSaleGoal;
    @BindView(R.id.tv_shx_ale_target)
    TextView mTvShxAleTarget;
    @BindView(R.id.tv_hx_execute_city)
    TextView mTvHxExecuteCity;
    @BindView(R.id.tv_hx_execute_range)
    TextView mTvHxExecuteRange;
    @BindView(R.id.tv_hx_execute_time)
    TextView mTvHxExecuteTime;
    @BindView(R.id.tv_hx_feixiao_bv)
    TextView mTvHxFeixiaoBv;
    @BindView(R.id.tv_hx_sale_volume)
    TextView mTvHxSaleVolume;
    @BindView(R.id.tv_hx_zhuanjia_num)
    TextView mTvHxZhuanjiaNum;
    @BindView(R.id.tv_hx_zhuanjia_area)
    TextView mTvHxZhuanjiaArea;
    @BindView(R.id.tv_hx_location_explain)
    TextView mTvHxLocationExplain;
    @BindView(R.id.tv_hx_cost_budget)
    TextView mTvHxCostBudget;
    @BindView(R.id.tv_hx_baozhu_num)
    TextView mTvHxBaozhuNum;
    @BindView(R.id.tv_hx_dispaly_style)
    TextView mTvHxDispalyStyle;
    @BindView(R.id.tv_hx_bazozhu_location_explain)
    TextView mTvHxBazozhuLocationExplain;
    @BindView(R.id.tv_hx_baozhu_cost_budget)
    TextView mTvHxBaozhuCostBudget;
    @BindView(R.id.tv_other_num)
    TextView mTvOtherNum;
    @BindView(R.id.tv_hx_other_dispaly_style)
    TextView mTvHxOtherDispalyStyle;
    @BindView(R.id.tv_hx_other_location_explain)
    TextView mTvHxOtherLocationExplain;
    @BindView(R.id.tv_hx_other_cost_budget)
    TextView mTvHxOtherCostBudget;
    @BindView(R.id.myl_approve_info)
    MyListView mMylApproveInfo;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;
    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.myl_product_list)
    MyListView mMylProductList;
    @BindView(R.id.tv_total_box)
    TextView mTvTotalBox;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.ll_bottom_price_num)
    RelativeLayout mLlBottomPriceNum;
    @BindView(R.id.rl_mark_photo_look)
    RelativeLayout mRlMarkPhotoLook;

    @BindView(R.id.mgv_mark_detail)
    MyGridView mMgvMarkDetail;

    @BindView(R.id.rl_display_photo_look)
    RelativeLayout mRlDisplayPhotoLook;
    @BindView(R.id.mgv_dispaly_detail)
    MyGridView mMgvDispalyDetail;

    @BindView(R.id.rl_ticket_photo_look)
    RelativeLayout mRlTicketPhotoLook;

    @BindView(R.id.mgv_ticket_pic_detail)
    MyGridView mMgvTicketPicDetail;

    @BindView(R.id.bt_sale_hexiao_submit)
    Button mBtSaleHexiaoSubmit;

    @BindView(R.id.bt_xiaohe_refuse)
    Button mBtXiaoheRefuse;
    @BindView(R.id.bt_xiaohe_pass)
    Button mBtXiaohePass;
    @BindView(R.id.ll_refuse_pass_xiaohe)
    LinearLayout mLlRefusePassXiaohe;
    @BindView(R.id.tv_ac_sale_cost)

    TextView mTvAcSaleCost;
    @BindView(R.id.tv_ac_bilv)
    TextView mTvAcBilv;

    private String mUserId;
    private String mOrderId;
    private Bundle mArguments;

    private String mApprovalType;
    private String mApprovalMemo;
    private String mExamineStatus;
    private String mId;
    private Topbar mTopbar;
    private View mTopBarView;
    private RelativeLayout mMarketPlace;
    private RelativeLayout mDisplayPlace;
    private RelativeLayout mRlTicketPlace;
    private Gson mGson;

    protected int inflateContentView() {
        return R.layout.fragment_display_hexiao_detail;
    }


    public static Fragment newInstance(Bundle bundle) {
        DisPlayHeXiaoDetailFragment heXiaoDetailFragment = new DisPlayHeXiaoDetailFragment();
        heXiaoDetailFragment.setArguments(bundle);
        return heXiaoDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        mGson = new Gson();
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mMarketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_mark_place));
        mDisplayPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_display_place));
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_ticket_place));


        mArguments = getArguments();
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initTopbar();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNet2Data();
        requestNet2MarkPic();
        requestNet2DisplayPic();
        requestNet2TicketPic();
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
                intent.putExtra(Constant.CONTENT_TYPE, Constant.DISPLAY_HEXIAO_UPDATE);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
            }
        });
    }

    /**
     * 超市图片接口
     */
    private void requestNet2MarkPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "4").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> markList = pageList.getList();
                initMarkData(markList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 产品成列照片
     */
    private void requestNet2DisplayPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "5").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> disPlayList = pageList.getList();
                initDisplayData(disPlayList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 票据照片查看
     */
    private void requestNet2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "6").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> ticketList = pageList.getList();
                initTicketData(ticketList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 初始化票据照片
     *
     * @param ticketList
     */
    private void initTicketData(List<CommPicListBean.PageListBean.ListBean> ticketList) {
        List<ImageDataUrlBean> mTicketList = new ArrayList<>();
        if (ticketList != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < ticketList.size(); i++) {
                mTicketList.add(new ImageDataUrlBean(ticketList.get(i).getImgs(), ticketList.get(i).getTitle()));
            }
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mJoinTicketPicSite = new CommBackBigAdapter(getContext(), mTicketList);
        mMgvTicketPicDetail.setAdapter(mJoinTicketPicSite);
    }

    /**
     * 初始化超市照片数据
     *
     * @param markList
     */
    private void initMarkData(List<CommPicListBean.PageListBean.ListBean> markList) {
        List<ImageDataUrlBean> mMarkList = new ArrayList<>();
        if (markList != null) {
            mMarketPlace.setVisibility(View.GONE);
            for (int i = 0; i < markList.size(); i++) {
                mMarkList.add(new ImageDataUrlBean(markList.get(i).getImgs(), markList.get(i).getTitle()));

            }
        } else {
            mMarketPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mJoinSitePicSite = new CommBackBigAdapter(getContext(), mMarkList);
        mMgvMarkDetail.setAdapter(mJoinSitePicSite);
    }


    /***
     * 初始化产品成列照片
     *
     * @param disPlayList
     */
    private void initDisplayData(List<CommPicListBean.PageListBean.ListBean> disPlayList) {
        List<ImageDataUrlBean> mDispalyList = new ArrayList<>();
        if (disPlayList != null) {
            mDisplayPlace.setVisibility(View.GONE);
            for (int i = 0; i < disPlayList.size(); i++) {
                mDispalyList.add(new ImageDataUrlBean(disPlayList.get(i).getImgs(), disPlayList.get(i).getTitle()));
            }
        } else {
            mDisplayPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mJoinDisplayPicSite = new CommBackBigAdapter(getContext(), mDispalyList);
        mMgvDispalyDetail.setAdapter(mJoinDisplayPicSite);
    }


    private void initView() {
        mRlMarkPhotoLook.setOnClickListener(this);
        mRlMarkPhotoLook.setOnClickListener(this);
        mRlDisplayPhotoLook.setOnClickListener(this);
        mRlTicketPhotoLook.setOnClickListener(this);
        mBtSaleHexiaoSubmit.setOnClickListener(this);

        mBtXiaohePass.setOnClickListener(this);
        mBtXiaoheRefuse.setOnClickListener(this);
    }

    /***
     * 获取所有数据
     */
    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.get(HttpConstant.DISPLAY_HEXIAO_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoDetailBean detailBean = mGson.fromJson(s, DisplayHeXiaoDetailBean.class);
                boolean success = detailBean.isSuccess();
                if (success) {
                    initData(detailBean);
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
     * 展示数据
     *
     * @param detailBean
     */
    private void initData(DisplayHeXiaoDetailBean detailBean) {
        List<DisplayHeXiaoDetailBean.RecordListBean> recordList = detailBean.getRecordList();
        List<DisplayHeXiaoDetailBean.ItemListBean> itemList = detailBean.getItemList();
        DisplayHeXiaoDetailBean.KwDisplayExamineBean kwDisplayExamine = detailBean.getKwDisplayExamine();

        mId = kwDisplayExamine.getId();
        String userId = kwDisplayExamine.getUser().getId();
        String approverUser = kwDisplayExamine.getApproverUser();
        String approvalType = kwDisplayExamine.getApprovalType();
        boolean isRefuse = "1".equals(approvalType);

        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(kwDisplayExamine.getApprovalMemo());
        }
        mExamineStatus = kwDisplayExamine.getExamineStatus();
        String approveState = Util.getHxApproveState(mExamineStatus, kwDisplayExamine.getApprovalType());
        mApprovalType = kwDisplayExamine.getApprovalType();
        mApprovalMemo = kwDisplayExamine.getApprovalMemo();

        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }

        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        if ((mUserId.equals(userId)) && (isShowMdBt || isRefuse)) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
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

        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUserId.equals(approverUser))) {
            mLlRefusePassXiaohe.setVisibility(View.VISIBLE);
        }

        DispalyHeXiaoInfoAdapter commApproveInfoAdapter = new DispalyHeXiaoInfoAdapter(getActivity(), recordList);
        mMylApproveInfo.setAdapter(commApproveInfoAdapter);

        DisplayProductListHeXiaoAdapter applyProductListAdapter = new DisplayProductListHeXiaoAdapter(getContext(), itemList);
        mMylProductList.setAdapter(applyProductListAdapter);

        List<String> objectiveList = kwDisplayExamine.getObjectiveList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objectiveList.size(); i++) {
            String activityName = Util.getActivityName(objectiveList.get(i));
            if (i == objectiveList.size() - 1) {
                stringBuffer.append(activityName);
            } else {
                stringBuffer.append(activityName).append(",");
            }
        }
        mTvHxSaleGoal.setText(stringBuffer.toString());

        mTvDisplayName.setText(kwDisplayExamine.getName());
        mTvShxAleTarget.setText(kwDisplayExamine.getTarget());
        mTvHxExecuteCity.setText(kwDisplayExamine.getCity());
        mTvHxExecuteRange.setText(kwDisplayExamine.getRanges());
        mTvHxExecuteTime.setText(kwDisplayExamine.getExecutionTime());
        mTvHxFeixiaoBv.setText(kwDisplayExamine.getRatio() + "%");
        mTvHxSaleVolume.setText(kwDisplayExamine.getSalesVolume() + "元");

        mTvHxZhuanjiaNum.setText(kwDisplayExamine.getShelfNumber() + "家");
        mTvHxZhuanjiaArea.setText(kwDisplayExamine.getShelfArea() + "平方米");
        mTvHxLocationExplain.setText(kwDisplayExamine.getShelfDescription());
        mTvHxCostBudget.setText(kwDisplayExamine.getShelfBudget() + "元");

        mTvHxBaozhuNum.setText(kwDisplayExamine.getStackingNumber() + "家");
        mTvHxDispalyStyle.setText(kwDisplayExamine.getStackingForm());
        mTvHxBazozhuLocationExplain.setText(kwDisplayExamine.getStackingArea() + "平方米");
        mTvHxBaozhuCostBudget.setText(kwDisplayExamine.getStackingBudget() + "元");

        mTvOtherNum.setText(kwDisplayExamine.getOtherNumber() + "家");
        mTvHxOtherDispalyStyle.setText(kwDisplayExamine.getOtherForm());
        mTvHxOtherLocationExplain.setText(kwDisplayExamine.getOtherArea() + "平方米");
        mTvHxOtherCostBudget.setText(kwDisplayExamine.getOtherBudget() + "元");
        mTvAcSaleCost.setText(kwDisplayExamine.getActivitySales() + "元");
        mTvAcBilv.setText(kwDisplayExamine.getActivityRatio() + "%");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (view.getId()) {
            case R.id.rl_ticket_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_DETAIL_HEXIAO_TICKET);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_display_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_DETAIL_HEXIAO_DISPLAY);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_mark_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_DETAIL_HEXIAO_MARK);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_xiaohe_pass:
                //通过；
                popupConfirmSuccessTip();
                break;
            case R.id.bt_xiaohe_refuse:
                //拒绝；
                popupRefuseTip();
                break;
            case R.id.bt_sale_hexiao_submit:
                //调用审批接口；
                submitApprove();
                break;

        }
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

    /**
     * 拒绝申请；
     */
    private void refuseApprove(String refuseMemo) {
        showLoading();
        OkHttpUtils.post(HttpConstant.DISPLAY_HEXIAO_APPROVE + "id=" + mOrderId + "&examineStatus=" + mExamineStatus + "&user.id=" + mUserId + "&approvalType=" + "1" + "&approvalMemo=" + refuseMemo).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "操作成功");
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
     * 调用审批接口；
     */
    private void submitApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.DISPLAY_HEXIAO_APPROVE + "id=" + mOrderId + "&examineStatus=" + mExamineStatus + "&user.id=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "操作成功");
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
