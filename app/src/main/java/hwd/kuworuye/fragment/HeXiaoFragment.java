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
import hwd.kuworuye.bean.DisplayHeXiaoListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.JoinSiteHeXiaoBean;
import hwd.kuworuye.bean.OtherCostHexiaoListBean;
import hwd.kuworuye.bean.QueryAllSaleHeXiaoListBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class HeXiaoFragment extends FBaseFragment {

    @BindView(R.id.plr_xioahe)
    PullToRefreshListView mPlrSaleStatePage;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private String mUserId;
    private String mWhichActivity;
    private int mPageSize = 10;
    private Bundle mArguments;
    private String orderStateName = "全部";
    private Gson mGson;

    private int mAllPageNo = 1;
    private int mIngPageNo = 1;
    private int mDonePageNo = 1;
    private int mRefusePageNo = 1;
    private int mWaiteMePageNo = 1;

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
        return R.layout.fragment_xiaohe;
    }

    public static Fragment newInstance(Bundle bundle) {
        HeXiaoFragment orderSortFragment = new HeXiaoFragment();
        orderSortFragment.setArguments(bundle);
        return orderSortFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initListener();
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mArguments = getArguments();
        mGson = new Gson();
        if (mArguments != null) {
            mWhichActivity = mArguments.getString(Constant.DISTINCTION_WHICH_ACTIVITIY);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAllList.clear();
        mAllAdapter = null;
        switch (mWhichActivity) {
            case "促销活动申请":
                queryAllSaleList(true);
                break;
            case "产品陈列费用申请":
                queryAllDisplayList(true);
                break;
            case "进场费用申请":
                queryAllJoinSiteList(true);
                break;
            case "其他费用申请":
                queryAllOtherCostList(true);
                break;
        }
    }

    /**
     * 根据不同状态向上或向下刷新
     */
    private void refreshByDownOrUp() {
        switch (mWhichActivity) {
            case "促销活动申请":
                switch (orderStateName) {
                    case "全部":
                        queryAllSaleList(false);
                        break;
                    case "审批中":
                        saleIng(false);
                        break;
                    case "待我审批":
                        saleWaitMe(false);
                        break;
                    case "交易完成":
                        saleDone(false);
                        break;
                    case "已拒绝":
                        saleRefuse(false);
                        break;
                }
                break;
            case "产品陈列费用申请":
                switch (orderStateName) {
                    case "全部":
                        queryAllDisplayList(false);
                        break;
                    case "审批中":
                        disPlayIng(false);
                        break;
                    case "待我审批":
                        disPlayWaitMe(false);
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
                        queryAllJoinSiteList(false);
                        break;
                    case "审批中":
                        joinIng(false);
                        break;
                    case "待我审批":
                        joinSiteWaiteMe(false);
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
                        queryAllOtherCostList(true);
                        break;
                    case "审批中":
                        otherIng(true);
                        break;
                    case "待我审批":
                        otherWaitMe(true);
                        break;
                    case "交易完成":
                        otherDone(true);
                        break;
                    case "已拒绝":
                        otherRefuse(true);
                        break;
                }
                break;

        }
    }

    private void initListener() {
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

                refreshByDownOrUp();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mAllPageNo++;
                mIngPageNo++;
                mDonePageNo++;
                mRefusePageNo++;
                mWaiteMePageNo++;

                refreshByDownOrUp();
            }
        });
    }

    /**
     * 得到标题类型；
     *
     * @param titleName
     */
    @Subscribe
    public void getTitleType(String titleName) {
        orderStateName = titleName;

        switch (mWhichActivity) {
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
                        saleWaitMe(true);
                        break;
                    case "交易完成":
                        mDoneList.clear();
                        mDoneAdapter = null;
                        saleDone(true);
                        break;
                    case "已拒绝":
                        mRefuseList.clear();
                        mRefuseAdapter = null;
                        saleRefuse(true);
                        break;
                }
                break;
            case "产品陈列费用申请":
                switch (titleName) {
                    case "全部":
                        mAllList.clear();
                        mAllAdapter = null;
                        queryAllDisplayList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        disPlayIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        disPlayWaitMe(true);
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
                        queryAllJoinSiteList(true);
                        break;
                    case "审批中":
                        mIngList.clear();
                        mIngAdapter = null;
                        joinIng(true);
                        break;
                    case "待我审批":
                        mWaiteMeList.clear();
                        mWaiteMeAdapter = null;
                        joinSiteWaiteMe(true);
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
                        otherWaitMe(true);
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

        }
    }


    /**
     * 刷新列表；
     *
     * @param event
     */
  /*  @Subscribe
    public void refreshHeXiaoList(CommRefreshListEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            switch (mWhichActivity) {
                case "促销活动申请":
                    queryAllSaleList(true);
                    break;
                case "产品陈列费用申请":
                    queryAllDisplayList(true);
                    break;
                case "进场费用申请":
                    queryAllJoinSiteList(true);
                    break;
                case "其他费用申请":
                    queryAllOtherCostList(true);
                    break;
            }
        }
    }*/


    /***
     * 拒绝操作；
     */

    private void disPlayRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_DISPLAY_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoListBean displayHeXiaoListBean = mGson.fromJson(s, DisplayHeXiaoListBean.class);
                if (displayHeXiaoListBean.isSuccess()) {
                    DisplayHeXiaoListBean.DisplayEListBean displayEList = displayHeXiaoListBean.getDisplayEList();
                    List<DisplayHeXiaoListBean.DisplayEListBean.ListBean> heXiaoList = displayEList.getList();
                    if (heXiaoList != null) {

                        if (mRefuseList.size() == displayEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
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
     * 其他  经销商拒绝状态的接口；
     */
    private void otherRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHexiaoListBean otherCostHexiaoListBean = mGson.fromJson(s, OtherCostHexiaoListBean.class);
                if (otherCostHexiaoListBean.isSuccess()) {
                    List<OtherCostHexiaoListBean.DataBean> data = otherCostHexiaoListBean.getData();
                    if (data != null) {
                        if (mRefuseList.size() == otherCostHexiaoListBean.getSize()) {
                            if (otherCostHexiaoListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < data.size(); i++) {
                                String createDate = data.get(i).getCreateDate();
                                String costs = data.get(i).getCosts();
                                String approvalStatus = data.get(i).getExamineStatus();
                                String approvalType = data.get(i).getApprovalType();

                                String explains = data.get(i).getExplains();
                                String name = data.get(i).getTitle();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = data.get(i).getId();
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 进场 拒绝状态的接口；
     */
    private void joinSiteRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHeXiaoBean joinSiteHeXiaoBean = mGson.fromJson(s, JoinSiteHeXiaoBean.class);
                if (joinSiteHeXiaoBean.isSuccess()) {
                    List<JoinSiteHeXiaoBean.DataBean> joinList = joinSiteHeXiaoBean.getData();
                    if (joinList != null) {
                        if (mRefuseList.size() == joinSiteHeXiaoBean.getSize()) {
                            if (joinSiteHeXiaoBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getExamineStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
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
                noRefresh();
                dismissLoading();
            }
        });
    }

    /**
     * 促销活动 拒绝状态的接口；
     */
    private void saleRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_SALE_XIAOHE_LIST + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleHeXiaoListBean xiaoheListBean = mGson.fromJson(s, QueryAllSaleHeXiaoListBean.class);
                boolean success = xiaoheListBean.isSuccess();
                if (success) {
                    QueryAllSaleHeXiaoListBean.PromotionEListBean promotionEList = xiaoheListBean.getPromotionEList();
                    List<QueryAllSaleHeXiaoListBean.PromotionEListBean.ListBean> heXiaoList = promotionEList.getList();
                    if (heXiaoList != null) {
                        if (mRefuseList.size() == promotionEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            if (mPlrSaleStatePage != null) {
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                dismissLoading();
                noRefresh();
            }
        });
    }


    /**
     * 全部
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


    private void initItem(final List<CommApplyListBean> list) {
        if (mPlrSaleStatePage != null) {
            mPlrSaleStatePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    switch (mWhichActivity) {
                        case "促销活动申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.ACTIVITY_HEXIAO_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待核销".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                        case "进场费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.JOIN_SITE_HEXIAO_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待核销".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                        case "产品陈列费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.DISPLAY_HEXIAO_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待核销".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                            } else {
                                intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                            }
                            startActivity(intent);
                            break;
                        case "其他费用申请":
                            intent.putExtra(Constant.CONTENT_TYPE, Constant.OTHER_COST_HEXIAO_DETAIL);
                            intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                            if ("待核销".equals(list.get(i - 1).getApprovalStatus()) || "待审批".equals(list.get(i - 1).getApprovalStatus())) {
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

    /**
     * 其他 非拒绝状态 待我审批；
     */
    private void otherWaitMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHexiaoListBean otherCostHexiaoListBean = mGson.fromJson(s, OtherCostHexiaoListBean.class);
                if (otherCostHexiaoListBean.isSuccess()) {
                    List<OtherCostHexiaoListBean.DataBean> oterCostList = otherCostHexiaoListBean.getData();
                    if (oterCostList != null) {
                        if (mWaiteMeList.size() == otherCostHexiaoListBean.getSize()) {
                            if (otherCostHexiaoListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getExamineStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();
                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 进场 待我审批；
     */
    private void joinSiteWaiteMe(boolean isShowLoading) {
        if (isShowLoading) {

        }
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHeXiaoBean joinSiteHeXiaoBean = mGson.fromJson(s, JoinSiteHeXiaoBean.class);
                if (joinSiteHeXiaoBean.isSuccess()) {
                    List<JoinSiteHeXiaoBean.DataBean> joinList = joinSiteHeXiaoBean.getData();
                    if (joinList != null) {
                        if (mWaiteMeList.size() == joinSiteHeXiaoBean.getSize()) {
                            if (joinSiteHeXiaoBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getExamineStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 产品成列 ，待我审批；
     */
    private void disPlayWaitMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }

        OkHttpUtils.post(HttpConstant.QUERY_DISPLAY_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoListBean displayHeXiaoListBean = mGson.fromJson(s, DisplayHeXiaoListBean.class);
                if (displayHeXiaoListBean.isSuccess()) {
                    DisplayHeXiaoListBean.DisplayEListBean displayEList = displayHeXiaoListBean.getDisplayEList();
                    List<DisplayHeXiaoListBean.DisplayEListBean.ListBean> heXiaoList = displayEList.getList();
                    if (heXiaoList != null) {
                        if (mWaiteMeList.size() == displayEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * 待我审批；
     */
    private void saleWaitMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_SALE_XIAOHE_LIST + "user.id=" + mUserId + "&pageNo=" + mWaiteMePageNo + "&pageSize=" + mPageSize + "&approverUser=" + mUserId + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleHeXiaoListBean xiaoheListBean = mGson.fromJson(s, QueryAllSaleHeXiaoListBean.class);
                boolean success = xiaoheListBean.isSuccess();
                if (success) {
                    QueryAllSaleHeXiaoListBean.PromotionEListBean promotionEList = xiaoheListBean.getPromotionEList();
                    List<QueryAllSaleHeXiaoListBean.PromotionEListBean.ListBean> heXiaoList = promotionEList.getList();
                    if (heXiaoList != null) {
                        if (mWaiteMeList.size() == promotionEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                dismissLoading();
                noRefresh();
            }
        });
    }

    /**
     * ， 进场费用 完成；
     */
    private void otherDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHexiaoListBean otherCostHexiaoListBean = mGson.fromJson(s, OtherCostHexiaoListBean.class);
                if (otherCostHexiaoListBean.isSuccess()) {
                    List<OtherCostHexiaoListBean.DataBean> oterCostList = otherCostHexiaoListBean.getData();
                    if (oterCostList != null) {
                        if (mDoneList.size() == otherCostHexiaoListBean.getSize()) {
                            if (otherCostHexiaoListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < oterCostList.size(); i++) {
                                String createDate = oterCostList.get(i).getCreateDate();
                                String costs = oterCostList.get(i).getCosts();
                                String approvalStatus = oterCostList.get(i).getExamineStatus();
                                String approvalType = oterCostList.get(i).getApprovalType();

                                String explains = oterCostList.get(i).getExplains();
                                String name = oterCostList.get(i).getTitle();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
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
                noRefresh();
                dismissLoading();
            }
        });
    }

    /**
     * ， 其他费用 审批中；
     */
    private void otherIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4,5" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHexiaoListBean otherCostHexiaoListBean = mGson.fromJson(s, OtherCostHexiaoListBean.class);
                if (otherCostHexiaoListBean.isSuccess()) {
                    List<OtherCostHexiaoListBean.DataBean> heXiaoList = otherCostHexiaoListBean.getData();
                    if (heXiaoList != null) {
                        if (mIngList.size() == otherCostHexiaoListBean.getSize()) {
                            if (otherCostHexiaoListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getCosts();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                String explains = heXiaoList.get(i).getExplains();
                                String name = heXiaoList.get(i).getTitle();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, explains, id, Constant.OTHER_APPLY);
                                mIngList.add(commApplyListBean);
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
     * ， 进场费用 审批中；
     */
    private void joinIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4,5" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHeXiaoBean joinSiteHeXiaoBean = mGson.fromJson(s, JoinSiteHeXiaoBean.class);
                if (joinSiteHeXiaoBean.isSuccess()) {
                    List<JoinSiteHeXiaoBean.DataBean> heXiaoList = joinSiteHeXiaoBean.getData();
                    if (heXiaoList != null) {
                        if (mIngList.size() == joinSiteHeXiaoBean.getSize()) {
                            if (joinSiteHeXiaoBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                double totalExpenses = heXiaoList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * ， 进场费用 非拒绝
     */
    private void joinSiteDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHeXiaoBean joinSiteHeXiaoBean = mGson.fromJson(s, JoinSiteHeXiaoBean.class);
                if (joinSiteHeXiaoBean.isSuccess()) {
                    List<JoinSiteHeXiaoBean.DataBean> joinList = joinSiteHeXiaoBean.getData();
                    if (joinList != null) {
                        if (mDoneList.size() == joinSiteHeXiaoBean.getSize()) {
                            if (joinSiteHeXiaoBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }

                        } else {
                            for (int i = 0; i < joinList.size(); i++) {
                                String createDate = joinList.get(i).getCreateDate();
                                double totalExpenses = joinList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = joinList.get(i).getExamineStatus();
                                String approvalType = joinList.get(i).getApprovalType();

                                double salesVolume = joinList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = joinList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * ， 审批中；
     */
    private void disPlayIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_DISPLAY_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4,5" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoListBean displayHeXiaoListBean = mGson.fromJson(s, DisplayHeXiaoListBean.class);
                if (displayHeXiaoListBean.isSuccess()) {
                    DisplayHeXiaoListBean.DisplayEListBean displayEList = displayHeXiaoListBean.getDisplayEList();
                    List<DisplayHeXiaoListBean.DisplayEListBean.ListBean> heXiaoList = displayEList.getList();
                    if (heXiaoList != null) {
                        if (mIngList.size() == displayEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                noRefresh();
                dismissLoading();
            }
        });
    }


    /**
     * ，
     * 完成；
     */
    private void disPlayDone(boolean iShowLoading) {
        if (iShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_DISPLAY_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoListBean displayHeXiaoListBean = mGson.fromJson(s, DisplayHeXiaoListBean.class);
                if (displayHeXiaoListBean.isSuccess()) {
                    DisplayHeXiaoListBean.DisplayEListBean displayEList = displayHeXiaoListBean.getDisplayEList();
                    List<DisplayHeXiaoListBean.DisplayEListBean.ListBean> heXiaoList = displayEList.getList();
                    if (heXiaoList != null) {
                        if (mDoneList.size() == displayEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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


    /**
     * 审核中；
     */
    private void saleIng(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_SALE_XIAOHE_LIST + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "2,3,4,5" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleHeXiaoListBean xiaoheListBean = mGson.fromJson(s, QueryAllSaleHeXiaoListBean.class);
                boolean success = xiaoheListBean.isSuccess();
                if (success) {
                    QueryAllSaleHeXiaoListBean.PromotionEListBean promotionEList = xiaoheListBean.getPromotionEList();
                    List<QueryAllSaleHeXiaoListBean.PromotionEListBean.ListBean> heXiaoList = promotionEList.getList();
                    if (heXiaoList != null) {
                        if (mIngList.size() == promotionEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
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
     * ， 完成；
     */
    private void saleDone(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_SALE_XIAOHE_LIST + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "6" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleHeXiaoListBean xiaoheListBean = mGson.fromJson(s, QueryAllSaleHeXiaoListBean.class);
                boolean success = xiaoheListBean.isSuccess();
                if (success) {
                    QueryAllSaleHeXiaoListBean.PromotionEListBean promotionEList = xiaoheListBean.getPromotionEList();
                    List<QueryAllSaleHeXiaoListBean.PromotionEListBean.ListBean> heXiaoList = promotionEList.getList();
                    if (heXiaoList != null) {

                        if (mDoneList.size() == promotionEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
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
                dismissLoading();
                noRefresh();
            }
        });
    }

    /**
     * 产品成列核销接口
     */
    private void queryAllDisplayList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_DISPLAY_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoListBean displayHeXiaoListBean = mGson.fromJson(s, DisplayHeXiaoListBean.class);
                if (displayHeXiaoListBean.isSuccess()) {
                    DisplayHeXiaoListBean.DisplayEListBean displayEList = displayHeXiaoListBean.getDisplayEList();
                    List<DisplayHeXiaoListBean.DisplayEListBean.ListBean> heXiaoList = displayEList.getList();
                    if (heXiaoList != null) {
                        if (mAllList.size() == displayEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                String ratio = heXiaoList.get(i).getRatio();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                double salesVolume = heXiaoList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.DISPLAY_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                        initSaleAllData(mAllList);
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
     * 其他费用核销列表；
     */
    private void queryAllOtherCostList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHexiaoListBean otherCostHexiaoListBean = mGson.fromJson(s, OtherCostHexiaoListBean.class);
                if (otherCostHexiaoListBean.isSuccess()) {
                    List<OtherCostHexiaoListBean.DataBean> heXiaoList = otherCostHexiaoListBean.getData();
                    if (heXiaoList != null) {
                        if (mAllList.size() == otherCostHexiaoListBean.getSize()) {
                            if (otherCostHexiaoListBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
//                                String ratio = heXiaoList.get(i).getExplains();
                                String ratio = heXiaoList.get(i).getCosts();
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();
                                String explains = heXiaoList.get(i).getExplains();
                                String name = heXiaoList.get(i).getTitle();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, explains, id, Constant.OTHER_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }

                        initSaleAllData(mAllList);

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
     * 获取进场费用消核列表
     */
    private void queryAllJoinSiteList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_HEXIAO_LIST + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHeXiaoBean joinSiteHeXiaoBean = mGson.fromJson(s, JoinSiteHeXiaoBean.class);
                if (joinSiteHeXiaoBean.isSuccess()) {
                    List<JoinSiteHeXiaoBean.DataBean> heXiaoList = joinSiteHeXiaoBean.getData();
                    if (heXiaoList != null) {
                        if (mAllList.size() == joinSiteHeXiaoBean.getSize()) {
                            if (joinSiteHeXiaoBean.getSize() != 0) {
                                Util.noMoreDataToast(getContext());
                                noRefresh();
                                mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                        } else {
                            for (int i = 0; i < heXiaoList.size(); i++) {
                                String createDate = heXiaoList.get(i).getCreateDate();
                                double totalExpenses = heXiaoList.get(i).getTotalExpenses();
                                String totalExpensesStr = String.valueOf(totalExpenses);
                                String approvalStatus = heXiaoList.get(i).getExamineStatus();
                                String approvalType = heXiaoList.get(i).getApprovalType();

                                double salesVolume = heXiaoList.get(i).getAnnualSales();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = heXiaoList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = heXiaoList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, totalExpensesStr, saleVoluemeStr, id, Constant.JOINSITE_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }

                        initSaleAllData(mAllList);

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
     * 获取促销活动消核列表；
     */
    private void queryAllSaleList(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_SALE_XIAOHE_LIST + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllSaleHeXiaoListBean xiaoheListBean = mGson.fromJson(s, QueryAllSaleHeXiaoListBean.class);
                boolean success = xiaoheListBean.isSuccess();
                if (success) {
                    QueryAllSaleHeXiaoListBean.PromotionEListBean promotionEList = xiaoheListBean.getPromotionEList();
                    List<QueryAllSaleHeXiaoListBean.PromotionEListBean.ListBean> applyList = promotionEList.getList();
                    if (applyList != null) {
                        if (mAllList.size() == promotionEList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSaleStatePage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            for (int i = 0; i < applyList.size(); i++) {
                                String createDate = applyList.get(i).getCreateDate();
                                String ratio = applyList.get(i).getRatio();
                                String approvalStatus = applyList.get(i).getExamineStatus();
                                String approvalType = applyList.get(i).getApprovalType();
                                double salesVolume = applyList.get(i).getSalesVolume();
                                String saleVoluemeStr = String.valueOf(salesVolume);
                                String name = applyList.get(i).getName();
                                String approveState = Util.getHxApproveState(approvalStatus, approvalType);
                                String id = applyList.get(i).getId();
                                CommApplyListBean commApplyListBean = new CommApplyListBean(createDate, approveState, name, ratio, saleVoluemeStr, id, Constant.SALE_APPLY);
                                mAllList.add(commApplyListBean);
                            }
                        }
                        initSaleAllData(mAllList);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
