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
import hwd.kuworuye.adapter.AskGoodsPlanAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.AskGoodsPlanListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class AskGoodsPlanFragment extends FBaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.ptr_ask_plan)
    PullToRefreshListView mLvAskPlan;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    private View mTopBarView;
    private Topbar mTopbar;

    private int mPageNo = 1;
    private int mPageSize = 10;
    private List<AskGoodsPlanListBean.DataBean> mData = new ArrayList<>();
    private AskGoodsPlanAdapter mGoodsPlanAdapter;

    public static Fragment newInstance(Bundle bundle) {
        AskGoodsPlanFragment askGoodsPlanFragment = new AskGoodsPlanFragment();
        askGoodsPlanFragment.setArguments(bundle);
        return askGoodsPlanFragment;
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_ask_goods_plan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initListener();
        EventBus.getDefault().register(this);
        initTopbar();
        mLvAskPlan.setOnItemClickListener(this);
        getAskPlanList(true);
        return rootView;
    }


    private void initListener() {
        mLvAskPlan.setMode(PullToRefreshBase.Mode.BOTH);
        mLvAskPlan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mLvAskPlan.setMode(PullToRefreshBase.Mode.BOTH);
                mData.clear();
                mPageNo = 1;
                getAskPlanList(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNo++;
                getAskPlanList(false);
            }
        });
    }

    /**
     * 刷新列表；
     *
     * @param event
     */
    @Subscribe
    public void refreshAskGoodsListEvent(CommRefreshListEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            mData.clear();
            getAskPlanList(true);
        }
    }


    /**
     * 获取要货计划列表；
     */
    private void getAskPlanList(boolean isShowLaoding) {
        if (isShowLaoding) {
            showLoading();
        }
        String userId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        OkHttpUtils.post(HttpConstant.GET_USER_ASKGOODS_PLAN + "userId=" + userId + "&pageNo=" + mPageNo + "&pageSize=" + mPageSize).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                AskGoodsPlanListBean askGoodsPlanListBean = gson.fromJson(s, AskGoodsPlanListBean.class);
                boolean success = askGoodsPlanListBean.isSuccess();
                if (success) {
                    List<AskGoodsPlanListBean.DataBean> data = askGoodsPlanListBean.getData();
                    if (mData.size() == askGoodsPlanListBean.getSize()) {
                        if (mData.size() != 0) {
                            Util.noMoreDataToast(getContext());
                        }
                        noRefresh();
                        mLvAskPlan.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        mData.addAll(data);
                    }
                    initData(mData);
                    noRefresh();
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                    getActivity().finish();
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    private void noRefresh() {
        if (mLvAskPlan != null) {
            mLvAskPlan.onRefreshComplete();
        }
    }


    private void initData(List<AskGoodsPlanListBean.DataBean> data) {
        if (mGoodsPlanAdapter == null) {
            mGoodsPlanAdapter = new AskGoodsPlanAdapter(getActivity(), data);
            mLvAskPlan.setAdapter(mGoodsPlanAdapter);
            noRefresh();
        } else {
            mGoodsPlanAdapter.notifyDataSetChanged();
        }
        setEmpty();

    }

    private void setEmpty() {
        if (mLvAskPlan != null) {
            mLvAskPlan.setEmptyView(mLlEmpty);
        }
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
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.ADD_ASKGOODS_PLAN);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        intent.putExtra(Constant.CONTENT_TYPE, Constant.ASK_GOODS_PLAN_DETAIL);
        intent.putExtra(Constant.PASS_ASK_PLAN_DETAIL, mData.get(i - 1).getId());
        startActivity(intent);
    }
}