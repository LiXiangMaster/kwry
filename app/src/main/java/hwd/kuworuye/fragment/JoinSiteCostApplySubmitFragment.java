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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.CommAddPicBackAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ImageTypeTitleUrlBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.BackPic2ListEvent;
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
public class JoinSiteCostApplySubmitFragment extends FBaseFragment implements View.OnClickListener {
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
    RelativeLayout mRlJoinSiteDetail;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;
    @BindView(R.id.bt_joinsite_submit)
    Button mBtSubmit;
    Unbinder unbinder;
    private List<ImageTypeTitleUrlBean> imageList = new ArrayList<>();
    private CommAddPicBackAdapter mAdapter = new CommAddPicBackAdapter(getContext(), null);
    private String mSingleStr;
    private RelativeLayout mRlJoinPlace;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_join_site_cost_submit;
    }

    public static Fragment newInstance(Bundle bundle) {
        JoinSiteCostApplySubmitFragment costApplySubmit = new JoinSiteCostApplySubmitFragment();
        costApplySubmit.setArguments(bundle);
        return costApplySubmit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        mRlJoinPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_joinsite_place));
        initData();
        return rootView;
    }

    private void initData() {
        mRlJoinSiteDetail.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
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
            ImageTypeTitleUrlBean imageTypeTitleUrlBean = new ImageTypeTitleUrlBean("7", backPicList.get(i).getTitle(), backPicList.get(i).getImgs());
            imageList.add(imageTypeTitleUrlBean);
        }
        initAdapter(backPicList);
        initSignMark();
    }


    private void initSignMark() {
        String listJoson = mGson.toJson(imageList);
        mSingleStr = Util.replaceSingleMark(listJoson);
    }

    private void initAdapter(List<ImageDataUrlBean> list) {
        if (list != null) {
            mRlJoinPlace.setVisibility(View.GONE);
        } else {
            mRlJoinPlace.setVisibility(View.VISIBLE);
        }
        mAdapter = new CommAddPicBackAdapter(getContext(), list);
        mMgvJoinSiteDetail.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_site_detail_pic:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.SUBMIT_JOIN_SITE_COST_APPLY);
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
                if (mAdapter.getCount() == 0) {
                    submitData2Net(false,markName, address, phoneNum, shoppingPeople, storeNum, yearMange, brand, yearSale, totalCost);
                } else {
                    submitData2Net(true,markName, address, phoneNum, shoppingPeople, storeNum, yearMange, brand, yearSale, totalCost);
                }

                //进场费用提交到后台服务器；
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
    private void submitData2Net(boolean isUploadPic,String markName, String address, String phoneNum, String shoppingPeople, String storeNum, String yearMange, String brand, String yearSale, String totalCost) {
        String usrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        showLoading();
        String runIsUpLoadPic = "";
        if (isUploadPic) {
            //上传了图片；
            runIsUpLoadPic = "&photoList=" + mSingleStr;
        }
        String removePrecent = Util.removePrecent(HttpConstant.JOIN_COST_SUBMIT + "user.id=" + usrId + "&name=" + markName + "&address=" + address +
                "&phone=" + phoneNum + "&purchase=" + shoppingPeople + "&storeNumber=" + storeNum + "&annualOperation=" + yearMange +
                "&similarBrands=" + brand + "&annualSales=" + yearSale + "&totalExpenses=" + totalCost + "&approvalStatus=" + "1" + "&approvalType=" + "0"
                + runIsUpLoadPic);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "提交成功");
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
}
