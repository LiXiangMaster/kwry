package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.AskGoodsDetailAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.AskGoodsDetailBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/25.
 */
public class AskGoodsPlanDetailFragment extends FBaseFragment {

    @BindView(R.id.plr_ask_plan_detail)
    ListView mPlrAskPlanDetail;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    Unbinder unbinder;
    private String mCargoPlanId;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_ask_goods_plan_detail;
    }

    public static Fragment newInstance(Bundle bundle) {
        AskGoodsPlanDetailFragment askGoodsPlanDetailFragment = new AskGoodsPlanDetailFragment();
        askGoodsPlanDetailFragment.setArguments(bundle);
        return askGoodsPlanDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCargoPlanId = arguments.getString(Constant.PASS_ASK_PLAN_DETAIL);
        }
        requestNet2DetailData();
        return rootView;
    }

    private void requestNet2DetailData() {
        showLoading();
        OkHttpUtils.post(HttpConstant.ASK_GOODS_DETAIL + "cargoPlanId=" + mCargoPlanId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                AskGoodsDetailBean askGoodsDetailBean = gson.fromJson(s, AskGoodsDetailBean.class);
                boolean success = askGoodsDetailBean.isSuccess();
                if (success) {
                    initData(askGoodsDetailBean);
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

    private void initData(AskGoodsDetailBean askGoodsDetailBean) {
        mPlrAskPlanDetail.setEmptyView(mLlEmpty);
        View view = View.inflate(getContext(), R.layout.item_detail_footer, null);
        TextView tv_total_boxNum = (TextView) view.findViewById(R.id.tv_total_box_number);
        mPlrAskPlanDetail.addFooterView(view);
        int total = askGoodsDetailBean.getTotal();
        String size = askGoodsDetailBean.getSize();
        tv_total_boxNum.setText(total + "箱");

        List<AskGoodsDetailBean.DataBean> data = askGoodsDetailBean.getData();
        AskGoodsDetailAdapter askGoodsDetailAdapter = new AskGoodsDetailAdapter(getActivity(), data);
        mPlrAskPlanDetail.setAdapter(askGoodsDetailAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
