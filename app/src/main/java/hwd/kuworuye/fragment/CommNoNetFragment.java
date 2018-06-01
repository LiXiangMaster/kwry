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
import hwd.kuworuye.activity.MainActivity;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.utils.Util;

/**
 * Created by Administrator on 2017/3/9.
 */

public class CommNoNetFragment extends FBaseFragment {
    @BindView(R.id.tv_agin_show)
    TextView mTvAginShow;
    Unbinder unbinder;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_comm_nodata;
    }

    public static Fragment newInstance(Bundle bundle) {
        CommNoNetFragment dataFragment = new CommNoNetFragment();
        dataFragment.setArguments(bundle);
        return dataFragment;
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
        mTvAginShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.checkNetWorkStatus(getContext())){
                    Util.startActivity(getContext(), MainActivity.class);
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
