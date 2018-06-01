package hwd.kuworuye.base;

import android.os.Bundle;

import hwd.kuworuye.utils.ProgressUtil;
import hwd.kuworuye.utils.Util;

public abstract class FBaseFragment extends GBaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract int inflateContentView();

    private boolean isLoading;

    protected void checkNetWork() {
        if (this.getActivity() == null) {
            return;
        }
        Util.checkNetWorkStatus(this.getActivity());
    }

    protected void showLoading(String message) {
        isLoading = true;
        if (this.getActivity() != null) {
            ProgressUtil.show(this.getActivity(), message);
        }
    }

    protected void showLoading() {
        isLoading = true;
        if (this.getActivity() != null) {
            ProgressUtil.show(this.getActivity());
        }
    }

    protected void dismissLoading() {
        if (getActivity() != null) {
            ProgressUtil.dismiss();
            isLoading = false;
        }
    }

    public void inProcessing() {
        isLoading = true;
    }

    public boolean isInProcessing() {
        return isLoading;
    }

    public void completeProcessing() {
        isLoading = false;
    }
}
