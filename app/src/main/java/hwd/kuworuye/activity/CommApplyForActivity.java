package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.fragment.ActivityHeXiaoFragment;
import hwd.kuworuye.fragment.CommApplyForFragment;

/**
 * Created by Administrator on 2017/3/6.
 */

public class CommApplyForActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rb_left)
    RadioButton mRbLeft;
    @BindView(R.id.rb_right)
    RadioButton mRbRight;
    @BindView(R.id.rg_sale_check)
    RadioGroup mRgSaleCheck;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;

    private int curIndex;
    private RadioButton[] rbArray;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentTransaction transaction;
    private String mHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_promotion);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mHome = intent.getStringExtra("home");
        }
        mIvBack.setOnClickListener(this);
        mFragmentList = getmFragmentList(mHome);
        rbArray = new RadioButton[mRgSaleCheck.getChildCount()];

        for (int i = 0; i < rbArray.length; i++) {
            rbArray[i] = (RadioButton) mRgSaleCheck.getChildAt(i);
        }
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fl_container, mFragmentList.get(0));
        transaction.commit();
        curIndex = 0;

        mRgSaleCheck.setOnCheckedChangeListener(this);


    }

    public List<Fragment> getmFragmentList(String homeStr) {
        Bundle bundle = new Bundle();
        bundle.putString("homefragment",homeStr);
        List<Fragment> list = new ArrayList<>();
        list.add(CommApplyForFragment.newInstance(bundle));
        list.add(ActivityHeXiaoFragment.newInstance(bundle));
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rbArray.length; i++) {
            if (checkedId == rbArray[i].getId()) {
                // 切换Fragment
                switchFragment(i);
            }
        }
    }

    protected void switchFragment(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = mFragmentList.get(i);
        if (!fragment.isAdded()) {
            transaction.hide(mFragmentList.get(curIndex)).add(R.id.fl_container, fragment);
        } else {
            transaction.hide(mFragmentList.get(curIndex)).show(fragment);
        }
        transaction.commit();
        curIndex = i;
    }

}
