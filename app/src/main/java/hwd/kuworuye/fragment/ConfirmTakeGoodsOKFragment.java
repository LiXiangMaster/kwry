package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.constants.Constant;

/**
 * Created by Administrator on 2017/3/7.
 */
public class ConfirmTakeGoodsOKFragment extends FBaseFragment {
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    Unbinder unbinder;
    private String mOrderNum;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_confirm_take_goods_ok;
    }

    public static Fragment newInstance(Bundle bundle) {
        ConfirmTakeGoodsOKFragment okFragment = new ConfirmTakeGoodsOKFragment();
        okFragment.setArguments(bundle);
        return okFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            mOrderNum = getArguments().getString(Constant.ORDER_NUM);
        }
        initData();
        return rootView;
    }

    private void initData() {
        mTvOrderNumber.setText(mOrderNum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
