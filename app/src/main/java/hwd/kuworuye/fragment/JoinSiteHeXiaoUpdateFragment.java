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
import hwd.kuworuye.bean.JoinSiteHxDetailBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyGridView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/7.
 */
public class JoinSiteHeXiaoUpdateFragment extends FBaseFragment implements View.OnClickListener {
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

    @BindView(R.id.rl_join_site_look)
    RelativeLayout mRlJoinSiteLook;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;

    @BindView(R.id.rl_ticket_look)
    RelativeLayout mRlTicketLook;
    @BindView(R.id.mgv_ticket_detail)
    MyGridView mMgvTicketDetail;

    @BindView(R.id.bt_joinsite_submit)
    Button mBtJoinsiteSubmit;
    Unbinder unbinder;
    private String mUserId;
    private String mOrderId;
    private Gson mGson;
    private String mExamineStatus;
    private String mApprovalType;
    private RelativeLayout mRlSitePlace;
    private RelativeLayout mRlTicketPlace;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_join_site_hexiao_update;
    }

    public static Fragment newInstance(Bundle bundle) {
        JoinSiteHeXiaoUpdateFragment xiaoUpdateFragment = new JoinSiteHeXiaoUpdateFragment();
        xiaoUpdateFragment.setArguments(bundle);
        return xiaoUpdateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mRlSitePlace = ((RelativeLayout) rootView.findViewById(R.id.rl_join_site_place));
        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_join_ticket_place));

        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mGson = new Gson();
        initView();
        requestNet2DataShowView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        joinSitePicShow();
        joinSiteTicketShow();
    }

    private void initView() {
        mRlJoinSiteLook.setOnClickListener(this);
        mRlTicketLook.setOnClickListener(this);
        mBtJoinsiteSubmit.setOnClickListener(this);
    }

    /**
     * 显示到控件上；
     */
    private void requestNet2DataShowView() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_JOIN_SITE_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                JoinSiteHxDetailBean joinSiteHxDetailBean = mGson.fromJson(s, JoinSiteHxDetailBean.class);
                boolean success = joinSiteHxDetailBean.isSuccess();
                if (success) {
                    JoinSiteHxDetailBean.DataBean data = joinSiteHxDetailBean.getData();
                    initShowData2View(data);
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
     * i显示数据到控件上；
     *
     * @param data
     */
    private void initShowData2View(JoinSiteHxDetailBean.DataBean data) {
        mExamineStatus = data.getExamineStatus();
        mApprovalType = data.getApprovalType();
        String userId = data.getUserId();
        String approveState = Util.getHxApproveState(mExamineStatus, mApprovalType);
        boolean isRefuse = "1".equals(mApprovalType);

        if ((mUserId.equals(userId)) && ("待核销".equals(approveState) || "待审批".equals(approveState) || isRefuse)) {
            mBtJoinsiteSubmit.setVisibility(View.VISIBLE);
        }

        mEtMarkName.setText(data.getName());
        mEtAddress.setText(data.getAddress());
        mEtPhoneNumber.setText(data.getPhone());
        mEtShoppingPeople.setText(data.getPurchase());
        mEtStoreNum.setText(data.getStoreNumber() + "");
        mEtYearManage.setText(data.getAnnualOperation() + "");
        mEtSameBrand.setText(data.getSimilarBrands() + "");

        mEtYearSale.setText(data.getAnnualSales() + "");
        mEtCostTotal.setText(data.getTotalExpenses() + "");


    }

    /**
     * 进场票据照片查看；
     */
    private void joinSiteTicketShow() {
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "9").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean commPicListBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
                initJoinSiteTicket(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 进场现场照片查看；
     */
    private void joinSitePicShow() {
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "8").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                CommPicListBean commPicListBean = gson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = commPicListBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> list = pageList.getList();
                initJoinSitePhoto(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 进场票据查看
     *
     * @param list
     */
    private void initJoinSiteTicket(List<CommPicListBean.PageListBean.ListBean> list) {
        final List<ImageDataUrlBean> joinSiteTicketList = new ArrayList<>();
        List<ImageTypeTitleUrlBean> ticketJsonList = new ArrayList<>();
        if (list != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                joinSiteTicketList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
                ticketJsonList.add(new ImageTypeTitleUrlBean("9", list.get(i).getTitle(), list.get(i).getImgs()));
            }
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }

        //票据照片图片；
        CommBackBigAdapter mJoinSitePicTicket = new CommBackBigAdapter(getContext(), joinSiteTicketList);
        mMgvTicketDetail.setAdapter(mJoinSitePicTicket);

    }

    /**
     * 进场现场照片显示；
     *
     * @param list
     */
    private void initJoinSitePhoto(List<CommPicListBean.PageListBean.ListBean> list) {
        List<ImageDataUrlBean> joinSiteList = new ArrayList<>();
        final List<ImageDataUrlBean> newList = new ArrayList<>();
        if (list != null) {
            mRlSitePlace.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                joinSiteList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
                newList.add(new ImageDataUrlBean(joinSiteList.get(i).getImgs(), joinSiteList.get(i).getTitle()));
            }
        } else {
            mRlSitePlace.setVisibility(View.VISIBLE);
        }

        CommBackBigAdapter mJoinSitePicSite = new CommBackBigAdapter(getContext(), newList);
        mMgvJoinSiteDetail.setAdapter(mJoinSitePicSite);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_site_look:
                Intent intentSite = new Intent(getContext(), ContentActivity.class);
                intentSite.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intentSite.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOIN_SITE_HEXIAO_SITE_UPDATE);
                //processid 其实就是mOrderId;
                intentSite.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intentSite);
                break;
            case R.id.rl_ticket_look:
                Intent intentTicket = new Intent(getContext(), ContentActivity.class);
                intentTicket.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intentTicket.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE);
                //processid 其实就是mOrderId;
                intentTicket.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intentTicket);
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
                joinSiteHeXiaoUpdate(markName, address, phoneNum, shoppingPeople, storeNum, yearMange, brand, yearSale, totalCost);
                break;
        }
    }

    /**
     * 进场费用核销；
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
    private void joinSiteHeXiaoUpdate(String markName, String address, String phoneNum, String shoppingPeople, String storeNum, String yearMange, String brand, String yearSale, String totalCost) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.JOIN_SITE_COST_HEXIAO_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&name=" + markName +
                "&address=" + address + "&phone=" + phoneNum + "&purchase=" + shoppingPeople + "&storeNumber=" + storeNum + "&annualOperation=" + yearMange + "&similarBrands=" + brand + "&annualSales=" + yearSale + "&totalExpenses=" +
                totalCost + "&examineStatus=" + mExamineStatus + "&approvalType=" + mApprovalType);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(getContext(), "修改成功");
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
