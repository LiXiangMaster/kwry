package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.TargetDoneDetailAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.TargetDoneDetailBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/27.
 */
public class TargetDoneDatailFragment extends FBaseFragment {
    @BindView(R.id.pl_target_done_detail)
    ListView mPlTargetDoneDetail;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    Unbinder unbinder;
    private String mUserId;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_target_done_tail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        mGson = new Gson();
        if (arguments != null) {
            mUserId = arguments.getString(Constant.USERID);
        }
        unbinder = ButterKnife.bind(this, rootView);
        requestNet2Detail();
        return rootView;
    }

    private void requestNet2Detail() {
        showLoading();
        OkHttpUtils.post(HttpConstant.TARGET_DONE_DETAIL + "user.id=" + mUserId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                TargetDoneDetailBean targetDoneDetailBean = mGson.fromJson(s, TargetDoneDetailBean.class);
                boolean success = targetDoneDetailBean.isSuccess();
                if (success) {
                    List<TargetDoneDetailBean.DataBean> data = targetDoneDetailBean.getData();
                    initData(data);
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

    private void initData( List<TargetDoneDetailBean.DataBean> dataBean) {
        mPlTargetDoneDetail.setEmptyView(mLlEmpty);
        TargetDoneDetailAdapter adapter = new TargetDoneDetailAdapter(getActivity(),dataBean);
        mPlTargetDoneDetail.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    public static Fragment newInstance(Bundle bundle) {
        TargetDoneDatailFragment targetDoneDatail = new TargetDoneDatailFragment();
        targetDoneDatail.setArguments(bundle);
        return targetDoneDatail;
    }
}
