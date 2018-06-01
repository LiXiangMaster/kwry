package hwd.kuworuye.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.SubmitReperToryAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.JxcProductBean;
import hwd.kuworuye.bean.NowReperToryBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyListView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class SubmitReperToryFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.prl_submit_repertory)
    MyListView mPrlSubmitRepertory;
    Unbinder unbinder;
    @BindView(R.id.tv_submit_repertory)
    TextView mTvSubmitRepertory;
    @BindView(R.id.tv_choose_date)
    TextView mTvChooseDate;
    @BindView(R.id.ll_choose_second_week)
    LinearLayout mLlChooseSecondWeek;
    @BindView(R.id.bt_submit_repertory)
    Button mBtSubmitRepertory;
    private List<NowReperToryBean.DataBean.InventoryProductListBean> mInventoryProductList;
    private SubmitReperToryAdapter mAdapter;
    private List<JxcProductBean> submitList = new ArrayList<>();
    private String mUserId;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_submit_repertory;
    }

    public static Fragment newInstance(Bundle bundle) {
        SubmitReperToryFragment fragment = new SubmitReperToryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        String currentTimeYYMM = Util.getCurrentTimeYYMM();
        mTvChooseDate.setText(currentTimeYYMM);
        Bundle arguments = getArguments();
        mGson = new Gson();
        if (arguments != null) {
            mUserId = arguments.getString(Constant.USERID);
        }
        String currentTimeYYYYMMDD = Util.getCurrentTimeYYYYMMDD();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            mTvSubmitRepertory.setText(secondWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getNowReperTory();
        return rootView;
    }

    private void getNowReperTory() {
        showLoading();
        OkHttpUtils.post(HttpConstant.GET_NOW_REPERTORY + "userId=" + mUserId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();

                NowReperToryBean nowReperToryBean = mGson.fromJson(s, NowReperToryBean.class);
                boolean success = nowReperToryBean.isSuccess();
                if (success) {
                    NowReperToryBean.DataBean data = nowReperToryBean.getData();
                    initData(data);
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

    private void initData(NowReperToryBean.DataBean dataBean) {
        mInventoryProductList = dataBean.getInventoryProductList();
        mAdapter = new SubmitReperToryAdapter(getActivity(), mInventoryProductList);
        mPrlSubmitRepertory.setAdapter(mAdapter);
        mBtSubmitRepertory.setOnClickListener(this);
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
            case R.id.bt_submit_repertory:
                Map<Integer, Integer> countMap = mAdapter.getCountMap();
                for (int i = 0; i < countMap.size(); i++) {
                    int count = countMap.get(i);
                    String countStr = String.valueOf(count);
                    String productAttributeId = mInventoryProductList.get(i).getProductAttributeId();
                    JxcProductBean commProductBean = new JxcProductBean(countStr,productAttributeId);
                    submitList.add(commProductBean);
                }
                String json = mGson.toJson(submitList);
                String replaceJson = json.replace("\"", "'");
                sumbitUpdate(replaceJson);
                break;
            default:
                break;
        }
    }

    private void sumbitUpdate(String replaceJson) {
        showLoading();
        OkHttpUtils.post(HttpConstant.UPDATE_REPERTORY + "userId=" + mUserId + "&data=" + replaceJson).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    Util.toast(getContext(), "更新成功");
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
