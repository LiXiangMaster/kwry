package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
import hwd.kuworuye.adapter.CommBackBigAdapter;
import hwd.kuworuye.adapter.KwCommProductAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommProductBean;
import hwd.kuworuye.bean.CommUpdateProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ImageTypeTitleUrlBean;
import hwd.kuworuye.bean.ProductDisPlayCostDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.BackPic2ListEvent;
import hwd.kuworuye.event.CommKwBackEvent;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyGridView;
import hwd.kuworuye.weight.MyListView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/3.
 */
public class DisPlayApplySubmitFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.et_display_name)
    EditText mEtDisplayName;
    @BindView(R.id.rl_kuwo_sc)
    RelativeLayout mRlKuwoSc;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.lv_order_detail)
    MyListView mLvOrderDetail;
    @BindView(R.id.cb_1)
    CheckBox mCb1;
    @BindView(R.id.cb_2)
    CheckBox mCb2;
    @BindView(R.id.cb_3)
    CheckBox mCb3;
    @BindView(R.id.cb_4)
    CheckBox mCb4;
    @BindView(R.id.cb_5)
    CheckBox mCb5;
    @BindView(R.id.et_sale_target)
    EditText mEtSaleTarget;
    @BindView(R.id.et_execute_city)
    EditText mEtExecuteCity;
    @BindView(R.id.et_execute_range)
    EditText mEtExecuteRange;
    @BindView(R.id.et_execute_time)
    EditText mEtExecuteTime;
    @BindView(R.id.et_feixiao_bv)
    EditText mEtFeixiaoBv;
    @BindView(R.id.et_sale_volume)
    EditText mEtSaleVolume;
    @BindView(R.id.et_zhuanjia_num)
    EditText mEtZhuanjiaNum;
    @BindView(R.id.et_zhuanjia_area)
    EditText mEtZhuanjiaArea;
    @BindView(R.id.et_location_explain)
    EditText mEtLocationExplain;
    @BindView(R.id.et_cost_budget)
    EditText mEtCostBudget;
    @BindView(R.id.et_baozhu_num)
    EditText mEtBaozhuNum;
    @BindView(R.id.et_dispaly_style)
    EditText mEtDispalyStyle;
    @BindView(R.id.et_bazozhu_location_explain)
    EditText mEtBazozhuLocationExplain;
    @BindView(R.id.et_baozhu_cost_budget)
    EditText mEtBaozhuCostBudget;
    @BindView(R.id.et_other_num)
    EditText mEtOtherNum;
    @BindView(R.id.et_other_dispaly_style)
    EditText mEtOtherDispalyStyle;
    @BindView(R.id.et_other_location_explain)
    EditText mEtOtherLocationExplain;
    @BindView(R.id.et_other_cost_budget)
    EditText mEtOtherCostBudget;
    @BindView(R.id.rl_mark_photo_look)
    RelativeLayout mRlMarkPhotoLook;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;
    Unbinder unbinder;
    @BindView(R.id.bt_submit_modifier)
    Button mBtSubmitModifier;
    @BindView(R.id.tv_total_box)
    TextView mTvTotalBox;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rl_bottom_price_num)
    RelativeLayout mRlBottomPriceNum;

    @BindView(R.id.ll_target_1)
    LinearLayout mLlTarget1;
    @BindView(R.id.ll_target_2)
    LinearLayout mLlTarget2;
    @BindView(R.id.ll_target_3)
    LinearLayout mLlTarget3;
    @BindView(R.id.ll_target_4)
    LinearLayout mLlTarget4;
    @BindView(R.id.ll_target_5)
    LinearLayout mLlTarget5;

    @BindView(R.id.et_ac_sale_cost)
    EditText mEtAcSaleCost;
    @BindView(R.id.et_cost_bilv)
    EditText mEtCostBilv;

    private String mUsrId;
    private String mOrderId;
    private String mJoinWay;
    private List<ImageTypeTitleUrlBean> imageList = new ArrayList<>();
    private List<CommUpdateProductBean> mUpdateProductList = new ArrayList<>();
    private List<CommPassBean> mPassList = new ArrayList<>();
    private String productIdAndnum = "";
    private String mSingleStr;
    private Bundle mArguments;
    private Gson mGson;
    private RelativeLayout mRlDisplayMarkPlace;
    private boolean isUpLoadPic = true;
    private CommBackBigAdapter mSubmitAdapter = new CommBackBigAdapter(getContext(), null);

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_display_submit;
    }

    public static Fragment newInstance(Bundle bundle) {
        DisPlayApplySubmitFragment submitFragment = new DisPlayApplySubmitFragment();
        submitFragment.setArguments(bundle);
        return submitFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mArguments = getArguments();
        if (mArguments != null) {
            mJoinWay = mArguments.getString(Constant.APPLY_ACTIVITY_EDIT_JOIN_WAY);
            if (Constant.DISPLAY_APPLY_UPDATE.equals(mJoinWay)) {
                mBtSubmitModifier.setText("确认修改");
                mOrderId = mArguments.getString(Constant.ORDER_DATAIL_ID);
                queryProductDisplayDetail();
            }
        }
        mRlDisplayMarkPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_join_place));
        mGson = new Gson();
        initProductAdapter();
        initData();
        return rootView;
    }


    private void initData() {
        mUsrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mRlKuwoSc.setOnClickListener(this);
        mBtSubmitModifier.setOnClickListener(this);
        mRlKuwoSc.setOnClickListener(this);
        mRlMarkPhotoLook.setOnClickListener(this);

        mLlTarget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb1.isChecked()) {
                    mCb1.setChecked(false);
                } else {
                    mCb1.setChecked(true);
                }
            }
        });

        mLlTarget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb2.isChecked()) {
                    mCb2.setChecked(false);
                } else {
                    mCb2.setChecked(true);
                }
            }
        });

        mLlTarget3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb3.isChecked()) {
                    mCb3.setChecked(false);
                } else {
                    mCb3.setChecked(true);
                }
            }
        });


        mLlTarget4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb4.isChecked()) {
                    mCb4.setChecked(false);
                } else {
                    mCb4.setChecked(true);
                }
            }
        });


        mLlTarget5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb5.isChecked()) {
                    mCb5.setChecked(false);
                } else {
                    mCb5.setChecked(true);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mArguments != null) {
            if (Constant.DISPLAY_APPLY_UPDATE.equals(mJoinWay)) {
                requestNet2PicList();
            }
        }
    }

    /**
     * 查询 超市明细管理照片
     */
    private void requestNet2PicList() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "3").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean commPicListBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                initPicData(pageList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });

    }

    /**
     * 初始化图片数据；
     *
     * @param pageList
     */
    private void initPicData(CommPicListBean.PageListBean pageList) {
        List<ImageDataUrlBean> picList = new ArrayList<>();
        List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
        if (list != null) {
            mRlDisplayMarkPlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                picList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
            }
        } else {
            mRlDisplayMarkPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mPicBackAdapter = new CommBackBigAdapter(getContext(), picList);
        mMgvJoinSiteDetail.setAdapter(mPicBackAdapter);
        this.mSubmitAdapter = mPicBackAdapter;
    }


    /**
     * 查询产品陈列详情接口；
     */
    private void queryProductDisplayDetail() {
        showLoading();
        OkHttpUtils.get(HttpConstant.PRODUCT_DISPLAY_COST_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ProductDisPlayCostDetailBean playCostDetailBean = mGson.fromJson(s, ProductDisPlayCostDetailBean.class);
                boolean success = playCostDetailBean.isSuccess();
                if (success) {
                    showData2Et(playCostDetailBean);
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

    /**
     * 显示数据到控件上
     *
     * @param playCostDetailBean
     */
    private void showData2Et(ProductDisPlayCostDetailBean playCostDetailBean) {
        List<ProductDisPlayCostDetailBean.ItemListBean> itemList = playCostDetailBean.getItemList();
        List<CommUpdateProductBean> updateProductList = new ArrayList<>();
        List<CommProductBean> productList = new ArrayList<>();
        ProductDisPlayCostDetailBean.KwDisplayApplicationBean kwDisplayApplication = playCostDetailBean.getKwDisplayApplication();
        for (int i = 0; i < itemList.size(); i++) {
            ProductDisPlayCostDetailBean.ItemListBean.KwProductAttributeBean kwProductAttribute = itemList.get(i).getKwProductAttribute();
            String productAttributeId = itemList.get(i).getProductAttributeId();
            String quantityStr = itemList.get(i).getQuantity();
            int quantity = Integer.parseInt(quantityStr);
            String productName = itemList.get(i).getKwProductAttribute().getProduct().getProductName();
            String price = itemList.get(i).getKwProductAttribute().getPrice();
            double priceDouble = Double.parseDouble(price);
            String thumbnail = itemList.get(i).getKwProductAttribute().getThumbnail();
            String tasteStr = itemList.get(i).getKwProductAttribute().getTasteStr();
            String specificationsStr = itemList.get(i).getKwProductAttribute().getSpecificationsStr();
            updateProductList.add(new CommUpdateProductBean(productAttributeId, quantityStr, price, tasteStr, productName, specificationsStr, thumbnail));
            productList.add(new CommProductBean(productAttributeId, quantityStr));
            double passPriceDouble = priceDouble * quantity;
            mPassList.add(new CommPassBean(productAttributeId, quantity, passPriceDouble));
        }
        this.mUpdateProductList = updateProductList;
        String s = mGson.toJson(productList);
        productIdAndnum = Util.replaceSingleMark(s);
        initProductAdapter();
        bottomRlPriceAndnum();

        mEtDisplayName.setText(kwDisplayApplication.getName());

        List<String> objectiveList = kwDisplayApplication.getObjectiveList();
        if (objectiveList.contains("1")) {
            mCb1.setChecked(true);
        }

        if (objectiveList.contains("2")) {
            mCb2.setChecked(true);
        }

        if (objectiveList.contains("3")) {
            mCb3.setChecked(true);
        }

        if (objectiveList.contains("4")) {
            mCb4.setChecked(true);
        }

        if (objectiveList.contains("5")) {
            mCb5.setChecked(true);
        }

        mEtAcSaleCost.setText(kwDisplayApplication.getActivitySales());
        mEtCostBilv.setText(kwDisplayApplication.getActivityRatio());

        mEtSaleTarget.setText(kwDisplayApplication.getTarget());
        mEtExecuteCity.setText(kwDisplayApplication.getCity());
        mEtExecuteRange.setText(kwDisplayApplication.getRanges());
        mEtExecuteTime.setText(kwDisplayApplication.getExecutionTime());
        mEtFeixiaoBv.setText(kwDisplayApplication.getRatio());
        mEtSaleVolume.setText(kwDisplayApplication.getSalesVolume() + "");

        mEtZhuanjiaNum.setText(kwDisplayApplication.getShelfNumber());
        mEtZhuanjiaArea.setText(kwDisplayApplication.getShelfArea());
        mEtLocationExplain.setText(kwDisplayApplication.getShelfDescription());
        mEtCostBudget.setText(kwDisplayApplication.getShelfBudget());

        mEtBaozhuNum.setText(kwDisplayApplication.getStackingNumber());
        mEtDispalyStyle.setText(kwDisplayApplication.getStackingForm());
        mEtBazozhuLocationExplain.setText(kwDisplayApplication.getStackingArea());
        mEtBaozhuCostBudget.setText(kwDisplayApplication.getStackingBudget());

        mEtOtherNum.setText(kwDisplayApplication.getOtherNumber());
        mEtOtherDispalyStyle.setText(kwDisplayApplication.getOtherForm());
        mEtOtherLocationExplain.setText(kwDisplayApplication.getOtherArea());
        mEtOtherCostBudget.setText(kwDisplayApplication.getOtherBudget());

    }

    private void initAdapter(List<ImageDataUrlBean> list) {
        CommBackBigAdapter mPicBackAdapter = new CommBackBigAdapter(getContext(), list);
        this.mSubmitAdapter = mPicBackAdapter;
        mMgvJoinSiteDetail.setAdapter(mPicBackAdapter);
        mMgvJoinSiteDetail.setEmptyView(mLlEmpty);
        if (mPicBackAdapter.getCount() != 0) {
            mRlDisplayMarkPlace.setVisibility(View.GONE);
        }
    }

    private void initProductAdapter() {
        mLvOrderDetail.setEmptyView(mLlEmpty);
        KwCommProductAdapter kwCommProductAdapter = new KwCommProductAdapter(getContext(), mUpdateProductList);
        mLvOrderDetail.setAdapter(kwCommProductAdapter);
    }

    /**
     * 下一个界面保存的图片返回
     *
     * @param event
     */
    @Subscribe
    public void nextPageBackPic(BackPic2ListEvent event) {
        int backType = event.getBackType();
        List<ImageDataUrlBean> backPicList = event.getBackPicList();
        for (int i = 0; i < backPicList.size(); i++) {
            ImageTypeTitleUrlBean imageTypeTitleUrlBean = new ImageTypeTitleUrlBean("3", backPicList.get(i).getTitle(), backPicList.get(i).getImgs());
            imageList.add(imageTypeTitleUrlBean);
        }
        initAdapter(backPicList);
        initSignMark();
    }

    private void initSignMark() {
        String listJoson = mGson.toJson(imageList);
        mSingleStr = Util.replaceSingleMark(listJoson);
    }

    /**
     * 初始化要添加的照片；
     *
     * @param backPicList
     */
    private void initPicAdapter(List<ImageDataUrlBean> backPicList) {
        CommBackBigAdapter mPicBackAdapter = new CommBackBigAdapter(getContext(), backPicList);
        mMgvJoinSiteDetail.setAdapter(mPicBackAdapter);
        mMgvJoinSiteDetail.setEmptyView(mLlEmpty);
    }


    /**
     * 获取商品id和数量
     *
     * @param event
     */
    @Subscribe
    public void getProductIdAndNum(CommKwBackEvent event) {
        String json = event.getJson();
        String singleMark = Util.replaceSingleMark(json);
        this.productIdAndnum = singleMark;
        initProductAdapter();
        bottomRlPriceAndnum();
    }

    /**
     * 返回商品列表；
     *
     * @param event
     */
    @Subscribe
    public void getProductListEvent(KwChooseProductListEvent event) {
        List<CommProductAllNatureBean> list = event.getList();
        List<CommUpdateProductBean> updateProductList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String normals = list.get(i).getNormals();
            String number = list.get(i).getNumber();
            String price = list.get(i).getPrice();
            String picUrl = list.get(i).getPicUrl();
            String productName = list.get(i).getProductName();
            String smellStr = list.get(i).getSmellStr();
            String productAttributeId = list.get(i).getProductAttributeId();
            updateProductList.add(new CommUpdateProductBean(productAttributeId, number, price, smellStr, productName, normals, picUrl));
        }
        this.mUpdateProductList = updateProductList;

    }

    private void bottomRlPriceAndnum() {
        int productNum = 0;
        double totalPrice = 0;

        if ("[]".equals(productIdAndnum)) {
            mRlBottomPriceNum.setVisibility(View.GONE);
        } else {
            mRlBottomPriceNum.setVisibility(View.VISIBLE);
        }

        List<Integer> productNumList = new ArrayList<>();
        List<Double> productPriceList = new ArrayList<>();
        if (mUpdateProductList.size() != 0) {
            for (int i = 0; i < mUpdateProductList.size(); i++) {
                String number = mUpdateProductList.get(i).getQuantity();
                int numberInt = Integer.parseInt(number);
                productNumList.add(numberInt);

                String price = mUpdateProductList.get(i).getPrice();
                double priceDouble = Double.parseDouble(price);
                productPriceList.add(priceDouble * numberInt);
            }
            for (int i = 0; i < productNumList.size(); i++) {
                int num = productNumList.get(i);
                productNum += num;
            }
            for (int i = 0; i < productPriceList.size(); i++) {
                double productPrice = productPriceList.get(i);
                totalPrice += productPrice;
            }
            mTvTotalBox.setText("共" + productNum + "件商品");
            mTvTotalPrice.setText("合计：¥" + totalPrice);

            for (int i = 0; i < mUpdateProductList.size(); i++) {
                String productAttributeId = mUpdateProductList.get(i).getProductId();
                String number = mUpdateProductList.get(i).getQuantity();
                int itemNum = Integer.parseInt(number);
                String price = mUpdateProductList.get(i).getPrice();
                double priceDouble = Double.parseDouble(price);
                double passPriceDouble = priceDouble * itemNum;
                mPassList.add(new CommPassBean(productAttributeId, itemNum, passPriceDouble));
            }
        } else {
            mPassList.clear();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (view.getId()) {
            case R.id.bt_submit_modifier:
                String etDisplayName = mEtDisplayName.getText().toString().trim();
                String etSaleTarget = mEtSaleTarget.getText().toString().trim();
                String etExecuteCity = mEtExecuteCity.getText().toString().trim();
                String etExecuteRange = mEtExecuteRange.getText().toString().trim();
                String etExecuteTime = mEtExecuteTime.getText().toString().trim();
                String etFeixiaoBv = mEtFeixiaoBv.getText().toString().trim();
                String etSaleVolume = mEtSaleVolume.getText().toString().trim();

                String etZhuanjiaNum = mEtZhuanjiaNum.getText().toString().trim();
                String etZhuanjiaArea = mEtZhuanjiaArea.getText().toString().trim();
                String etLocationExplain = mEtLocationExplain.getText().toString().trim();
                String etCostBudget = mEtCostBudget.getText().toString().trim();

                String etBaozhuNum = mEtBaozhuNum.getText().toString().trim();
                String etDispalyStyle = mEtDispalyStyle.getText().toString().trim();
                String etBazozhuLocationExplain = mEtBazozhuLocationExplain.getText().toString().trim();
                String etBaozhuCostBudget = mEtBaozhuCostBudget.getText().toString().trim();

                String etOtherNum = mEtOtherNum.getText().toString().trim();
                String etOtherDispalyStyle = mEtOtherDispalyStyle.getText().toString().trim();
                String etOtherLocationExplain = mEtOtherLocationExplain.getText().toString().trim();
                String etOtherCostBudget = mEtOtherCostBudget.getText().toString().trim();

                String acSaleCost = mEtAcSaleCost.getText().toString().trim();
                String acCostBilv = mEtCostBilv.getText().toString().trim();

                boolean displayName = TextUtils.isEmpty(etDisplayName);
                boolean saleTarget = TextUtils.isEmpty(etSaleTarget);
                boolean executeCity = TextUtils.isEmpty(etExecuteCity);
                boolean executeRange = TextUtils.isEmpty(etExecuteRange);
                boolean executeTime = TextUtils.isEmpty(etExecuteTime);
                boolean feixiaoBv = TextUtils.isEmpty(etFeixiaoBv);
                boolean saleVolume = TextUtils.isEmpty(etSaleVolume);
                boolean zhuanjiaNum = TextUtils.isEmpty(etZhuanjiaNum);
                boolean locationExplain = TextUtils.isEmpty(etLocationExplain);
                boolean baozhuNum = TextUtils.isEmpty(etBaozhuNum);
                boolean costBudget = TextUtils.isEmpty(etCostBudget);
                boolean dispalyStyle = TextUtils.isEmpty(etDispalyStyle);
                boolean bazozhuLocationExplain = TextUtils.isEmpty(etBazozhuLocationExplain);
                boolean baozhuCostBudget = TextUtils.isEmpty(etBaozhuCostBudget);
                boolean otherNum = TextUtils.isEmpty(etOtherNum);
                boolean otherDispalyStyle = TextUtils.isEmpty(etOtherDispalyStyle);
                boolean otherLocationExplain = TextUtils.isEmpty(etOtherLocationExplain);
                boolean otherCostBudget = TextUtils.isEmpty(etOtherCostBudget);
                boolean zhuanjiaArea = TextUtils.isEmpty(etZhuanjiaArea);
                boolean emptyAcSaleCost = TextUtils.isEmpty(acSaleCost);
                boolean emptyAcCostBilv = TextUtils.isEmpty(acCostBilv);

                if ("0".equals(etFeixiaoBv)) {
                    Util.toast(getContext(), "费效比率不能为0");
                    return;
                }


                if (displayName || saleTarget || executeCity || executeRange || executeTime || feixiaoBv
                        || saleVolume || zhuanjiaNum || locationExplain || baozhuNum || costBudget || dispalyStyle ||
                        bazozhuLocationExplain || baozhuCostBudget || otherNum || otherDispalyStyle || otherLocationExplain ||
                        otherCostBudget || zhuanjiaArea || emptyAcSaleCost || emptyAcCostBilv) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }
                boolean checked1 = mCb1.isChecked();
                boolean checked2 = mCb2.isChecked();
                boolean checked3 = mCb3.isChecked();
                boolean checked4 = mCb4.isChecked();
                boolean checked5 = mCb5.isChecked();

                if (checked1 == false && checked2 == false && checked3 == false && checked4 == false && checked5 == false) {
                    Util.toast(getContext(), "请选择活动目的");
                    return;
                }

                int visibility = mRlBottomPriceNum.getVisibility();
                if (visibility != View.VISIBLE) {
                    Util.toast(getContext(), "您还未选择商品");
                    return;
                }


                StringBuffer stringBuffer = new StringBuffer();
                if (checked1) {
                    stringBuffer.append("1").append(",");
                }
                if (checked2) {
                    stringBuffer.append("2").append(",");
                }
                if (checked3) {
                    stringBuffer.append("3").append(",");
                }
                if (checked4) {
                    stringBuffer.append("4").append(",");
                }
                if (checked5) {
                    stringBuffer.append("5");
                }
                if (mSubmitAdapter == null) {
                    isUpLoadPic = false;
                } else {
                    if (mSubmitAdapter.getCount() == 0) {
                        isUpLoadPic = false;
                    }
                }
                //活动目的；
                String activityObjective = stringBuffer.toString();

                String text = (String) mBtSubmitModifier.getText();


                switch (text) {
                    case "提交":
                        submitDisplayApply(etDisplayName, activityObjective, etSaleTarget, etExecuteCity,
                                etExecuteRange, etExecuteTime, etZhuanjiaNum, etZhuanjiaArea, etLocationExplain, etCostBudget,
                                etBaozhuNum, etDispalyStyle, etBazozhuLocationExplain, etBaozhuCostBudget, etOtherNum, etOtherDispalyStyle,
                                etOtherLocationExplain, etOtherCostBudget, etSaleVolume, etFeixiaoBv, acSaleCost, acCostBilv);
                        break;
                    case "确认修改":
                        confirmUpdate(etDisplayName, activityObjective, etSaleTarget, etExecuteCity,
                                etExecuteRange, etExecuteTime, etZhuanjiaNum, etZhuanjiaArea, etLocationExplain, etCostBudget,
                                etBaozhuNum, etDispalyStyle, etBazozhuLocationExplain, etBaozhuCostBudget, etOtherNum, etOtherDispalyStyle,
                                etOtherLocationExplain, etOtherCostBudget, etSaleVolume, etFeixiaoBv, acSaleCost, acCostBilv);
                        break;
                }
                break;
            case R.id.rl_kuwo_sc:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.DISPLAY_APPLY_EDIT);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.rl_mark_photo_look:
                if (Constant.DISPLAY_APPLY_UPDATE.equals(mJoinWay)) {
                    intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                    intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_APPLY_MARK_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mOrderId);
                    startActivity(intent);
                } else {
                    intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                    intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_APPLY_MARK_SUBMIT);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 修改产品成列
     *
     * @param etDisplayName
     * @param activityObjective
     * @param etSaleTarget
     * @param etExecuteCity
     * @param etExecuteRange
     * @param etExecuteTime
     * @param etZhuanjiaNum
     * @param etZhuanjiaArea
     * @param etLocationExplain
     * @param etCostBudget
     * @param etBaozhuNum
     * @param etDispalyStyle
     * @param etBazozhuLocationExplain
     * @param etBaozhuCostBudget
     * @param etOtherNum
     * @param etOtherDispalyStyle
     * @param etOtherLocationExplain
     * @param etOtherCostBudget
     * @param etSaleVolume
     * @param etFeixiaoBv
     */
    private void confirmUpdate(String etDisplayName, String activityObjective, String etSaleTarget, String etExecuteCity, String etExecuteRange, String etExecuteTime, String etZhuanjiaNum, String etZhuanjiaArea, String etLocationExplain, String etCostBudget,
                               String etBaozhuNum, String etDispalyStyle, String etBazozhuLocationExplain, String etBaozhuCostBudget, String etOtherNum,
                               String etOtherDispalyStyle, String etOtherLocationExplain, String etOtherCostBudget, String etSaleVolume,
                               String etFeixiaoBv, String acSaleCost, String acCostBilv) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.PRODUCT_DISPLAY_UPDATE + "id=" + mOrderId + "&user.id=" + mUsrId + "&name=" + etDisplayName + "&objective=" + activityObjective +
                "&target=" + etSaleTarget + "&city=" + etExecuteCity + "&ranges=" + etExecuteRange + "&executionTime=" + etExecuteTime +
                "&shelfNumber=" + etZhuanjiaNum + "&shelfArea=" + etZhuanjiaArea + "&shelfDescription=" + etLocationExplain + "&shelfBudget=" + etCostBudget +
                "&stackingNumber=" + etBaozhuNum + "&stackingForm=" + etDispalyStyle + "&stackingArea=" + etBazozhuLocationExplain + "&stackingBudget=" + etBaozhuCostBudget +
                "&otherNumber=" + etOtherNum + "&otherForm=" + etOtherDispalyStyle + "&otherArea=" + etOtherLocationExplain + "&otherBudget=" + etOtherCostBudget + "&salesVolume=" + etSaleVolume +
                "&ratio=" + etFeixiaoBv + "&displayAProductList=" + productIdAndnum + "&activitySales=" + acSaleCost + "&activityRatio=" + acCostBilv);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "提交成功");
                    getActivity().finish();
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

    /***
     * 提交产品成列数据到后台；
     *
     * @param etDisplayName
     * @param activityObjective
     * @param etSaleTarget
     * @param etExecuteCity
     * @param etExecuteRange
     * @param etExecuteTime
     * @param etZhuanjiaNum
     * @param etZhuanjiaArea
     * @param etLocationExplain
     * @param etCostBudget
     * @param etBaozhuNum
     * @param etDispalyStyle
     * @param etBazozhuLocationExplain
     * @param etBaozhuCostBudget
     * @param etOtherNum
     * @param etOtherDispalyStyle
     * @param etOtherLocationExplain
     * @param etOtherCostBudget
     * @param etSaleVolume
     * @param etFeixiaoBv
     */
    private void submitDisplayApply(String etDisplayName, String activityObjective, String etSaleTarget, String etExecuteCity, String etExecuteRange, String etExecuteTime, String etZhuanjiaNum, String etZhuanjiaArea, String etLocationExplain, String etCostBudget, String etBaozhuNum, String etDispalyStyle, String etBazozhuLocationExplain, String etBaozhuCostBudget, String etOtherNum, String etOtherDispalyStyle, String etOtherLocationExplain,
                                    String etOtherCostBudget, String etSaleVolume, String etFeixiaoBv, String acSaleCost, String acCostBilv) {
        String userId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        showLoading();
        String isUpLoadStr = "";
        if (isUpLoadPic) {
            isUpLoadStr = "&displayAPhotoList=" + mSingleStr;
        }
        String removePrecent = Util.removePrecent(HttpConstant.SUBMIT_DISPLAY_APPLY + "user.id=" + userId + "&name=" + etDisplayName + "&objective=" + activityObjective +
                "&target=" + etSaleTarget + "&city=" + etExecuteCity + "&ranges=" + etExecuteRange + "&executionTime=" + etExecuteTime +
                "&shelfNumber=" + etZhuanjiaNum + "&shelfArea=" + etZhuanjiaArea + "&shelfDescription=" + etLocationExplain + "&shelfBudget=" + etCostBudget +
                "&stackingNumber=" + etBaozhuNum + "&stackingForm=" + etDispalyStyle + "&stackingArea=" + etBazozhuLocationExplain + "&stackingBudget=" + etBaozhuCostBudget +
                "&otherNumber=" + etOtherNum + "&otherForm=" + etOtherDispalyStyle + "&otherArea=" + etOtherLocationExplain + "&otherBudget=" + etOtherCostBudget + "&salesVolume=" + etSaleVolume +
                "&ratio=" + etFeixiaoBv + "&approvalStatus=" + "1" + "&approvalType=" + "0" + "&displayAProductList=" + productIdAndnum + isUpLoadStr + "&activitySales=" + acSaleCost + "&activityRatio=" + acCostBilv);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "提交成功");
                    getActivity().finish();
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
}
