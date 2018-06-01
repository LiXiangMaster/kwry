package hwd.kuworuye.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * 工具类
 *
 * @author sharoncn
 */
public class ProgressUtil {

    private static ProgressDialog dialog;

    public static void show(Context context) {
        show(context, "");
    }

    public static void show(Context context, String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);                             //设置进度条是否为不明确(来回旋转)
        dialog.setCanceledOnTouchOutside(false);                    //设置点击屏幕不消失
        dialog.setCancelable(true);

        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        } else {
            dialog.setMessage("数据加载中...");
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception ex) {

        }
    }

}
