package hwd.kuworuye.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.IOException;

import hwd.kuworuye.bean.ShowPersionInfo;

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil me;
    private static Context context;

    public static SharedPreferencesUtil getInstance(Context c) {

        context = c;
        if (me == null) {
            me = new SharedPreferencesUtil();
        }
        return me;
    }



    public void save(String key, Object value) {
        if (context == null) {
            return;
        }

        try {
            String obj2Str = SerializableUtil.obj2Str(value);
            SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, obj2Str);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser() {
        if (context == null) {
            return;
        }
        try {
            SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("userInfo");
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String key) {
        if (context == null) {
            return;
        }
        try {
            SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地用户信息，如果没有返回用户Id为0
     *
     * @param
     * @return
     */
    public ShowPersionInfo.DataBean readUser() {

        ShowPersionInfo.DataBean data = new ShowPersionInfo.DataBean();
        if (context == null) {
            return data;
        }

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String string2obj = sp.getString("userInfo", "");

        if (TextUtils.isEmpty(string2obj)) {
            return data;
        }

        try {
            data = (ShowPersionInfo.DataBean) SerializableUtil.str2Obj(string2obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Object read(String key) {

        if (context == null) {
            return null;
        }

        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        String string2obj = sp.getString(key, "");

        if (TextUtils.isEmpty(string2obj)) {
            return null;
        }

        Object data = null;
        try {
            data = SerializableUtil.str2Obj(string2obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


    /**
     * 保存用户信息
     *
     * @param
     * @param data
     */
    public void saveUser(ShowPersionInfo.DataBean data) {
        if (context == null) {
            return;
        }
        try {
            String obj2Str = SerializableUtil.obj2Str(data);
            SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userInfo", obj2Str);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
