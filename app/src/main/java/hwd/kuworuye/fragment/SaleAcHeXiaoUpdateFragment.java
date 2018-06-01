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
import hwd.kuworuye.bean.SaleActivityXiaoHeDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommHeXiaoRefrestEvent;
import hwd.kuworuye.event.CommKwBackEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyGridView;
import hwd.kuworuye.weight.MyListView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/7.
 */
public class SaleAcHeXiaoUpdateFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.et_activity_name)
    EditText mEtActivityName;
    @BindView(R.id.rl_kuwo_sc)
    RelativeLayout mRlKuwoSc;

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

    @BindView(R.id.rl_activity_site_pic_manager)
    RelativeLayout mRlActivitySitePicManager;

    @BindView(R.id.mgv_activity_site)
    MyGridView mMgvActivitySite;

    @BindView(R.id.rl_ticket_manager)
    RelativeLayout mRlTicketManager;

    @BindView(R.id.mgv_ticket_manage)
    MyGridView mMgvTicketManage;

    @BindView(R.id.bt_submit_sale_activity)
    Button mBtSubmitSaleActivity;
    Unbinder unbinder;
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
    @BindView(R.id.et_ac_cost_bilv)
    EditText mEtAcCostBilv;

    private String productIdAndnum = "";

    private String mOrderId;
    private String mUserId;
    private String submitTotalPrice;
    private String submitTotalNum;

    private KwCommProductAdapter mAdapter;
    List<CommProductAllNatureBean> mList = new ArrayList<>();
    private Gson mGson;
    private RelativeLayout mRlSitePlace;
    private RelativeLayout mRlTicketPlace;
    private List<CommPassBean> mPassList = new ArrayList<>();

    @Override
    protected int inflateContentView() {

        return R.layout.fragment_sale_activity_xiaohe_update;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mRlSitePlace = ((RelativeLayout) rootView.findViewById(R.id.rl_sale_site_hx_place));
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_sale_hx_ticket_place));

        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
            requestNet2Detail(mOrderId);
        }
        initData();
        mGson = new Gson();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        requestNet2Site();
        requestNet2TicketPic();
    }

    /**
     * 票据图片
     */
    private void requestNet2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "2").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> ticketList = pageList.getList();
                initTicketData(ticketList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 活动现场；
     */
    private void requestNet2Site() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "1").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> siteList = pageList.getList();
                initSiteData(siteList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 票据；
     *
     * @param ticketList
     */
    private void initTicketData(List<CommPicListBean.PageListBean.ListBean> ticketList) {

        List<ImageDataUrlBean> ticketPicUrlTitle = new ArrayList<>();
        if (ticketList != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < ticketList.size(); i++) {
                String imgs = ticketList.get(i).getImgs();
                String title = ticketList.get(i).getTitle();
                ticketPicUrlTitle.add(new ImageDataUrlBean(imgs, title));
            }
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter ticketAdapter = new CommBackBigAdapter(getContext(), ticketPicUrlTitle);
        mMgvTicketManage.setAdapter(ticketAdapter);
    }

    /**
     * 现场；
     *
     * @param
     */
    private void initSiteData(List<CommPicListBean.PageListBean.ListBean> siteList) {

        List<ImageDataUrlBean> sitePicUrlTitle = new ArrayList<>();
        if (siteList != null) {
            mRlSitePlace.setVisibility(View.GONE);
            for (int i = 0; i < siteList.size(); i++) {
                String imgs = siteList.get(i).getImgs();
                String title = siteList.get(i).getTitle();
                sitePicUrlTitle.add(new ImageDataUrlBean(imgs, title));
            }
        } else {
            mRlSitePlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter siteAdapter = new CommBackBigAdapter(getContext(), sitePicUrlTitle);
        mMgvActivitySite.setAdapter(siteAdapter);
    }

    public static Fragment newInstance(Bundle bundle) {
        SaleAcHeXiaoUpdateFragment fragment = new SaleAcHeXiaoUpdateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initData() {
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mRlKuwoSc.setOnClickListener(this);
        mBtSubmitSaleActivity.setOnClickListener(this);
        mRlActivitySitePicManager.setOnClickListener(this);
        mRlTicketManager.setOnClickListener(this);

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

    private void initAdapter() {
        List<CommUpdateProductBean> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            String normals = mList.get(i).getNormals();
            String picUrl = mList.get(i).getPicUrl();
            String smellStr = mList.get(i).getSmellStr();
            String productName = mList.get(i).getProductName();
            String price = mList.get(i).getPrice();
            String number = mList.get(i).getNumber();
            String productAttributeId = mList.get(i).getProductAttributeId();
            list.add(new CommUpdateProductBean(productAttributeId, number, price, smellStr, productName, normals, picUrl));
        }
        mAdapter = new KwCommProductAdapter(getActivity(), list);
        mLvOrderDetail.setAdapter(mAdapter);

        bottomRlPriceAndnum();
    }


    /**
     * 请求接口获取详情数据；
     *
     * @param orderId
     */
    private void requestNet2Detail(String orderId) {
        showLoading();
        OkHttpUtils.get(HttpConstant.SALE_XIAOHE_DETAIL + "id=" + orderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SaleActivityXiaoHeDetailBean saleActivityXiaoHeDetailBean = mGson.fromJson(s, SaleActivityXiaoHeDetailBean.class);
                boolean success = saleActivityXiaoHeDetailBean.isSuccess();
                if (success) {
                    initData(saleActivityXiaoHeDetailBean);
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


    private void initData(SaleActivityXiaoHeDetailBean saleActivityXiaoHeDetailBean) {
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        SaleActivityXiaoHeDetailBean.KwPromotionExamineBean kwPromotionExamine = saleActivityXiaoHeDetailBean.getKwPromotionExamine();
        List<SaleActivityXiaoHeDetailBean.ItemListBean> itemList = saleActivityXiaoHeDetailBean.getItemList();
        List<String> objectiveList = kwPromotionExamine.getObjectiveList();
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

        mEtAcSaleCost.setText(kwPromotionExamine.getActivitySales());
        mEtAcCostBilv.setText(kwPromotionExamine.getActivityRatio());
        mEtExecuteRange.setText(kwPromotionExamine.getRanges());
        mEtSaleTarget.setText(kwPromotionExamine.getTarget());
        mEtActivityName.setText(kwPromotionExamine.getName());
        mEtCity.setText(kwPromotionExamine.getCity());
        mEtExecuteTime.setText(kwPromotionExamine.getExecutionTime());
        mEtExecuteShape.setText(kwPromotionExamine.getContent());
        mEtMateriel.setText(kwPromotionExamine.getMateriel());
        mEtCostBilv.setText(kwPromotionExamine.getRatio());
        mEtSaleCost.setText(kwPromotionExamine.getSalesVolume() + "");


        List<CommProductBean> productList = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            int quantity = itemList.get(i).getQuantity();
            String quantityStr = String.valueOf(quantity);
            CommProductBean commProductBean = new CommProductBean(itemList.get(i).getProductAttributeId(), quantityStr);
            String productName = itemList.get(i).getKwProductAttribute().getProduct().getProductName();
            String price = itemList.get(i).getKwProductAttribute().getPrice();
            String thumbnail = itemList.get(i).getKwProductAttribute().getThumbnail();
            String tasteStr = itemList.get(i).getKwProductAttribute().getTasteStr();
            String specificationsStr = itemList.get(i).getKwProductAttribute().getSpecificationsStr();
            String productAttributeId = itemList.get(i).getProductAttributeId();
            productList.add(commProductBean);
            mList.add(new CommProductAllNatureBean(quantityStr, productAttributeId, thumbnail, tasteStr, productName, specificationsStr, price, "0"));
        }

        initAdapter();
        String initJson = mGson.toJson(productList);
        String s = Util.replaceSingleMark(initJson);
        this.productIdAndnum = s;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (view.getId()) {
            case R.id.rl_kuwo_sc:
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
                String acSaleCost = mEtAcSaleCost.getText().toString().trim();
                String acBilv = mEtAcCostBilv.getText().toString().trim();

                boolean emptyActivityName = TextUtils.isEmpty(activityName);
                boolean emptySaleTarget = TextUtils.isEmpty(saleTarget);
                boolean emptyCity = TextUtils.isEmpty(city);
                boolean emptyRange = TextUtils.isEmpty(range);
                boolean emptyExecuteTiem = TextUtils.isEmpty(executeTiem);
                boolean emptyMateriel = TextUtils.isEmpty(materiel);
                boolean emptyCostBilv = TextUtils.isEmpty(costBilv);
                boolean emptySaleCost = TextUtils.isEmpty(saleCost);
                boolean emptyContentShape = TextUtils.isEmpty(contentShape);
                boolean emptyAcSaleCost = TextUtils.isEmpty(acSaleCost);
                boolean emptyAcBilv = TextUtils.isEmpty(acBilv);

                boolean checked1 = mCb1.isChecked();
                boolean checked2 = mCb2.isChecked();
                boolean checked3 = mCb3.isChecked();
                boolean checked4 = mCb4.isChecked();
                boolean checked5 = mCb5.isChecked();

                if (emptyActivityName || emptySaleTarget || emptyCity || emptyRange ||
                        emptyExecuteTiem || emptyMateriel || emptyCostBilv ||
                        emptySaleCost || emptyContentShape || emptyAcSaleCost || emptyAcBilv) {
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
                confirmUpdate(activityName, activityObjective, saleTarget, executeTiem, city, range, contentShape, materiel, saleCost, costBilv, acSaleCost, acBilv);
                break;
            case R.id.rl_activity_site_pic_manager:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.SALE_HEXIAO_SITE_UPDATE);
                //processid 其实就是mOrderId;
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_ticket_manager:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.SALE_HEXIAO_TICKET_UPDATE);
                //processid 其实就是mOrderId;
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
        }
    }

    /**
     * 修改订单；
     *
     * @param activityName
     * @param activityObjective
     * @param saleTarget
     * @param executeTiem
     * @param city
     * @param range
     * @param contentShape
     * @param materiel
     * @param saleCost
     * @param costBilv
     */

    private void confirmUpdate(String activityName, String activityObjective, String saleTarget, String executeTiem, String city, String range, String contentShape, String materiel, String saleCost, String costBilv, String acSaleCost, String acBilv) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.UPDATE_SALE_HEXIAO + "id=" + mOrderId + "&user.id=" + mUserId + "&name=" + activityName + "&objective=" + activityObjective +
                "&target=" + saleTarget + "&city=" + city + "&ranges=" + range + "&executionTime=" + executeTiem + "&content=" + contentShape +
                "&materiel=" + materiel + "&salesVolume=" + saleCost + "&ratio=" + costBilv + "&promotionEProductList=" + productIdAndnum + "&activitySales=" + acSaleCost + "&activityRatio=" + acBilv);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    Util.toast(getContext(), "修改成功");
                    EventBus.getDefault().post(new CommHeXiaoRefrestEvent(success));
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
    }

    private void bottomRlPriceAndnum() {
        mPassList.clear();
        List<Integer> productNumList = new ArrayList<>();
        List<Double> productPriceList = new ArrayList<>();
        int productNum = 0;
        double totalPrice = 0;
        if (mList.size() == 0) {
            mRlBottomPriceNum.setVisibility(View.GONE);
        } else {
            mRlBottomPriceNum.setVisibility(View.VISIBLE);
        }

        if (mList.size() != 0) {
            for (int i = 0; i < mList.size(); i++) {
                String number = mList.get(i).getNumber();
                int numberInt = Integer.parseInt(number);
                productNumList.add(numberInt);

                String price = mList.get(i).getPrice();
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
            this.submitTotalPrice = String.valueOf(totalPrice);
            this.submitTotalNum = String.valueOf(productNum);
            mTvTotalBox.setText("共" + productNum + "件商品");
            mTvTotalPrice.setText("合计：¥" + totalPrice);


            for (int i = 0; i < mList.size(); i++) {
                String productAttributeId = mList.get(i).getProductAttributeId();
                String number = mList.get(i).getNumber();
                int itemNum = Integer.parseInt(number);
                String price = mList.get(i).getPrice();
                double priceDouble = Double.parseDouble(price);
                double priceDoubleTotal = priceDouble * itemNum;
                mPassList.add(new CommPassBean(productAttributeId, itemNum, priceDoubleTotal));
            }
        } else {
            mPassList.clear();
        }

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
}
