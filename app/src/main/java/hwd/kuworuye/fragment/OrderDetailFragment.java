package hwd.kuworuye.fragment;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ConfirmPayActivity;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.GiftAdapter;
import hwd.kuworuye.adapter.OrderDetailAdapter;
import hwd.kuworuye.adapter.OrderInfoAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.OrderDetailBean;
import hwd.kuworuye.bean.OrderWaitSubmitBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;
import hwd.kuworuye.weight.MyListView;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static hwd.kuworuye.R.id.tv_driver_phone;

/**
 * Created by Administrator on 2017/3/7.
 */
public class OrderDetailFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_order_detail_next)
    MyListView mLvOrderDetailNext;
    Unbinder unbinder;
    @BindView(R.id.bt_confirm_take_goods)
    Button mBtConfirmTakeGoods;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.rl_product_num_price)
    RelativeLayout mRlProductNumPrice;

    @BindView(R.id.tv_all_total_price)
    TextView mTvAllTotalPrice;

    @BindView(R.id.tv_product_total_num)
    TextView mTvProductTotalNum;

    @BindView(R.id.ml_order_info)
    MyListView mMlOrderInfo;
    @BindView(R.id.bt_refuse)
    Button mBtRefuse;
    @BindView(R.id.bt_pass)
    Button mBtPass;
    @BindView(R.id.ll_refuse_pass)
    LinearLayout mLlRefusePass;

    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;

    @BindView(R.id.tv_submit_time)
    TextView mTvSubmitTime;

    @BindView(R.id.tv_order_memo)
    TextView mTvOrderMemo;

    @BindView(R.id.tv_getgoods_address)
    TextView mTvGetGoodsAddress;

    @BindView(R.id.tv_driver_name)
    TextView mTvDriverName;

    @BindView(R.id.tv_driver_phone)
    TextView mTvDriverPhone;

    @BindView(R.id.ll_approve_info)
    LinearLayout mLlApproveInfo;
    @BindView(R.id.ll_delivery_info)
    LinearLayout mLlDeliveryInfo;

    @BindView(R.id.tv_getadress_name)
    TextView mTvGetadressName;
    @BindView(R.id.tv_getadress_phone)
    TextView mTvGetadressPhone;
    @BindView(R.id.tv_refuse_reason)
    TextView mTvRefuseReason;

    @BindView(R.id.ll_refuse_reason)
    LinearLayout mLlRefuseReason;
    @BindView(R.id.lv_gift)
    MyListView mLvGift;
    @BindView(R.id.ll_gift)
    LinearLayout mLlGift;

    private View mTopBarView;
    private Topbar mTopbar;
    private String mOrderId;
    private boolean mIsShowUPdateBt;
    private String mUserId;
    private String mOrderStatus;
    private String mApprovalType;
    private String mOrderNumber;
    private String mCreateDate;
    private String mUserName;
    private String mUserPhone;
    private String mUserAddress;
    private String mOrderMemo;
    private int mTotalBox;
    private double mTotalPrice;
    private double mTotalWeight;
    private String mProductJson;
    private Gson mGson;

    public static Fragment newInstance(Bundle bundle) {
        OrderDetailFragment nextFragment = new OrderDetailFragment();
        nextFragment.setArguments(bundle);
        return nextFragment;
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_order_detail_next;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        mGson = new Gson();
        initView();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
            mIsShowUPdateBt = arguments.getBoolean(Constant.IS_HAVE_MODIFIER);
            requestNet2DetailData(mOrderId);
        }
        initTopbar();
        return rootView;
    }

    private void initView() {
        mBtRefuse.setOnClickListener(this);
        mBtPass.setOnClickListener(this);
    }

    private void requestNet2DetailData(final String orderId) {
        showLoading();
        OkHttpUtils.get(HttpConstant.ORDER_DETAIL + "orderId=" + orderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                OrderDetailBean orderDetailBean = mGson.fromJson(s, OrderDetailBean.class);
                if (orderDetailBean.isSuccess()) {
                    initData(orderDetailBean);
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

    private void initData(OrderDetailBean listBean) {
        List<OrderDetailBean.ItemListBean> listBeanList = listBean.getItemList();
        String mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        String userId = listBean.getKwOrder().getUser().getId();
        mUserName = listBean.getKwOrder().getUserName();
        mUserPhone = listBean.getKwOrder().getUserPhone();
        mUserAddress = listBean.getKwOrder().getUserAddress();
        mOrderMemo = listBean.getKwOrder().getOrderMemo();
        //赠品
        List<OrderDetailBean.GiftlistBean> giftlist = listBean.getGiftlist();
        if (giftlist != null&&giftlist.size()!=0) {
            mLlGift.setVisibility(View.VISIBLE);
            GiftAdapter giftAdapter = new GiftAdapter(getContext(), giftlist);
            mLvGift.setAdapter(giftAdapter);
        }

        List<OrderWaitSubmitBean> list = new ArrayList<>();
        if (listBeanList != null) {
            for (int i = 0; i < listBeanList.size(); i++) {
                String productAttributeId = listBeanList.get(i).getProductAttributeId();
                double price = listBeanList.get(i).getPrice();
                int quantity = listBeanList.get(i).getQuantity();
                double weight = listBeanList.get(i).getWeight();
                String productName = listBeanList.get(i).getProductName();
                String weightStr = String.valueOf(weight);
                String quantityStr = String.valueOf(quantity);
                String priceStr = String.valueOf(price);
                list.add(new OrderWaitSubmitBean(productAttributeId, productName, priceStr, quantityStr, weightStr));
            }
            mProductJson = Util.replaceSingleMark(mGson.toJson(list));
        } else {
            mRlProductNumPrice.setVisibility(View.GONE);
        }
        mOrderNumber = listBean.getKwOrder().getOrderNumber();
        mCreateDate = listBean.getKwOrder().getCreateDate();

        mOrderStatus = listBean.getKwOrder().getOrderStatus();
        mApprovalType = listBean.getKwOrder().getApprovalType();


        boolean isRefuse = "1".equals(mApprovalType);
        String orderState = Util.getOrderState(mOrderStatus, mApprovalType);
        if ((mUserId.equals(userId)) && (mIsShowUPdateBt || isRefuse)) {
            ContentActivity contentActivity = (ContentActivity) getActivity();
            contentActivity.changeTopbarRightBtnVisiable(true);
            contentActivity.changeRight2WhichPic(R.drawable.sign_modify);
        }
        if ("待审批".equals(orderState)) {
            mLlApproveInfo.setVisibility(View.GONE);
        }
        if (isRefuse) {
            mLlRefuseReason.setVisibility(View.VISIBLE);
            mTvRefuseReason.setText(listBean.getKwOrder().getRefuseReason());
        }
        if ("待收货".equals(orderState) || ("已完成".equals(orderState))) {
            mLlDeliveryInfo.setVisibility(View.VISIBLE);
            mTvDriverName.setText(listBean.getKwOrder().getDriverName());
            mTvDriverPhone.setText(listBean.getKwOrder().getDriverPhone());
        }

        if ((mUserId.equals(userId)) && ("待收货".equals(orderState))) {
            mBtConfirmTakeGoods.setText("确认收货");
        } else if ((mUserId.equals(userId)) && ("待提交".equals(orderState) || "待审批".equals(orderState) || isRefuse)) {
            mBtConfirmTakeGoods.setText("确认删除");
        } else if ((mUserId.equals(userId)) && ("待支付".equals(orderState))) {
            mBtConfirmTakeGoods.setText("确认支付");
        } else if ("待审批".equals(orderState) || "审批中".equals(orderState)) {
            String approverUserId = listBean.getKwOrder().getApproverUserId();
            if (approverUserId != null && (approverUserId.equals(mUserId))) {
                mBtConfirmTakeGoods.setVisibility(View.GONE);
                mLlRefusePass.setVisibility(View.VISIBLE);
            } else {
                mBtConfirmTakeGoods.setVisibility(View.GONE);
            }

        } else if ("已完成".equals(orderState)) {
            mBtConfirmTakeGoods.setVisibility(View.GONE);
        } else {
            mBtConfirmTakeGoods.setVisibility(View.GONE);
        }

        if (mUserId.endsWith(userId) && ("待提交".equals(orderState))) {
            mBtConfirmTakeGoods.setText("提交");
        }

        OrderDetailAdapter adapter = new OrderDetailAdapter(getActivity(), listBeanList);
        mLvOrderDetailNext.setAdapter(adapter);
        mLvOrderDetailNext.setEmptyView(mLlEmpty);
        mBtConfirmTakeGoods.setOnClickListener(this);
        if (listBeanList.size() != 0) {
            mRlProductNumPrice.setVisibility(View.VISIBLE);
        }

        List<OrderDetailBean.RecordListBean> recordList = listBean.getRecordList();
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(getActivity(), recordList);
        mMlOrderInfo.setAdapter(orderInfoAdapter);

        OrderDetailBean.KwOrderBean kwOrder = listBean.getKwOrder();
        mTotalBox = kwOrder.getTotalBox();
        mTotalPrice = kwOrder.getTotalPrice();
        mTotalWeight = kwOrder.getTotalWeight();

        mTvProductTotalNum.setText("共" + mTotalBox + "件商品");
        mTvAllTotalPrice.setText("合计：¥" + mTotalPrice);

        mTvOrderNumber.setText(listBean.getKwOrder().getOrderNumber());
        mTvSubmitTime.setText(mCreateDate);

        mTvOrderMemo.setText(listBean.getKwOrder().getOrderMemo());
        mTvGetadressName.setText(listBean.getKwOrder().getUserName());
        mTvGetadressPhone.setText(listBean.getKwOrder().getUserPhone());
        mTvGetGoodsAddress.setText(listBean.getKwOrder().getUserAddress());

        mTvDriverPhone.setOnClickListener(this);

    }

    /**
     * 拒绝提示；
     */
    private void popupRefusePassTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.refuse_pass_tip, null);
        final EditText etRefuseReason = (EditText) view.findViewById(R.id.et_refuse_reason);


        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_refuse_pass_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_refuse_pass_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refuseReason = etRefuseReason.getText().toString().trim();
                if (TextUtils.isEmpty(refuseReason)) {
                    Util.toast(getContext(), "请填写拒绝原因");
                    return;
                }
                refuseOrder(refuseReason);
                dialog.dismiss();
            }
        });
    }

    /**
     * 拒绝订单
     *
     * @param refuseReason
     */
    private void refuseOrder(String refuseReason) {
        showLoading();
        OkHttpUtils.post(HttpConstant.ORDER_APPROVE_SUBMIT + "id=" + mOrderId + "&user.id=" + mUserId + "&orderStatus=" + mOrderStatus + "&approvalType=" + "1" + "&refuseReason=" + refuseReason).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    Util.toast(getContext(), "操作成功");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }


    private void initTopbar() {
        mTopBarView = getActivity().findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(getActivity(), mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void rightClick() {
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.UPDATE_ORDER);
                intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm_take_goods:
                String text = (String) mBtConfirmTakeGoods.getText();
                if ("确认支付".equals(text)) {
                    Intent intent = new Intent(getContext(), ConfirmPayActivity.class);
                    intent.putExtra(Constant.ORDER_DATAIL_ID, mOrderId);
                    intent.putExtra(Constant.ORDER_STATE, mOrderStatus);
                    intent.putExtra(Constant.ORDER_ISREFUSE, mApprovalType);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    popupCommTip(text);
                }
                break;
            case tv_driver_phone:
                //拨打电话号码：
                callPhone();
                break;
            case R.id.bt_refuse:
                popupRefusePassTip();
                break;
            case R.id.bt_pass:
                popupCommTip("确认通过");
                break;
        }
    }


    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mTvDriverPhone.getText()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 删除提示；
     *
     * @param str
     */
    private void popupCommTip(final String str) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_tip, null);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText(str + "？");
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
                if (str.equals("确认删除")) {
                    deleteOrder();
                } else if (str.equals("确认通过")) {
                    commApprovePass("确认通过");
                } else if (str.equals("确认收货")) {
                    commApprovePass("确认收货");
                } else if (str.equals("提交")) {
                    submitOrder();
                }
                dialog.dismiss();
            }
        });
    }


    /**
     * 提交订单
     */
    private void submitOrder() {
        OkHttpUtils.post(HttpConstant.SUBMIT_ORDER + "user.id=" + mUserId + "&userName=" + mUserName + "&userPhone=" + mUserPhone + "&userAddress=" + mUserAddress +
                "&totalBox=" + mTotalBox + "&totalPrice=" + mTotalPrice + "&totalWeight=" + mTotalWeight +
                "&orderStatus=" + mOrderStatus + "&orderMemo=" + mOrderMemo + "&approvalType=" + mApprovalType + "&kwOrderItemList=" + mProductJson).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "订单提交成功");
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

            }
        });
    }


    /***
     * 通过订单；
     */
    private void commApprovePass(final String str) {
        showLoading();
        OkHttpUtils.post(HttpConstant.ORDER_APPROVE_SUBMIT + "id=" + mOrderId + "&user.id=" + mUserId + "&orderStatus=" + mOrderStatus + "&approvalType=" + "0").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    if (str.equals("确认收货")) {
                        Intent intent = new Intent(getContext(), ContentActivity.class);
                        intent.putExtra(Constant.CONTENT_TYPE, Constant.CONFIRM_TAKE_GOODS_OK);
                        intent.putExtra(Constant.ORDER_NUM, mOrderNumber);
                        startActivity(intent);
                    } else {
                        Util.toast(getContext(), "操作成功");
                    }
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
     * 删除订单；
     */
    private void deleteOrder() {
        showLoading();
        OkHttpUtils.get(HttpConstant.DELETE_ORDER + "orderId=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    getActivity().finish();
                    Util.toast(getContext(), "删除订单成功");
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
