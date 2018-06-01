package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.bean.AppVerSionInfo;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.fragment.HomeFragment;
import hwd.kuworuye.fragment.MeFragment;
import hwd.kuworuye.fragment.NewsFragment;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.utils.VersionUtil;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fl_home_main_container)
    FrameLayout mFlHomeMainContainer;
    @BindView(R.id.rb_news)
    RadioButton mRbNews;
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_me)
    RadioButton mRbMe;
    @BindView(R.id.rg_home_main)
    RadioGroup mRgHomeMain;
    private int curIndex;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentTransaction transaction;
    private boolean isExit;
    private RadioButton[] rbArray;
    private String mUserId;

    private static final int MSG_SET_TAGS = 1002;
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            isExit = false;
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inintData();
        checkVersionUpdate();
        setTag();
        setAlias();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState); 为了防止手机内存不足的时候 fragement重影的问题；
    }

    /**
     * 根据别名推送；
     */
    private void setAlias() {
        mUserId = ((String) SharedPreferencesUtil.getInstance(this).read(Constant.USERID));
        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, mUserId));
    }


    /***
     * 极光推送根据标签来推送消息；
     */
    private void setTag() {
        String userType = (String) SharedPreferencesUtil.getInstance(MainActivity.this).read(Constant.USERTYPE);
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add(userType);
        if (!Util.isValidTagAndAlias(userType)) {
            Util.toast(MainActivity.this, "标签格式设置错误");
            return;
        }
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;

                case 6002:
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    break;

                default:
            }
        }

    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
            }

        }

    };

    private void checkVersionUpdate() {
        showLoading();
        OkHttpUtils.post(HttpConstant.APP_VERSION_UPDATE + "type=" + 1).tag(this).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                AppVerSionInfo appVerSionInfo = gson.fromJson(s, AppVerSionInfo.class);
                boolean success = appVerSionInfo.isSuccess();
                if (success) {
                    update(appVerSionInfo);
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(MainActivity.this, failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });
    }

    private void update(AppVerSionInfo appVerSionInfo) {
        VersionUtil updateManager = new VersionUtil(MainActivity.this, appVerSionInfo);
        updateManager.checkUpdate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Util.checkNetWorkStatus(this)) {
            Util.openContentActivity(this, ContentActivity.class, Constant.COMM_NO_DATA);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private void inintData() {
        mFragmentList = getFragmentList();
        rbArray = new RadioButton[mRgHomeMain.getChildCount()];

        for (int i = 0; i < rbArray.length; i++) {
            rbArray[i] = (RadioButton) mRgHomeMain.getChildAt(i);
        }

        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fl_home_main_container, mFragmentList.get(1));
        transaction.commit();
        curIndex = 1;
        mRgHomeMain.setOnCheckedChangeListener(this);
    }

    public List<Fragment> getFragmentList() {
        List<Fragment> list = new ArrayList<>();
        list.add(NewsFragment.newInstance(null));
        list.add(HomeFragment.newInstance(null));
        list.add(MeFragment.newInstance(null));
        return list;
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Util.toast(this, "再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rbArray.length; i++) {
            if (checkedId == rbArray[i].getId()) {
                // 切换Fragment
                switchFragment(i);
            }
        }
    }

    protected void switchFragment(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = mFragmentList.get(i);
        if (!fragment.isAdded()) {
            transaction.hide(mFragmentList.get(curIndex)).add(R.id.fl_home_main_container, fragment);
        } else {
            transaction.hide(mFragmentList.get(curIndex)).show(fragment);
        }
        transaction.commit();
        curIndex = i;
    }

}
