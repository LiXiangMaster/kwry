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
import android.widget.ImageView;
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
import hwd.kuworuye.adapter.KwCommProductAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommUpdateProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommKwBackEvent;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyListView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/7.
 */
public class SaleAcAySubmitFragment extends FBaseFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.et_activity_name)
    EditText mEtActivityName;
    @BindView(R.id.iv_shangchen)
    ImageView mIvShangchen;
    @BindView(R.id.rl_kuwo_sc)
    RelativeLayout mRlKuwoSc;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.lv_order_detail)
    MyListView mLvOrderDetail;
    @BindView(R.id.tv_total_box)
    TextView mTvTotalBox;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rl_bottom_price_num)
    RelativeLayout mRlBottomPriceNum;
    @BindView(R.id.cb_1)
    CheckBox mCb1;
    @BindView(R.id.cb_2)
    CheckBox mCb2;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.cb_3)
    CheckBox mCb3;
    @BindView(R.id.cb_4)
    CheckBox mCb4;
    @BindView(R.id.cb_5)
    CheckBox mCb5;
    @BindView(R.id.et_sale_target)
    EditText mEtSaleTarget;
    @BindView(R.id.et_city)
    EditText mEtCity;
    @BindView(R.id.et_execute_range)
    EditText mEtExecuteRange;
    @BindView(R.id.et_execute_time)
    EditText mEtExecuteTime;
    @BindView(R.id.et_execute_shape)
    EditText mEtExecuteShape;
    @BindView(R.id.et_materiel)
    EditText mEtMateriel;
    @BindView(R.id.et_cost_bilv)
    EditText mEtCostBilv;
    @BindView(R.id.et_sale_cost)
    EditText mEtSaleCost;
    @BindView(R.id.bt_submit_sale_activity)
    Button mBtSubmitSaleActivity;
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

    @BindView(R.id.et_ac_cost_bilv)
    EditText mEtAcCostBilv;
    @BindView(R.id.et_ac_sale_cost)
    EditText mEtAcSaleCost;

    private List<CommPassBean> mPassList = new ArrayList<>();
    private List<CommProductAllNatureBean> mList = new ArrayList<>();
    private String productIdAndnum = "";
    private String mUsrId;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_apply_for_activity_submit;
    }

    public static Fragment newInstance(Bundle bundle) {
        SaleAcAySubmitFragment fragment = new SaleAcAySubmitFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        initData();
        return rootView;
    }

    private void initData() {
        mUsrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mRlKuwoSc.setOnClickListener(this);
        mBtSubmitSaleActivity.setOnClickListener(this);
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
        initAdapter();
    }

    private void initAdapter() {
        List<CommUpdateProductBean> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            String normals = mList.get(i).getNormals();
            String price = mList.get(i).getPrice();
            String picUrl = mList.get(i).getPicUrl();
            String productName = mList.get(i).getProductName();
            String smellStr = mList.get(i).getSmellStr();
            String number = mList.get(i).getNumber();
            String productAttributeId = mList.get(i).getProductAttributeId();
            list.add(new CommUpdateProductBean(productAttributeId, number, price, smellStr, productName, normals, picUrl));
        }
        mLvOrderDetail.setEmptyView(mLlEmpty);
        KwCommProductAdapter kwCommProductAdapter = new KwCommProductAdapter(getContext(), list);
        mLvOrderDetail.setAdapter(kwCommProductAdapter);
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
        switch (view.getId()) {
            case R.id.rl_kuwo_sc:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.APPLY_FOR_ACTIVITY_SUBMIT);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.bt_submit_sale_activity:
                String activityName = mEtActivityName.getText().toString().trim();
                String saleTarget = mEtSaleTarget.getText().toString().trim();
                String city = mEtCity.getText().toString().trim();
                String range = mEtExecuteRange.getText().toString().trim();
                String executeTiem = mEtExecuteTime.getText().toString().trim();
                String materiel = mEtMateriel.getText().toString().trim();
                String costBilv = mEtCostBilv.getText().toString().trim();
                String saleCost = mEtSaleCost.getText().toString().trim();
                String contentShape = mEtExecuteShape.getText().toString().trim();
                String acBilv = mEtAcCostBilv.getText().toString().trim();
                String acSaleNum = mEtAcSaleCost.getText().toString().trim();

                boolean emptyActivityName = TextUtils.isEmpty(activityName);
                boolean emptySaleTarget = TextUtils.isEmpty(saleTarget);
                boolean emptyCity = TextUtils.isEmpty(city);
                boolean emptyRange = TextUtils.isEmpty(range);
                boolean emptyExecuteTiem = TextUtils.isEmpty(executeTiem);
                boolean emptyMateriel = TextUtils.isEmpty(materiel);
                boolean emptyCostBilv = TextUtils.isEmpty(costBilv);
                boolean emptySaleCost = TextUtils.isEmpty(saleCost);
                boolean emptyContentShape = TextUtils.isEmpty(contentShape);
                boolean emptyBilv = TextUtils.isEmpty(acBilv);
                boolean emptyAcSaleNum = TextUtils.isEmpty(acSaleNum);

                boolean checked1 = mCb1.isChecked();
                boolean checked2 = mCb2.isChecked();
                boolean checked3 = mCb3.isChecked();
                boolean checked4 = mCb4.isChecked();
                boolean checked5 = mCb5.isChecked();



                if (emptyActivityName || emptySaleTarget || emptyCity || emptyRange ||
                        emptyExecuteTiem || emptyMateriel || emptyCostBilv ||
                        emptySaleCost || emptyContentShape || emptyBilv || emptyAcSaleNum) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }
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

                double costBilvDouble = Double.parseDouble(costBilv);
                if (costBilvDouble <= 0) {
                    Util.toast(getContext(), "费效比率需大于0");
                    return;
                }

                //活动目的；
                String activityObjective = stringBuffer.toString();
                submitSaleActivity(activityName, activityObjective, saleTarget, executeTiem, city, range, contentShape, materiel, saleCost, costBilv, acSaleNum, acBilv);

        }
    }


    /**
     * 提交申请；
     *
     * @param activityName
     * @param saleTarget
     * @param executeTiem
     * @param city
     * @param range
     * @param materiel
     * @param saleCost
     * @param costBilv
     */
    private void submitSaleActivity(String activityName, String activityObjective, String saleTarget, String executeTiem, String city, String range, String contentShape, String materiel, String saleCost, String costBilv, String acSaleNum, String acBilv) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.SUBMIT_APPLY_SALE_ACTIVITY + "user.id=" + mUsrId + "&name=" + activityName + "&objective=" + activityObjective +
                "&target=" + saleTarget + "&executionTime=" + executeTiem + "&city=" + city + "&ranges=" + range +
                "&content=" + contentShape + "&materiel=" + materiel + "&salesVolume=" + saleCost + "&ratio=" + costBilv + "&approvalStatus=" + "1" +
                "&approvalType=" + "0" + "&promotionAProductList=" + productIdAndnum + "&activitySales=" + acSaleNum + "&activityRatio=" + acBilv);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    Util.toast(getContext(), "提交成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(success));
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

    /**
     * 返回商品列表；
     *
     * @param event
     */
    @Subscribe
    public void getProductListEvent(KwChooseProductListEvent event) {
        List<CommProductAllNatureBean> list = event.getList();
        this.mList = list;
        initAdapter();
        bottomRlPriceAndnum(list);
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
    }

    private void bottomRlPriceAndnum(List<CommProductAllNatureBean> list) {
        int productNum = 0;
        double totalPrice = 0;
        if (list.size() == 0) {
            mRlBottomPriceNum.setVisibility(View.GONE);
        } else {
            mRlBottomPriceNum.setVisibility(View.VISIBLE);
        }
        List<Integer> productNumList = new ArrayList<>();
        List<Double> productPriceList = new ArrayList<>();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String number = list.get(i).getNumber();
                int numberInt = Integer.parseInt(number);
                productNumList.add(numberInt);

                String price = list.get(i).getPrice();
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

            for (int i = 0; i < mList.size(); i++) {
                String productAttributeId = mList.get(i).getProductAttributeId();
                String number = mList.get(i).getNumber();
                int itemNum = Integer.parseInt(number);
                String price = mList.get(i).getPrice();
                double priceDouble = Double.parseDouble(price);
                double passPriceDouble = priceDouble * itemNum;
                mPassList.add(new CommPassBean(productAttributeId, itemNum, passPriceDouble));
            }
        } else {
            mPassList.clear();
        }
    }
}
