package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import hwd.kuworuye.activity.PhotoViewPagerActivity;
import hwd.kuworuye.adapter.JoinSiteAyDetailStateAdapter;
import hwd.kuworuye.adapter.JoinSiteAyPicMangeAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.JoinSiteCostApplyDetailBean;
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
 * Created by Administrator on 2017/3/31.
 */
public class JoinSiteCostApplyDetailFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_mark_name)
    TextView mTvMarkName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_shopping_people)
    TextView mTvShoppingPeople;
    @BindView(R.id.tv_store_num)
    TextView mTvStoreNum;
    @BindView(R.id.tv_same_brand)
    TextView mTvSameBrand;
    @BindView(R.id.tv_cost_total)
    TextView mTvCostTotal;
    @BindView(R.id.myl_apply)
    MyListView mMylApply;
    Unbinder unbinder;
    @BindView(R.id.rl_join_site_look)
    RelativeLayout mRlJoinSiteDetail;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;

    @BindView(R.id.bt_delete)
    Button mBtDelete;
    @BindView(R.id.tv_ay_year_managecost)
    TextView mTvAyYearManagecost;
    @BindView(R.id.tv_ay_year_salecost)
    TextView mTvAyYearSalecost;

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
    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;

    private String mOrderId;
    private String mUrserId;
    private List<ImageDataUrlBean> joinSiteDetailList = new ArrayList<>();
    private Bundle mArguments;
    private Topbar mTopbar;
    private View mTopBarView;
    private String mApproverUser;
    private String mApprovalStatus;
    private String mApprovalType;
    private RelativeLayout mRlJoinPlace;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_join_cost_apply_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        JoinSiteCostApplyDetailFragment joinSiteCostApplyDetailFragment = new JoinSiteCostApplyDetailFragment();
        joinSiteCostApplyDetailFragment.setArguments(bundle);
        return joinSiteCostApplyDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUrserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mArguments = getArguments();
        mRlJoinPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_join_place));
        if (mArguments != null) {
            mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initTopbar();
        requestNet2Data();
        return rootView;
    }


    private void initTopbar() {
        mBtPass.setOnClickListener(this);
        mBtRefuse.setOnClickListener(this);
        mRlJoinSiteDetail.setOnClickListener(this);
        mBtDelete.setOnClickListener(this);

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
                intent.putExtra(Constant.CONTENT_TYPE, Constant.JOIN_SITE_COST_APPLY_UPDATE);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void requestNet2Data() {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                JoinSiteCostApplyDetailBean detailBean = gson.fromJson(s, JoinSiteCostApplyDetailBean.class);
                if (detailBean.isSuccess()) {
                    JoinSiteCostApplyDetailBean.DataBean data = detailBean.getData();
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

    /**
     * 进场费用详情界面；
     *
     * @param data
     */
    private void initData(JoinSiteCostApplyDetailBean.DataBean data) {
        mApprovalStatus = data.getApprovalStatus();
        mApprovalType = data.getApprovalType();
        String approveState = Util.getApproveState(mApprovalStatus, mApprovalType);
        mApproverUser = data.getApproverUser();

        String userId = data.getUserId();
        String approvalType = data.getApprovalType();
        boolean isRefuse = "1".equals(approvalType);
        boolean isShowMdBt = mArguments.getBoolean(Constant.IS_HAVE_MODIFIER);

        if ((mUrserId.equals(userId) && (isShowMdBt || isRefuse))) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }
        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(data.getRemarks());
        }
        if ("待审批".equals(approveState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }

        if (("待审批".equals(approveState) || "审批中".equals(approveState)) && (mUrserId.equals(mApproverUser))) {
            mLlRefusePassOther.setVisibility(View.VISIBLE);
        }

        if ((mUrserId.equals(userId)) && ("待审批".equals(approveState) || "待提交".equals(approveState) || isRefuse)) {
            mBtDelete.setVisibility(View.VISIBLE);
        }

        mTvMarkName.setText(data.getName());
        mTvAddress.setText(data.getAddress());
        mTvPhoneNumber.setText(data.getPhone());
        mTvShoppingPeople.setText(data.getPurchase());
        mTvStoreNum.setText(data.getStoreNumber() + "家");
        mTvSameBrand.setText(data.getSimilarBrands());
        mTvCostTotal.setText(data.getTotalExpenses() + "元");
        mTvAyYearManagecost.setText(data.getAnnualOperation() + "万元");
        mTvAyYearSalecost.setText(data.getAnnualSales() + "万元");

        List<JoinSiteCostApplyDetailBean.DataBean.ApprovalListBean> approvalList = data.getApprovalList();
        JoinSiteAyDetailStateAdapter stateAdapter = new JoinSiteAyDetailStateAdapter(getContext(), approvalList);
        mMylApply.setAdapter(stateAdapter);

        List<JoinSiteCostApplyDetailBean.DataBean.PhotoListBean> photoList = data.getPhotoList();
        if (photoList != null) {
            mRlJoinPlace.setVisibility(View.GONE);
            initPicData(photoList);
        } else {
            mRlJoinPlace.setVisibility(View.VISIBLE);
        }

    }

    /***
     * 初始化详情中的图片接口
     *
     * @param photoList
     */
    private void initPicData(List<JoinSiteCostApplyDetailBean.DataBean.PhotoListBean> photoList) {
        String processId = photoList.get(0).getProcessId();
        String type = photoList.get(0).getType();
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + processId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + type).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                CommPicListBean commPicListBean = gson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
                initPicShow2View(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 数据显示到控件上面；
     *
     * @param list
     */
    private void initPicShow2View(List<CommPicListBean.PageListBean.ListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            ImageDataUrlBean imageDataUrlBean = new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle());
            joinSiteDetailList.add(imageDataUrlBean);
        }
        JoinSiteAyPicMangeAdapter joinSitePicMange = new JoinSiteAyPicMangeAdapter(getContext(), joinSiteDetailList);
        mMgvJoinSiteDetail.setAdapter(joinSitePicMange);

        mMgvJoinSiteDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), PhotoViewPagerActivity.class);
                intent.putParcelableArrayListExtra(Constant.COMM_PIC_URL_TITLE, (ArrayList<? extends Parcelable>) joinSiteDetailList);
                startActivity(intent);
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
            case R.id.rl_join_site_look:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOINSITE_APPLY_DETAIL);
                //processid 其实就是mOrderId;
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_delete:
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

    private void refuseApprove(String refuseReason, String s) {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
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


    private void passApprove() {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY_APPROVE + "id=" + mOrderId + "&approverUser=" + mApproverUser +
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
                    deleteJoinSiteDetail();
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除进场详情；
     */
    private void deleteJoinSiteDetail() {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_DELETE + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
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
