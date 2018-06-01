package hwd.kuworuye.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.AskGoodsPlanShowAdapter;
import hwd.kuworuye.adapter.SimpleAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.AddAskGoodsPlanBean;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.JxsNameBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.event.KwChooseProductListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AskGoodsPlanSubmitFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.rl_ask_plan_detail)
    RelativeLayout mRlSubmitRepertory;
    @BindView(R.id.submit_askgood_plan)
    ListView mSubmitAskgoodPlan;
    Unbinder unbinder;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_second_week)
    TextView mTvSecondWeek;
    @BindView(R.id.tv_jxs)
    TextView mTvJxs;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.bt_confirm_submit)
    Button mBtConfirmSubmit;
    @BindView(R.id.et_mark)
    EditText mEtMark;
    @BindView(R.id.tv_total_box)
    TextView mTvTotalBox;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rl_bottom_price_num)
    RelativeLayout mRlBottomPriceNum;
    @BindView(R.id.ll_jxs)
    LinearLayout mLlJxs;
    private String productJson = "";
    private String jxsId = "";
    private List<JxsNameBean.DataBean> mData;
    private AskGoodsPlanShowAdapter mAdapter;
    List<CommProductAllNatureBean> mList = new ArrayList<>();
    private List<CommPassBean> mPassList = new ArrayList<>();
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_askgoods_plan_submit;
    }

    public static Fragment newInstance(Bundle bundle) {
        AskGoodsPlanSubmitFragment fragment = new AskGoodsPlanSubmitFragment();
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
        requestNet2JxsName();
        return rootView;
    }

    private void requestNet2JxsName() {
        String usrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_JXS + "userId=" + usrId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                JxsNameBean jxsNameBean = gson.fromJson(s, JxsNameBean.class);
                boolean success = jxsNameBean.isSuccess();
                if (success) {
                    mData = jxsNameBean.getData();
                    if (mData.size() > 0) {
                        jxsId = mData.get(0).getUserId();
                        mTvJxs.setText(mData.get(0).getUserName());
                    }
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

    private void initData() {

        initAdapter();
        mRlSubmitRepertory.setOnClickListener(this);
        mBtConfirmSubmit.setOnClickListener(this);
        mLlJxs.setOnClickListener(this);
        mTvDate.setText(Util.getCurrentTimeYYMM());
        String currentTimeYYYYMMDD = Util.getCurrentTimeYYYYMMDD();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(currentTimeYYYYMMDD);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
            String secondWeek = "";
            switch (weekOfMonth) {
                case 1:
                    secondWeek = "第一周";
                    break;
                case 2:
                    secondWeek = "第二周";
                    break;
                case 3:
                    secondWeek = "第三周";
                    break;
                case 4:
                    secondWeek = "第四周";
                    break;
                case 5:
                    secondWeek = "第五周";
                    break;
            }
            mTvSecondWeek.setText(secondWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void initAdapter() {
        mAdapter = new AskGoodsPlanShowAdapter(getActivity(), mList);
        mSubmitAskgoodPlan.setEmptyView(mLlEmpty);
        mSubmitAskgoodPlan.setAdapter(mAdapter);
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
            case R.id.rl_ask_plan_detail:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.ADD_ASKGOODS_PLAN);
                intent.putParcelableArrayListExtra(Constant.PASS_PRODUCT, (ArrayList<CommPassBean>) mPassList);
                startActivity(intent);
                break;
            case R.id.ll_jxs:
                popupChooseJxs();
                break;
            case R.id.bt_confirm_submit:
                String etMark = mEtMark.getText().toString().trim();
                if (TextUtils.isEmpty(etMark)) {
                    Util.toast(getContext(), "请填写备注");
                    return;
                }
                if (mAdapter.getCount() == 0) {
                    Util.toast(getContext(), "请选择商品");
                    return;
                }
                String userId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
                requestNet2addGoods(userId, jxsId, etMark);
                break;
            default:
                break;
        }
    }

    private void requestNet2addGoods(String userId, String jxsId, String etMark) {
        showLoading();
        OkHttpUtils.post(HttpConstant.ADD_SUBMIT_PLAN + "user.id=" + userId + "&distributor.id=" + jxsId + "&data=" + productJson + "&remarks=" + etMark).tag(getContext()).execute(new StringCallback() {
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

    private void popupChooseJxs() {
        View contentView = View.inflate(getContext(), R.layout.choose_city, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(getActivity().findViewById(R.id.ll_jxs), 0, 0);

        ListView lvLeft = (ListView) contentView.findViewById(R.id.lv_choose_city);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), mData);
        lvLeft.setAdapter(simpleAdapter);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jxsName = mData.get(position).getUserName();
                mTvJxs.setText(jxsName);
                jxsId = mData.get(position).getUserId();
                popupWindow.dismiss();
            }
        });
        //当popupWindow关闭的时候调用
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
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
        List<AddAskGoodsPlanBean> addAskGoodsPlanBeanList = new ArrayList<>();
        List<CommProductAllNatureBean> list = event.getList();
        this.mList = list;
        for (int i = 0; i < list.size(); i++) {
            String productAttributeId = list.get(i).getProductAttributeId();
            String number = list.get(i).getNumber();
            addAskGoodsPlanBeanList.add(new AddAskGoodsPlanBean(productAttributeId, number));
        }
        Gson gson = new Gson();
        String s = gson.toJson(addAskGoodsPlanBeanList);
        productJson = Util.replaceSingleMark(s);
        bottomRlPriceAndnum();
        initAdapter();
    }

    private void bottomRlPriceAndnum() {
        mRlBottomPriceNum.setVisibility(View.VISIBLE);
        List<CommPassBean> passList = new ArrayList<>();
        int productNum = 0;
        double totalPrice = 0;
        List<Integer> productNumList = new ArrayList<>();
        List<Double> productPriceList = new ArrayList<>();
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
            mTvTotalBox.setText("共" + productNum + "件商品");
            mTvTotalPrice.setText("合计：¥" + totalPrice);

            mPassList.clear();
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
        }else {
            mRlBottomPriceNum.setVisibility(View.INVISIBLE);
        }
    }
}
