package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import hwd.kuworuye.adapter.CommApplyListAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommApplyListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.JoinSiteCostListBean;
import hwd.kuworuye.bean.OtherCostApplyListBean;
import hwd.kuworuye.bean.ProductPlayCostListBean;
import hwd.kuworuye.bean.QueryAllSaleApplyListBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class ApplyForFragment extends FBaseFragment {

    @BindView(R.id.plr_apply_for)
    PullToRefreshListView mPlrSaleStatePage;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private String mUserId;
    private String mJoinWays;

    private int mAllPageNo = 1;
    private int mIngPageNo = 1;
    private int mDonePageNo = 1;
    private int mRefusePageNo = 1;
    private int mWaiteMePageNo = 1;

    private int mPageSize = 10;
    private Bundle mArguments;
    private String orderStateName;
    private Gson mGson;

    List<CommApplyListBean> mAllList = new ArrayList<>();
    List<CommApplyListBean> mIngList = new ArrayList<>();
    List<CommApplyListBean> mDoneList = new ArrayList<>();
    List<CommApplyListBean> mRefuseList = new ArrayList<>();
    List<CommApplyListBean> mWaiteMeList = new ArrayList<>();

    private CommApplyListAdapter mAllAdapter = null;
    private CommApplyListAdapter mIngAdapter = null;
    private CommApplyListAdapter mDoneAdapter = null;
    private CommApplyListAdapter mRefuseAdapter = null;
    private CommApplyListAdapter mWaiteMeAdapter = null;

    @Override
    protected int inflateContentView() {

        return R.layout.fragment_apply_for;

    }

    public static Fragment newInstance(Bundle bundle) {
        ApplyForFragment orderSortFragment = new ApplyForFragment();
        orderSortFragment.setArguments(bundle);
        return orderSortFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initLinster();
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        mArguments = getArguments();
        if (mArguments != null) {
            mJoinWays = mArguments.getString(Constant.JOIN_APPLYFROR_WAY);
        }

        return rootView;
    }

    private void initLinster() {
        mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.BOTH);
        mPlrSaleStatePage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.BOTH);
                mAllList.clear();
                mIngList.clear();
                mDoneList.clear();
                mRefuseList.clear();
                mWaiteMeList.clear();

                mAllPageNo = 1;
                mIngPageNo = 1;
                mDonePageNo = 1;
                mRefusePageNo = 1;
                mWaiteMePageNo = 1;

                refreshDownOrUp();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mAllPageNo++;
                mIngPageNo++;
                mDonePageNo++;
                mRefusePageNo++;
                mWaiteMePageNo++;

                refreshDownOrUp();
            }
        });
    }

    /**
     * 根据状态，向上或向下刷新
     */
    private void refreshDownOrUp() {
        mJoinWays = mArguments.getString(Constant.JOIN_APPLYFROR_WAY);
        if (mArguments != null) {
            switch (mJoinWays) {
                case "促销活动申请":
                    switch (orderStateName) {
                        case "全部":
                            queryAllSaleList(false);
                            break;
                        case "审批中":
                            saleIng(false);
                            break;
                        case "待我审批":
                            saleQueryWaitMe(false);
                            break;
                        case "交易完成":
                            saleDone(false);
                            break;
                        case "已拒绝":
                            saleQuerybyRefuse(false);
                            break;
                    }
                    break;
                case "产品陈列费用申请":
                    switch (orderStateName) {
                        case "全部":
                            queryAllDisplayCostList(false);
                            break;
                        case "审批中":
                            disPlayIng(false);
                            break;
                        case "待我审批":
                            disPlayWiteMe(false);
                            break;
                        case "交易完成":
                            disPlayDone(false);
                            break;
                        case "已拒绝":
                            disPlayRefuse(false);
                            break;
                    }
                    break;
                case "进场费用申请":
                    switch (orderStateName) {
                        case "全部":
                            queryAllJoinSitList(false);
                            break;
                        case "审批中":
                            joinIng(false);
                            break;
                        case "待我审批":
                            joinSiteWaite(false);
                            break;
                        case "交易完成":
                            joinSiteDone(false);
                            break;
                        case "已拒绝":
                            joinSiteRefuse(false);
                            break;
                    }
                    break;
                case "其他费用申请":
                    switch (orderStateName) {
                        case "全部":
                            queryAllOtherCostList(false);
                            break;
                        case "审批中":
                            otherIng(false);
                            break;
                        case "待我审批":
                            otherWaite(false);
                            break;
                        case "交易完成":
                            otherDone(false);
                            break;
                        case "已拒绝":
                            otherRefuse(false);
                            break;
                    }
                    break;

            }
        }


    }

    /**
     * 其他费用申请列表；
     */
    private void queryAllOtherCostList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_LIST + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostApplyListBean joinSiteCostListBean = mGson.fromJson(s, OtherCostApplyListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<OtherCostApplyListBean.DataBean> oterCostList = joinSiteCostListBean.getData();
                    if (oterCostList != null) {
                        if (mAllList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getApprovalStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = oterCostList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, costs, explains, id,
                                        Constant.OTHER_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleAllData(mAllList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 进场费用申请；
     */
    private void queryAllJoinSitList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteCostListBean joinSiteCostListBean = mGson.fromJson(s, JoinSiteCostListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<JoinSiteCostListBean.DataBean> joinList = joinSiteCostListBean.getData();
                    if (joinList != null) {
                        if (mAllList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getApprovalStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = joinList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleAllData(mAllList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 产品成列费用申请接口；
     */
    private void queryAllDisplayCostList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_COST_APPLY + "user.id=" + mUserId + "&pageNo="
                + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductPlayCostListBean productPlayCostListBean = mGson.fromJson(s, ProductPlayCostListBean.class);
                boolean success = productPlayCostListBean.isSuccess();
                if (success) {
                    ProductPlayCostListBean.DisplayAListBean displayAList = productPlayCostListBean.getDisplayAList();
                    List<ProductPlayCostListBean.DisplayAListBean.ListBean> displayAListList = displayAList.getList();
                    if (displayAListList != null) {
                        if (mAllList.size() == displayAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < displayAListList.size(); i++) {
                                String createDate = displayAListList.get(i).getCreateDate();
                                String ratio = displayAListList.get(i).getRatio();
                                String approvalStatus = displayAListList.get(i).getApprovalStatus();
                                String approvalType = displayAListList.get(i).getApprovalType();
                                double salesVolume = displayAListList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = displayAListList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = displayAListList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleAllData(mAllList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 促销申请 拒绝
     *
     * @param list
     */

    private void initSaleRefuseData(List<CommApplyListBean> list) {
        if (mRefuseAdapter == null) {
            mRefuseAdapter = new CommApplyListAdapter(getContext(), list);
            if (mPlrSaleStatePage != null) {
                mPlrSaleStatePage.setAdapter(mRefuseAdapter);
            }
        } else {
            mRefuseAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    /**
     * 促销申请 待我审批
     *
     * @param list
     */

    private void initSaleWaiteMeData(List<CommApplyListBean> list) {
        if (mWaiteMeAdapter == null) {
            mWaiteMeAdapter = new CommApplyListAdapter(getContext(), list);
            if (mPlrSaleStatePage != null) {
                mPlrSaleStatePage.setAdapter(mWaiteMeAdapter);
            }
        } else {
            mWaiteMeAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    /**
     * 促销申请 交易完成
     *
     * @param list
     */

    private void initSaleDoneData(List<CommApplyListBean> list) {
        if (mDoneAdapter == null) {
            mDoneAdapter = new CommApplyListAdapter(getContext(), list);
            if (mPlrSaleStatePage != null) {
                mPlrSaleStatePage.setAdapter(mDoneAdapter);
            }
        } else {
            mDoneAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    /**
     * 促销申请 审批中
     *
     * @param list
     */

    private void initSaleIngData(List<CommApplyListBean> list) {
        if (mIngAdapter == null) {
            mIngAdapter = new CommApplyListAdapter(getContext(), list);
            if (mPlrSaleStatePage != null) {
                mPlrSaleStatePage.setAdapter(mIngAdapter);
            }
        } else {
            mIngAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    /**
     * 促销申请 全部
     *
     * @param list
     */
    private void initSaleAllData(List<CommApplyListBean> list) {
        if (mAllAdapter == null) {
            mAllAdapter = new CommApplyListAdapter(getContext(), list);
            if (mPlrSaleStatePage != null) {
                mPlrSaleStatePage.setAdapter(mAllAdapter);
            }
        } else {
            mAllAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    private void noRefresh() {
        if (mPlrSaleStatePage != null) {
            mPlrSaleStatePage.onRefreshComplete();
        }
    }

    private void setEmpty() {
        if (mPlrSaleStatePage != null) {
            mPlrSaleStatePage.setEmptyView(mLlEmpty);
        }
    }

    /**
     * 获取活动申请列表；
     */
    private void queryAllSaleList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_APPLY_SALE_ACTIVITY + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleApplyListBean queryAllSaleApplyListBean = mGson.fromJson(s, QueryAllSaleApplyListBean.class);
                boolean success = queryAllSaleApplyListBean.isSuccess();
                if (success) {
                    QueryAllSaleApplyListBean.PromotionAListBean promotionAList = queryAllSaleApplyListBean.getPromotionAList();

                    List<QueryAllSaleApplyListBean.PromotionAListBean.ListBean> applyList = promotionAList.getList();
                    if (applyList != null) {
                        if (mAllList.size() == promotionAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < applyList.size(); i++) {
                                String createDate = applyList.get(i).getCreateDate();
                                String ratio = applyList.get(i).getRatio();
                                String approvalStatus = applyList.get(i).getApprovalStatus();
                                String approvalType = applyList.get(i).getApprovalType();
                                double salesVolume = applyList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = applyList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = applyList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleAllData(mAllList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                noRefresh();
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
    public void refreshApplyActivityList(CommRefreshListEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            mAllList.clear();
            mAllAdapter = null;
            switch (mJoinWays) {
                case "促销活动申请":
                    queryAllSaleList(true);
                    break;
                case "产品陈列费用申请":
                    queryAllDisplayCostList(true);
                    break;
                case "进场费用申请":
                    queryAllJoinSitList(true);
                    break;
                case "其他费用申请":
                    queryAllOtherCostList(true);
                    break;
            }
        }
    }

    /**
     * 得到标题类型；
     *
     * @param titleName
     */
    @Subscribe
    public void getTitleType(String titleName) {
        orderStateName = titleName;
        switch (mJoinWays) {
            case "促销活动申请":
                switch (titleName) {
                    case "全部":
                        mAllList.clear();
                        mAllAdapter = null;
                        queryAllSaleList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        saleIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        saleQueryWaitMe(true);
                        break;
                    case "交易完成":
                        mDoneList.clear();
                        mDoneAdapter = null;
                        saleDone(true);
                        break;
                    case "已拒绝":
                        mRefuseList.clear();
                        mRefuseAdapter = null;
                        saleQuerybyRefuse(true);
                        break;
                }
                break;
            case "产品陈列费用申请":
                switch (titleName) {
                    case "全部":
                        mAllList.clear();
                        mAllAdapter = null;
                        queryAllDisplayCostList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        disPlayIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        disPlayWiteMe(true);
                        break;
                    case "交易完成":
                        mDoneList.clear();
                        mDoneAdapter = null;
                        disPlayDone(true);
                        break;
                    case "已拒绝":
                        mRefuseList.clear();
                        mRefuseAdapter = null;
                        disPlayRefuse(true);
                        break;
                }
                break;
            case "进场费用申请":
                switch (titleName) {
                    case "全部":
                        mAllList.clear();
                        mAllAdapter = null;
                        queryAllJoinSitList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        joinIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        joinSiteWaite(true);
                        break;
                    case "交易完成":
                        mDoneList.clear();
                        mDoneAdapter = null;
                        joinSiteDone(true);
                        break;
                    case "已拒绝":
                        mRefuseList.clear();
                        mRefuseAdapter = null;
                        joinSiteRefuse(true);
                        break;
                }

                break;
            case "其他费用申请":
                switch (titleName) {
                    case "全部":
                        mAllList.clear();
                        mAllAdapter = null;
                        queryAllOtherCostList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        otherIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        otherWaite(true);
                        break;
                    case "交易完成":
                        mDoneList.clear();
                        mDoneAdapter = null;
                        otherDone(true);
                        break;
                    case "已拒绝":
                        mRefuseList.clear();
                        mRefuseAdapter = null;
                        otherRefuse(true);
                        break;
                }
                break;
            default:
                break;
        }
    }

    /***
     * 进场  拒绝操作；
     */
    private void joinSiteRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteCostListBean joinSiteCostListBean = mGson.fromJson(s, JoinSiteCostListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<JoinSiteCostListBean.DataBean> joinList = joinSiteCostListBean.getData();
                    if (joinList != null) {
                        if (mRefuseList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getApprovalStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = joinList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mRefuseList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleRefuseData(mRefuseList);

                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 其他费用，经销商拒绝状态的接口；
     */
    private void otherRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_LIST + "user.id=" + mUserId + "&pageNo=" + mRefuseList + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostApplyListBean joinSiteCostListBean = mGson.fromJson(s, OtherCostApplyListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {

                    List<OtherCostApplyListBean.DataBean> oterCostList = joinSiteCostListBean.getData();
                    if (oterCostList != null) {
                        if (mRefuseList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getApprovalStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = oterCostList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, costs, explains, id,
                                        Constant.OTHER_APPLY);
                                mRefuseList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleRefuseData(mRefuseList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 产品成列活动  拒绝状态的接口；
     */
    private void disPlayRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductPlayCostListBean productPlayCostListBean = mGson.fromJson(s, ProductPlayCostListBean.class);
                boolean success = productPlayCostListBean.isSuccess();
                if (success) {
                    ProductPlayCostListBean.DisplayAListBean displayAList = productPlayCostListBean.getDisplayAList();
                    List<ProductPlayCostListBean.DisplayAListBean.ListBean> displayAListList = displayAList.getList();
                    if (displayAListList != null) {

                        if (mRefuseList.size() == displayAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < displayAListList.size(); i++) {
                                String createDate = displayAListList.get(i).getCreateDate();
                                String ratio = displayAListList.get(i).getRatio();
                                String approvalStatus = displayAListList.get(i).getApprovalStatus();
                                String approvalType = displayAListList.get(i).getApprovalType();
                                double salesVolume = displayAListList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = displayAListList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = displayAListList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mRefuseList.add(commApplyListBean);
                            }
                        }
                        initSaleRefuseData(mRefuseList);
                    }
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 促销活动 拒绝状态
     */
    private void saleQuerybyRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_APPLY_SALE_ACTIVITY + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleApplyListBean queryAllSaleApplyListBean = mGson.fromJson(s, QueryAllSaleApplyListBean.class);
                boolean success = queryAllSaleApplyListBean.isSuccess();
                if (success) {
                    QueryAllSaleApplyListBean.PromotionAListBean promotionAList = queryAllSaleApplyListBean.getPromotionAList();
                    List<QueryAllSaleApplyListBean.PromotionAListBean.ListBean> applyList = promotionAList.getList();
                    if (applyList != null) {
                        if (mRefuseList.size() == promotionAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < applyList.size(); i++) {
                                String createDate = applyList.get(i).getCreateDate();
                                String ratio = applyList.get(i).getRatio();
                                String approvalStatus = applyList.get(i).getApprovalStatus();
                                String approvalType = applyList.get(i).getApprovalType();
                                double salesVolume = applyList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = applyList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = applyList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                mRefuseList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleRefuseData(mRefuseList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 其他费用 非拒绝状态
     */
    private void
    otherWaite(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_LIST + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostApplyListBean joinSiteCostListBean = mGson.fromJson(s, OtherCostApplyListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<OtherCostApplyListBean.DataBean> oterCostList = joinSiteCostListBean.getData();
                    if (oterCostList != null) {
                        if (mWaiteMeList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }

                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getApprovalStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = oterCostList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, costs, explains, id,
                                        Constant.OTHER_APPLY);
                                mWaiteMeList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleWaiteMeData(mWaiteMeList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 进场活动 ，待我审批
     */
    private void joinSiteWaite(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteCostListBean joinSiteCostListBean = mGson.fromJson(s, JoinSiteCostListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<JoinSiteCostListBean.DataBean> joinList = joinSiteCostListBean.getData();
                    if (joinList != null) {
                        if (mWaiteMeList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getApprovalStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = joinList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mWaiteMeList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleWaiteMeData(mWaiteMeList);

                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 产品成列活动 ，非经销商 非拒绝状态
     */
    private void disPlayWiteMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductPlayCostListBean productPlayCostListBean = mGson.fromJson(s, ProductPlayCostListBean.class);
                boolean success = productPlayCostListBean.isSuccess();
                if (success) {
                    ProductPlayCostListBean.DisplayAListBean displayAList = productPlayCostListBean.getDisplayAList();
                    List<ProductPlayCostListBean.DisplayAListBean.ListBean> displayAListList = displayAList.getList();
                    if (displayAListList != null) {
                        if (mWaiteMeList.size() == displayAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < displayAListList.size(); i++) {
                                String createDate = displayAListList.get(i).getCreateDate();
                                String ratio = displayAListList.get(i).getRatio();
                                String approvalStatus = displayAListList.get(i).getApprovalStatus();
                                String approvalType = displayAListList.get(i).getApprovalType();
                                double salesVolume = displayAListList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = displayAListList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = displayAListList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mWaiteMeList.add(commApplyListBean);
                            }
                        }
                        initSaleWaiteMeData(mWaiteMeList);
                    }
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }

    /**
     * 促销活动 待我审批状态；
     */
    private void saleQueryWaitMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_APPLY_SALE_ACTIVITY + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleApplyListBean queryAllSaleApplyListBean = mGson.fromJson(s, QueryAllSaleApplyListBean.class);
                boolean success = queryAllSaleApplyListBean.isSuccess();
                if (success) {
                    QueryAllSaleApplyListBean.PromotionAListBean promotionAList = queryAllSaleApplyListBean.getPromotionAList();
                    List<QueryAllSaleApplyListBean.PromotionAListBean.ListBean> applyList = promotionAList.getList();
                    if (applyList != null) {
                        if (mWaiteMeList.size() == promotionAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < applyList.size(); i++) {
                                String createDate = applyList.get(i).getCreateDate();
                                String ratio = applyList.get(i).getRatio();
                                String approvalStatus = applyList.get(i).getApprovalStatus();
                                String approvalType = applyList.get(i).getApprovalType();
                                double salesVolume = applyList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = applyList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = applyList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                mWaiteMeList.add(commApplyListBean);
                            }

                        }
                    }
                    initSaleWaiteMeData(mWaiteMeList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 其他， 审批中；
     */
    private void otherIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_LIST + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostApplyListBean joinSiteCostListBean = mGson.fromJson(s, OtherCostApplyListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<OtherCostApplyListBean.DataBean> oterCostList = joinSiteCostListBean.getData();
                    if (oterCostList != null) {
                        if (mIngList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }

                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getApprovalStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = oterCostList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, costs, explains, id,
                                        Constant.OTHER_APPLY);
                                mIngList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleIngData(mIngList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 其他， 查询所有的订单除了拒绝之外
     */
    private void otherDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_APPLY_LIST + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostApplyListBean joinSiteCostListBean = mGson.fromJson(s, OtherCostApplyListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {

                    List<OtherCostApplyListBean.DataBean> oterCostList = joinSiteCostListBean.getData();
                    if (oterCostList != null) {
                        if (mDoneList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getApprovalStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = oterCostList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, costs, explains, id,
                                        Constant.OTHER_APPLY);
                                mDoneList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleDoneData(mDoneList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 进场活动， 审批中；
     */
    private void joinIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteCostListBean joinSiteCostListBean = mGson.fromJson(s, JoinSiteCostListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<JoinSiteCostListBean.DataBean> joinList = joinSiteCostListBean.getData();

                    if (joinList != null) {
                        if (mIngList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getApprovalStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = joinList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mIngList.add(commApplyListBean);
                            }
                        }

                    }
                    initSaleIngData(mIngList);

                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 进场活动， 查询所有的订单除了 拒绝之外
     */
    private void joinSiteDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteCostListBean joinSiteCostListBean = mGson.fromJson(s, JoinSiteCostListBean.class);
                boolean success = joinSiteCostListBean.isSuccess();
                if (success) {
                    List<JoinSiteCostListBean.DataBean> joinList = joinSiteCostListBean.getData();
                    if (joinList != null) {
                        if (mDoneList.size() == joinSiteCostListBean.getSize()) {
                            if (joinSiteCostListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getApprovalStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = joinList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mDoneList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleDoneData(mDoneList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 产品陈列活动，审批中
     */
    private void disPlayIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductPlayCostListBean productPlayCostListBean = mGson.fromJson(s, ProductPlayCostListBean.class);
                boolean success = productPlayCostListBean.isSuccess();
                if (success) {
                    ProductPlayCostListBean.DisplayAListBean displayAList = productPlayCostListBean.getDisplayAList();
                    List<ProductPlayCostListBean.DisplayAListBean.ListBean> displayAListList = displayAList.getList();
                    if (displayAListList != null) {
                        if (mIngList.size() == displayAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < displayAListList.size(); i++) {
                                String createDate = displayAListList.get(i).getCreateDate();
                                String ratio = displayAListList.get(i).getRatio();
                                String approvalStatus = displayAListList.get(i).getApprovalStatus();
                                String approvalType = displayAListList.get(i).getApprovalType();
                                double salesVolume = displayAListList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = displayAListList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = displayAListList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mIngList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleIngData(mIngList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 产品陈列活动，
     */
    private void disPlayDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.PRODUCT_DISPLAY_COST_APPLY + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductPlayCostListBean productPlayCostListBean = mGson.fromJson(s, ProductPlayCostListBean.class);
                boolean success = productPlayCostListBean.isSuccess();
                if (success) {
                    ProductPlayCostListBean.DisplayAListBean displayAList = productPlayCostListBean.getDisplayAList();
                    List<ProductPlayCostListBean.DisplayAListBean.ListBean> displayAListList = displayAList.getList();
                    if (displayAListList != null) {
                        if (mDoneList.size() == displayAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < displayAListList.size(); i++) {
                                String createDate = displayAListList.get(i).getCreateDate();
                                String ratio = displayAListList.get(i).getRatio();
                                String approvalStatus = displayAListList.get(i).getApprovalStatus();
                                String approvalType = displayAListList.get(i).getApprovalType();
                                double salesVolume = displayAListList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = displayAListList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = displayAListList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mDoneList.add(commApplyListBean);
                            }
                        }
                    }
                        initSaleDoneData(mDoneList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 促销活动， 查询所有的订单除了拒绝之外；
     */
    private void saleIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_APPLY_SALE_ACTIVITY + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleApplyListBean queryAllSaleApplyListBean = mGson.fromJson(s, QueryAllSaleApplyListBean.class);
                boolean success = queryAllSaleApplyListBean.isSuccess();
                if (success) {
                    QueryAllSaleApplyListBean.PromotionAListBean promotionAList = queryAllSaleApplyListBean.getPromotionAList();
                    if (promotionAList != null) {
                        List<QueryAllSaleApplyListBean.PromotionAListBean.ListBean> applyList = promotionAList.getList();
                        if (applyList != null) {
                            if (mIngList.size() == promotionAList.getCount()) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {

                                for (int i = 0; i < applyList.size(); i++) {
                                    String createDate = applyList.get(i).getCreateDate();
                                    String ratio = applyList.get(i).getRatio();
                                    String approvalStatus = applyList.get(i).getApprovalStatus();
                                    String approvalType = applyList.get(i).getApprovalType();
                                    double salesVolume = applyList.get(i).getSalesVolume();
                                    String saleVoluemeStr = String.valueOf(salesVolume);
                                    String name = applyList.get(i).getName();
                                    String approveState = Util.getApproveState(approvalStatus, approvalType);
                                    String id = applyList.get(i).getId();
                                    CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                    mIngList.add(commApplyListBean);
                                }
                            }
                        }
                        initSaleIngData(mIngList);
                    }
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 促销活动，交易完成
     */
    private void saleDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_APPLY_SALE_ACTIVITY + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleApplyListBean queryAllSaleApplyListBean = mGson.fromJson(s, QueryAllSaleApplyListBean.class);
                boolean success = queryAllSaleApplyListBean.isSuccess();
                if (success) {
                    QueryAllSaleApplyListBean.PromotionAListBean promotionAList = queryAllSaleApplyListBean.getPromotionAList();
                    List<QueryAllSaleApplyListBean.PromotionAListBean.ListBean> applyList = promotionAList.getList();
                    if (applyList != null) {
                        if (mDoneList.size() == promotionAList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {

                            for (int i = 0; i < applyList.size(); i++) {
                                String createDate = applyList.get(i).getCreateDate();
                                String ratio = applyList.get(i).getRatio();
                                String approvalStatus = applyList.get(i).getApprovalStatus();
                                String approvalType = applyList.get(i).getApprovalType();
                                double salesVolume = applyList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = applyList.get(i).getName();
                                String approveState = Util.getApproveState(approvalStatus, approvalType);
                                String id = applyList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                mDoneList.add(commApplyListBean);
                            }
                        }
                    }
                    initSaleDoneData(mDoneList);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
                noRefresh();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                noRefresh();
                dismissLoading();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
        EventBus.getDefault().unregister(this);
    }

    private void initItem(final List<CommApplyListBean> list) {
        final Intent intent = new Intent(getContext(), ContentActivity.class);
        if (mPlrSaleStatePage != null) {
            mPlrSaleStatePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (mJoinWays) {
                        case "促销活动申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.APPLY_FOR_ACTIVITY_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            String approvalStatus = list.get(i - 1).getApprovalStatus();
                            if ("待提交".equals(approvalStatus) || "待审批".equals(approvalStatus)) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                        case "产品陈列费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.DISPLAY_APPLY_DETAIL_MARK);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待提交".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                        case "进场费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.JOINSITE_APPLY_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待提交".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }

                            startActivity(intent);
                            break;
                        case "其他费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.OTHER_COST_SUBMIT_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待提交".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                    }
                }
            });

        }
    }
}