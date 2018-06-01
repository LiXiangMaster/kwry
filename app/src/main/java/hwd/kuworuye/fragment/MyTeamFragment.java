package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.SimpleTreeAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.MyTeamHeadBean;
import hwd.kuworuye.bean.MyTeamBean;
import hwd.kuworuye.bean.tree.FileBean;
import hwd.kuworuye.bean.tree.Node;
import hwd.kuworuye.bean.tree.TreeListViewAdapter;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/5.
 */
public class MyTeamFragment extends FBaseFragment {

    @BindView(R.id.id_tree)
    ListView mIdTree;
    Unbinder unbinder;
    private List<FileBean> mDatas2 = new ArrayList<FileBean>();
    private TreeListViewAdapter mAdapter;
    private String mUserId;
    private MyTeamHeadBean.DataBean mMaxUpData;
    private Gson mGson;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_my_team;
    }

    public static Fragment newInstance(Bundle bundle) {
        MyTeamFragment teamFragment = new MyTeamFragment();
        teamFragment.setArguments(bundle);
        return teamFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUserId = ((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERID));
        requestNet2OneJobData();
        mGson = new Gson();
        return rootView;
    }

    private void requestNet2OneJobData() {
        showLoading();
        OkHttpUtils.post(HttpConstant.MY_TEAM_ONE + "userId=" + mUserId).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                MyTeamHeadBean myTeamBean = mGson.fromJson(s, MyTeamHeadBean.class);
                boolean success = myTeamBean.isSuccess();
                if (success) {
                    mMaxUpData = myTeamBean.getData();
                    int headSalesTotalBox = mMaxUpData.getSalesTotalBox();
                    int headSalesTotalVolume = mMaxUpData.getSalesTotalVolume();
                    String headUserType = mMaxUpData.getUserType();
                    String headUserId = mMaxUpData.getUserId();
                    String headTotalBox = String.valueOf(headSalesTotalBox);
                    String headTotalVolume = String.valueOf(headSalesTotalVolume);
                    String headUserArea = mMaxUpData.getUserArea();
                    String headUserName = mMaxUpData.getUserName();
                    mDatas2.add(new FileBean(1, 0, headUserArea, headUserName, headTotalBox, headTotalVolume, headUserType, headUserId));
                    secondRequest();
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

    private void secondRequest() {
        showLoading();
        OkHttpUtils.post(HttpConstant.SECOND_GET_TEAM + "userId=" + mUserId).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                MyTeamBean testJsonBean = mGson.fromJson(s, MyTeamBean.class);
                if (testJsonBean.isSuccess()) {
                    List<MyTeamBean.DataBean> data = testJsonBean.getData();
                    for (int j = 0; j < data.size(); j++) {
                        String userId = data.get(j).getUserId();
                        String userName = data.get(j).getUserName();
                        String userType = data.get(j).getUserType();
                        String userArea = data.get(j).getUserArea();
                        int salesTotalBox = data.get(j).getSalesTotalBox();
                        String salesTotalBoxStr = String.valueOf(salesTotalBox);
                        int salesTotalVolume = data.get(j).getSalesTotalVolume();
                        String saleTotalVolumeStr = String.valueOf(salesTotalVolume);
                        int id = data.get(j).getId();
                        int parentId = data.get(j).getParentId();

                        mDatas2.add(new FileBean(id, parentId, userArea, userName, salesTotalBoxStr, saleTotalVolumeStr, userType, userId));

                        try {
                            mAdapter = new SimpleTreeAdapter<FileBean>(mIdTree, getContext(), mDatas2, 10);
                            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                                @Override
                                public void onClick(Node node, int position) {

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mIdTree.setAdapter(mAdapter);
                    }
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
