package hwd.kuworuye.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hwd.kuworuye.constants.Constant;

/**
 * 工具类
 *
 * @author sharoncn
 */
public class Util {

    private static Toast toastInstance;
    private static ProgressDialog pd;

    //检查网路的方法；
    public static boolean checkNetWorkStatus(Context context) {

        boolean result;
        if (context == null) {
            // 假定网络为空
            return true;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        result = ni != null && ni.isConnectedOrConnecting();

        if (!result) {
            Util.toast(context, "网络不可用，请检查网络连接");
        }

        return result;
    }

    /**
     * 要选择的图片数量key
     */
    public static final String EXTRA_PHOTO_LIMIT = "com.ns.mutiphotochoser.extra.PHOTO_LIMIT";

    /**
     * 选择返回结果保存key
     */
    public static final String EXTRA_PHOTO_PATHS = "com.ns.mutiphotochoser.extra.PHOTO_PATHS";

    /**
     * 开启一个Activity；
     */
    public static void startActivity(Context context, Class clazz, String str) {
        Intent intent = new Intent();
        intent.putExtra("home", str);
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    /**
     * 验证是否有安装微信
     *
     * @param mContext
     * @return
     */
    public static boolean isInstallWeChat(Context mContext) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        // 这里如果intent为空，就说名没有安装要跳转的应用嘛
        if (intent != null) {
            // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
            return true;
        } else {
            // 没有安装要跳转的app应用，提醒一下
            Util.toast(mContext, "不能使用微信支付，请先下载微信app");
            return false;
        }
    }

    /**
     * 验证是否有安装支付宝支付
     *
     * @param
     * @return
     */
//    public static void isInstallAliPay(BaseFragment fragment, Handler handler) {
//        AliPayHelper aliPayHelper = new AliPayHelper(fragment);
//        aliPayHelper.check(handler);
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 正则表达式验证手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
//         ^1(3|4|5|7|8)[0-9]\d{8}$
//        Pattern pattern_phone = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern pattern_phone = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
        return pattern_phone.matcher(phone).matches();
    }

