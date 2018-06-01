package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.adapter.SplashPagerAdapter;
import hwd.kuworuye.base.BaseActivity;
import rebus.permissionutils.AskagainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class GuideActivity extends BaseActivity {
    @BindView(R.id.vp_main_splash)
    ViewPager mVpMainSplash;
    private List<View> viewList = new ArrayList<>();
    private View view1, view2, view3;
    private ViewPager mViewPager;
    private TextView passMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initListener();
    }

    private void initListener() {
        passMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initAdapter() {
        mVpMainSplash.setAdapter(new SplashPagerAdapter(this, viewList));
    }

    private void initView() {
        //动态权限
        permissionData();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.guide_image_one, null);
        view2 = inflater.inflate(R.layout.guide_image_two, null);
        view3 = inflater.inflate(R.layout.guide_image_three, null);
        passMain = (TextView) view3.findViewById(R.id.tv_guide_pass);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
    }

    //=========================6.0系统权限=========================
    ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    private void permissionData() {
        //权限
        permissionEnumArrayList.add(PermissionEnum.CAMERA);
        permissionEnumArrayList.add(PermissionEnum.GET_ACCOUNTS);
        permissionEnumArrayList.add(PermissionEnum.WRITE_EXTERNAL_STORAGE);
        permissionEnumArrayList.add(PermissionEnum.READ_EXTERNAL_STORAGE);
        permissionEnumArrayList.add(PermissionEnum.READ_CONTACTS);
        permissionEnumArrayList.add(PermissionEnum.ACCESS_COARSE_LOCATION);

    }

    public void permissionSelector() {
        //权限选择
        PermissionManager.with(this)
                .permissions(permissionEnumArrayList)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(2);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                    }
                })
                .ask();
    }

}
