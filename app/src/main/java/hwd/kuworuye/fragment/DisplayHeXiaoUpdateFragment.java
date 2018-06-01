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
import hwd.kuworuye.bean.DisplayHeXiaoDetailBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
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
 * Created by Administrator on 2017/4/6.
 */
public class DisplayHeXiaoUpdateFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.et_display_name)
    EditText mEtDisplayName;
    @BindView(R.id.et_shx_ale_target)
    EditText mEtShxAleTarget;
    @BindView(R.id.et_hx_execute_city)
    EditText mEtHxExecuteCity;
    @BindView(R.id.et_hx_execute_range)
    EditText mEtHxExecuteRange;
    @BindView(R.id.et_hx_execute_time)
    EditText mEtHxExecuteTime;
    @BindView(R.id.et_hx_feixiao_bv)
    EditText mEtHxFeixiaoBv;
    @BindView(R.id.et_hx_sale_volume)
    EditText mEtHxSaleVolume;
    @BindView(R.id.et_hx_zhuanjia_num)
    EditText mEtHxZhuanjiaNum;
    @BindView(R.id.et_hx_zhuanjia_area)
    EditText mEtHxZhuanjiaArea;
    @BindView(R.id.et_hx_location_explain)
    EditText mEtHxLocationExplain;
    @BindView(R.id.et_hx_cost_budget)
    EditText mEtHxCostBudget;
    @BindView(R.id.et_hx_baozhu_num)
    EditText mEtHxBaozhuNum;
    @BindView(R.id.et_hx_dispaly_style)
    EditText mEtHxDispalyStyle;
    @BindView(R.id.et_hx_bazozhu_location_explain)
    EditText mEtHxBazozhuLocationExplain;
    @BindView(R.id.et_hx_baozhu_cost_budget)
    EditText mEtHxBaozhuCostBudget;
    @BindView(R.id.et_other_num)
    EditText mEtOtherNum;
    @BindView(R.id.et_hx_other_dispaly_style)
    EditText mEtHxOtherDispalyStyle;
    @BindView(R.id.et_hx_other_location_explain)
    EditText mEtHxOtherLocationExplain;
    @BindView(R.id.et_hx_other_cost_budget)
    EditText mEtHxOtherCostBudget;
    @BindView(R.id.rl_kuwo_sc)
    RelativeLayout mRlKuwoSc;
    @BindView(R.id.lv_order_detail)
    MyListView mLvOrderDetail;
    @BindView(R.id.tv_total_box)
    TextView mTtTotalBox;
    @BindView(R.id.tv_total_price)
    TextView mTtTotalPrice;

    @BindView(R.id.ll_bottom_price_num)
    RelativeLayout mLlBottomPriceNum;
    @BindView(R.id.rl_mark_photo_look)
    RelativeLayout mRlMarkPhotoLook;

    @BindView(R.id.mgv_mark_detail)
    MyGridView mMgvMarkDetail;

    @BindView(R.id.rl_display_photo_look)
    RelativeLayout mRlDisplayPhotoLook;

    @BindView(R.id.mgv_display_detail)
    MyGridView mMgvDisplayDetail;

    @BindView(R.id.rl_ticket_photo_look)
    RelativeLayout mRlTicketPhotoLook;

    @BindView(R.id.mgv_ticket_pic_detail)
    MyGridView mMgvTicketPicDetail;

    @BindView(R.id.bt_refuse_pass)
    Button mBtHxDisplaySubmit;
    Unbinder unbinder;
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
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
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

    private String mOrderId;
    //商品清单json。

    private List<CommProductBean> productList = new ArrayList<>();
    //所有产品json
    private String productIdAndnum = "";
    private String mMarkListJson;
    private String mDisPlayListJson;
    private String mticketListJson;
    private List<CommUpdateProductBean> mUpdateProductList = new ArrayList<>();
    private List<CommPassBean> mPassList = new ArrayList<>();

    private String mId;
    private String mUserId;
    private Gson mGson;
    private RelativeLayout mRlTicketPlace;
    private RelativeLayout mRlDisplayPlace;
    private RelativeLayout mRlMarkPlace;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_display_hexiao_update;
    }


    public static Fragment newInstance(Bundle bundle) {
        DisplayHeXiaoUpdateFragment heXiaoUpdateFragment = new DisplayHeXiaoUpdateFragment();
        heXiaoUpdateFragment.setArguments(bundle);
        return heXiaoUpdateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();

        mRlTicketPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_display_ticket));
        mRlDisplayPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_display_pic));
        mRlMarkPlace = ((RelativeLayout) rootView.findViewById(R.id.rl_market_place));

        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
        }
        initView();
        requestNetShowDate2View();
        mGson = new Gson();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNet2MarkPic();
        requestNet2DisplayPic();
        requestNet2TicketPic();
    }

    private void initView() {
        mRlKuwoSc.setOnClickListener(this);
        mRlMarkPhotoLook.setOnClickListener(this);
        mRlDisplayPhotoLook.setOnClickListener(this);
        mRlTicketPhotoLook.setOnClickListener(this);
        mBtHxDisplaySubmit.setOnClickListener(this);

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

    /**
     * 调用核销详情接口；
     */
    private void requestNetShowDate2View() {
        showLoading();
        OkHttpUtils.get(HttpConstant.DISPLAY_HEXIAO_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                DisplayHeXiaoDetailBean detailBean = mGson.fromJson(s, DisplayHeXiaoDetailBean.class);
                boolean success = detailBean.isSuccess();
                if (success) {
                    initData(detailBean);
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
     * 超市图片接口
     */
    private void requestNet2MarkPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "4").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> markList = pageList.getList();
                initMarkData(markList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 产品成列照片
     */
    private void requestNet2DisplayPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "5").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                CommPicListBean ticketBean = mGson.fromJson(s, CommPicListBean.class);
                CommPicListBean.PageListBean pageList = ticketBean.getPageList();
                List<CommPicListBean.PageListBean.ListBean> disPlayList = pageList.getList();
                initDisplayData(disPlayList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }


    /**
     * 票据照片查看
     */
    private void requestNet2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "6").tag(getContext()).execute(new StringCallback() {
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
     * 初始化票据照片
     *
     * @param ticketList
     */
    private void initTicketData(List<CommPicListBean.PageListBean.ListBean> ticketList) {
        List<ImageDataUrlBean> ticketImageList = new ArrayList<>();
        if (ticketList != null) {
            mRlTicketPlace.setVisibility(View.GONE);
            for (int i = 0; i < ticketList.size(); i++) {
                ticketImageList.add(new ImageDataUrlBean(ticketList.get(i).getImgs(), ticketList.get(i).getTitle()));
            }
            mticketListJson = mGson.toJson(ticketImageList);
        } else {
            mRlTicketPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mTicketAdpter = new CommBackBigAdapter(getContext(), ticketImageList);
        mMgvTicketPicDetail.setAdapter(mTicketAdpter);
        mTicketAdpter.notifyDataSetChanged();
    }

    /**
     * 初始化超市照片数据
     *
     * @param markList
     */
    private void initMarkData(List<CommPicListBean.PageListBean.ListBean> markList) {
        List<ImageDataUrlBean> markImageList = new ArrayList<>();
        if (markList != null) {
            mRlMarkPlace.setVisibility(View.GONE);
            for (int i = 0; i < markList.size(); i++) {
                markImageList.add(new ImageDataUrlBean(markList.get(i).getImgs(), markList.get(i).getTitle()));
            }
            mMarkListJson = mGson.toJson(markImageList);
        } else {
            mRlMarkPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mMarkAdpter = new CommBackBigAdapter(getContext(), markImageList);
        mMgvMarkDetail.setAdapter(mMarkAdpter);
        mMarkAdpter.notifyDataSetChanged();
    }


    /***
     * 初始化产品成列照片
     *
     * @param disPlayList
     */
    private void initDisplayData(List<CommPicListBean.PageListBean.ListBean> disPlayList) {
        List<ImageDataUrlBean> disPlayImageList = new ArrayList<>();
        if (disPlayList != null) {
            mRlDisplayPlace.setVisibility(View.GONE);
            for (int i = 0; i < disPlayList.size(); i++) {
                disPlayImageList.add(new ImageDataUrlBean(disPlayList.get(i).getImgs(), disPlayList.get(i).getTitle()));
            }
            mDisPlayListJson = mGson.toJson(disPlayImageList);
        } else {
            mRlDisplayPlace.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter mDisPlayAdpter = new CommBackBigAdapter(getContext(), disPlayImageList);
        mMgvDisplayDetail.setAdapter(mDisPlayAdpter);
        mDisPlayAdpter.notifyDataSetChanged();
    }


    //显示数据到控件上；
    private void initData(DisplayHeXiaoDetailBean detailBean) {
        List<CommUpdateProductBean> updateProductList = new ArrayList<>();
        List<DisplayHeXiaoDetailBean.ItemListBean> itemList = detailBean.getItemList();
        DisplayHeXiaoDetailBean.KwDisplayExamineBean kwDisplayExamine = detailBean.getKwDisplayExamine();
        for (int i = 0; i < itemList.size(); i++) {
            CommProductBean commProductBean = new CommProductBean(itemList.get(i).getProductAttributeId(), itemList.get(i).getQuantity());
            String productAttributeId = itemList.get(i).getProductAttributeId();
            String quantity = itemList.get(i).getQuantity();
            int quantityInt = Integer.parseInt(quantity);
            String productName = itemList.get(i).getKwProductAttribute().getProduct().getProductName();
            String price = itemList.get(i).getKwProductAttribute().getPrice();
            double priceDouble = Double.parseDouble(price);
            String thumbnail = itemList.get(i).getKwProductAttribute().getThumbnail();
            String tasteStr = itemList.get(i).getKwProductAttribute().getTasteStr();
            String specificationsStr = itemList.get(i).getKwProductAttribute().getSpecificationsStr();
            double passPriceDouble = priceDouble * quantityInt;
            productList.add(commProductBean);
            updateProductList.add(new CommUpdateProductBean(productAttributeId, quantity, price, tasteStr, productName, specificationsStr, thumbnail));
            mPassList.add(new CommPassBean(productAttributeId, quantityInt, passPriceDouble));
        }
        this.mUpdateProductList = updateProductList;

        String s = mGson.toJson(productList);
        String singleJson = Util.replaceSingleMark(s);
        productIdAndnum = singleJson;
        initAdapter();
        bottomRlPriceAndnum();

        mId = kwDisplayExamine.getId();
        String userId = kwDisplayExamine.getUser().getId();
        String examineStatus = kwDisplayExamine.getExamineStatus();
        String approveState = Util.getHxApproveState(examineStatus, kwDisplayExamine.getApprovalType());
        String approvalType = kwDisplayExamine.getApprovalType();
        boolean isRefuse = "1".equals(approvalType);

        //TODO  这里的逻辑判断 有点多 有点乱 可能会有bug；


        if ((mUserId.equals(userId)) && ("待核销".equals(approveState) || "待审批".equals(approveState) || isRefuse)) {
            mBtHxDisplaySubmit.setVisibility(View.VISIBLE);
        }

        List<String> objectiveList = kwDisplayExamine.getObjectiveList();
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

        mEtDisplayName.setText(kwDisplayExamine.getName());

        mEtShxAleTarget.setText(kwDisplayExamine.getTarget());
        mEtHxExecuteCity.setText(kwDisplayExamine.getCity());
        mEtHxExecuteRange.setText(kwDisplayExamine.getRanges());
        mEtHxExecuteTime.setText(kwDisplayExamine.getExecutionTime());
        mEtHxFeixiaoBv.setText(kwDisplayExamine.getRatio());
        mEtHxSaleVolume.setText(kwDisplayExamine.getSalesVolume() + "");
        mEtHxZhuanjiaNum.setText(kwDisplayExamine.getShelfNumber());
        mEtHxZhuanjiaArea.setText(kwDisplayExamine.getShelfArea());
        mEtHxLocationExplain.setText(kwDisplayExamine.getShelfDescription());
        mEtHxCostBudget.setText(kwDisplayExamine.getShelfBudget());

        mEtHxBaozhuNum.setText(kwDisplayExamine.getStackingNumber());
        mEtHxDispalyStyle.setText(kwDisplayExamine.getStackingForm());
        mEtHxBazozhuLocationExplain.setText(kwDisplayExamine.getStackingArea());
        mEtHxBaozhuCostBudget.setText(kwDisplayExamine.getStackingBudget());

        mEtOtherNum.setText(kwDisplayExamine.getOtherNumber());
        mEtHxOtherDispalyStyle.setText(kwDisplayExamine.getOtherForm());
        mEtHxOtherLocationExplain.setText(kwDisplayExamine.getOtherArea());
        mEtHxOtherCostBudget.setText(kwDisplayExamine.getOtherBudget());

        mEtAcSaleCost.setText(kwDisplayExamine.getActivitySales());
        mEtAcCostBilv.setText(kwDisplayExamine.getActivityRatio());
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
        initAdapter();
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


    private void initAdapter() {
        KwCommProductAdapter kwCommProductAdapter = new KwCommProductAdapter(getContext(), mUpdateProductList);
        mLvOrderDetail.setAdapter(kwCommProductAdapter);
        mLvOrderDetail.setEmptyView(mLlEmpty);
    }

    private void bottomRlPriceAndnum() {
        int productNum = 0;
        double totalPrice = 0;

        if ("[]".equals(productIdAndnum)) {
            mLlBottomPriceNum.setVisibility(View.GONE);
        } else {
            mLlBottomPriceNum.setVisibility(View.VISIBLE);
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
            mTtTotalBox.setText("共" + productNum + "件商品");
            mTtTotalPrice.setText("合计：¥" + totalPrice);

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
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(getContext());
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (view.getId()) {
            case R.id.rl_kuwo_sc:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.DISPLAY_HEXIAO_UPDATE);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.rl_mark_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_HEXIAO_UPDATE_MARK);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_display_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.rl_ticket_photo_look:
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.DISPLAY_HEXIAO_UPDATE_TICKET);
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_refuse_pass:
                String disPlayName = mEtDisplayName.getText().toString().trim();
                String target = mEtShxAleTarget.getText().toString().trim();
                String executeCity = mEtHxExecuteCity.getText().toString().trim();

                String range = mEtHxExecuteRange.getText().toString().trim();
                String executeTime = mEtHxExecuteTime.getText().toString().trim();
                String feixiaoBv = mEtHxFeixiaoBv.getText().toString().trim();
                String saleVolume = mEtHxSaleVolume.getText().toString().trim();

                String zhuanJianNum = mEtHxZhuanjiaNum.getText().toString().trim();
                String zhuanJiaArea = mEtHxZhuanjiaArea.getText().toString().trim();
                String locationExplain = mEtHxLocationExplain.getText().toString().trim();
                String costBudget = mEtHxCostBudget.getText().toString().trim();

                String baozhuNum = mEtHxBaozhuNum.getText().toString().trim();
                String displayStyle = mEtHxDispalyStyle.getText().toString().trim();
                String baozhuLocationExplain = mEtHxBazozhuLocationExplain.getText().toString().trim();
                String baozhuCostBudget = mEtHxBaozhuCostBudget.getText().toString().trim();

                String otherNum = mEtOtherNum.getText().toString().trim();
                String otherDisplayStyle = mEtHxOtherDispalyStyle.getText().toString().trim();
                String otherLocationExplain = mEtHxOtherLocationExplain.getText().toString().trim();
                String otherCostBudget = mEtHxOtherCostBudget.getText().toString().trim();
                String acSaleCost = mEtAcSaleCost.getText().toString().trim();
                String acCostBilv = mEtAcCostBilv.getText().toString().trim();

                boolean disPlayNameEy = TextUtils.isEmpty(disPlayName);
                boolean targetEy = TextUtils.isEmpty(target);
                boolean executeCityEy = TextUtils.isEmpty(executeCity);

                boolean rangeEy = TextUtils.isEmpty(range);
                boolean executeTimeEy = TextUtils.isEmpty(executeTime);
                boolean feixiaoBvEy = TextUtils.isEmpty(feixiaoBv);
                boolean saleVolumeEy = TextUtils.isEmpty(saleVolume);

                boolean zhuanJianNumEy = TextUtils.isEmpty(zhuanJianNum);
                boolean zhuanJiaAreaEy = TextUtils.isEmpty(zhuanJiaArea);
                boolean locationExplainEy = TextUtils.isEmpty(locationExplain);
                boolean costBudgetEy = TextUtils.isEmpty(costBudget);

                boolean baozhuNumEy = TextUtils.isEmpty(baozhuNum);
                boolean displayStyleEy = TextUtils.isEmpty(displayStyle);
                boolean baozhuLocationExplainEy = TextUtils.isEmpty(baozhuLocationExplain);
                boolean baozhuCostBudgetEy = TextUtils.isEmpty(baozhuCostBudget);

                boolean otherNumEy = TextUtils.isEmpty(otherNum);
                boolean otherDisplayStyleEy = TextUtils.isEmpty(otherDisplayStyle);
                boolean otherLocationExplainEy = TextUtils.isEmpty(otherLocationExplain);
                boolean otherCostBudgetEy = TextUtils.isEmpty(otherCostBudget);

                boolean emptyAcSaleCost = TextUtils.isEmpty(acSaleCost);
                boolean emptyAcCostBilv = TextUtils.isEmpty(acCostBilv);

                boolean checked1 = mCb1.isChecked();
                boolean checked2 = mCb2.isChecked();
                boolean checked3 = mCb3.isChecked();
                boolean checked4 = mCb4.isChecked();
                boolean checked5 = mCb5.isChecked();


                if ("0".equals(mEtHxFeixiaoBv)) {
                    Util.toast(getContext(), "费效比率不能为0");
                    return;
                }

                if (checked1 == false && checked2 == false && checked3 == false && checked4 == false && checked5 == false) {
                    Util.toast(getContext(), "请选择活动目的");
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
                //活动目的；
                String activityObjective = stringBuffer.toString();

                if (disPlayNameEy || targetEy || executeCityEy || rangeEy || executeTimeEy ||
                        feixiaoBvEy || saleVolumeEy || zhuanJianNumEy || zhuanJiaAreaEy || locationExplainEy ||
                        costBudgetEy || baozhuNumEy || displayStyleEy || baozhuLocationExplainEy || baozhuCostBudgetEy ||
                        otherNumEy || otherDisplayStyleEy || otherLocationExplainEy || otherCostBudgetEy || emptyAcSaleCost || emptyAcCostBilv) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }

                int visibility = mLlBottomPriceNum.getVisibility();
                if (visibility != View.VISIBLE) {
                    Util.toast(getContext(), "您还未选择商品");
                    return;
                }

                //修改数据
                confirmUpdate(disPlayName, activityObjective, target, executeCity, range, executeTime, feixiaoBv, saleVolume,
                        zhuanJianNum, zhuanJiaArea, locationExplain, costBudget, baozhuNum, displayStyle,
                        baozhuLocationExplain, baozhuCostBudget, otherNum, otherDisplayStyle, otherLocationExplain, otherCostBudget, acSaleCost, acCostBilv);
                break;

        }
    }

    /**
     * 修改接口
     *
     * @param disPlayName
     * @param target
     * @param executeCity
     * @param range
     * @param executeTime
     * @param feixiaoBv
     * @param saleVolume
     * @param zhuanJianNum
     * @param zhuanJiaArea
     * @param locationExplain
     * @param costBudget
     * @param baozhuNum
     * @param displayStyle
     * @param baozhuLocationExplain
     * @param baozhuCostBudget
     * @param otherNum
     * @param otherDisplayStyle
     * @param otherLocationExplain
     * @param otherCostBudget
     */
    private void confirmUpdate(String disPlayName, String activityObjective, String target, String executeCity, String range, String executeTime, String feixiaoBv, String saleVolume, String zhuanJianNum, String zhuanJiaArea, String locationExplain, String costBudget, String baozhuNum, String displayStyle,
                               String baozhuLocationExplain, String baozhuCostBudget, String otherNum, String otherDisplayStyle, String otherLocationExplain,
                               String otherCostBudget, String acSaleCost, String acCostBilv) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.DISPLAY_HEXIAO_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&name=" + disPlayName + "&objective=" + activityObjective + "&target=" + target + "&city=" + executeCity +
                "&ranges=" + range + "&executionTime=" + executeTime + "&shelfNumber=" + zhuanJianNum + "&shelfArea=" + zhuanJiaArea + "&shelfDescription=" + locationExplain + "&shelfBudget=" + costBudget +
                "&stackingNumber=" + baozhuNum + "&stackingForm=" + displayStyle + "&stackingArea=" + baozhuLocationExplain + "&stackingBudget=" + baozhuCostBudget + "&otherNumber=" + otherNum + "&otherForm=" + otherDisplayStyle +
                "&otherArea=" + otherLocationExplain + "&otherBudget=" + otherCostBudget + "&salesVolume=" + saleVolume + "&ratio=" + feixiaoBv + "&displayEProductList=" + productIdAndnum + "&activitySales=" + acSaleCost + "&activityRatio=" + acCostBilv);
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
