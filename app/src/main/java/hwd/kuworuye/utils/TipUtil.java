package hwd.kuworuye.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import hwd.kuworuye.R;
import hwd.kuworuye.weight.MyDialog;

/**
 * Created by Administrator on 2017/3/10.
 */

public class TipUtil {
    public static void operationSuccessTip(final Context context, final boolean isFinishActivity) {
        View view = LayoutInflater.from(context).inflate(R.layout.operation_success, null);
        final MyDialog dialog = new MyDialog(context, view, R.style.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_operation_confirm);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFinishActivity) {
                    ((Activity) context).finish();
                }
                dialog.dismiss();
            }
        });
    }
}
