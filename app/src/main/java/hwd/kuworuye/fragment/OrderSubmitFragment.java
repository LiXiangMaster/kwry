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
import android.widget.FrameLayout;
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
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.ChooseAreaBean;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommUpdateProductBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ShowSiteListBean;
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
 * Created by Administrator on 2017/3/7.
 */
public class OrderSubmitFragment extends FBaseFragment implements View.OnClickListener {
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
    private List<CommProductAllNatureBean> mList = new ArrayList<>();
    private List<CommPassBean> mPassList = new ArrayList<>();
    private KwCommProductAdapter mAdapter;
    private RelativeLayout mRlBottom;
    private TextView mTvTotalBox;
    private TextView mTvTotalPrice;
    private TextView mTvTotalPriceBottom;
    private TextView mTvTotalBoxBottom;
    private EditText mEtRemark;
    private FrameLayout mFrameLayout;
    private Gson mGson;


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_comm_order_submit_update;
    }

    public static Fragment newInstance(Bundle bundle) {
        OrderSubmitFragment fragment = new OrderSubmitFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mRlBottom = ((RelativeLayout) rootView.findViewById(R.id.rl_bottom_price_num));
        mGson = new Gson();
        mTvTotalBox = ((TextView) rootView.findViewById(R.id.tv_total_box));
        mTvTotalPrice = ((TextView) rootView.findViewById(R.id.tv_total_price));

        mTvTotalPriceBottom = ((TextView) rootView.findViewById(R.id.tv_bottom_total_price));
        mTvTotalBoxBottom = ((TextView) rootView.findViewById(R.id.tv__bottom_total_box));
        mFrameLayout = ((FrameLayout) rootView.findViewById(R.id.fl_address_choose));
        mEtRemark = ((EditText) rootView.findViewById(R.id.et_remark));
        //得到默认地址；
        requestNet2GetSiteList();
        initData();
        return rootView;
    }


    private void requestNet2GetSiteList() {
        showLoading();
        String userId = (String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERID);
        OkHttpUtils.post(HttpConstant.GET_SITE_LIST + "userId=" + userId).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                ShowSiteListBean showSiteList = mGson.fromJson(s, ShowSiteListBean.class);
                List<ShowSiteListBean.DataBean> dateBeanList = showSiteList.getData();
                boolean success = showSiteList.isSuccess();
                if (success) {
                    showDefaultAddress(dateBeanList);
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
     * 显示默认地址；
     *
     * @param dateBeanList
     */
    private void showDefaultAddress(List<ShowSiteListBean.DataBean> dateBeanList) {
        boolean isHaveDefault = false;
        for (int i = 0; i < dateBeanList.size(); i++) {
            if ("1".equals(dateBeanList.get(i).getIsDefault())) {
                isHaveDefault = true;
                mLlAddress.setVisibility(View.VISIBLE);
                mLlFistChooseAddress.setVisibility(View.INVISIBLE);
                String address = dateBeanList.get(i).getAddress();
                String phone = dateBeanList.get(i).getPhone();
                String name = dateBeanList.get(i).getName();
                String region = dateBeanList.get(i).getRegion();
                mTvPhoneNumber.setText(phone);
                mTvContactName.setText(name);
                mTvDetailAddress.setText(region + address);
            }
        }
        if (!isHaveDefault) {
            ChooseAreaBean chooseAreaBean = (ChooseAreaBean) Util.readObj(getContext(), Constant.FIRST_CHOOSE);
            if (chooseAreaBean != null) {
                mLlAddress.setVisibility(View.VISIBLE);
                mTvContactName.setText(chooseAreaBean.getName());
                mTvPhoneNumber.setText(chooseAreaBean.getPhoneNumber());
                mTvDetailAddress.setText(chooseAreaBean.getAddress());
            } else {
                mLlAddress.setVisibility(View.INVISIBLE);
                mLlFistChooseAddress.setVisibility(View.VISIBLE);
            }
        }
    }


    private void initData() {
        mBtBottomCom.setText(getResources().getString(R.string.order_submit));
        mBtBottomCom.setOnClickListener(this);
        mRlKuwoSc.setOnClickListener(this);
        mFrameLayout.setOnClickListener(this);
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
            list.add(new CommUpdateProductBean(productAttributeId,number, price, smellStr, productName, normals, picUrl));
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
                if (mProductNum == 0 || mTotalPrice == 0) {
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

                String usrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
                String totalProductNum = String.valueOf(this.mProductNum);
                String totalProductPrice = String.valueOf(this.mTotalPrice);
                String allWeightProduct = String.valueOf(allWeight);
                popupLoginOutTip(usrId, allWeightProduct, contantName, contantPhone, contantAddress, totalProductNum, totalProductPrice, etRemark);
                break;
            case R.id.rl_kuwo_sc:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.SUBMIT_ORDER);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.fl_address_choose:
                Intent intentAddress = new Intent(getContext(), ContentActivity.class);
                intentAddress.putExtra(Constant.CONTENT_TYPE, Constant.AREA_MANAGER);
                intentAddress.putExtra(Constant.AREA_MANAGER_JOIN_WAY, Constant.SUBMIT_ORDER);
                startActivity(intentAddress);
                break;
        }
    }

    /**
     * 弹出是否要提交订单按钮
     *
     * @param usrId
     * @param allWeightProduct
     * @param contantName
     * @param contantPhone
     * @param contantAddress
     * @param totalProductNum
     * @param totalProductPrice
     * @param etRemark
     */
    private void popupLoginOutTip(final String usrId, final String allWeightProduct, final String contantName, final String contantPhone, final String contantAddress, final String totalProductNum, final String totalProductPrice, final String etRemark) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_tip, null);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText("确定要提交订单？");
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
                submitOrder2Net(usrId, allWeightProduct, contantName, contantPhone, contantAddress, totalProductNum, totalProductPrice, etRemark);
                dialog.dismiss();
            }
        });
    }

    /**
     * 提交订单；
     *
     * @param usrId
     * @param allWeight
     * @param contantName
     * @param contantPhone
     * @param contantAddress
     * @param totalProductNum
     * @param totalProductPrice
     * @param etRemark
     */
    private void submitOrder2Net(String usrId, String allWeight, String contantName, String contantPhone, String contantAddress, String totalProductNum, String totalProductPrice, String etRemark) {
        String needCode = HttpConstant.SUBMIT_ORDER + "user.id=" + usrId + "&userName=" + contantName + "&userPhone=" + contantPhone + "&userAddress=" + contantAddress +
                "&totalBox=" + totalProductNum + "&totalPrice=" + totalProductPrice + "&totalWeight=" + allWeight +
                "&orderStatus=" + "1" + "&orderMemo=" + etRemark + "&approvalType=" + "0" + "&kwOrderItemList=" + productJson;
        String removePrecentStr = Util.removePrecent(needCode);
        OkHttpUtils.post(removePrecentStr).tag(getContext()).execute(new StringCallback() {
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

    /**
     * 返回一个被选中的产品集合；
     *
     * @param event
     */

    @Subscribe
    public void getKwChooseProductList(KwChooseProductListEvent event) {
        List<CommProductAllNatureBean> list = event.getList();
        this.mList = list;
        bottomRlPriceAndnum(list);
    }

    @Subscribe
    public void refreshAddress(CommRefreshListEvent event) {
        if (event.isRefresh()) {
            ChooseAreaBean chooseAreaBean = (ChooseAreaBean) Util.readObj(getContext(), Constant.FIRST_CHOOSE);
            if (chooseAreaBean != null) {
                mLlAddress.setVisibility(View.VISIBLE);
                mTvContactName.setText(chooseAreaBean.getName());
                mTvPhoneNumber.setText(chooseAreaBean.getPhoneNumber());
                mTvDetailAddress.setText(chooseAreaBean.getAddress());
            } else {
                mLlAddress.setVisibility(View.INVISIBLE);
                mLlFistChooseAddress.setVisibility(View.VISIBLE);
            }
        }
    }

    private int mProductNum;
    private double mTotalPrice;

    private void bottomRlPriceAndnum(List<CommProductAllNatureBean> list) {
        List<CommPassBean> passList = new ArrayList<>();
        int productNum = 0;
        double totalPrice = 0;
        if (list.size() == 0) {
            mRlBottom.setVisibility(View.INVISIBLE);
        } else {
            mRlBottom.setVisibility(View.VISIBLE);
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

            this.mProductNum = productNum;
            this.mTotalPrice = totalPrice;

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
                double passTotalPrice = priceDouble*itemNum;
                passList.add(new CommPassBean(productAttributeId, itemNum, passTotalPrice));
            }
            this.mPassList = passList;
        } else {
            mPassList.clear();
            mTvTotalPriceBottom.setText("" + totalPrice);
            mTvTotalBoxBottom.setText("共" + productNum + "件商品");
        }

    }

    /**
     * 返回的是一个json
     *
     * @param event
     */
    @Subscribe
    public void kwCommEvent(CommKwBackEvent event) {
        String json = event.getJson();
        String singleMark = Util.replaceSingleMark(json);
        this.productJson = singleMark;
        initAdapter();
        mAdapter.notifyDataSetChanged();
    }
}
