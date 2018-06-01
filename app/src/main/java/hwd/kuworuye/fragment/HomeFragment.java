package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.CommApplyForActivity;
import hwd.kuworuye.activity.NewsDetailActivity;
import hwd.kuworuye.activity.OrderManagerActivity;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.BannerBean;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import lix.lib.arl.AutoRollLayout;
import lix.lib.arl.IRollItem;
import lix.lib.arl.RollItem;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16.
 */

public class HomeFragment extends FBaseFragment implements View.OnClickListener, AutoRollLayout.OnItemClickListener {

    @BindView(R.id.autobar_news)
    AutoRollLayout mAutobarNews;
    Unbinder unbinder;
    @BindView(R.id.ll_oder_manager)
    LinearLayout mLlOderManager;
    @BindView(R.id.ll_join_company_apply)
    LinearLayout mLlJoinCompanyApply;
    @BindView(R.id.ll_sales_applay)
    LinearLayout mLlSalesApplay;
    @BindView(R.id.ll_goods_play_apply)
    LinearLayout mLlGoodsPlayApply;
    @BindView(R.id.ll_other_cost_apply)
    LinearLayout mLlOtherCostApply;

    private List<IRollItem> mItems;
    private List<BannerBean.DataBean> mDataList;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_home;
    }

    public static Fragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNetForData();
    }

    private void requestNetForData() {
        showLoading();
        OkHttpUtils.get(HttpConstant.BANNER).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                BannerBean bannerBean = gson.fromJson(s, BannerBean.class);
                mDataList = bannerBean.getData();
                initData();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.toast(getContext(),"请求超时");
                dismissLoading();
            }
        });
    }


    private void initData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            mItems.add(new RollItem(mDataList.get(i).getImgUrl(), mDataList.get(i).getTitle(), mDataList.get(i).getDescribes()));
        }
        if (mAutobarNews==null){
            return;
        }
        mAutobarNews.setItems(mItems);

        mAutobarNews.setAutoRoll(true);
        mLlOderManager.setOnClickListener(this);
        mAutobarNews.setOnItemClickListener(this);
        mLlSalesApplay.setOnClickListener(this);
        mLlOtherCostApply.setOnClickListener(this);
        mLlJoinCompanyApply.setOnClickListener(this);
        mLlGoodsPlayApply.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mAutobarNews != null) {
            mAutobarNews.setAutoRoll(false);
        }
    }

    @Override
    public void onClick(View view) {
        String str = "";
        switch (view.getId()) {
            case R.id.ll_oder_manager:
                Util.startActivity(getContext(), OrderManagerActivity.class);
                break;
            case R.id.autobar_news:
                Util.startActivity(getContext(), NewsDetailActivity.class);
                break;
            case R.id.ll_sales_applay:
                str = "促销活动申请";
                Util.startActivity(getContext(), CommApplyForActivity.class, str);
                break;
            case R.id.ll_other_cost_apply:
                str = "其他费用申请";
                Util.startActivity(getContext(), CommApplyForActivity.class, str);
                break;
            case R.id.ll_join_company_apply:
                str = "进场费用申请";
                Util.startActivity(getContext(), CommApplyForActivity.class, str);
                break;
            case R.id.ll_goods_play_apply:
                str = "产品陈列费用申请";
                Util.startActivity(getContext(), CommApplyForActivity.class, str);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

        Util.startActivity(getContext(), NewsDetailActivity.class, mDataList.get(position).getContent());
    }
}
