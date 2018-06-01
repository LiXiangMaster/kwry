package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.NewsDetailActivity;
import hwd.kuworuye.adapter.NewsListAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.NewsListBean;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16.
 */

public class NewsFragment extends FBaseFragment {

    @BindView(R.id.prl_news)
    PullToRefreshListView mPrlNews;
    Unbinder unbinder;
    @BindView(R.id.tv_comm_title_bar_one)
    TextView mTvCommTitleBarOne;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private int mPageNo = 1;
    private int mPageSize = 10;

    private NewsListAdapter mNewsListAdapter;
    private List<NewsListBean.DataBean> mDataBeanList = new ArrayList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_news;
    }

    public static Fragment newInstance(Bundle bundle) {
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initListener();
        requestNetForData(true);
        return rootView;
    }

    private void initListener() {
        mPrlNews.setMode(PullToRefreshBase.Mode.BOTH);
        mPrlNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPrlNews.setMode(PullToRefreshBase.Mode.BOTH);
                mDataBeanList.clear();
                mPageNo = 1;
                requestNetForData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNo++;
                requestNetForData(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData(final List<NewsListBean.DataBean> beanList) {
        mTvCommTitleBarOne.setText(R.string.news_title);
        if (mNewsListAdapter == null) {
            mNewsListAdapter = new NewsListAdapter(getActivity(), beanList);
            mPrlNews.setAdapter(mNewsListAdapter);
            noRefresh();
        } else {
            mNewsListAdapter.notifyDataSetChanged();
        }
        setEmpty();
        mPrlNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i-1 是因为，这个pulltorefresh 控件 本身有一个头部，
                Util.startActivity(getContext(), NewsDetailActivity.class, beanList.get(i - 1).getContent());
            }
        });
    }

    private void setEmpty() {
        if (mPrlNews != null) {
            mPrlNews.setEmptyView(mLlEmpty);
        }
    }

    private void requestNetForData(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }

        OkHttpUtils.get(HttpConstant.NEWS_LIST + "&pageNo=" + mPageNo + "&pageSize=" + mPageSize).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                NewsListBean newsListBean = gson.fromJson(s, NewsListBean.class);
                List<NewsListBean.DataBean> data = newsListBean.getData();
                if (mDataBeanList.size() == newsListBean.getSize()) {
                   Util.noMoreDataToast(getContext());
                    noRefresh();
                    mPrlNews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                } else {
                    mDataBeanList.addAll(data);
                }
                initData(mDataBeanList);
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

    private void noRefresh() {
        if (mPrlNews != null) {
            mPrlNews.onRefreshComplete();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
