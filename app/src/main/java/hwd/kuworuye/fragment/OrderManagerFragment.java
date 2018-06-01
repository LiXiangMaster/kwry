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
import android.widget.ImageView;
import android.widget.TextView;

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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.adapter.OrderManagerAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;

/**
 * Created by Administrator on 2017/3/4.
 */
public class OrderManagerFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.start_down_bill)
    Button mStartDownBill;
    @BindView(R.id.mci_order_manager)
    MagicIndicator mMciOrderManager;
    private ArrayList<String> tableData = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @BindView(R.id.comm_back)
    ImageView mCommBack;
    @BindView(R.id.tv_comm_title_bar_two)
    TextView mTvCommTitleBarTwo;
    private boolean firstShowAll;
    @BindView(R.id.vp_order_manager)
    ViewPager mVpOrderManager;
    Unbinder unbinder;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_order_manager;
    }

    public static Fragment newInstance(Bundle bundle) {
        OrderManagerFragment fragment = new OrderManagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private void inintData() {
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
            //只有经销商才有下单按钮；
            mStartDownBill.setVisibility(View.GONE);
        }

        mTvCommTitleBarTwo.setText(getResources().getText(R.string.oder_manager_title));
        mCommBack.setOnClickListener(this);
        mStartDownBill.setOnClickListener(this);
        initTitle();
    }


    private void initTitle() {
        for (int i = 0; i < tableData.size(); i++) {
            fragmentList.add(OrderSortFragment.newInstance(null));
        }
        OrderManagerAdapter mViewPagerAdapter = new OrderManagerAdapter(getContext(), getFragmentManager(), fragmentList, tableData);
        mVpOrderManager.setAdapter(mViewPagerAdapter);
        //这里浪费了好多时间，擦擦。
        mVpOrderManager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset == 0.0 && !firstShowAll) {
                    EventBus.getDefault().post(tableData.get(0));
                }
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
        mMciOrderManager.setBackgroundColor(Color.parseColor("#ffffff"));
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
                        mVpOrderManager.setCurrentItem(index);
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
        mMciOrderManager.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMciOrderManager, mVpOrderManager);
    }


    /**
     * 回到全部列表；
     *
     * @param event
     */
    @Subscribe
    public void refreshEvent(CommRefreshListEvent event) {
        boolean refresh = event.isRefresh();
        if (refresh) {
            mVpOrderManager.setCurrentItem(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comm_back:
                getActivity().finish();
                break;
            case R.id.start_down_bill:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.SUBMIT_ORDER);
//                Util.startActivity(getContext(), DealActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        inintData();
        return rootView;
    }
}
