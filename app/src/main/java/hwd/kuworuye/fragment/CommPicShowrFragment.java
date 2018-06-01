package hwd.kuworuye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
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
import hwd.kuworuye.activity.SitePicUpLoadActivity;
import hwd.kuworuye.adapter.CommAddPicBackAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.QueryPicBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.BackPic2ListEvent;
import hwd.kuworuye.event.CommPicShowEvent;
import hwd.kuworuye.event.HideDeleteIvEvent;
import hwd.kuworuye.event.ShowDeleteIvEvent;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CommPicShowrFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.mg_pic_manager)
    PullToRefreshGridView mMgPicManager;
    Unbinder unbinder;
    @BindView(R.id.add_pic)
    Button mAddPic;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private Topbar mTopbar;
    private View mTopBarView;
    private boolean isFirstClick = true;
    private int mJoinWay;
    private Bundle mArguments;
    private String mProcessId;

    private int mPageNo = 1;
    private int mPageSize = 15;
    private List<ImageDataUrlBean> backPicList = new ArrayList<>();
    private CommAddPicBackAdapter mMAddPicBackAdapter;

    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_activity_site_manager;
    }

    public static Fragment newInstance(Bundle bundle) {
        CommPicShowrFragment picManager = new CommPicShowrFragment();
        picManager.setArguments(bundle);
        return picManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        mArguments = getArguments();
        if (mArguments != null) {
            mJoinWay = mArguments.getInt(Constant.JOIN_COMM_PIC_SHOW_WAY);
            mProcessId = mArguments.getString(Constant.PROCESSID);
        }
        initTopbar();
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        backPicList.clear();
        mMAddPicBackAdapter = null;
        initData(true);
        mAddPic.setOnClickListener(this);
    }


    private void initListener() {
        mMgPicManager.setMode(PullToRefreshBase.Mode.BOTH);
        mMgPicManager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mMgPicManager.setMode(PullToRefreshBase.Mode.BOTH);
                backPicList.clear();
                mPageNo = 1;
                initData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mPageNo++;
                initData(false);
            }
        });

    }

    private void initData(boolean isShowLoading) {

        switch (mJoinWay) {
            case Constant.JOINSITE_APPLY_DETAIL:
                mAddPic.setVisibility(View.GONE);
                //调用查看图片接口；
                lookPic("7", isShowLoading);
                break;
            case Constant.JOIN_SITE_HEXIAO_DETAIL_SITE:
                mAddPic.setVisibility(View.GONE);
                lookPic("8", isShowLoading);
                break;
            case Constant.JOIN_SITE_HEXIAO_DETAIL_TICKET:
                mAddPic.setVisibility(View.GONE);
                lookPic("9", isShowLoading);
                break;
            case Constant.DISPLAY_APPLY_DETAIL_MARK:
                lookPic("3", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.DISPLAY_APPLY_MARK_UPDATE:
                lookPic("3", isShowLoading);
                break;
            case Constant.DISPLAY_DETAIL_HEXIAO_TICKET:
                lookPic("6", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.DISPLAY_DETAIL_HEXIAO_DISPLAY:
                lookPic("5", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.DISPLAY_DETAIL_HEXIAO_MARK:
                lookPic("4", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.OTHER_COST_HEXIAO_DETAIL:
                lookPic("10", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.OTHER_COST_HEXIAO_UPDATE:
                lookPic("10", isShowLoading);
                break;
            case Constant.JOIN_SITE_HEXIAO_SITE_UPDATE:
                lookPic("8", isShowLoading);
                break;
            case Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE:
                lookPic("9", isShowLoading);
                break;
            case Constant.SALE_ACTIVITY_SITE_HEXIAO_PIC:
                lookPic("1", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.SALE_ACTIVITY_SITE_TICKET_PIC:
                lookPic("2", isShowLoading);
                mAddPic.setVisibility(View.GONE);
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_MARK:
                lookPic("4", isShowLoading);
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY:
                lookPic("5", isShowLoading);
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE_TICKET:
                lookPic("6", isShowLoading);
                break;
            case Constant.JOIN_SITE_COST_APPLY_UPDATE:
                lookPic("7", isShowLoading);
                break;
            case Constant.SALE_HEXIAO_SITE_UPDATE:
                lookPic("1", isShowLoading);
                break;
            case Constant.SALE_HEXIAO_TICKET_UPDATE:
                lookPic("2", isShowLoading);
                break;
            case Constant.DISPLAY_APPLY_MARK_SUBMIT:
                //提交的操作过来的是不需要请求网络的 所以死禁止掉刷新；
                mMgPicManager.setMode(PullToRefreshBase.Mode.DISABLED);
                break;
            case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                //提交进场费用
                mMgPicManager.setMode(PullToRefreshBase.Mode.DISABLED);
                break;

        }

    }

    @Subscribe
    public void updatePic(CommPicShowEvent event) {
        if (event.isRefresh()) {
            backPicList.clear();
            mMAddPicBackAdapter = null;
            switch (mJoinWay) {
                case Constant.DISPLAY_APPLY_MARK_UPDATE:
                    lookPic("3", true);
                    break;
                case Constant.OTHER_COST_HEXIAO_UPDATE:
                    lookPic("10", true);
                    break;
                case Constant.JOIN_SITE_HEXIAO_SITE_UPDATE:
                    lookPic("8", true);
                    break;
                case Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE:
                    lookPic("9", true);
                    break;
                case Constant.DISPLAY_DETAIL_HEXIAO_MARK:
                    lookPic("4", true);
                    break;
                case Constant.DISPLAY_DETAIL_HEXIAO_DISPLAY:
                    lookPic("5", true);
                    break;
                case Constant.DISPLAY_DETAIL_HEXIAO_TICKET:
                    lookPic("6", true);
                    break;
                case Constant.DISPLAY_HEXIAO_UPDATE_TICKET:
                    lookPic("6", true);
                    break;
                case Constant.JOIN_SITE_COST_APPLY_UPDATE:
                    lookPic("7", true);
                    break;
                case Constant.SALE_HEXIAO_SITE_UPDATE:
                    lookPic("1", true);
                    break;
                case Constant.SALE_HEXIAO_TICKET_UPDATE:
                    lookPic("2", true);
                    break;
                case Constant.DISPLAY_HEXIAO_UPDATE_MARK:
                    lookPic("4", true);
                    break;
                case Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY:
                    lookPic("5", true);
                    break;
            }

        }
    }

    /**
     * 查看图片接口
     *
     * @param type
     */
    private void lookPic(String type, boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
        OkHttpUtils.post(HttpConstant.QUEERY_PIC_BY_TYPE + "processId=" + mProcessId + "&pageNo=" + mPageNo + "&pageSize=" + mPageSize + "&type=" + type).tag(getContext()).execute(new StringCallback() {
            List<ImageDataUrlBean> addList = new ArrayList<>();

            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                QueryPicBean queryPicBean = mGson.fromJson(s, QueryPicBean.class);
                int count = queryPicBean.getPageList().getCount();
                List<QueryPicBean.PageListBean.ListBean> list = queryPicBean.getPageList().getList();
                if (list != null) {
                    if (backPicList.size() == count) {
                        Util.toast(getContext(), "没有更多照片啦~");
                        noRefresh();
                        mMgPicManager.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            addList.add(new ImageDataUrlBean(list.get(i).getImgs(), list.get(i).getTitle()));
                        }
                        backPicList.addAll(addList);
                    }

                }
                noRefresh();
                initAdapter(backPicList);
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
                noRefresh();
            }
        });
    }


    private void noRefresh() {
        if (mMgPicManager != null) {
            mMgPicManager.onRefreshComplete();
        }
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
        switch (backType) {
            case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                initAdapter(backPicList);
                break;
            case Constant.DISPLAY_APPLY_MARK_SUBMIT:
                initAdapter(backPicList);
                break;
        }
    }

    private void initAdapter(List<ImageDataUrlBean> list) {
        if (mMAddPicBackAdapter == null) {
            mMAddPicBackAdapter = new CommAddPicBackAdapter(getContext(), list);
            mMgPicManager.setAdapter(mMAddPicBackAdapter);
            noRefresh();
        } else {
            mMAddPicBackAdapter.notifyDataSetChanged();
        }
        setEmpty();
    }


    private void setEmpty() {
        if (mMgPicManager != null) {
            mMgPicManager.setEmptyView(mLlEmpty);
        }
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
                if (isFirstClick) {
                    EventBus.getDefault().post(new ShowDeleteIvEvent(isFirstClick));
                    isFirstClick = !isFirstClick;
                } else {
                    EventBus.getDefault().post(new HideDeleteIvEvent(isFirstClick));
                    isFirstClick = !isFirstClick;
                }
            }
        });
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
        Intent intent = new Intent(getContext(), SitePicUpLoadActivity.class);
        switch (view.getId()) {
            case R.id.add_pic:
                if (Constant.SUBMIT_JOIN_SITE_COST_APPLY == mJoinWay) {
                    //进场费用添加图片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.SUBMIT_JOIN_SITE_COST_APPLY);
                    startActivity(intent);
                } else if (Constant.DISPLAY_APPLY_EDIT == mJoinWay) {
                    //产品陈列申请添加图片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_APPLY_EDIT);
                    startActivity(intent);
                } else if (Constant.OTHER_COST_HEXIAO_UPDATE == mJoinWay) {
                    //其他费用核销票据修改添加图片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.OTHER_COST_HEXIAO_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.JOIN_SITE_HEXIAO_SITE_UPDATE == mJoinWay) {
                    //进场费用核销现场修改
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.JOIN_SITE_HEXIAO_SITE_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE == mJoinWay) {
                    //进场费用票据修改
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_MARK == mJoinWay) {
                    //产品陈列核销修改，超市明细照片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_HEXIAO_UPDATE_MARK);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY == mJoinWay) {
                    //产品陈列核销修改，产品陈列照片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.DISPLAY_HEXIAO_UPDATE_TICKET == mJoinWay) {
                    //产品陈列核销修改，票据照片；
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_HEXIAO_UPDATE_TICKET);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.DISPLAY_APPLY_MARK_UPDATE == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_APPLY_MARK_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.DISPLAY_APPLY_MARK_SUBMIT == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_APPLY_MARK_SUBMIT);
                    startActivity(intent);
                } else if (Constant.DISPLAY_DETAIL_HEXIAO_TICKET == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.DISPLAY_DETAIL_HEXIAO_TICKET);
                    startActivity(intent);
                } else if (Constant.JOIN_SITE_COST_APPLY_UPDATE == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.JOIN_SITE_COST_APPLY_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.SALE_HEXIAO_SITE_UPDATE == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.SALE_HEXIAO_SITE_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                } else if (Constant.SALE_HEXIAO_TICKET_UPDATE == mJoinWay) {
                    intent.putExtra(Constant.JOIN_ADD_PIC_WAY, Constant.SALE_HEXIAO_TICKET_UPDATE);
                    intent.putExtra(Constant.PROCESSID, mProcessId);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
