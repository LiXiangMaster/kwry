package hwd.kuworuye.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.jpush.android.api.JPushInterface;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.StatusBarUtil;

/**
 * Created by Administrator on 2017/3/10.
 */

public class SplashActivity extends BaseActivity {
    private static final long WAIT_TIME = 2000;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {

        if (getSharedPreferences("firstOpen", MODE_PRIVATE).getInt("firstOpen", 0) == 0) {
            SharedPreferences spCur = getApplication().getSharedPreferences("firstOpen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spCur.edit();
            editor.putInt("firstOpen", 1);
            editor.commit();
            toActivity(GuideActivity.class);
        } else {
            String isSuccessed = (String) SharedPreferencesUtil.getInstance(SplashActivity.this).read("loginsuccessed");
            if ("loginsuccessed".equals(isSuccessed)) {
                toActivity(MainActivity.class);
            } else {
                toActivity(LoginActivity.class);
            }
        }
    }

    private void toActivity(final Class<?> target) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(SplashActivity.this, target);
                startActivity(intent);
                finish();
            }
        }, WAIT_TIME);
    }

    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
