package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.JXCAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.IsCanUpdateRepertoryBean;
import hwd.kuworuye.bean.JxcManagerBean;
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
public class JXCManagerListFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.ptr_jxc_manager)
    PullToRefreshListView mLvJxcManager;
    Unbinder unbinder;
    @BindView(R.id.update_repertory)
    Button mUpdateRepertory;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private String mUserId;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private List<JxcManagerBean.DataBean> mData = new ArrayList<>();
    private JXCAdapter mJxcAdapter;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_jxc_manager;
    }

    public static Fragment newInstance(Bundle bundle) {
        JXCManagerListFragment fragment = new JXCManagerListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERID);
        mUpdateRepertory.setOnClickListener(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String userId = arguments.getString(Constant.USERID);
            String isShowBtStr = arguments.getString(Constant.ISSHOW_UPDATE_BT);
            if (isShowBtStr != null && "noshow".equals(isShowBtStr)) {
                mUpdateRepertory.setVisibility(View.GONE);
            }
            if (userId != null) {
                mUserId = userId;
            }
        }
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mData.clear();
        mPageNo = 1;
        getJxcMangerData(true);
    }

    private void initListener() {
        mLvJxcManager.setMode(PullToRefreshBase.Mode.BOTH);
        mLvJxcManager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mLvJxcManager.setMode(PullToRefreshBase.Mode.BOTH);
                mData.clear();
                mPageNo = 1;
                getJxcMangerData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNo++;
                getJxcMangerData(false);
            }
        });
    }

    private void getJxcMangerData(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.GET_USER_JXC + "userId=" + mUserId + "&pageNo=" + mPageNo + "&pageSize=" + mPageSize).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                JxcManagerBean jxcManagerBean = gson.fromJson(s, JxcManagerBean.class);
                boolean success = jxcManagerBean.isSuccess();
                if (success) {
                    List<JxcManagerBean.DataBean> data = jxcManagerBean.getData();
                    if (mData.size() == jxcManagerBean.getSize()) {
                        if (mData.size() != 0) {
                            Util.noMoreDataToast(getContext());
                        }
                        noRefresh();
                        mLvJxcManager.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        mData.addAll(data);
                    }
                    initData(mData);
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
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
        if (mLvJxcManager != null) {
            mLvJxcManager.onRefreshComplete();
        }
    }

    private void initData(final List<JxcManagerBean.DataBean> dataBeanList) {
        if (mJxcAdapter == null) {
            mJxcAdapter = new JXCAdapter(getActivity(), dataBeanList);
            mLvJxcManager.setAdapter(mJxcAdapter);
            noRefresh();
        } else {
            mJxcAdapter.notifyDataSetChanged();
        }
        setEmpty();
        mLvJxcManager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i-1 是应为 该控件有个头。
                if (i - 1 >= 0 && i - 1 < dataBeanList.size()) {
                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    intent.putExtra(Constant.CONTENT_TYPE, Constant.JXC_MANAGER_LIST_SHOW);
                    intent.putExtra(Constant.JXID, dataBeanList.get(i - 1).getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void setEmpty() {
        if (mLvJxcManager != null) {
            mLvJxcManager.setEmptyView(mLlEmpty);
        }
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
            case R.id.update_repertory:
                isCanUpdateReperTory();
                break;
        }
    }

    private void isCanUpdateReperTory() {

        showLoading();
        OkHttpUtils.post(HttpConstant.IS_CAN_UPDAT_REPERTORY + "userId=" + mUserId).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                IsCanUpdateRepertoryBean isCanUpdateRepertoryBean = gson.fromJson(s, IsCanUpdateRepertoryBean.class);
                boolean success = isCanUpdateRepertoryBean.isSuccess();
                if (success) {
                    boolean isSubmit = isCanUpdateRepertoryBean.isIsSubmit();
                    if (isSubmit) {
                        Intent intent = new Intent(getContext(), ContentActivity.class);
                        intent.putExtra(Constant.CONTENT_TYPE, Constant.SUBMIT_REPERTORY);
                        intent.putExtra(Constant.USERID, mUserId);
                        startActivity(intent);
                    } else {
                        Util.toast(getContext(), "已经更新过");
                    }
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
