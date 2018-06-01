package hwd.kuworuye.fragment;

import android.content.Intent;
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
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.constants.Constant;

/**
 * Created by Administrator on 2017/3/18.
 */
public class InitRepertoryFragment extends FBaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_perfect_repertory)
    TextView mTvPerfectRepertory;
    Unbinder unbinder;
    private String mUserId;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_init_repertory;
    }

    public static Fragment newInstance(Bundle bundle) {
        InitRepertoryFragment repertoryFragment = new InitRepertoryFragment();
        repertoryFragment.setArguments(bundle);
        return repertoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserId = arguments.getString(Constant.USERID);
        }
        initData();
        return rootView;
    }

    private void initData() {
        mTvPerfectRepertory.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_perfect_repertory:
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.KWRY_COMM);
                intent.putExtra(Constant.JOIN_KW_COM_TYPE, Constant.INIT_REPERTORY);
                intent.putExtra(Constant.USERID, mUserId);
                startActivity(intent);
                break;
        }
    }
}
