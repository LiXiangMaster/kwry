package hwd.kuworuye.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/26.
 * 实体店铺的类目数据库处理
 */

public class AdressDBManager {

    private SQLiteDatabase db;

    private static final String DB_NAME = "china_cities.db";

    private String DB_PATH;

    private Context mContext;

    public AdressDBManager(Context mContext) {
        this.mContext = mContext;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + mContext.getPackageName() + File.separator + "databases" + File.separator;
    }

    //初始化省 市 区三级地址
    public Map<String, Object> initAddressData() {
        Map<String, Object> map = new HashMap<>();
        db = SQLiteDatabase.openOrCreateDatabase("file:///android_asset/china_cities", null);
        Cursor cursor1 = db.rawQuery("select code , name from t_address_province", null);
        List<String> city = new ArrayList<>();
        List<String> town = new ArrayList<>();
        List<String> options1Items = new ArrayList<>();
        List<List<String>> options2Items = new ArrayList<>();
        List<List<String>> options3_01Items = new ArrayList<>(); //根据city code封装
        List<List<List<String>>> options3Items = new ArrayList<>();//再加上省份code封装
        while (cursor1.moveToNext()) {
            //封装省份
            String name1 = cursor1.getString(cursor1.getColumnIndex("name")); //省份
            String code1 = cursor1.getString(cursor1.getColumnIndex("code")); //省份code
            options1Items.add(name1);
            //根据省份code查找封装对于的市区
            Cursor cursor2 = db.rawQuery("select code , name from t_address_city where provinceCode=" + code1, null);
            //根据省code查找市区
            while (cursor2.moveToNext()) {
                String name2 = cursor2.getString(cursor2.getColumnIndex("name")); //市区
                String code2 = cursor2.getString(cursor2.getColumnIndex("code")); //市区code
                city.add(name2);
                //根据市区查找城镇区
                Cursor cursor3 = db.rawQuery("select name from t_address_town where cityCode=" + code2, null);
                while (cursor3.moveToNext()) {
                    String name3 = cursor3.getString(cursor3.getColumnIndex("name")); //城镇
                    town.add(name3);
                }
                options3_01Items.add(town);
                town = new ArrayList<>();//每次封装一个 需要重新new新对象 防止数据重复
                cursor3.close();
            }
            cursor2.close();
            //封装市区
            options2Items.add(city);
            city = new ArrayList<>();//每次封装一个 需要重新new新对象 防止数据重复
            //封装城镇
            options3Items.add(options3_01Items);
            options3_01Items = new ArrayList<>(); //每次封装一个 需要重新new新对象 防止数据重复
        }
        map.put("shenfen", options1Items);
        map.put("city", options2Items);
        map.put("town", options3Items);
        cursor1.close();
        db.close();
        return map;
    }

}
