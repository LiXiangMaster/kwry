package hwd.kuworuye.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.SaleListShowAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ActivityHeXiaoFragment extends FBaseFragment {

    @BindView(R.id.vp_activity_xiaohe)
    ViewPager mVpSaleStateManagerActivity;
    @BindView(R.id.bt_apply_for_xiaohe)
    Button mBtApplyForXiaohe;
    Unbinder unbinder;
    @BindView(R.id.mci_activity_xiaohe)
    MagicIndicator mMciActivityXiaohe;
    private ArrayList<String> tableData = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private String mHomefragment;
    private boolean firstShowAll;

    @Override
    protected int inflateContentView() {

        return R.layout.fragment_activity_xiaohe;
    }

    public static Fragment newInstance(Bundle bundle) {
        ActivityHeXiaoFragment fragment = new ActivityHeXiaoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private void initData() {
        //消核没有提交按钮；
        mBtApplyForXiaohe.setVisibility(View.GONE);
        String userType = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERTYPE);
        //1代表经销商；
        if ("1".equals(userType)) {
            tableData.add("全部");
            tableData.add("审批中");
            tableData.add("交易完成");
            tableData.add("已拒绝");
        } else {
            tableData.add("全部");
            tableData.add("审批中");
            tableData.add("待我审批");
            tableData.add("交易完成");
            tableData.add("已拒绝");

        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            mHomefragment = arguments.getString("homefragment");
        }

        initTitle();
    }


    private void initTitle() {
        for (int i = 0; i < tableData.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.DISTINCTION_WHICH_ACTIVITIY, mHomefragment);
            fragmentList.add(HeXiaoFragment.newInstance(bundle));
        }


        mVpSaleStateManagerActivity.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //核销和申请  不一样这个位置 核销是不要的；
               /* if (position == 0 && positionOffset == 0.0 && !firstShowAll) {
                    EventBus.getDefault().post(tableData.get(0));
                }*/
            }

            @Override
            public void onPageSelected(int position) {
                firstShowAll = true;
                EventBus.getDefault().post(tableData.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SaleListShowAdapter adapter = new SaleListShowAdapter(getFragmentManager(), fragmentList, tableData);
        mVpSaleStateManagerActivity.setAdapter(adapter);
        mMciActivityXiaohe.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tableData == null ? 0 : tableData.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(tableData.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#55aee9"));
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpSaleStateManagerActivity.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#55aee9"));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return super.getTitleWeight(context, index);

            }
        });
        mMciActivityXiaohe.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMciActivityXiaohe, mVpSaleStateManagerActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
