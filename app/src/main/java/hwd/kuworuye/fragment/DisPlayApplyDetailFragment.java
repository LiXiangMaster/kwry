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
import hwd.kuworuye.adapter.KwCommProductAdapter;
import hwd.kuworuye.adapter.ProductDispalyDetailAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.CommUpdateProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ProductDisPlayCostDetailBean;
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
 * Created by Administrator on 2017/4/3.
 */
public class DisPlayApplyDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_display_name)
    TextView mTvDisplayName;
    @BindView(R.id.tv_sale_goal)
    TextView mTvSaleGoal;
    @BindView(R.id.tv_sale_target)
    TextView mTvSaleTarget;
    @BindView(R.id.tv_execute_city)
    TextView mTvExecuteCity;
    @BindView(R.id.tv_execute_range)
    TextView mTvExecuteRange;
    @BindView(R.id.tv_execute_time)
    TextView mTvExecuteTime;
    @BindView(R.id.tv_feixiao_bv)
    TextView mTvFeixiaoBv;

    @BindView(R.id.tv_zhuanjia_num)
    TextView mTvZhuanjiaNum;
    @BindView(R.id.tv_zhuanjia_area)
    TextView mTvZhuanjiaArea;
    @BindView(R.id.tv_location_explain)
    TextView mTvLocationExplain;
    @BindView(R.id.tv_cost_budget)
    TextView mTvCostBudget;
    @BindView(R.id.tv_baozhu_num)
    TextView mTvBaozhuNum;
    @BindView(R.id.tv_dispaly_style)
    TextView mTvDispalyStyle;
    @BindView(R.id.tv_bazozhu_location_explain)
    TextView mTvBazozhuLocationExplain;
    @BindView(R.id.tv_baozhu_cost_budget)
    TextView mTvBaozhuCostBudget;
    @BindView(R.id.tv_other_num)
    TextView mTvOtherNum;
    @BindView(R.id.tv_other_dispaly_style)
    TextView mTvOtherDispalyStyle;
    @BindView(R.id.tv_other_location_explain)
    TextView mTvOtherLocationExplain;
    @BindView(R.id.tv_other_cost_budget)
    TextView mTvOtherCostBudget;
    @BindView(R.id.myl_approve_info)
    MyListView mMylApproveInfo;
    @BindView(R.id.myl_product_list)
    MyListView mMylProductList;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;
    @BindView(R.id.bt_display_delete)
    Button mBtDisplayDelete;
    Unbinder unbinder;
    @BindView(R.id.tv_sale_volume)
    TextView mTvSaleVolume;
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;
    @BindView(R.id.rl_mark_photo_look)
    RelativeLayout mRlMarkPhotoLook;

    @BindView(R.id.bt_refuse)
    Button mBtRefuse;
    @BindView(R.id.bt_pass)
    Button mBtPass;
    @BindView(R.id.ll_refuse_pass_other)
    LinearLayout mLlRefusePassOther;
    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;
    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.tv_ac_sale_cost)

    TextView mTvAcSaleCost;
    @BindView(R.id.tv_ac_bilv)
    TextView mTvAcBilv;

    private String mUserId;
    private String mOrderId;
    private Bundle mArguments;
    private Topbar mTopbar;
    private View mTopBarView;
    private List<ProductDisPlayCostDetailBean.ItemListBean> mItemList;
    private String mApprovalStatus;
    private List<CommUpdateProductBean> mUpdateProductList = new ArrayList<>();
    private String mApprovalType;
    private String mApproverUser;
    private RelativeLayout mRlMarkPlace;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_product_dispaly_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        DisPlayApplyDetailFragment fragment = new DisPlayApplyDetailFragment();
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
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mRlMarkPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_mark_place));
        initView();
        initTopbar();
        requestNet2Data();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNet2PicList();
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
                intent.putExtra(Constant.CONTENT_TYPE, Constant.DISPLAY_APPLY_EDIT);
                intent.putExtra(Constant.APPLY_ACTIVITY_EDIT_JOIN_WAY, Constant.DISPLAY_APPLY_UPDATE);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void initView() {
        mBtRefuse.setOnClickListener(this);
        mRlMarkPhotoLook.setOnClickListener(this);
        mBtDisplayDelete.setOnClickListener(this);
        mBtPass.setOnClickListener(this);
    }

    private void requestNet2PicList() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "3").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                CommPicListBean commPicListBean = gson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                initPicData(pageList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });

    }

    /**
     * 初始化图片数据；
     *
     * @param pageList
     */
    private void initPicData(CommPicListBean.PageListBean pageList) {
        List<ImageDataUrlBean> picList = new ArrayList<>();
        List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
        if (list != null) {
            mRlMarkPlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                picList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
            }
        } else {
            mRlMarkPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mPicBackAdapter = new CommBackBigAdapter(getContext(), picList);
        mMgvJoinSiteDetail.setAdapter(mPicBackAdapter);
    }

    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.get(HttpConstant.PRODUCT_DISPLAY_COST_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                ProductDisPlayCostDetailBean playCostDetailBean = gson.fromJson(s, ProductDisPlayCostDetailBean.class);
                boolean success = playCostDetailBean.isSuccess();
                if (success) {
                    initData(playCostDetailBean);
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
     * 初始化数据
     *
     * @param playCostDetailBean
     */
    private void initData(ProductDisPlayCostDetailBean playCostDetailBean) {
        List<ProductDisPlayCostDetailBean.RecordListBean> recordList = playCostDetailBean.getRecordList();
        mItemList = playCostDetailBean.getItemList();
        ProductDisPlayCostDetailBean.KwDisplayApplicationBean kwDisplayApplication = playCostDetailBean.getKwDisplayApplication();

        mApproverUser = kwDisplayApplication.getApproverUser();
        mApprovalStatus = kwDisplayApplication.getApprovalStatus();
        mApprovalType = kwDisplayApplication.getApprovalType();
        String approveState = Util.getApproveState(mApprovalStatus, mApprovalType);
        String userId = kwDisplayApplication.getUser().getId();
        boolean isRefuse = "1".equals(mApprovalType);
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);
        //如果是已拒绝的时候也会有修改按钮；
        if ((mUserId.equals(userId) && (isShowMdBt || isRefuse))) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }

        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(kwDisplayApplication.getApprovalMemo());
        }

        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }
        if ((mUserId.equals(userId)) && ("待提交".equals(approveState) || "待审批".equals(approveState) || isRefuse)) {
            mBtDisplayDelete.setVisibility(View.VISIBLE);
        }
        if (("待审批".equals(approveState) || "审批中".equals(approveState)) &&
                (mUserId.equals(mApproverUser))) {
            mLlRefusePassOther.setVisibility(View.VISIBLE);
        }


        ProductDispalyDetailAdapter commApproveInfoAdapter = new ProductDispalyDetailAdapter(getActivity(), recordList);
        mMylApproveInfo.setAdapter(commApproveInfoAdapter);
        for (int i = 0; i < mItemList.size(); i++) {
            String quantity = mItemList.get(i).getQuantity();
            String productName = mItemList.get(i).getKwProductAttribute().getProduct().getProductName();
            String price = mItemList.get(i).getKwProductAttribute().getPrice();
            String thumbnail = mItemList.get(i).getKwProductAttribute().getThumbnail();
            String tasteStr = mItemList.get(i).getKwProductAttribute().getTasteStr();
            String specificationsStr = mItemList.get(i).getKwProductAttribute().getSpecificationsStr();
            String productAttributeId = mItemList.get(i).getProductAttributeId();
            mUpdateProductList.add(new CommUpdateProductBean(productAttributeId, quantity, price, tasteStr, productName, specificationsStr, thumbnail));
        }

        KwCommProductAdapter kwCommProductAdapter = new KwCommProductAdapter(getContext(), mUpdateProductList);
        mMylProductList.setAdapter(kwCommProductAdapter);

        mTvDisplayName.setText(kwDisplayApplication.getName());

        List<String> objectiveList = kwDisplayApplication.getObjectiveList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objectiveList.size(); i++) {
            String activityName = Util.getActivityName(objectiveList.get(i));
            if (i == objectiveList.size() - 1) {
                stringBuffer.append(activityName);
            } else {
                stringBuffer.append(activityName).append(",");
            }
        }

        mTvSaleGoal.setText(stringBuffer.toString());

        mTvSaleTarget.setText(kwDisplayApplication.getTarget());
        mTvExecuteCity.setText(kwDisplayApplication.getCity());
        mTvExecuteRange.setText(kwDisplayApplication.getRanges());
        mTvExecuteTime.setText(kwDisplayApplication.getExecutionTime());
        mTvFeixiaoBv.setText(kwDisplayApplication.getRatio() + "%");
        mTvSaleVolume.setText(kwDisplayApplication.getSalesVolume() + "元");

        mTvZhuanjiaNum.setText(kwDisplayApplication.getShelfNumber() + "家");
        String shelfArea = kwDisplayApplication.getShelfArea();
        mTvZhuanjiaArea.setText(kwDisplayApplication.getShelfArea() + "平方米");
        mTvLocationExplain.setText(kwDisplayApplication.getShelfDescription());
        mTvCostBudget.setText(kwDisplayApplication.getShelfBudget() + "元");

        mTvBaozhuNum.setText(kwDisplayApplication.getStackingNumber() + "家");
        mTvDispalyStyle.setText(kwDisplayApplication.getStackingForm());
        mTvBazozhuLocationExplain.setText(kwDisplayApplication.getStackingArea());
        mTvBaozhuCostBudget.setText(kwDisplayApplication.getStackingBudget() + "元");

        mTvOtherNum.setText(kwDisplayApplication.getOtherNumber() + "家");
        mTvOtherDispalyStyle.setText(kwDisplayApplication.getOtherForm());
        mTvOtherLocationExplain.setText(kwDisplayApplication.getOtherArea());
        mTvOtherCostBudget.setText(kwDisplayApplication.getOtherBudget() + "元");

        mTvAcSaleCost.setText(kwDisplayApplication.getActivitySales() + "元");
        mTvAcBilv.setText(kwDisplayApplication.getActivityRatio() + "%");


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
            case R.id.rl_mark_photo_look:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_APPLY_DETAIL_MARK);
                //processid 其实就是mOrderId;
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_display_delete:
                popupLoginOutTip("删除");
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
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_APPLY_APPROVE + "id=" + mOrderId + "&approvalStatus=" + mApprovalStatus +
                "&user.id=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
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
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_APPLY_APPROVE + "id=" + mOrderId + "&approvalStatus=" + mApprovalStatus +
                "&user.id=" + mUserId + "&approvalType=" + s + "&approvalMemo=" + refuseReason).tag(getContext()).execute(new StringCallback() {

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
                    deleteDispalyApplyDetail();
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除产品成列详情；
     */
    private void deleteDispalyApplyDetail() {
        showLoading();
        OkHttpUtils.get(HttpConstant.PRODUCT_DISPLAY_APPLY_DELETE + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
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
