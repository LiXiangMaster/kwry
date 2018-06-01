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
import android.widget.RelativeLayout;

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
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.CommBackBigAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPicListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ImageTypeTitleUrlBean;
import hwd.kuworuye.bean.JoinSiteCostApplyDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyGridView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/31.
 */
public class JoinSiteCostApplyUpdateFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.et_mark_name)
    EditText mEtMarkName;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.et_shopping_people)
    EditText mEtShoppingPeople;
    @BindView(R.id.et_store_num)
    EditText mEtStoreNum;
    @BindView(R.id.et_year_manage)
    EditText mEtYearManage;

    @BindView(R.id.et_same_brand)
    EditText mEtSameBrand;
    @BindView(R.id.et_year_sale)
    EditText mEtYearSale;
    @BindView(R.id.et_cost_total)
    EditText mEtCostTotal;
    @BindView(R.id.rl_join_site_detail_pic)
    RelativeLayout mRlJoinSiteDetailPic;
    @BindView(R.id.mgv_join_site_detail)

    MyGridView mMgvJoinSiteDetail;
    @BindView(R.id.bt_joinsite_submit)
    Button mBtJoinsiteSubmit;
    Unbinder unbinder;
    private String mUserId;
    private String mOrderId;
    private Gson mGson;
    private RelativeLayout mRlJoinPlace;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_join_site_cost_update;
    }

    public static Fragment newInstance(Bundle bundle) {
        JoinSiteCostApplyUpdateFragment costApplySubmit = new JoinSiteCostApplyUpdateFragment();
        costApplySubmit.setArguments(bundle);
        return costApplySubmit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        mRlJoinPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_join_place));
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mRlJoinSiteDetailPic.setOnClickListener(this);
        mBtJoinsiteSubmit.setOnClickListener(this);
        mGson = new Gson();
        requestNet2View();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNet2Pic();
    }

    private void requestNet2Pic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "7").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> joinSiteList = pageList.getList();
                initJoinSiteData(joinSiteList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 初始化超市照片数据
     *
     * @param markList
     */
    private void initJoinSiteData(List<CommPicListBean.PageListBean.ListBean> markList) {

        List<ImageTypeTitleUrlBean> markImageList = new ArrayList<>();
        List<ImageDataUrlBean> newList = new ArrayList<>();
        if (markList != null) {
            mRlJoinPlace.setVisibility(View.GONE);
            for (int i = 0; i < markList.size(); i++) {
                markImageList.add(new ImageTypeTitleUrlBean("7", markList.get(i).getTitle(), markList.get(i).getImgs()));
                newList.add(new ImageDataUrlBean(markList.get(i).getImgs(), markList.get(i).getTitle()));
            }
        } else {
            mRlJoinPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mAdapter = new CommBackBigAdapter(getContext(), newList);
        mMgvJoinSiteDetail.setAdapter(mAdapter);
    }


    /**
     * 调用详情接口；
     */
    private void requestNet2View() {
        showLoading();
        OkHttpUtils.post(HttpConstant.JOIN_SITE_COST_APPLY_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                JoinSiteCostApplyDetailBean detailBean = gson.fromJson(s, JoinSiteCostApplyDetailBean.class);
                if (detailBean.isSuccess()) {
                    JoinSiteCostApplyDetailBean.DataBean data = detailBean.getData();
                    showData2View(data);
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

    /**
     * 显示数据到控件上面；
     *
     * @param data
     */
    private void showData2View(JoinSiteCostApplyDetailBean.DataBean data) {
        mEtMarkName.setText(data.getName());
        mEtAddress.setText(data.getAddress());
        mEtPhoneNumber.setText(data.getPhone());
        mEtShoppingPeople.setText(data.getPurchase());
        mEtStoreNum.setText(data.getStoreNumber() + "");
        mEtSameBrand.setText(data.getSimilarBrands());
        mEtCostTotal.setText(data.getTotalExpenses() + "");
        mEtYearManage.setText(data.getAnnualOperation() + "");
        mEtYearSale.setText(data.getAnnualSales() + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_site_detail_pic:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOIN_SITE_COST_APPLY_UPDATE);
                startActivity(intent);
                break;
            case R.id.bt_joinsite_submit:
                String markName = mEtMarkName.getText().toString().trim();
                String address = mEtAddress.getText().toString().trim();
                String phoneNum = mEtPhoneNumber.getText().toString().trim();
                String shoppingPeople = mEtShoppingPeople.getText().toString().trim();
                String storeNum = mEtStoreNum.getText().toString().trim();
                String yearMange = mEtYearManage.getText().toString().trim();
                String brand = mEtSameBrand.getText().toString().trim();
                String yearSale = mEtYearSale.getText().toString().trim();
                String totalCost = mEtCostTotal.getText().toString().trim();

                boolean emptyMarkName = TextUtils.isEmpty(markName);
                boolean emptyAddress = TextUtils.isEmpty(address);
                boolean emptyPhoneNum = TextUtils.isEmpty(phoneNum);
                boolean emptyShoppingPeople = TextUtils.isEmpty(shoppingPeople);
                boolean emptyStoreNum = TextUtils.isEmpty(storeNum);
                boolean emptyYearMange = TextUtils.isEmpty(yearMange);
                boolean emptyBrand = TextUtils.isEmpty(brand);
                boolean emptyYearSale = TextUtils.isEmpty(yearSale);
                boolean emptyTotalCost = TextUtils.isEmpty(totalCost);

                if (!Util.checkPhone(phoneNum)) {
                    Util.toast(getContext(), "手机格式不正确");
                    return;
                }

                if (emptyMarkName || emptyAddress || emptyPhoneNum || emptyShoppingPeople || emptyStoreNum || emptyYearMange || emptyBrand || emptyYearSale || emptyTotalCost) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }

                //进场费用提交到后台服务器；
                confirmUpdate(markName, address, phoneNum, shoppingPeople, storeNum, yearMange, brand, yearSale, totalCost);
                break;
        }
    }

    /**
     * 提交资料到后台；
     *
     * @param markName
     * @param address
     * @param phoneNum
     * @param shoppingPeople
     * @param storeNum
     * @param yearMange
     * @param brand
     * @param yearSale
     * @param totalCost
     */
    private void confirmUpdate(String markName, String address, String phoneNum, String shoppingPeople, String storeNum, String yearMange, String brand, String yearSale, String totalCost) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.JOIN_SITE_COST_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&name=" + markName + "&address=" + address +
                "&phone=" + phoneNum + "&purchase=" + shoppingPeople + "&storeNumber=" + storeNum + "&annualOperation=" + yearMange +
                "&similarBrands=" + brand + "&annualSales=" + yearSale + "&totalExpenses=" + totalCost + "&approvalStatus=" + "1" + "&approvalType=" + "0");
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "修改成功");
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
                    getActivity().finish();
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
}
