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
import hwd.kuworuye.bean.BackPicBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.ImageTypeTitleUrlBean;
import hwd.kuworuye.bean.OtherCostHeXiaoDetailBean;
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
 * Created by Administrator on 2017/4/6.
 */

public class OtherCostHeXiaoUpdateFragment extends FBaseFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.et_purport)
    EditText mEtPurport;
    @BindView(R.id.et_explain)
    EditText mEtExplain;
    @BindView(R.id.et_cost)
    EditText mEtCost;
    @BindView(R.id.bt_other_cost_submit)
    Button mBtOtherCostSubmit;
    @BindView(R.id.rl_join_site_look)
    RelativeLayout mRlJoinSiteLook;
    @BindView(R.id.mgv_join_site_detail)
    MyGridView mMgvJoinSiteDetail;
    private String mOrderId;
    private String mSingleStr;
    private String mUserId;
    private String mApprovalType;
    private String mExamineStatus;
    private RelativeLayout mRlTicketPic;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_other_cost_hexiao_update;
    }

    public static Fragment newInstance(Bundle bundle) {
        OtherCostHeXiaoUpdateFragment fragment = new OtherCostHeXiaoUpdateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID);
        mRlTicketPic = ((RelativeLayout) rootView.findViewById(R.id.other_cost_pic));
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderId = arguments.getString(Constant.ORDER_DATAIL_ID);
        }
        mGson = new Gson();
        questNetShowData2View();
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requsetNet2TicketPic();
    }


    private void initView() {
        mRlJoinSiteLook.setOnClickListener(this);
        mBtOtherCostSubmit.setOnClickListener(this);
    }

    private void questNetShowData2View() {
        showLoading();
        OkHttpUtils.post(HttpConstant.OTHER_COST_HEXIAO_DETAIL + "id=" + mOrderId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                OtherCostHeXiaoDetailBean xiaoDetailBean = mGson.fromJson(s, OtherCostHeXiaoDetailBean.class);
                if (xiaoDetailBean.isSuccess()) {
                    OtherCostHeXiaoDetailBean.DataBean data = xiaoDetailBean.getData();
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

    /**
     * 得到票据图片
     */
    private void requsetNet2TicketPic() {
        showLoading();
        OkHttpUtils.post(HttpConstant.QUERY_ALL_PIC + "processId=" + mOrderId + "&pageNo=" + "1" + "&pageSize=" + "3" + "&type=" + "10").tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                BackPicBean backPicBean = mGson.fromJson(s, BackPicBean.class);
                BackPicBean.PageListBean pageList = backPicBean.getPageList();
                List<BackPicBean.PageListBean.ListBean> list = pageList.getList();
                initPic(list);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    /**
     * 初始化图片显示到控件上；
     *
     * @param list
     */
    private void initPic(List<BackPicBean.PageListBean.ListBean> list) {
        List<ImageDataUrlBean> picUrlTitle = new ArrayList<>();
        List<ImageTypeTitleUrlBean> typeUrlImageList = new ArrayList<>();
        if (list != null) {
            mRlTicketPic.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                String imgs = list.get(i).getImgs();
                String title = list.get(i).getTitle();
                picUrlTitle.add(new ImageDataUrlBean(imgs, title));
                typeUrlImageList.add(new ImageTypeTitleUrlBean("10", list.get(i).getTitle(), list.get(i).getImgs()));
            }
        } else {
            mRlTicketPic.setVisibility(View.VISIBLE);
        }
        CommBackBigAdapter adapter = new CommBackBigAdapter(getContext(), picUrlTitle);
        mSingleStr = Util.replaceSingleMark(mGson.toJson(typeUrlImageList));
        mMgvJoinSiteDetail.setAdapter(adapter);
    }


    /**
     * 其他费用详情展示
     *
     * @param data
     */
    private void initData(OtherCostHeXiaoDetailBean.DataBean data) {
        mEtPurport.setText(data.getTitle());
        mEtExplain.setText(data.getExplains());
        mEtCost.setText(data.getCosts());

        mApprovalType = data.getApprovalType();
        mExamineStatus = data.getExamineStatus();
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
            case R.id.rl_join_site_look:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.COMM_PIC_SHOW_SITE);
                intent.putExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, Constant.OTHER_COST_HEXIAO_UPDATE);
                //processid 其实就是mOrderId;
                intent.putExtra(Constant.PROCESSID, mOrderId);
                startActivity(intent);
                break;
            case R.id.bt_other_cost_submit:
                String etPurport = mEtPurport.getText().toString().trim();
                String etExplain = mEtExplain.getText().toString().trim();
                String etCost = mEtCost.getText().toString().trim();
                boolean emptyPurport = TextUtils.isEmpty(etPurport);
                boolean emptyCost = TextUtils.isEmpty(etCost);
                boolean emptyExplain = TextUtils.isEmpty(etExplain);
                if (emptyCost || emptyPurport || emptyExplain) {
                    Util.toast(getContext(), "资料填写不完整");
                    return;
                }
                submitUpdate(etPurport, etExplain, etCost);
                break;
        }
    }

    private void submitUpdate(String etPurport, String etExplain, String etCost) {
        showLoading();
        String removePrecent = Util.removePrecent(HttpConstant.OTHER_COST_HEXIAO_UPDATE + "id=" + mOrderId + "&user.id=" + mUserId + "&title=" + etPurport +
                "&explains=" + etExplain + "&costs=" + etCost + "&examineStatus=" + mExamineStatus + "&approvalType=" + mApprovalType + "&photoArrayList=" + mSingleStr);
        OkHttpUtils.post(removePrecent).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                SuccessBean successBean = mGson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommRefreshListEvent(successBean.isSuccess()));
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
