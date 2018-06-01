package hwd.kuworuye.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.activity.LoginActivity;
import hwd.kuworuye.activity.MyDatasActivity;
import hwd.kuworuye.base.FBaseFragment;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.utils.SharedPreferencesUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MeFragment extends FBaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_comm_title_bar_one)
    TextView mTvCommTitleBarOne;
    Unbinder unbinder;
    @BindView(R.id.rl_area_manager)
    RelativeLayout mRlAreaManager;
    @BindView(R.id.rl_pwd_modifir)
    RelativeLayout mRlPwdModifir;
    @BindView(R.id.rl_jxc_manager)
    RelativeLayout mRlJxcManager;
    @BindView(R.id.rl_ask_plan)
    RelativeLayout mRlAskPlan;
    @BindView(R.id.rl_me_datas)
    RelativeLayout mRlMeDatas;
    @BindView(R.id.rl_target_complete)
    RelativeLayout mRlTargetComplete;
    @BindView(R.id.rl_me_team)
    RelativeLayout mRlMeTeam;
    @BindView(R.id.rl_me_sign)
    RelativeLayout mRlMeSign;
    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;
    @BindView(R.id.tv_post_name)
    TextView mTvPostName;
    @BindView(R.id.tv_sale_area)
    TextView mTvSaleArea;


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_me;
    }

    public static Fragment newInstance(Bundle bundle) {
        MeFragment fragment = new MeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String usrType = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERTYPE);
        String userName = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.USERNAME);
        switch (usrType) {
            case "1":
                mRlAreaManager.setVisibility(View.VISIBLE);
                mRlAskPlan.setVisibility(View.GONE);
                mRlMeTeam.setVisibility(View.GONE);
                mTvPostName.setText("经销商" + "  " + userName);
                break;
            case "2":
                mRlTargetComplete.setVisibility(View.GONE);
                mRlJxcManager.setVisibility(View.GONE);
                mTvPostName.setText("区域主管" + "  " + userName);
                break;
            case "3":
                mRlTargetComplete.setVisibility(View.GONE);
                mTvPostName.setText("区域经理" + "   " + userName);
                mRlJxcManager.setVisibility(View.GONE);
                break;
            case "4":
                mRlTargetComplete.setVisibility(View.GONE);
                mTvPostName.setText("省区经理" + "  " + userName);
                mRlJxcManager.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        mTvSaleArea.setText(((String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.SALE_AREA)));
        mTvCommTitleBarOne.setText(R.string.me_center_title);
        mRlAreaManager.setOnClickListener(this);
        mRlPwdModifir.setOnClickListener(this);
        mRlJxcManager.setOnClickListener(this);
        mRlAskPlan.setOnClickListener(this);
        mRlMeDatas.setOnClickListener(this);
        mRlTargetComplete.setOnClickListener(this);
        mRlMeTeam.setOnClickListener(this);
        mRlMeSign.setOnClickListener(this);
        mTvLoginOut.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_area_manager:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.AREA_MANAGER);
                break;
            case R.id.rl_pwd_modifir:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.PWD_MODIFIER);
                break;
            case R.id.rl_jxc_manager:
                String isInit = (String) SharedPreferencesUtil.getInstance(getContext()).read(Constant.ISINIT);
                if ("0".equals(isInit)) {
                    Util.openContentActivity(getContext(), ContentActivity.class, Constant.INIT_REPERTORY);
                } else if ("1".equals(isInit)) {
                    Util.openContentActivity(getContext(), ContentActivity.class, Constant.JXC_MANAGER);
                }
                break;
            case R.id.rl_ask_plan:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.ASK_GOODS_PLAN);
                break;
            case R.id.rl_me_datas:
//                Util.openContentActivity(getContext(), ContentActivity.class, Constant.MY_DATAS);
                Util.startActivity(getContext(), MyDatasActivity.class);
                break;
            case R.id.rl_target_complete:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.TARGET_DONE);
                break;
            case R.id.rl_me_team:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.MY_TEAM);
                break;
            case R.id.rl_me_sign:
                Util.openContentActivity(getContext(), ContentActivity.class, Constant.MY_SIGN);
                break;
            case R.id.tv_login_out:
                popupLoginOutTip();
                break;
            default:
                break;
        }
    }


    private void popupLoginOutTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_tip, null);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText("确定退出登录？");
        final MyDialog dialog = new MyDialog(getContext(), view, R.style.dialog);
        dialog.show();
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_delete_cancel);
        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_delete_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.startActivity(getContext(), LoginActivity.class);
                SharedPreferencesUtil.getInstance(getContext()).save("loginsuccessed", "loginout");
                //清楚地址缓存：
                Util.removeObj(getContext(), Constant.FIRST_CHOOSE);
                getActivity().finish();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(getActivity());
    }
}