    /**
     * 显示ProgressDialog
     *
     * @param context Context
     * @param msg     文本
     * @param params  如果需要设置样式,放置在这里
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context, String msg, int... params) {
        int style = ProgressDialog.STYLE_SPINNER;
        try {
            style = params[0];
        } catch (Exception e) {
        }
        pd = new ProgressDialog(context);
        pd.setMessage(msg);
        pd.setProgressStyle(style);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        return pd;
    }

    /**
     * 隐藏进度条；
     */
    public static void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * 身份证号密文显示
     *
     * @param cardNo
     * @return
     */
    public static String transCardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) {
            return null;
        }
        int stars = cardNo.length() - 8;
        if (stars == 10) {
            return (cardNo.substring(0, 3) + "**********" + cardNo.substring(12, 18));
        } else if (stars == 7) {
            return (cardNo.substring(0, 3) + "*******" + cardNo.substring(9, 15));
        }
        return null;
    }


    /**
     * 手机号密文显示
     *
     * @param phone
     * @return
     */
    public static String transPhoneNumber(String phone) {
        if (phone == null) {
            return "";
        } else {
            return (phone.substring(0, 3) + "*****" + phone.substring(8, 11));
        }
    }


    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 显示ProgressDialog
     *
     * @param context Context
     * @param resId   文本ID
     * @param params  如果需要设置样式,放置在这里
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context, int resId, int... params) {
        return showProgressDialog(context, context.getString(resId), params);
    }

    /**
     * 设置EditText只保留两位小数点
     *
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得SD卡根目录
     *
     * @return
     */
    public static String getSdCardPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }


    /**
     * 根据对象获得和字段名相匹配的方法
     *
     * @param field 字段名
     * @param obj   对象
     * @return 匹配到的方法
     */
    public static Method getMethodByField(String field, Object obj) {
        return getMethodByField(field, obj.getClass());
    }

    /**
     * 根据Class对象获得和字段名相匹配的方法
     *
     * @param field 字段名
     * @param clazz Class
     * @return 匹配到的方法
     */
    public static Method getMethodByField(String field, Class<?> clazz) {
        final String get = "get" + field;
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (get.equalsIgnoreCase(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public static void removePreferences(Context mContext, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 向Preferences中写入键值对
     *
     * @param mContext context
     * @param key      键
     * @param value    值
     */
    public static void writePreferences(Context mContext, String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void writePreferences(Context mContext, String key, boolean value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writePreferences(Context mContext, String key, int value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    /**
     * MD5加密
     *
     * @param content 待加密内容
     * @return 密文
     */
    public static String encrypt(String content) {
        StringBuffer sb = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("md5");
            md.update(content.getBytes());
            byte b[] = md.digest();

            int i;
            sb = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb == null ? null : sb.toString().toUpperCase();
    }

    /**
     * 循环加密
     *
     * @param content 待加密内容
     * @param count   循环加密次数
     * @return 密文
     */
    public static String circleEncrypt(String content, int count) {
        String result = content;
        for (int i = 0; i < count; i++) {
            result = encrypt(result);
        }
        return result;
    }


    public static String getCurrentTimeYYYYMMDD() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    public static String getCurrentTimeYYMM() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    /**
     * 获取现在的日期
     *
     * @return
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }


    public static String getMDHMByYMDHMS(String ymdhms) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(ymdhms);
            String mdhm = dateFormat1.format(date);
            return mdhm;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "未知";
    }

    /**
     * 获取本地软件版本
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    /**
     * Toast提示
     * 中间位置
     *
     * @param context
     * @param content
     */
    public static void toast(Context context, String content) {
        if (content == null) {
            return;
        }

        if (toastInstance == null) {
            toastInstance = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }

        toastInstance.setText(content);
        toastInstance.setGravity(Gravity.CENTER, 0, 0);
        toastInstance.show();
    }

    public static void noMoreDataToast(Context context) {
        if (context == null) {
            return;
        }
        if (toastInstance == null) {
            toastInstance = Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT);
        }
        toastInstance.setText("没有更多数据了");
        toastInstance.setGravity(Gravity.CENTER, 0, 0);
        toastInstance.show();
    }

    public static void toastLong(Context context, String content) {
        if (content == null) {
            return;
        }
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void openContentActivity(Context context, Class<?> activityClass, int type) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(Constant.CONTENT_TYPE, type);
        context.startActivity(intent);
    }

    /**
     * 保存一个对象
     */

    public static void saveObj(Context context, Object object, String keyStr) {
        try {
            String obj2Str = SerializableUtil.obj2Str(object);
            SharedPreferences sp = context.getSharedPreferences(keyStr, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(keyStr, obj2Str);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一个对象；
     */
    public static Object readObj(Context context, String keyStr) {
        Object o = null;
        SharedPreferences sp = context.getSharedPreferences(keyStr, Context.MODE_PRIVATE);
        String string2obj = sp.getString(keyStr, "");

        if (TextUtils.isEmpty(string2obj)) {
            return null;
        }

        try {
            o = SerializableUtil.str2Obj(string2obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }


    /**
     * 删除对象
     */

    public static void removeObj(Context context, String keyStr) {
        if (context == null) {
            return;
        }
        try {
            SharedPreferences sp = context.getSharedPreferences(keyStr, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(keyStr);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把双引号的json字符串转换成单引号；
     */
    public static String replaceSingleMark(String doubleMark) {
        String singleMark = doubleMark.replace("\"", "'");
        return singleMark;
    }

    public static String getActivityName(String numStr) {
        if ("1".equals(numStr)) {
            return "推动渠道";
        } else if ("2".equals(numStr)) {
            return "提升认知";
        } else if ("3".equals(numStr)) {
            return "推动试用";
        } else if ("4".equals(numStr)) {
            return "提高使用量";
        } else if ("5".equals(numStr)) {
            return "推动忠诚度";
        }
        return "";
    }

    /**
     * 获取订单中的状态；
     */
    public static String getOrderState(String numStr, String refuse) {
        if ("1".equals(refuse)) {
            if ("4".equals(numStr)) {
                return "待支付";
            } else {
                return "已拒绝";
            }
        } else {
            if ("0".equals(numStr)) {
                return "待提交";
            } else if ("1".equals(numStr)) {
                return "待审批";
            } else if ("2".equals(numStr)) {
                return "审批中";
            } else if ("3".equals(numStr)) {
                return "发货部审批";
            } else if ("4".equals(numStr)) {
                return "待支付";
            } else if ("5".equals(numStr)) {
                return "待发货";
            } else if ("6".equals(numStr)) {
                return "待收货";
            } else if ("7".equals(numStr)) {
                return "已完成";
            } else if ("8".equals(numStr)) {
                return "待财务部审批";
            }
        }
        return "未知";
    }

    /**
     * 获取申请批审状态；
     *
     * @param str
     * @return
     */

    public static String getApproveState(String str, String refuse) {
        if ("1".equals(refuse)) {
            return "已拒绝";
        } else {
            if ("0".equals(str)) {
                return "待提交";
            } else if ("1".equals(str)) {
                return "待审批";
            } else if ("2".equals(str)) {
                return "审批中";
            } else if ("3".equals(str)) {
                return "审批中";
            } else if ("4".equals(str)) {
                return "审批中";
            } else if ("6".equals(str)) {
                return "完成";
            }
        }
        return "未知";
    }

    public static String getHxApproveState(String str, String refuse) {
        if ("1".equals(refuse)) {
            return "已拒绝";
        } else {
            if ("0".equals(str)) {
                return "待核销";
            } else if ("1".equals(str)) {
                return "待审批";
            } else if ("2".equals(str)) {
                return "审批中";
            } else if ("3".equals(str)) {
                return "审批中";
            } else if ("4".equals(str)) {
                return "审批中";
            } else if ("5".equals(str)) {
                return "审批中";
            } else if ("6".equals(str)) {
                return "完成";
            }
        }

        return "未知";
    }

    /**
     * 去掉网络请求中的url%，转换成正确的%
     */

    public static String removePrecent(String url) {
        String removePrecent = url.replace("%", "%25");
        return removePrecent;
    }

}
