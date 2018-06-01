package hwd.kuworuye.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import hwd.kuworuye.bean.AppVerSionInfo;
import hwd.kuworuye.service.UpdateService;

/**
 * app自动更新
 **/
public class VersionUtil {
    private Context mContext;

    private AppVerSionInfo mVersionInfo;
    private Intent intent;

    public VersionUtil(Context context, AppVerSionInfo versionInfo) {
        this.mContext = context;
        this.mVersionInfo = versionInfo;
        intent = new Intent(mContext, UpdateService.class);
        String apkUrl = versionInfo.getData().getUrls();
//        intent.putExtra("apkUrl", "http://www.chijiao79.com:8081/apk/beita.apk");
        intent.putExtra("apkUrl", apkUrl);
    }

    /**
     * 获取程序版本号
     */
    private String getVersionCode() {
        String versionCode = "";
        try {
            versionCode = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 是否更新提示窗口
     */
    private void showNoticeDialog() {
        final AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，请立即更新");
        builder.setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startService(intent);
                        dialog.dismiss();
                    }
                });

    /*    builder.setNegativeButton("取消",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new UpdateCancleEvent(true));
                    }
                });*/
        Dialog noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    public void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String versionCode = getVersionCode();
                double versionCodeDouble = Double.parseDouble(versionCode);
                String varsionCodeNet = mVersionInfo.getData().getVarsionCode();
                double versionCodeNetDouble = Double.parseDouble(varsionCodeNet);
                if (!TextUtils.isEmpty(versionCode) &&(versionCodeNetDouble>versionCodeDouble)) {
                    showNoticeDialog();
                }
            }
        }).run();
    }
}