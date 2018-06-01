package hwd.kuworuye.interf;

import android.support.v4.app.Fragment;

/**
 * 功能：改变标题文字Listener
 */
public interface ChangeFragmentInterface {
    /**
     * 功能：改变标题文字
     */
    void changeFragment(String titleStr, boolean isShowRightBtn, Fragment toFragment);
}
