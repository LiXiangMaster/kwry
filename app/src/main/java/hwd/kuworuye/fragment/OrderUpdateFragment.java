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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import hwd.kuworuye.adapter.OrderDetailAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.ChooseAreaBean;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommUpdateProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.KwProductNatureBean;
import hwd.kuworuye.bean.OrderDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommKwBackEvent;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/29.
 */
public class OrderUpdateFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.bt_bottom_com)
    Button mBtBottomCom;
    Unbinder unbinder;
    @BindView(R.id.lv_order_detail)
    ListView mLvOrderDetail;
    @BindView(R.id.rl_kuwo_sc)
    RelativeLayout mRlKuwoSc;
    @BindView(R.id.ll_fist_choose_address)
    LinearLayout mLlFistChooseAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @BindView(R.id.tv_choose_address)
    TextView mTvChooseAddress;
    @BindView(R.id.tv_contact_name)
    TextView mTvContactName;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_detail_address)
    TextView mTvDetailAddress;
    private List<Double> allWeightList = new ArrayList<>();
    private double allWeight;

    private String productJson = "";
    List<CommProductAllNatureBean> mList = new ArrayList<>();
    private KwCommProductAdapter mAdapter;
    private RelativeLayout mRlBottom;
    private TextView mTvTotalBox;
    private TextView mTvTotalPrice;
    private TextView mTvTotalPriceBottom;
    private TextView mTvTotalBoxBottom;
    private EditText mEtRemark;
    private String mOrderId;
    private String submitTotalPrice;
    private String submitTotalNum;
    private String mUserId;
    private Gson mGson;
    private List<CommPassBean> mPassList = new ArrayList<>();


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_comm_order_submit_update;
    }

    public static Fragment newInstance(Bundle bundle) {
        OrderUpdateFragment orderFragment = new OrderUpdateFragment();
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mRlBottom = ((RelativeLayout) rootView.findViewById(R.id.rl_bottom_price_num));
        mTvTotalBox = ((TextView) rootView.findViewById(R.id.tv_total_box));
        mTvTotalPrice = ((TextView) rootView.findViewById(R.id.tv_total_price));
        mTvTotalPriceBottom = ((TextView) rootView.findViewById(R.id.tv_bottom_total_price));
        mTvTotalBoxBottom = ((TextView) rootView.findViewById(R.id.tv__bottom_total_box));
        mEtRemark = ((EditText) rootView.findViewById(R.id.et_remark));
        showDetail2View();
        initData();
        return rootView;
    }

    /**
     * 获取网络详情展示到控件上面；
     */
    private void showDetail2View() {
        showLoading();
        OkHttpUtils.get(HttpConstant.ORDER_DETAIL + "orderId=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                OrderDetailBean orderDetailBean = mGson.fromJson(s, OrderDetailBean.class);
                if (orderDetailBean.isSuccess()) {
                    ShowData2View(orderDetailBean);
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
     * 显示数据到控件上面；
     *
     * @param orderDetailBean
     */
    private List<KwProductNatureBean> initJsonList = new ArrayList<>();

    private void ShowData2View(OrderDetailBean orderDetailBean) {
        List<OrderDetailBean.ItemListBean> itemList = orderDetailBean.getItemList();
        OrderDetailBean.KwOrderBean kwOrder = orderDetailBean.getKwOrder();
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                int quantity = itemList.get(i).getQuantity();
                String quantityStr = String.valueOf(quantity);
                double weight = itemList.get(i).getWeight();
                String weightStr = String.valueOf(weight);
                double price = itemList.get(i).getPrice();
                String priceStr = String.valueOf(price);
                KwProductNatureBean kwProductNatureBean = new KwProductNatureBean(priceStr, itemList.get(i).getProductAttributeId(), quantityStr, itemList.get(i).getProductName(), weightStr);
                initJsonList.add(kwProductNatureBean);
            }
            String initJson = mGson.toJson(initJsonList);
            String s = Util.replaceSingleMark(initJson);

            for (int i = 0; i < itemList.size(); i++) {
                String productAttributeId = itemList.get(i).getProductAttributeId();
                int quantity = itemList.get(i).getQuantity();
                double price = itemList.get(i).getPrice();
                String priceStr = String.valueOf(price);
                double priceDouble = Double.parseDouble(priceStr);
                double passPriceDouble = priceDouble * quantity;
                mPassList.add(new CommPassBean(productAttributeId, quantity, passPriceDouble));
            }
            this.productJson = s;
        }

        OrderDetailAdapter adapter = new OrderDetailAdapter(getActivity(), itemList);
        mLvOrderDetail.setAdapter(adapter);
        mLvOrderDetail.setEmptyView(mLlEmpty);

        if (itemList.size() != 0) {
            mRlBottom.setVisibility(View.VISIBLE);
        }

        int totalBox = kwOrder.getTotalBox();
        double totalPrice = kwOrder.getTotalPrice();

        this.submitTotalPrice = String.valueOf(totalPrice);
        this.submitTotalNum = String.valueOf(totalBox);

        mTvTotalBox.setText("共" + totalBox + "件商品");
        mTvTotalPrice.setText("合计：¥" + totalPrice);

        mTvTotalPriceBottom.setText("" + totalPrice);
        mTvTotalBoxBottom.setText("共" + totalBox + "件商品");

        mEtRemark.setText(kwOrder.getOrderMemo());
        mTvContactName.setText(kwOrder.getUserName());
        mTvPhoneNumber.setText(kwOrder.getUserPhone());
        mTvDetailAddress.setText(kwOrder.getUserAddress());
    }

    @Override
    public void onResume() {
        super.onResume();
        ChooseAreaBean chooseAreaBean = (ChooseAreaBean) Util.readObj(getContext(), Constant.FIRST_CHOOSE);
        if (chooseAreaBean != null) {
            mLlAddress.setVisibility(View.VISIBLE);
            mTvContactName.setText(chooseAreaBean.getName());
            mTvPhoneNumber.setText(chooseAreaBean.getPhoneNumber());
            mTvDetailAddress.setText(chooseAreaBean.getAddress());
        } else {
//            mLlAddress.setVisibility(View.INVISIBLE);
            mLlFistChooseAddress.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        mBtBottomCom.setText(getResources().getString(R.string.order_update));
        mBtBottomCom.setOnClickListener(this);
        mLlFistChooseAddress.setOnClickListener(this);
        mRlKuwoSc.setOnClickListener(this);
        initAdapter();
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
        mLvOrderDetail.setEmptyView(mLlEmpty);
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
            case R.id.bt_bottom_com:
                String contantName = (String) mTvContactName.getText();
                String contantPhone = (String) mTvPhoneNumber.getText();
                String contantAddress = (String) mTvDetailAddress.getText();

                String etRemark = mEtRemark.getText().toString().trim();
                int visibility = mLlAddress.getVisibility();
                if (visibility == View.INVISIBLE) {
                    Util.toast(getContext(), "请选择收货地址");
                    return;
                }
                if (TextUtils.isEmpty(etRemark)) {
                    Util.toast(getContext(), "请填写备注信息");
                    return;
                }
                if (mRlBottom.getVisibility() != View.VISIBLE) {
                    Util.toast(getContext(), "您还未选择商品");
                    return;
                }
                for (int i = 0; i < mList.size(); i++) {
                    String weight = mList.get(i).getWeight();
                    String number = mList.get(i).getNumber();
                    int numberInt = Integer.parseInt(number);
                    Double weightDouble = Double.parseDouble(weight);
                    allWeightList.add(weightDouble*numberInt);
                }
                for (int i = 0; i < allWeightList.size(); i++) {
                    double weightNum = allWeightList.get(i);
                    allWeight += weightNum;
                }

                String allWeightProduct = String.valueOf(allWeight);
                popupLoginOutTip(allWeightProduct, contantName, contantPhone, contantAddress, submitTotalNum, submitTotalPrice, etRemark);
                break;
            case R.id.rl_kuwo_sc:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.UPDATE_ORDER);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.ll_fist_choose_address:
                Intent intentAddress = new Intent(getContext(), ContentActivity.class);
                intentAddress.putExtra(Constant.CONTENT_TYPE, Constant.AREA_MANAGER);
                intentAddress.putExtra(Constant.AREA_MANAGER_JOIN_WAY, Constant.SUBMIT_ORDER);
                startActivity(intentAddress);
                break;
        }
    }

    /**
     * 弹出修改提示；
     *
     * @param allWeightProduct
     * @param contantName
     * @param contantPhone
     * @param contantAddress
     * @param totalProductNum
     * @param totalProductPrice
     * @param etRemark
     */
    private void popupLoginOutTip(final String allWeightProduct, final String contantName, final String contantPhone, final String contantAddress, final String totalProductNum, final String totalProductPrice, final String etRemark) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_tip, null);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText("确定要修改订单？");
        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_delete_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_delete_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrder2Net(allWeightProduct, contantName, contantPhone, contantAddress, totalProductNum, totalProductPrice, etRemark);
                dialog.dismiss();
            }
        });

    }

    /**
     * 修改订单接口；
     *
     * @param allWeightProduct
     * @param contantName
     * @param contantPhone
     * @param contantAddress
     * @param totalProductNum
     * @param totalProductPrice
     * @param etRemark
     */
    private void updateOrder2Net(String allWeightProduct, String contantName, String contantPhone, String contantAddress, String totalProductNum, String totalProductPrice, String etRemark) {
        showLoading();
        String needEncode = HttpConstant.UPDATE_ORDER + "id=" + mOrderId + "&user.id=" + mUserId + "&userName=" + contantName + "&userPhone=" + contantPhone +
                "&userAddress=" + contantAddress + "&totalBox=" + totalProductNum + "&totalPrice=" + totalProductPrice +
                "&totalWeight=" + allWeightProduct + "&orderMemo=" + etRemark + "&kwOrderItemList=" + productJson;
        String removePrecentStr = Util.removePrecent(needEncode);
        OkHttpUtils.post(removePrecentStr).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "订单修改成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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
     * 返回一个被选中的产品集合；
     *
     * @param event
     */

    @Subscribe
    public void getKwChooseProductList(KwChooseProductListEvent event) {
        List<CommProductAllNatureBean> list = event.getList();
        this.mList = list;
        initAdapter();
        mAdapter.notifyDataSetChanged();
        bottomRlPriceAndnum();
    }


    private void bottomRlPriceAndnum() {

        List<Integer> productNumList = new ArrayList<>();
        List<Double> productPriceList = new ArrayList<>();
        int productNum = 0;
        double totalPrice = 0;
        if (mList.size() == 0) {
            mRlBottom.setVisibility(View.GONE);
        } else {
            mRlBottom.setVisibility(View.VISIBLE);
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
                int num = (Integer) productNumList.get(i);
                productNum += num;
            }
            for (int i = 0; i < productPriceList.size(); i++) {
                double productPrice = (Double) productPriceList.get(i);
                totalPrice += productPrice;
            }
            this.submitTotalPrice = String.valueOf(totalPrice);
            this.submitTotalNum = String.valueOf(productNum);
            mTvTotalBox.setText("共" + productNum + "件商品");
            mTvTotalPrice.setText("合计：¥" + totalPrice);
            mTvTotalPriceBottom.setText("" + totalPrice);
            mTvTotalBoxBottom.setText("共" + productNum + "件商品");

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
            mTvTotalPriceBottom.setText("" + totalPrice);
            mTvTotalBoxBottom.setText("共" + productNum + "件商品");
        }
    }

    /**
     * 得到一个产品json字符串 单引号；
     *
     * @param event
     */
    @Subscribe
    public void kwCommEvent(CommKwBackEvent event) {
        String json = event.getJson();
        String singleMark = Util.replaceSingleMark(json);
        productJson = singleMark;
    }

}
