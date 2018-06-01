package hwd.kuworuye.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import hwd.kuworuye.R;
import hwd.kuworuye.utils.ProgressUtil;
import hwd.kuworuye.utils.StatusBarUtil;
import hwd.kuworuye.utils.Util;

/**
 * Created by Administrator on 2017/2/16.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.comm_title_bar));
        }
    }

    private boolean isLoading;

    public void checkNetWork() {
        Util.checkNetWorkStatus(this);
    }

    protected void showLoading(String message) {
        isLoading = true;
        ProgressUtil.show(this, message);
    }

    protected void showLoading() {
        ProgressUtil.show(this);
    }

    protected void dismissLoading() {
        ProgressUtil.dismiss();
    }

    public void inProcessing() {
        isLoading = true;
    }

    public boolean IsInLoading() {
        return isLoading;
    }

    public void completeProcessing() {
        isLoading = false;
    }
}
