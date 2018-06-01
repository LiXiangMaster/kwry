package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.GetGoodsAreaAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.ChooseAreaBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ShowSiteListBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.AddAdressSuccessEvent;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class AreaManagerFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_area_history)
    ListView mLvAreaHistory;
    Unbinder unbinder;
    @BindView(R.id.ll_add_area)
    LinearLayout mLlAddArea;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private ShowSiteListBean.DataBean mDataBean;
    private int mAnInt;
    private List<ShowSiteListBean.DataBean> mDataBeanList;

    @Override
    protected int inflateContentView() {

        return R.layout.fragment_area_manager;
    }

    public static Fragment newInstance(Bundle bundle) {
        AreaManagerFragment fragment = new AreaManagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mAnInt = arguments.getInt(Constant.JOIN_KW_COM_TYPE, 0);
        }
        requestNet2GetSiteList();
        mLlAddArea.setOnClickListener(this);
        mLvAreaHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mAnInt != 0 && mAnInt == Constant.SUBMIT_ORDER) {
                    if (mDataBeanList != null) {
                        ChooseAreaBean chooseAreaBean = new ChooseAreaBean(mDataBeanList.get(i).getName(), mDataBeanList.get(i).getPhone(), mDataBeanList.get(i).getRegion() + mDataBeanList.get(i).getAddress());
                        Util.saveObj(getContext(), chooseAreaBean, Constant.FIRST_CHOOSE);
                        EventBus.getDefault().post(new CommRefreshListEvent(true));
                        getActivity().finish();
                    }
                }
            }
        });
    }

    private void requestNet2GetSiteList() {
        showLoading();
        String userId = (String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERID);
        OkHttpUtils.post(HttpConstant.GET_SITE_LIST + "userId=" + userId).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                ShowSiteListBean showSiteList = gson.fromJson(s, ShowSiteListBean.class);
                mDataBeanList = showSiteList.getData();
                boolean success = showSiteList.isSuccess();
                if (success) {
                    initAdapter();
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

    private void initAdapter() {
        if (mDataBeanList.size() != 0) {
            for (int i = 0; i < mDataBeanList.size(); i++) {
                if (mDataBeanList.get(i).getIsDefault().equals("1")) {
                    mDataBean = mDataBeanList.get(i);
                    mDataBeanList.remove(i);
                    break;
                }
            }
        }
        if (mDataBean != null) {
            mDataBeanList.add(0, mDataBean);
        }

        GetGoodsAreaAdapter adapter = new GetGoodsAreaAdapter(getActivity(), mDataBeanList);
        mLvAreaHistory.setEmptyView(mLlEmpty);
        mLvAreaHistory.setAdapter(adapter);

    }

    @Subscribe
    public void addAdressSuccessEvent(AddAdressSuccessEvent event) {
        boolean addSuccess = event.isAddSuccess();
        if (addSuccess) {
            requestNet2GetSiteList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getActivity());
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_area:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.GET_GOODS_AREA_ADD);
                break;
        }
    }


}
