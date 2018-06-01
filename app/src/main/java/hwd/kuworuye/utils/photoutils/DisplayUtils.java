package hwd.kuworuye.utils.photoutils;

import android.content.Context;

public class DisplayUtils {

    public static int px2dip(float pxValue, Context context) {
        return (int) (pxValue
                / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int dip2px(float dipValue, Context context) {
        return (int) (dipValue
                * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
