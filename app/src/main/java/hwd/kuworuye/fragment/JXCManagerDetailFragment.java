package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.JXCManagerListAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.JxDetailBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class JXCManagerDetailFragment extends FBaseFragment {
    @BindView(R.id.ptr_jxc_manager_list)
    PullToRefreshListView mPtrJxcManagerList;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_jxc_manager_list;
    }

    public static Fragment newInstance(Bundle bundle) {
        JXCManagerDetailFragment fragment = new JXCManagerDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mPtrJxcManagerList.setMode(PullToRefreshBase.Mode.DISABLED);
        mPtrJxcManagerList.setEmptyView(mLlEmpty);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String jxId = arguments.getString(Constant.JXID);
            queryJxDetail(jxId);
        }
        return rootView;
    }

    private void queryJxDetail(String jxId) {

        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_JX_DATAIL + "invoicingId=" + jxId).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                JxDetailBean jxDetailBean = gson.fromJson(s, JxDetailBean.class);
                boolean success = jxDetailBean.isSuccess();
                if (success) {
                    initData(jxDetailBean);
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


    private void initData(JxDetailBean dataBeen) {
        List<JxDetailBean.DataBean> data = dataBeen.getData();
        JXCManagerListAdapter listAdapter = new JXCManagerListAdapter(getActivity(), data);
        mPtrJxcManagerList.setAdapter(listAdapter);
        View view = View.inflate(getContext(), R.layout.jxc_footer, null);
        TextView tv_all_box = (TextView) view.findViewById(R.id.tv_all_box);
        tv_all_box.setText(dataBeen.getTotal() + "箱");
        mPtrJxcManagerList.getRefreshableView().addFooterView(view);
        if (data.size()==0){
            mPtrJxcManagerList.getRefreshableView().removeFooterView(view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
