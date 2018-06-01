package hwd.kuworuye.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import hwd.kuworuye.R;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.AddAdressSuccessEvent;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.AddressPickTask;
import hwd.kuworuye.utils.LoctionUtil;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.TipUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.Topbar;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AreaAddorUpdateFragment extends FBaseFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.tv_getgoods_area)
    TextView mTvGetgoodsArea;
    @BindView(R.id.ll_check_choose)
    LinearLayout mLlCheckChoose;
    @BindView(R.id.et_getgoods_name)
    EditText mEtGetgoodsName;
    @BindView(R.id.et_getgoods_phone)
    EditText mEtGetgoodsPhone;
    @BindView(R.id.et_detail_adress)
    EditText mEtDetailAdress;
    private Topbar mTopbar;
    private View mTopBarView;
    private String mUserId;
    private String mString;
    private String mAddressid;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private String province = "湖北省";
    private String city = "武汉市";
    private String district = "武昌区";

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_getgoods_area;
    }

    public static Fragment newInstance(Bundle bundle) {
        AreaAddorUpdateFragment fragment = new AreaAddorUpdateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        initLocation();
        mUserId = ((String) SharedPreferencesUtil.getInstance(getActivity()).read(Constant.USERID));
        mLlCheckChoose.setOnClickListener(this);
        initTopbar();

        Bundle arguments = getArguments();
        if (arguments != null) {
            mString = arguments.getString(Constant.QUFEN_EDIT_ADD);
            if (mString.equals("edit")) {
                String goodsname = arguments.getString("goodsname");
                String phone = arguments.getString("phone");
                String address = arguments.getString("address");
                String region = arguments.getString("region");
                mAddressid = arguments.getString("addressid");

                mEtGetgoodsName.setText(goodsname);
                mEtGetgoodsPhone.setText(phone);
                mEtDetailAdress.setText(address);
                mTvGetgoodsArea.setText(region);
                mTvGetgoodsArea.setTextColor(Color.BLACK);
            }
        }
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        startLocation();
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }


    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(false);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);
        // 设置是否单次定位
        locationOption.setOnceLocation(false);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(false);
        //设置是否使用传感器
        locationOption.setSensorEnable(false);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
//        String strInterval = etInterval.getText().toString();
        String strInterval = "2000";
        if (!TextUtils.isEmpty(strInterval)) {
            try {
                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
                locationOption.setInterval(Long.valueOf(strInterval));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

//        String strTimeout = etHttpTimeout.getText().toString();
        String strTimeout = "30000";
        if (!TextUtils.isEmpty(strTimeout)) {
            try {
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String result = LoctionUtil.getLocationStr(loc);
                province = loc.getProvince();
                city = loc.getCity();
                district = loc.getDistrict();
                Log.i("test", "result+" + result);
//                mTvLocationArea.setText(loc.getAddress());
//                mTv_Show.setText(loc.getAddress());
            } else {
//                mTvLocationArea.setText("定位失败，loc is null");
                Util.toast(getContext(), "定位失败");
            }
        }
    };


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAppUpdate();
    }

    private void checkAppUpdate() {
        OkHttpUtils.post(HttpConstant.APP_VERSION_UPDATE + "").tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);

            }
        });
    }

    private void requestNetAddAddress(String getGoodsName, String getGoodsPhone, final String detailAdress, String cickChooseText) {
        showLoading();
        OkHttpUtils.post(HttpConstant.ADD_USER_ADDRESS + "userId=" + mUserId + "&name=" + getGoodsName + "&phone=" + getGoodsPhone + "&region=" + cickChooseText + "&address=" + detailAdress).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    TipUtil.operationSuccessTip(getActivity(), success);
                    EventBus.getDefault().post(new AddAdressSuccessEvent(success));
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getActivity());
        stopLocation();
        destroyLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_choose:
                showChooseAreaDialog();
                break;
            default:
                break;
        }
    }

    private void showChooseAreaDialog() {
        AddressPickTask task = new AddressPickTask(getActivity());
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
//                showToast("数据初始化失败");
                Util.toast(getContext(), "数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    mTvGetgoodsArea.setText(province.getAreaName() + city.getAreaName());
                } else {
                    mTvGetgoodsArea.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
                mTvGetgoodsArea.setTextColor(Color.BLACK);
            }
        });
        task.execute(province, city, district);
    }

    private void initTopbar() {
        mTopBarView = getActivity().findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(getContext(), mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void rightClick() {

                String getGoodsName = mEtGetgoodsName.getText().toString().trim();
                String getGoodsPhone = mEtGetgoodsPhone.getText().toString().trim();
                String detailAdress = mEtDetailAdress.getText().toString().trim();
                String cickChooseText = (String) mTvGetgoodsArea.getText();

                if (!Util.checkPhone(getGoodsPhone)) {
                    Util.toast(getContext(), "手机格式输入有误");
                    return;
                }
                boolean goodsName = TextUtils.isEmpty(getGoodsName);
                boolean goodsPhone = TextUtils.isEmpty(getGoodsPhone);
                boolean goodsDetail = TextUtils.isEmpty(detailAdress);
                boolean b = cickChooseText.equals("点击选择");
                if (goodsName || goodsPhone || goodsDetail || b) {
                    Util.toast(getContext(), "您填写的资料不完整");
                    return;
                }
                if (mString.equals("add")) {
                    requestNetAddAddress(getGoodsName, getGoodsPhone, detailAdress, cickChooseText);
                } else if (mString.equals("edit")) {
                    requestNetUpdateAddress(getGoodsName, getGoodsPhone, detailAdress, cickChooseText);
                }
            }


        });
    }

    private void requestNetUpdateAddress(final String getGoodsName, String getGoodsPhone, final String detailAdress, String cickChooseText) {
        showLoading();
        OkHttpUtils.post(HttpConstant.UPDATE_ADDRESS + "id=" + mAddressid + "&name=" + getGoodsName + "&phone=" + getGoodsPhone + "&region=" + cickChooseText + "&address=" + detailAdress).tag(getActivity()).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                dismissLoading();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    TipUtil.operationSuccessTip(getContext(), success);
                    EventBus.getDefault().post(new AddAdressSuccessEvent(success));
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(getContext(), failMsgBean.getMsg());
                }

            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                dismissLoading();

            }
        });
    }

}
