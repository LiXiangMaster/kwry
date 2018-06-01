package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lzy.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.ModifierBillAdapter;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.CommProductAllNatureBean;
import hwd.kuworuye.bean.CommProductBean;
import hwd.kuworuye.bean.KwCommProductBean;
import hwd.kuworuye.bean.KwProductNatureBean;
import hwd.kuworuye.bean.ProductListBean;
import hwd.kuworuye.constants.Constant;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ModifierBillFragment extends FBaseFragment {
    @BindView(R.id.lv_modifier)
    ListView mLvModifier;
    Unbinder unbinder;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    private List<ProductListBean.DataBean> mBeanList;
    private int mCategoryId;
    private ArrayList<String> mTitlelist;
    private ModifierBillAdapter mAdapter;
    private List<CommProductBean> submitList = new ArrayList<>();
    private List<KwProductNatureBean> submitKwList = new ArrayList<>();
    //被选中的产品集合；
    private List<CommProductAllNatureBean> submitCommProductList = new ArrayList<>();
    private ArrayList<CommPassBean> mPassProductList = new ArrayList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_modifier_order;
    }

    public static Fragment newInstance(Bundle bundle) {
        ModifierBillFragment fragment = new ModifierBillFragment();
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCategoryId = arguments.getInt("categoryId");
            mTitlelist = arguments.getStringArrayList("titlelist");
            List<ProductListBean.DataBean> bigProduct = (List<ProductListBean.DataBean>) arguments.get("productlist");
            if (arguments.getParcelableArrayList(Constant.PASS_PRODUCT) != null) {
                mPassProductList = arguments.getParcelableArrayList(Constant.PASS_PRODUCT);
            }
            mBeanList = bigProduct;
        }
        mAdapter = new ModifierBillAdapter(getActivity(), mCategoryId, mBeanList, mPassProductList);
        mLvModifier.setEmptyView(mLlEmpty);
        mLvModifier.setAdapter(mAdapter);
    }


    /**
     * 返回 公共产品被选中的的数量；
     *
     * @return
     */
    public List<CommProductAllNatureBean> getProductAllNatureList() {
        if (mAdapter != null) {
            Map<Integer, Integer> countNumMap = mAdapter.getCountNumMap();
            for (int i = 0; i < countNumMap.size(); i++) {
                KwCommProductBean kwList = (KwCommProductBean) mAdapter.getItem(i);
                String tasteName = kwList.getTasteName();
                String productName = kwList.getProductName();
                String price = kwList.getPrice();
                String productAttributeId = kwList.getProductAttributeId();
                String thumbnail = kwList.getThumbnail();
                String specificationsName = kwList.getSpecificationsName();
                if (countNumMap != null) {
                    Integer num = countNumMap.get(i);
                    if (num != null) {
                        if (num >0) {
                            String number = String.valueOf(num);
                            String weight = kwList.getWeight();
                            CommProductAllNatureBean commProductAllNatureBean = new CommProductAllNatureBean(number, productAttributeId, thumbnail, tasteName, productName, specificationsName, price, weight);
                            submitCommProductList.add(commProductAllNatureBean);
                        }
                    }
                }
            }
        }
        return submitCommProductList;
    }

    /**
     * 获取产品的数量和id 集合；
     *
     * @return
     */
    public List<CommProductBean> getSubmitList() {
        if (mAdapter != null) {
            Map<Integer, Integer> countNumMap = mAdapter.getCountNumMap();
            for (int i = 0; i < countNumMap.size(); i++) {
                KwCommProductBean kwBean = (KwCommProductBean) mAdapter.getItem(i);
                String productAttributeId = kwBean.getProductAttributeId();
                if (countNumMap != null) {
                    Integer num = countNumMap.get(i);
                    if (num != null) {
                        if (num > 0) {
                            String number = String.valueOf(num);
                            CommProductBean commProductBean = new CommProductBean(productAttributeId, number);
                            submitList.add(commProductBean);
                        }
                    }
                }
            }
        }
        return submitList;
    }

    /***
     * 获取被选中的数量等属性
     * 用于订单提交的；
     *
     * @return
     */
    public List<KwProductNatureBean> getKwSubmitList() {
        if (mAdapter != null) {
            Map<Integer, Integer> countNumMap = mAdapter.getCountNumMap();
            for (int i = 0; i < countNumMap.size(); i++) {
                KwCommProductBean kwList = (KwCommProductBean) mAdapter.getItem(i);
                String productAttributeId = kwList.getProductAttributeId();
                if (countNumMap != null) {
                    Integer num = countNumMap.get(i);
                    if (num != null) {
                        if (num > 0) {
                            String number = String.valueOf(num);
                            String price = kwList.getPrice();
                            String weight = kwList.getWeight();
                            String tasteName = kwList.getTasteName();
                            String productName = kwList.getProductName();
                            String specificationsName = kwList.getSpecificationsName();
                            String totalProductStr = tasteName + "," + productName + "," + specificationsName;
                            KwProductNatureBean kwProductNatureBean = new KwProductNatureBean(price, productAttributeId, number, totalProductStr, weight);
                            submitKwList.add(kwProductNatureBean);
                        }
                    }
                }
            }
        }
        return submitKwList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }
}
