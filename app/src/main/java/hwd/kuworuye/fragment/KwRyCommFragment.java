package hwd.kuworuye.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.ModifierBillListAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.AddAskGoodsPlanBean;
import hwd.kuworuye.bean.BackProductNumAndPriceBean;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.KwProductNatureBean;
import hwd.kuworuye.bean.ProductListBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommKwBackEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.event.TotalNumAndPriceIdEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/6.
 */
public class KwRyCommFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.vp_modifier_manager)
    ViewPager mVpModifierManager;
    Unbinder unbinder;
    @BindView(R.id.bt_bottom_com)
    Button mBtBottomCom;
    @BindView(R.id.mci_kw_com)
    MagicIndicator mMciKwCom;
    //标题类型；
    private ArrayList<String> tableData = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ModifierBillListAdapter mAdapter;
    private TextView mTvTotalPrice;
    private TextView mTvTotalBox;
    private List<CommProductBean> allSubmitList = new ArrayList<>();
    private List<KwProductNatureBean> allKwSubmitList = new ArrayList<>();
    //被选中的产品数量集合；
    private List<CommProductAllNatureBean> mCommProductAllNatureBeen = new ArrayList<>();
    private ArrayList<CommPassBean> passProductList = new ArrayList<>();
    private String mUserId;
    private Bundle mArguments;
    private Gson mGson;

    @Override
    protected int inflateContentView() {

        return R.layout.fragment_modifier_bill_list;
    }

    public static Fragment newInstance(Bundle bundle) {
        KwRyCommFragment fragment = new KwRyCommFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mTvTotalPrice = ((TextView) rootView.findViewById(R.id.tv_bottom_total_price));
        mTvTotalBox = ((TextView) rootView.findViewById(R.id.tv__bottom_total_box));
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mArguments = getArguments();
        mGson = new Gson();
        initData();
        requestNet();
        return rootView;
    }


    private void initData() {
        int mTotalNum = 0;
        double mTotalPrice = 0;

        if (mArguments != null) {
            int joinType = mArguments.getInt(Constant.JOIN_KW_COM_TYPE);
            if (Constant.INIT_REPERTORY == joinType) {
                mBtBottomCom.setText("提交");
            }
            passProductList = mArguments.getParcelableArrayList(Constant.PASS_PRODUCT);
        }

        if (passProductList != null && passProductList.size() != 0) {
            for (int i = 0; i < passProductList.size(); i++) {
                double itemPrice = passProductList.get(i).getTotalPrice();
                int itemNum = passProductList.get(i).getItemNum();
                String productId = passProductList.get(i).getProductId();
                backList.add(new BackProductNumAndPriceBean(itemNum, itemPrice, productId));
            }

            for (int i = 0; i < backList.size(); i++) {
                int totalBox = backList.get(i).getTotalBox();
                double price = backList.get(i).getPrice();
                mTotalNum += totalBox;
                mTotalPrice += price;
            }
            showBottomPriceAndNum(mTotalNum, mTotalPrice);
        }

        mBtBottomCom.setOnClickListener(this);
    }

    private List<BackProductNumAndPriceBean> backList = new ArrayList<>();

    /**
     * 底部数量和价格；
     *
     * @param event
     */
    @Subscribe
    public void backProductPriceNumId(TotalNumAndPriceIdEvent event) {
        int mTotalNum = 0;
        double mTotalPrice = 0;
        String orderId = event.getOrderId();
        int totalNum = event.getTotalNum();
        double totalPrice = event.getSinglePrice();


        for (int i = 0; i < backList.size(); i++) {
            if (backList.get(i).getOrderId().equals(orderId)) {
                backList.remove(i);
            }
        }

        backList.add(new BackProductNumAndPriceBean(totalNum, totalPrice, orderId));

        for (int i = 0; i < backList.size(); i++) {
            int totalBox = backList.get(i).getTotalBox();
            double price = backList.get(i).getPrice();
            mTotalNum += totalBox;
            mTotalPrice += price;
        }

        showBottomPriceAndNum(mTotalNum, mTotalPrice);
    }

    private void showBottomPriceAndNum(int totalNum, double totalPrice) {
        mTvTotalBox.setText("共" + totalNum + "件商品");
        mTvTotalPrice.setText(String.valueOf(totalPrice));
    }

    /**
     * 获取产品列表
     */
    private void requestNet() {
        showLoading();
        OkHttpUtils.get(HttpConstant.PRODUCT_LIST).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductListBean listBean = mGson.fromJson(s, ProductListBean.class);
                if (listBean.isSuccess()) {
                    List<ProductListBean.DataBean> data = listBean.getData();
                    for (ProductListBean.DataBean dataBean : data) {
                        tableData.add(dataBean.getTypeName());
                    }

                    initTitle(data);
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


    private void initTitle(List<ProductListBean.DataBean> bigProductList) {

        for (int i = 0; i < tableData.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("titlelist", tableData);
            bundle.putInt("categoryId", i);
            bundle.putSerializable("productlist", (Serializable) bigProductList);
            bundle.putParcelableArrayList(Constant.PASS_PRODUCT, passProductList);
            fragmentList.add(ModifierBillFragment.newInstance(bundle));
        }

        mAdapter = new ModifierBillListAdapter(getFragmentManager(), fragmentList, tableData);
        mVpModifierManager.setAdapter(mAdapter);
        mVpModifierManager.setOffscreenPageLimit(4);


        mMciKwCom.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tableData == null ? 0 : tableData.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(tableData.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#55aee9"));
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpModifierManager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#55aee9"));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return super.getTitleWeight(context, index);

            }
        });
        mMciKwCom.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMciKwCom, mVpModifierManager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getActivity());
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_bottom_com:
                for (int i = 0; i < tableData.size(); i++) {
                    ModifierBillFragment fragment = (ModifierBillFragment) mAdapter.getItem(i);
                    List<CommProductBean> submitList = fragment.getSubmitList();
                    List<KwProductNatureBean> kwSubmitList = fragment.getKwSubmitList();
                    List<CommProductAllNatureBean> productAllNatureList = fragment.getProductAllNatureList();
                    if (submitList.size() != 0) {
                        allSubmitList.addAll(submitList);
                        allKwSubmitList.addAll(kwSubmitList);
                        mCommProductAllNatureBeen.addAll(productAllNatureList);
                    }
                }
                //只返回产品id 和 数量；
                String json = mGson.toJson(allSubmitList);
                String kwJson = mGson.toJson(allKwSubmitList);
                if (mArguments != null) {
                    int joinType = mArguments.getInt(Constant.JOIN_KW_COM_TYPE);
                    String usrId = mArguments.getString(Constant.USERID);
                    if (usrId != null) {
                        mUserId = usrId;
                    }
                    switch (joinType) {
                        case Constant.INIT_REPERTORY:
                            initRepertory();
                            break;
                        case Constant.ADD_ASKGOODS_PLAN:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            getActivity().finish();
                            break;
                        case Constant.SUBMIT_ORDER:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            EventBus.getDefault().post(new CommKwBackEvent(kwJson));
                            getActivity().finish();
                            break;
                        case Constant.APPLY_FOR_ACTIVITY_SUBMIT:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            EventBus.getDefault().post(new CommKwBackEvent(json));
                            getActivity().finish();
                            break;
                        case Constant.UPDATE_ORDER:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            EventBus.getDefault().post(new CommKwBackEvent(kwJson));
                            getActivity().finish();
                            break;
                        case Constant.DISPLAY_APPLY_EDIT:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            EventBus.getDefault().post(new CommKwBackEvent(json));
                            getActivity().finish();
                            break;
                        case Constant.DISPLAY_HEXIAO_UPDATE:
                            EventBus.getDefault().post(new KwChooseProductListEvent(mCommProductAllNatureBeen));
                            EventBus.getDefault().post(new CommKwBackEvent(json));
                            getActivity().finish();
                            break;
                        default:
                            break;
                    }
                }
                break;
        }

    }

    private void initRepertory() {
        List<AddAskGoodsPlanBean> list = new ArrayList<>();
        for (int i = 0; i < allSubmitList.size(); i++) {
            String productAttributeId = allSubmitList.get(i).getProductAttributeId();
            String quantity = allSubmitList.get(i).getQuantity();
            list.add(new AddAskGoodsPlanBean(productAttributeId, quantity));
        }
        String s = mGson.toJson(list);
        String replaceJson = Util.replaceSingleMark(s);
        showLoading();
        OkHttpUtils.post(HttpConstant.INIT_REPERTORY + "userId=" + mUserId + "&data=" +
                replaceJson).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                getActivity().finish();
                if (success) {
                    Util.toast(getContext(), "提交成功");
                    SharedPreferencesUtil.getInstance(getContext()).save(Constant.ISINIT, "1");
                    Util.openContentActivity(getContext(), ContentActivity.class,
                            Constant.JXC_MANAGER);
                } else {
                    FailMsgBean failMsgBean = mGson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response
                    response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }
}
