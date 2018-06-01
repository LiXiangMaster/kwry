package hwd.kuworuye.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import hwd.kuworuye.R;

/**
 * @author cjj
 * @ClassName: DownloadService
 * @date 2014年11月18日 下午10:01:14
 * @Description: 下载网络图片的Service
 */
public class UpdateService extends Service {

    private static final String TAG = "DownloadService";

    /**
     * 定义通知管理者对象
     */
    private NotificationManager manager;

    /**
     * 定义通知对象
     */
    private Notification.Builder builder;
    private Notification notification;
    /**
     * 通知的id
     */
    private int notificationId = 1001;

    /**
     * 下载成功
     */
    private final int SUCCESS = 1002;

    /**
     * 下载失败
     */
    private final int FAILURE = 1003;


    /**
     * 定义意图对象
     */
    private PendingIntent pendingIntent;

    /**
     * 通知栏默认显示的View
     */
    private RemoteViews contentView;

    /**
     * 下载资源保存的路径
     */
    private String save_path;

    /**
     * 下载路径
     */
    private String down_url;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        down_url = intent.getStringExtra("apkUrl");

        // 创建保存路径
        save_path = initFile();
        // 创建通知
        createNotification();
        // 线程下载
        createThread();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 创建通知的方法
     */
    private void createNotification() {

        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new Notification.Builder(this);

        builder.setContentTitle("开始下载");
        builder.setContentText("message");
        builder.setSmallIcon(R.drawable.logo);
        contentView = new RemoteViews(getPackageName(), R.layout.update);
        contentView.setTextViewText(R.id.download_states, "正在下载");
        contentView.setTextViewText(R.id.percent, "0.0%");
        contentView.setProgressBar(R.id.progress_num, 100, 0, false);
        builder.setContent(contentView);

        notification = builder.getNotification();//将builder对象转换为普通的notification
        notification.flags |= Notification.FLAG_AUTO_CANCEL;//点击通知后通知消失
        manager.notify(notificationId, notification);//运行notification
    }

    /**
     * 创建线程下载资源的方法
     */
    @SuppressLint("HandlerLeak")
    private void createThread() {
        /**
         * 自定义handler更新UI和处理下载下来的资源
         */
        final Handler mHandler = new Handler() {

            @SuppressWarnings("deprecation")
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch (msg.what) {
                    case SUCCESS: //文件下载成功

                        //下载完成后点击通知栏进行安装
//                        File file = new File(save_path);
//                        Uri uri = Uri.fromFile(file);
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                        pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, intent, 0);
//                        builder.setContentIntent(pendingIntent);
//                        notification = builder.getNotification();
//                        manager.notify(notificationId, notification);

                        //关闭 服务
                        stopSelf();

                        manager.cancel(notificationId);
                        installApk();
                        break;
                    case FAILURE:
                        manager.notify(notificationId, notification);
                        //关闭 服务
                        stopSelf();
                        break;
                    default:
                        //关闭 服务
                        stopSelf();
                        break;
                }
            }
        };

        //下载文件
        final Message message = new Message();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    long downloadSize = downloadUpdateFile(down_url, save_path);
                    if (downloadSize > 0) {
                        message.what = SUCCESS;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = FAILURE;
                    mHandler.sendMessage(message);
                }

            }
        }).start();
    }


    private void installApk() {
        File apkFile = new File(save_path);
        if (!apkFile.exists()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
                "application/vnd.android.package-archive");

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    /**
     * 初始化被下载的文件保存的路径
     */
    private String initFile() {

        String fileName = "kwry.apk";
        File file;
        File parent = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "kwry" + File.separator);

        }

        //如果这个文件夹不存在就自己生成这个文件夹
        if (!parent.exists()) {
            parent.mkdirs();
        }
        file = new File(parent, fileName);
        String tempStr = file.getPath();
        return tempStr;
    }

    /***
     * 下载文件
     *
     * @return
     * @throws MalformedURLException
     */
    public long downloadUpdateFile(String down_url, String save_path)
            throws Exception {
        // 提示step
        int down_step = 1;
        // 文件总大小
        double totalSize;
        // 已经下载好的大小
        double downloadCount = 0.00;
        // 已经上传的文件大小
        int updateCount = 0;
        // 输入流
        InputStream inputStream;
        // 输出流
        OutputStream outputStream;

        // 下载链接
        URL url = new URL(down_url);
        // 网络请求
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setConnectTimeout(2000);
        httpURLConnection.setReadTimeout(5000);
        // 获取下载文件的总size
        totalSize = httpURLConnection.getContentLength();
        contentView.setTextViewText(R.id.total, "/" + doubleFormat(totalSize / (1024 * 1024)) + "M");
        // 网络请求失败
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }

        inputStream = httpURLConnection.getInputStream();

        outputStream = new FileOutputStream(save_path, false);
        byte buffer[] = new byte[1024];
        int readSize = 0;
        while ((readSize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readSize);
            downloadCount += readSize;// 时时获取下载到的大小
            /**
             * 每次增张5%
             */
            if (updateCount == 0
                    || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                updateCount += down_step;
                contentView.setTextViewText(R.id.percent, updateCount + "%");
                contentView.setProgressBar(R.id.progress_num, 100, updateCount,
                        false);
                contentView.setTextViewText(R.id.download, doubleFormat(downloadCount / (1024 * 1024)) + "M");
                // show_view
                manager.notify(notificationId, notification);

            }

        }
        //判断是否下载完成
        if (totalSize > downloadCount) {
            downloadUpdateFile(down_url, save_path);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();

        return (long) downloadCount;

    }

    /**
     * 将字节流转换成M的转换工具
     *
     * @param d
     * @return
     */
    public String doubleFormat(double d) {
        DecimalFormat df = new DecimalFormat("0.#");
        return df.format(d);
    }
}
