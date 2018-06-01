package hwd.kuworuye.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import hwd.kuworuye.adapter.TargetDoneAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.TargetDoneBean;
import hwd.kuworuye.bean.TargetDoneInfo;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.RoundProgressBar;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class TargetDoneFragment extends FBaseFragment {
    @BindView(R.id.lv_target_done)
    ListView mLvTargetDone;
    Unbinder unbinder;
    @BindView(R.id.rpb_target_done_progress)
    RoundProgressBar mRpbTargetDoneProgress;
    @BindView(R.id.tv_target_back_money)
    TextView mTvTargetBackMoney;
    @BindView(R.id.tv_really_back_money)
    TextView mTvReallyBackMoney;
    private List<TargetDoneInfo> list = new ArrayList<>();
    private Topbar mTopbar;
    private View mTopBarView;
    private String mUsrId;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_target_done;
    }

    public static Fragment newInstance(Bundle bundle) {
        TargetDoneFragment doneFragment = new TargetDoneFragment();
        doneFragment.setArguments(bundle);
        return doneFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUsrId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        Bundle arguments = getArguments();
        mGson = new Gson();
        if (arguments != null) {
            String userId = arguments.getString(Constant.USERID);
            if (userId != null) {
                mUsrId = userId;
            }
        }
        requestNet2targetDone();
        initTopbar();
        return rootView;
    }

    private void requestNet2targetDone() {
        showLoading();
        OkHttpUtils.post(HttpConstant.TARGET_PLAN + "userId=" + mUsrId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                TargetDoneBean targetDoneBean = mGson.fromJson(s, TargetDoneBean.class);
                boolean success = targetDoneBean.isSuccess();
                if (success) {
                    TargetDoneBean.DataBean data = targetDoneBean.getData();
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

    private void initData(TargetDoneBean.DataBean data) {
        for (int i = 1; i < 13; i++) {
            switch (i) {
                case 1:
                    double january = data.getJanuary();
                    double januaryActual = data.getJanuaryActual();
                    double precent1 = 0;
                    if (january != 0) {
                        precent1 = (januaryActual / january) * 100;
                    }
                    TargetDoneInfo oneMoth = new TargetDoneInfo("一月", january, januaryActual, precent1);
                    list.add(oneMoth);

                    break;
                case 2:
                    double february = data.getFebruary();
                    double februaryActual = data.getFebruaryActual();
                    double precent2 = 0;
                    if (february != 0) {
                        precent2 = (februaryActual / february) * 100;
                    }
                    TargetDoneInfo twoMoth = new TargetDoneInfo("二月", february, februaryActual, precent2);
                    list.add(twoMoth);
                    break;
                case 3:
                    double march = data.getMarch();
                    double precet3 = 0;
                    double marchActual = data.getMarchActual();
                    if (march != 0) {
                        precet3 = (marchActual / march) * 100;
                    }
                    TargetDoneInfo threeMoth = new TargetDoneInfo("三月", march, marchActual, precet3);
                    list.add(threeMoth);

                    break;
                case 4:
                    double april = data.getApril();
                    double percent4 = 0;
                    double aprilActual = data.getAprilActual();
                    if (april != 0) {
                        percent4 = (aprilActual / april) * 100;
                    }
                    TargetDoneInfo fourMoth = new TargetDoneInfo("四月", april, aprilActual, percent4);
                    list.add(fourMoth);

                    break;
                case 5:
                    double may = data.getMay();
                    double precent5 = 0;
                    double mayActual = data.getMayActual();
                    if (may != 0) {
                        precent5 = (mayActual / may) * 100;
                    }
                    TargetDoneInfo fiveMoth = new TargetDoneInfo("五月", may, mayActual, precent5);
                    list.add(fiveMoth);

                    break;
                case 6:
                    double june = data.getJune();
                    double precent6 = 0;
                    double juneActual = data.getJuneActual();
                    if (june != 0) {
                        precent6 = (juneActual / june) * 100;
                    }
                    TargetDoneInfo sixMonth = new TargetDoneInfo("六月", june, juneActual, precent6);
                    list.add(sixMonth);

                    break;
                case 7:
                    double july = data.getJuly();
                    double precent7 = 0;
                    double julyActual = data.getJulyActual();
                    if (july != 0) {
                        precent7 = (julyActual / july) * 100;
                    }
                    TargetDoneInfo senvenMoth = new TargetDoneInfo("七月", july, julyActual, precent7);
                    list.add(senvenMoth);

                    break;
                case 8:
                    double august = data.getAugust();
                    double precent8 = 0;
                    double augustActual = data.getAugustActual();
                    if (august != 0) {
                        precent8 = (augustActual / august) * 100;
                    }
                    TargetDoneInfo augustMoth = new TargetDoneInfo("八月", august, augustActual, precent8);
                    list.add(augustMoth);


                    break;
                case 9:
                    double september = data.getSeptember();
                    double precent9 = 0;
                    double septemberActual = data.getSeptemberActual();
                    if (september != 0) {
                        precent9 = (septemberActual / september) * 100;
                    }
                    TargetDoneInfo septemberMoth = new TargetDoneInfo("九月", september, septemberActual, precent9);
                    list.add(septemberMoth);

                    break;
                case 10:
                    double october = data.getOctober();
                    double precent10 = 0;
                    double octoberActual = data.getOctoberActual();

                    if (october != 0) {
                        precent10 = (octoberActual / october) * 100;
                    }
                    TargetDoneInfo shiMoth = new TargetDoneInfo("十月", october, octoberActual, precent10);
                    list.add(shiMoth);


                    break;
                case 11:
                    double november = data.getNovember();
                    double precent11 = 0;
                    double novemberActual = data.getNovemberActual();
                    if (november != 0) {
                        precent11 = (novemberActual / november) * 100;
                    }
                    TargetDoneInfo shiYiMoth = new TargetDoneInfo("十一月", november, novemberActual, precent11);
                    list.add(shiYiMoth);
                    break;
                case 12:
                    double december = data.getDecember();
                    double precent12 = 0;
                    double decemberActual = data.getDecemberActual();
                    if (december != 0) {
                        precent12 = (decemberActual / december) * 100;
                    }
                    TargetDoneInfo shiErMoth = new TargetDoneInfo("十二月", december, decemberActual, precent12);
                    list.add(shiErMoth);
                    break;
                default:
                    break;
            }
        }
        double totalAim = data.getTotalAim();
        double totalAimActual = data.getTotalAimActual();
        mTvTargetBackMoney.setText(totalAim + "元");
        mTvReallyBackMoney.setText(totalAimActual + "元");

        TargetDoneAdapter doneAdapter = new TargetDoneAdapter(getActivity(), list);
        mLvTargetDone.setAdapter(doneAdapter);
        double precnet = 0;
        if (totalAim != 0) {
            precnet = (totalAimActual / totalAim) * 100;
        }
        mRpbTargetDoneProgress.setProgress((int) precnet);
        mRpbTargetDoneProgress.setCricleProgressColor(Color.parseColor("#F96FA3"));
    }

    private void initTopbar() {
        mTopBarView = getActivity().findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(getContext(), mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void rightClick() {
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.TARGET_DONE_DETAIL);
                intent.putExtra(Constant.USERID, mUsrId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
