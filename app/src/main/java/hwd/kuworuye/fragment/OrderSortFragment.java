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
import hwd.kuworuye.adapter.OrderSortAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.QueryAllOrderBean;
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
public class OrderSortFragment extends FBaseFragment {
    @BindView(R.id.plr_sort_page)
    PullToRefreshListView mPlrSortPage;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private String mUserId;
    private int mAllPageNo = 1;
    private int mIngPageNo = 1;
    private int mDonePageNo = 1;
    private int mWaitePageNo = 1;
    private int mRefusePageNo = 1;
    private int mPageSize = 10;
    private String orderStateName;
    private Gson mGson;
    private List<QueryAllOrderBean.OrderListBean.ListBean> allOrderList = new ArrayList<>();
    private List<QueryAllOrderBean.OrderListBean.ListBean> ingOrderList = new ArrayList<>();
    private List<QueryAllOrderBean.OrderListBean.ListBean> orderDoneList = new ArrayList<>();
    private List<QueryAllOrderBean.OrderListBean.ListBean> refuseList = new ArrayList<>();
    private List<QueryAllOrderBean.OrderListBean.ListBean> waiteMeList = new ArrayList<>();
    private OrderSortAdapter mAllAdapter;
    private OrderSortAdapter mIngAdapter;
    private OrderSortAdapter mDoneAdapter;
    private OrderSortAdapter mRefuseAdapter;
    private OrderSortAdapter mWaitAdapter;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_order_sort;
    }

    public static Fragment newInstance(Bundle bundle) {
        OrderSortFragment orderSortFragment = new OrderSortFragment();
        orderSortFragment.setArguments(bundle);
        return orderSortFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        initListener();
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        return rootView;
    }

    private void initListener() {
        mPlrSortPage.setMode(PullToRefreshBase.Mode.BOTH);
        mPlrSortPage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPlrSortPage.setMode(PullToRefreshBase.Mode.BOTH);
                allOrderList.clear();
                orderDoneList.clear();
                refuseList.clear();
                waiteMeList.clear();
                ingOrderList.clear();

                mAllPageNo = 1;
                mIngPageNo = 1;
                mDonePageNo = 1;
                mRefusePageNo = 1;
                mWaitePageNo = 1;
                refreshDownorUp(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mAllPageNo++;
                mIngPageNo++;
                mDonePageNo++;
                mRefusePageNo++;
                mWaitePageNo++;

                refreshDownorUp(false);
            }
        });
    }

    /**
     * 根据向上或向下刷新；
     */
    private void refreshDownorUp(boolean isShowLoading) {
        switch (orderStateName) {
            case "全部":
                queryAllOrder(isShowLoading);
                break;
            case "审批中":
                ingOrder(isShowLoading);
                break;
            case "待我审批":
                queryWaitMe(isShowLoading);
                break;
            case "交易完成":
                doneOrder(isShowLoading);
                break;
            case "已拒绝":
                orderRefuse(isShowLoading);
                break;
            default:
                break;
        }
    }

    /**
     * 刷新列表；
     *
     * @param event
     */
    @Subscribe
    public void refreshEvent(CommRefreshListEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            allOrderList.clear();
            mAllAdapter = null;
            queryAllOrder(false);
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
        switch (titleName) {
            case "全部":
                allOrderList.clear();
                mAllAdapter = null;
                queryAllOrder(true);
                break;
            case "审批中":
                ingOrderList.clear();
                mIngAdapter = null;
                ingOrder(true);
                break;
            case "待我审批":
                waiteMeList.clear();
                mWaitAdapter = null;
                queryWaitMe(true);
                break;
            case "交易完成":
                orderDoneList.clear();
                mDoneAdapter = null;
                doneOrder(true);
                break;
            case "已拒绝":
                refuseList.clear();
                mRefuseAdapter = null;
                orderRefuse(true);
                break;
            default:
                break;
        }
    }

    /***
     * 拒绝操作；
     */
    private void orderRefuse(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_ORDER + "user.id=" + mUserId + "&pageNo=" + mRefusePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllOrderBean queryAllOrderBean = mGson.fromJson(s, QueryAllOrderBean.class);
                if (queryAllOrderBean.isSuccess()) {
                    QueryAllOrderBean.OrderListBean orderList = queryAllOrderBean.getOrderList();
                    List<QueryAllOrderBean.OrderListBean.ListBean> list = orderList.getList();
                    if (list != null) {
                        if (refuseList.size() == orderList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSortPage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            refuseList.addAll(list);
                        }
                    }
                    initRefuseData(refuseList);
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
     * 待我审批
     */
    private void queryWaitMe(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_ORDER + "user.id=" + mUserId + "&pageNo=" + mWaitePageNo + "&pageSize=" + mPageSize + "&approvalType=" + "0" + "&approverUserId=" +mUserId ).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllOrderBean queryAllOrderBean = mGson.fromJson(s, QueryAllOrderBean.class);
                if (queryAllOrderBean.isSuccess()) {
                    QueryAllOrderBean.OrderListBean orderList = queryAllOrderBean.getOrderList();
                    List<QueryAllOrderBean.OrderListBean.ListBean> list = orderList.getList();
                    if (list != null) {
                        if (waiteMeList.size() == orderList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSortPage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            waiteMeList.addAll(list);
                        }
                    }
                    initWaiteData(waiteMeList);
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
     * 订单完成；
     */
    private void doneOrder(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }

        OkHttpUtils.post(HttpConstant.QUERY_ALL_ORDER + "user.id=" + mUserId + "&pageNo=" + mDonePageNo + "&pageSize=" + mPageSize + "&statuslist=" + "7").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllOrderBean queryAllOrderBean = mGson.fromJson(s, QueryAllOrderBean.class);
                if (queryAllOrderBean.isSuccess()) {
                    QueryAllOrderBean.OrderListBean orderList = queryAllOrderBean.getOrderList();
                    List<QueryAllOrderBean.OrderListBean.ListBean> list = orderList.getList();
                    if (list != null) {
                        if (orderDoneList.size() == orderList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSortPage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            orderDoneList.addAll(list);
                        }
                    }
                    initDoneData(orderDoneList);
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
     * 审批中的单子
     */
    private void ingOrder(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_ORDER + "user.id=" + mUserId + "&pageNo=" + mIngPageNo + "&pageSize=" + mPageSize + "&statuslist=" + "3,4,5,6,8" + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllOrderBean queryAllOrderBean = mGson.fromJson(s, QueryAllOrderBean.class);
                if (queryAllOrderBean.isSuccess()) {
                    QueryAllOrderBean.OrderListBean orderList = queryAllOrderBean.getOrderList();
                    List<QueryAllOrderBean.OrderListBean.ListBean> list = orderList.getList();
                    if (list != null) {
                        if (ingOrderList.size() == orderList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSortPage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            ingOrderList.addAll(list);
                        }
                    }
                    initIngData(ingOrderList);
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


    private void queryAllOrder(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUERY_ALL_ORDER + "user.id=" + mUserId + "&pageNo=" + mAllPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryAllOrderBean queryAllOrderBean = mGson.fromJson(s, QueryAllOrderBean.class);
                if (queryAllOrderBean.isSuccess()) {
                    QueryAllOrderBean.OrderListBean orderList = queryAllOrderBean.getOrderList();
                    List<QueryAllOrderBean.OrderListBean.ListBean> allList = orderList.getList();
                    if (allList != null) {
                        if (allOrderList.size() == orderList.getCount()) {
                            Util.noMoreDataToast(getContext());
                            noRefresh();
                            mPlrSortPage.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            allOrderList.addAll(allList);
                        }
                    }
                    initAllData(allOrderList);
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

    private void noRefresh() {
        if (mPlrSortPage != null) {
            mPlrSortPage.onRefreshComplete();
        }
    }
    private void setEmpty(){
        if (mPlrSortPage != null) {
            mPlrSortPage.setEmptyView(mLlEmpty);
        }
    }

    private void initWaiteData(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mWaitAdapter == null) {
            mWaitAdapter = new OrderSortAdapter(getActivity(), list);
            if (mPlrSortPage != null) {
                mPlrSortPage.setAdapter(mWaitAdapter);
            }
        } else {
            mWaitAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    private void initRefuseData(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mRefuseAdapter == null) {
            mRefuseAdapter = new OrderSortAdapter(getActivity(), list);
            if (mPlrSortPage != null) {
                mPlrSortPage.setAdapter(mRefuseAdapter);
            }
        } else {
            mRefuseAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }

    private void initDoneData(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mDoneAdapter == null) {
            mDoneAdapter = new OrderSortAdapter(getActivity(), list);
            if (mPlrSortPage != null) {
                mPlrSortPage.setAdapter(mDoneAdapter);
            }
        } else {
            mDoneAdapter.notifyDataSetChanged();
        }
        setEmpty();

        initItem(list);
    }

    private void initIngData(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mIngAdapter == null) {
            mIngAdapter = new OrderSortAdapter(getActivity(), list);
            if (mPlrSortPage != null) {
                mPlrSortPage.setAdapter(mIngAdapter);
            }
        } else {
            mIngAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }


    private void initAllData(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mAllAdapter == null) {
            mAllAdapter = new OrderSortAdapter(getActivity(), list);
            if (mPlrSortPage != null) {
                mPlrSortPage.setAdapter(mAllAdapter);
            }
        } else {
            mAllAdapter.notifyDataSetChanged();
        }
        setEmpty();
        initItem(list);
    }

    private void initItem(final List<QueryAllOrderBean.OrderListBean.ListBean> list) {
        if (mPlrSortPage != null) {
            mPlrSortPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    intent.putExtra(Constant.CONTENT_TYPE, Constant.ORDER_DETAILS_NEXT);
                    intent.putExtra(Constant.ORDER_DATAIL_ID, list.get(i - 1).getId());
                    String orderState = Util.getOrderState(list.get(i - 1).getOrderStatus(), list.get(i - 1).getApprovalType());
                    if ("待提交".equals(orderState) || "待审批".equals(orderState)) {
                        intent.putExtra(Constant.IS_HAVE_MODIFIER, true);
                    } else {
                        intent.putExtra(Constant.IS_HAVE_MODIFIER, false);
                    }
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

}
