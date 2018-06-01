package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hwd.kuworuye.R;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.fragment.AreaManagerFragment;
import hwd.kuworuye.fragment.AreaAddorUpdateFragment;
import hwd.kuworuye.fragment.AskGoodsPlanDetailFragment;
import hwd.kuworuye.fragment.AskGoodsPlanFragment;
import hwd.kuworuye.fragment.AskGoodsPlanSubmitFragment;
import hwd.kuworuye.fragment.BackHandledFragment;
import hwd.kuworuye.fragment.CommNoNetFragment;
import hwd.kuworuye.fragment.CommPicShowrFragment;
import hwd.kuworuye.fragment.ConfirmTakeGoodsOKFragment;
import hwd.kuworuye.fragment.DisPlayApplyDetailFragment;
import hwd.kuworuye.fragment.DisPlayApplySubmitFragment;
import hwd.kuworuye.fragment.DisPlayHeXiaoDetailFragment;
import hwd.kuworuye.fragment.DisplayHeXiaoUpdateFragment;
import hwd.kuworuye.fragment.InitRepertoryFragment;
import hwd.kuworuye.fragment.JXCManagerDetailFragment;
import hwd.kuworuye.fragment.JXCManagerListFragment;
import hwd.kuworuye.fragment.JoinSiteCostApplyDetailFragment;
import hwd.kuworuye.fragment.JoinSiteCostApplySubmitFragment;
import hwd.kuworuye.fragment.JoinSiteCostApplyUpdateFragment;
import hwd.kuworuye.fragment.JoinSiteCostHeXiaoDetailFragment;
import hwd.kuworuye.fragment.JoinSiteHeXiaoUpdateFragment;
import hwd.kuworuye.fragment.KwRyCommFragment;
import hwd.kuworuye.fragment.MySignFragment;
import hwd.kuworuye.fragment.MyTeamFragment;
import hwd.kuworuye.fragment.OrderDetailFragment;
import hwd.kuworuye.fragment.OrderSubmitFragment;
import hwd.kuworuye.fragment.OrderUpdateFragment;
import hwd.kuworuye.fragment.OtherCostApplyDetailFragment;
import hwd.kuworuye.fragment.OtherCostApplySubmitFragment;
import hwd.kuworuye.fragment.OtherCostHeXiaoDetailFragment;
import hwd.kuworuye.fragment.OtherCostHeXiaoUpdateFragment;
import hwd.kuworuye.fragment.PwdUpdateFragment;
import hwd.kuworuye.fragment.SaleAcAyDetailFragment;
import hwd.kuworuye.fragment.SaleAcAySubmitFragment;
import hwd.kuworuye.fragment.SaleAcAyUpdateFragment;
import hwd.kuworuye.fragment.SaleAcHeXiaoDetailFragment;
import hwd.kuworuye.fragment.SaleAcHeXiaoUpdateFragment;
import hwd.kuworuye.fragment.SubmitReperToryFragment;
import hwd.kuworuye.fragment.TargetDoneDatailFragment;
import hwd.kuworuye.fragment.TargetDoneFragment;
import hwd.kuworuye.interf.TopBarClickListener;
import hwd.kuworuye.utils.StatusBarUtil;
import hwd.kuworuye.weight.Topbar;

import static hwd.kuworuye.constants.Constant.APPLY_ACTIVITY_EDIT_JOIN_WAY;

/**
 * Created by Administrator on 2017/3/4.
 */

public class ContentActivity extends FragmentActivity {
    private float mDensity = 0L; // 屏幕密度
    private FragmentManager mFragmentManager;
    private Map<String, String> fragmentNameAndTitleMap;
    private Map<String, Boolean> fragmentNameAndIsShowRightBtnMap;
    private Topbar mTopbar;
    private View mTopBarView;
    private BackHandledFragment mBackHandledFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.comm_title_bar));
        }
        initDisplayMetrics();
        init();
        openFragment(getContentType());
    }


    /**
     * 功能：初始化屏幕显示参数（APP字体不随系统设置大小字体而变化）
     */
    private void initDisplayMetrics() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (mDensity == 0L) {
            mDensity = displayMetrics.density;
            displayMetrics.scaledDensity = displayMetrics.density;
        } else {
            displayMetrics.scaledDensity = mDensity;
        }
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        fragmentNameAndTitleMap = new HashMap<>();
        fragmentNameAndIsShowRightBtnMap = new HashMap<>();
        initTopbar();
    }

    private void initTopbar() {
        mTopBarView = findViewById(R.id.topbar_layout);
        mTopbar = new Topbar(ContentActivity.this, mTopBarView);
        mTopbar.setTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                onBackPressed();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    private int getContentType() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getIntExtra(Constant.CONTENT_TYPE, 999);
        } else {
            return 999;
        }
    }


    private void openFragment(int type) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        switch (type) {
            case Constant.AREA_MANAGER:
                if (intent != null) {
                    int areaJoinWay = intent.getIntExtra(Constant.AREA_MANAGER_JOIN_WAY, 0);
                    bundle.putInt(Constant.JOIN_KW_COM_TYPE, areaJoinWay);
                    commitFragment(getString(R.string.area_manager), false, R.id.content_frame, AreaManagerFragment.newInstance(bundle));
                }
                break;
            case Constant.PWD_MODIFIER:
                commitFragment(getString(R.string.pwd_modifier), false, R.id.content_frame, PwdUpdateFragment.newInstance(null));
                break;
            case Constant.JXC_MANAGER:
                if (intent != null) {
                    String userId = intent.getStringExtra(Constant.USERID);
                    String isShowBtStr = intent.getStringExtra(Constant.ISSHOW_UPDATE_BT);
                    bundle.putString(Constant.ISSHOW_UPDATE_BT,isShowBtStr);
                    bundle.putString(Constant.USERID, userId);
                }
                commitFragment(getString(R.string.jxc_manager), false, R.id.content_frame, JXCManagerListFragment.newInstance(bundle));
                break;
            case Constant.ASK_GOODS_PLAN:
                commitFragment(getString(R.string.ask_goods_plan), true, R.id.content_frame, AskGoodsPlanFragment.newInstance(null));
                ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.add);
                break;
            case Constant.TARGET_DONE:
                if (intent != null) {
                    String targetUserId = intent.getStringExtra(Constant.USERID);
                    bundle.putString(Constant.USERID, targetUserId);
                }
                commitFragment(getString(R.string.target_done), true, R.id.content_frame, TargetDoneFragment.newInstance(bundle));
                break;
            case Constant.MY_TEAM:
                commitFragment(getString(R.string.my_team), false, R.id.content_frame, MyTeamFragment.newInstance(null));
                break;
            case Constant.MY_SIGN:
                if (intent!=null){
                    boolean booleanExtra = intent.getBooleanExtra(Constant.FIRSTCERTIFICATION, false);
                    bundle.putBoolean(Constant.FIRSTCERTIFICATION,booleanExtra);
                }
                commitFragment(getString(R.string.my_sign), true, R.id.content_frame, MySignFragment.newInstance(bundle));
                ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.sign_modify);
                break;
            case Constant.OTHER_COST_SUBMIT:
                if (intent != null) {
                    String title = "其他费用申请";
                    String joinWayOther = intent.getStringExtra(APPLY_ACTIVITY_EDIT_JOIN_WAY);
                    String orDerId = (String) intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    if ("其他费用申请".equals(joinWayOther)) {
                        title = "其他费用申请提交";
                    } else if ((Constant.MODIFIER).equals(joinWayOther)) {
                        title = "其他费用申请修改";
                    }
                    bundle.putString(APPLY_ACTIVITY_EDIT_JOIN_WAY, joinWayOther);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orDerId);
                    commitFragment(title, false, R.id.content_frame, OtherCostApplySubmitFragment.newInstance(bundle));
                }
                break;
            case Constant.KWRY_COMM:
                if (intent != null) {
                    int intExtra = intent.getIntExtra(Constant.JOIN_KW_COM_TYPE, 0);
                    String kwUerId = intent.getStringExtra(Constant.USERID);
                    ArrayList<Parcelable> passProductList = intent.getParcelableArrayListExtra(Constant.PASS_PRODUCT);
                    bundle.putInt(Constant.JOIN_KW_COM_TYPE, intExtra);
                    bundle.putString(Constant.USERID, kwUerId);
                    bundle.putParcelableArrayList(Constant.PASS_PRODUCT, passProductList);
                }
                commitFragment(getString(R.string.kuwo_ruye), false, R.id.content_frame, KwRyCommFragment.newInstance(bundle));
                break;
            case Constant.SUBMIT_ORDER:
                commitFragment(getString(R.string.submit_order), false, R.id.content_frame, OrderSubmitFragment.newInstance(null));
                break;
            case Constant.ORDER_DETAILS_NEXT:
                if (intent != null) {
                    String orderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean oderDetailIsShowBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, oderDetailIsShowBt);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orderId);
                    commitFragment(getString(R.string.order_detail), false, R.id.content_frame, OrderDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.CONFIRM_TAKE_GOODS_OK:
                if (intent != null) {
                    String orderNum = intent.getStringExtra(Constant.ORDER_NUM);
                    bundle.putString(Constant.ORDER_NUM, orderNum);
                    commitFragment(getString(R.string.confirm_up_take_goods), false, R.id.content_frame, ConfirmTakeGoodsOKFragment.newInstance(bundle));
                }
                break;
            case Constant.APPLY_FOR_ACTIVITY_DETAIL:
                if (intent != null) {
                    String orderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isShowApplyBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orderId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowApplyBt);
                    commitFragment(getString(R.string.sale_activity_apply_detail), false, R.id.content_frame, SaleAcAyDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.APPLY_FOR_ACTIVITY_SUBMIT:
                commitFragment(getString(R.string.sale_activity_submit), false, R.id.content_frame, SaleAcAySubmitFragment.newInstance(bundle));
                break;
            case Constant.ACTIVITY_HEXIAO_EDIT:
                if (intent != null) {
                    String xiaoHeEditOrderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, xiaoHeEditOrderId);
                    commitFragment(getString(R.string.sale_activity_hexiao_modifier), false, R.id.content_frame, SaleAcHeXiaoUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.ACTIVITY_HEXIAO_DETAIL:
                if (intent != null) {
                    String orderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isShowActivityXiaoHeBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orderId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowActivityXiaoHeBt);
                    commitFragment(getString(R.string.sale_activity_xiaohe_detail), false, R.id.content_frame, SaleAcHeXiaoDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.OTHER_COST_SUBMIT_DETAIL:
                if (intent != null) {
                    boolean isShowOtherBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    String orDerId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orDerId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowOtherBt);
                    commitFragment(getString(R.string.other_money_detail), false, R.id.content_frame, OtherCostApplyDetailFragment.newInstance(bundle));
                }
                break;
            //地址编辑
            case Constant.GET_GOODS_AREA_UPDATE:
                String goodsName = intent.getStringExtra(Constant.GET_GOODS_NAME);
                String address = intent.getStringExtra(Constant.GET_ADDRESS);
                String phone = intent.getStringExtra(Constant.GET_GOODS_PHONE);
                String region = intent.getStringExtra(Constant.GET_GOODS_REGION);
                String addressId = intent.getStringExtra(Constant.GET_GOODS_ADDRESS_ID);

                bundle.putString(Constant.QUFEN_EDIT_ADD, "edit");
                bundle.putString("goodsname", goodsName);
                bundle.putString("phone", phone);
                bundle.putString("address", address);
                bundle.putString("region", region);
                bundle.putString("addressid", addressId);
                commitFragment(getString(R.string.area_manager), true, R.id.content_frame, AreaAddorUpdateFragment.newInstance(bundle));
                ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.save_temp);
                break;
            //添加地址
            case Constant.GET_GOODS_AREA_ADD:
                bundle.putString(Constant.QUFEN_EDIT_ADD, "add");
                commitFragment(getString(R.string.area_manager), true, R.id.content_frame, AreaAddorUpdateFragment.newInstance(bundle));
                ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.save_temp);
                break;
            case Constant.JXC_MANAGER_LIST_SHOW:
                if (intent != null) {
                    String jxId = intent.getStringExtra(Constant.JXID);
                    bundle.putString(Constant.JXID, jxId);
                    commitFragment(getString(R.string.jxc_manager), false, R.id.content_frame, JXCManagerDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.SUBMIT_REPERTORY:
                if (intent != null) {
                    String submitReUserId = intent.getStringExtra(Constant.USERID);
                    bundle.putString(Constant.USERID, submitReUserId);
                    commitFragment(getString(R.string.submit_repertory), false, R.id.content_frame, SubmitReperToryFragment.newInstance(bundle));
                }
                break;
            case Constant.ASK_GOODS_PLAN_DETAIL:
                if (intent != null) {
                    String cargoPlanId = intent.getStringExtra(Constant.PASS_ASK_PLAN_DETAIL);
                    bundle.putString(Constant.PASS_ASK_PLAN_DETAIL, cargoPlanId);
                    commitFragment(getString(R.string.ask_goods_plan_detail), false, R.id.content_frame, AskGoodsPlanDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.COMM_NO_DATA:
                commitFragment("", false, R.id.content_frame, CommNoNetFragment.newInstance(null));
                break;
            case Constant.COMM_PIC_SHOW_SITE:
                boolean isShowMangerBt = false;
                String titleStr = "展示图片界面";
                if (intent != null) {
                    int joinCommPicWay = intent.getIntExtra(Constant.JOIN_COMM_PIC_SHOW_WAY, 0);
                    String processId = intent.getStringExtra(Constant.PROCESSID);
                    if (processId != null) {
                        bundle.putString(Constant.PROCESSID, processId);
                    }
                    bundle.putInt(Constant.JOIN_COMM_PIC_SHOW_WAY, joinCommPicWay);
                    switch (joinCommPicWay) {
                        case Constant.JOINSITE_APPLY_DETAIL:
                            isShowMangerBt = false;
                            titleStr = "进场明细照片查看";
                            break;
                        case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                            isShowMangerBt = true;
                            titleStr = "进场明细照片提交";
                            break;
                        case Constant.DISPLAY_APPLY_DETAIL_MARK:
                            isShowMangerBt = false;
                            titleStr = "超市明细照片查看";
                            break;
                        case Constant.DISPLAY_APPLY_MARK_SUBMIT:
                            isShowMangerBt = true;
                            titleStr = "超市明细照片提交";
                            break;
                        case Constant.DISPLAY_APPLY_MARK_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "产品成列申请超市明细照片修改";
                            break;
                        case Constant.DISPLAY_DETAIL_HEXIAO_MARK:
                            titleStr = "超市明细图片查看";
                            break;
                        case Constant.DISPLAY_DETAIL_HEXIAO_DISPLAY:
                            titleStr = "产品陈列照片查看";
                            break;
                        case Constant.DISPLAY_DETAIL_HEXIAO_TICKET:
                            titleStr = "产品陈列票据照片查看";
                            break;
                        case Constant.DISPLAY_HEXIAO_UPDATE_MARK:
                            isShowMangerBt = true;
                            titleStr = "产品陈列超市明细照片修改";
                            break;
                        case Constant.DISPLAY_HEXIAO_UPDATE_DISPLAY:
                            isShowMangerBt = true;
                            titleStr = "产品陈列照片修改";
                            break;
                        case Constant.DISPLAY_HEXIAO_UPDATE_TICKET:
                            isShowMangerBt = true;
                            titleStr = "产品陈列票据照片修改";
                            break;
                        case Constant.OTHER_COST_HEXIAO_DETAIL:
                            titleStr = "其他费用核销票据查看";
                            break;
                        case Constant.OTHER_COST_HEXIAO_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "其他费用核销票据修改";
                            break;
                        case Constant.JOIN_SITE_HEXIAO_SITE_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "进场费用核销现场照片修改";
                            break;
                        case Constant.JOIN_SITE_HEXIAO_TICKET_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "进场费用核销票据照片修改";
                            break;
                        case Constant.SALE_ACTIVITY_SITE_HEXIAO_PIC:
                            titleStr = "促销活动核销现场照片查看";
                            break;
                        case Constant.SALE_ACTIVITY_SITE_TICKET_PIC:
                            titleStr = "促销活动核销票据照片查看";
                            break;
                        case Constant.JOIN_SITE_HEXIAO_DETAIL_SITE:
                            titleStr = "进场现场照片查看";
                            break;
                        case Constant.JOIN_SITE_HEXIAO_DETAIL_TICKET:
                            titleStr = "进场票据照片查看";
                            break;
                        case Constant.JOIN_SITE_COST_APPLY_UPDATE:
                            titleStr = "进场费用申请明细修改";
                            isShowMangerBt = true;
                            break;
                        case Constant.SALE_HEXIAO_SITE_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "促销活动核销现场照片修改";
                            break;
                        case Constant.SALE_HEXIAO_TICKET_UPDATE:
                            isShowMangerBt = true;
                            titleStr = "促销活动核销票据照片修改";
                            break;

                    }
                    commitFragment(titleStr, isShowMangerBt, R.id.content_frame, CommPicShowrFragment.newInstance(bundle));
                    ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.manager);
                }

                break;
            case Constant.TICKET_PIC_MANAGER:
                commitFragment(getString(R.string.ticket_pic_manager), true, R.id.content_frame, CommPicShowrFragment.newInstance(null));
                ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(R.drawable.manager);
                break;
            case Constant.INIT_REPERTORY:
                if (intent != null) {
                    String initReUserId = intent.getStringExtra(Constant.USERID);
                    bundle.putString(Constant.USERID, initReUserId);
                }
                commitFragment(getString(R.string.init_repertory), false, R.id.content_frame, InitRepertoryFragment.newInstance(bundle));
                break;
            case Constant.ADD_ASKGOODS_PLAN:
                commitFragment(getString(R.string.submit_askplan), false, R.id.content_frame, AskGoodsPlanSubmitFragment.newInstance(null));
                break;
            case Constant.TARGET_DONE_DETAIL:
                if (intent != null) {
                    String targetDetailUserId = intent.getStringExtra(Constant.USERID);
                    bundle.putString(Constant.USERID, targetDetailUserId);
                    commitFragment(getString(R.string.detail), false, R.id.content_frame, TargetDoneDatailFragment.newInstance(bundle));
                }
                break;
            case Constant.UPDATE_ORDER:
                if (intent != null) {
                    String orderId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orderId);
                    commitFragment(getString(R.string.update_order), false, R.id.content_frame, OrderUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.JOINSITE_APPLY_DETAIL:
                if (intent != null) {
                    String orDerId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isShowMDbt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orDerId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowMDbt);
                    commitFragment(getString(R.string.join_site_cost_apply_detail), false, R.id.content_frame, JoinSiteCostApplyDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.SUBMIT_JOIN_SITE_COST_APPLY:
                commitFragment(getString(R.string.join_site_cost_apply_submit), false, R.id.content_frame, JoinSiteCostApplySubmitFragment.newInstance(null));
                break;
            case Constant.JOIN_SITE_HEXIAO_DETAIL:
                if (intent != null) {
                    String hxJoinOrderId = (String) intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isShowJoinHxBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, hxJoinOrderId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowJoinHxBt);
                    commitFragment(getString(R.string.join_site_cost_hexiao_detail), false, R.id.content_frame, JoinSiteCostHeXiaoDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.DISPLAY_APPLY_DETAIL_MARK:
                if (intent != null) {
                    String orDerIdDispaly = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isShowMDbt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, orDerIdDispaly);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isShowMDbt);
                    commitFragment(getString(R.string.display_cost_apply_detail), false, R.id.content_frame, DisPlayApplyDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.DISPLAY_APPLY_EDIT:
                if (intent != null) {
                    String joinSubmitWay = intent.getStringExtra(APPLY_ACTIVITY_EDIT_JOIN_WAY);
                    if ("产品陈列费用申请".equals(joinSubmitWay)) {
                        bundle.putString(APPLY_ACTIVITY_EDIT_JOIN_WAY, joinSubmitWay);
                        commitFragment(getString(R.string.display_cost_apply_submit), false, R.id.content_frame, DisPlayApplySubmitFragment.newInstance(bundle));
                    } else if (joinSubmitWay.equals(Constant.DISPLAY_APPLY_UPDATE)) {
                        String OrderIdDisplay = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                        bundle.putString(APPLY_ACTIVITY_EDIT_JOIN_WAY, joinSubmitWay);
                        bundle.putString(Constant.ORDER_DATAIL_ID, OrderIdDisplay);
                        commitFragment(getString(R.string.display_cost_apply_update), false, R.id.content_frame, DisPlayApplySubmitFragment.newInstance(bundle));
                    }
                }
                break;
            case Constant.DISPLAY_HEXIAO_DETAIL:
                if (intent != null) {
                    boolean isDisplayHexiaoBt = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    String disPlayOrDerId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, disPlayOrDerId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isDisplayHexiaoBt);
                    commitFragment(getString(R.string.display_cost_hexiao_detail), false, R.id.content_frame, DisPlayHeXiaoDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.DISPLAY_HEXIAO_UPDATE:
                if (intent != null) {
                    String dispalyUpdateId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, dispalyUpdateId);
                    commitFragment(getString(R.string.display_hexiao_update), false, R.id.content_frame, DisplayHeXiaoUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.OTHER_COST_HEXIAO_DETAIL:
                if (intent != null) {
                    String otherHeXiaoId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    boolean isOtherCostShow = intent.getBooleanExtra(Constant.IS_HAVE_MODIFIER, false);
                    bundle.putString(Constant.ORDER_DATAIL_ID, otherHeXiaoId);
                    bundle.putBoolean(Constant.IS_HAVE_MODIFIER, isOtherCostShow);
                    commitFragment(getString(R.string.other_cost_hexiao_detail), false, R.id.content_frame, OtherCostHeXiaoDetailFragment.newInstance(bundle));
                }
                break;
            case Constant.OTHER_COST_HEXIAO_UPDATE:
                if (intent != null) {
                    String otherUpdateId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, otherUpdateId);
                    commitFragment(getString(R.string.other_cost_hexiao_update), false, R.id.content_frame, OtherCostHeXiaoUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.JOIN_SITE_HEXIAO_UPDATE:
                if (intent != null) {
                    String joinSiteUpdateId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, joinSiteUpdateId);
                    commitFragment(getString(R.string.join_site_hexiao_update), false, R.id.content_frame, JoinSiteHeXiaoUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.SALE_ACTIVITY_APPLY_UPDATE:
                if (intent != null) {
                    String saleUpdateId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, saleUpdateId);
                    commitFragment(getString(R.string.sale_activity_update), false, R.id.content_frame, SaleAcAyUpdateFragment.newInstance(bundle));
                }
                break;
            case Constant.JOIN_SITE_COST_APPLY_UPDATE:
                if (intent != null) {
                    String joinSiteUpdateId = intent.getStringExtra(Constant.ORDER_DATAIL_ID);
                    bundle.putString(Constant.ORDER_DATAIL_ID, joinSiteUpdateId);
                    commitFragment(getString(R.string.join_site_cost_update), false, R.id.content_frame, JoinSiteCostApplyUpdateFragment.newInstance(bundle));
                }
                break;
            default:
                break;
        }
    }

    private void commitFragment(String titleStr, boolean isShowTopBarRightBtn, int containerViewId, Fragment toFragment) {
        // 是否显示Topbar
        changeTopbarTitleStr(titleStr);
        // 是否显示Topbar右侧按钮
        changeTopbarRightBtnVisiable(isShowTopBarRightBtn);

        String fragmentName = toFragment.getClass().getSimpleName();
        if (!"".equals(fragmentNameAndTitleMap.get(fragmentName))) { // 当前Map中没有存储此值
            fragmentNameAndTitleMap.put(fragmentName, titleStr);
        }
        fragmentNameAndIsShowRightBtnMap.put(fragmentName, isShowTopBarRightBtn);

        // 切换Fragment
        mFragmentManager.beginTransaction().replace(containerViewId, toFragment, toFragment.getClass().getSimpleName()).addToBackStack(toFragment.getTag()).commit();
    }

    /**
     * 功能：设置Topbar的显示/隐藏，与TopBar上标题文字设置
     *
     * @param titleStr
     */
    private void changeTopbarTitleStr(String titleStr) {
        if ("".equals(titleStr)) {
            mTopBarView.setVisibility(View.GONE);
        } else {
            mTopbar.setTopbarTitleStr(titleStr);
            mTopBarView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 功能：是否显示Topbar右侧按钮
     *
     * @param isShow
     */
    public void changeTopbarRightBtnVisiable(boolean isShow) {
        if (isShow) {
            mTopbar.setRightBtnVisiable(true);
        } else {
            mTopbar.setRightBtnVisiable(false);
        }
    }

    /**
     * 改变标题右边的图片；
     *
     * @param whichPicDrawable
     */
    public void changeRight2WhichPic(int whichPicDrawable) {
        ((ImageView) findViewById(R.id.comm_right_three)).setImageResource(whichPicDrawable);
    }

    @Override
    public void onBackPressed() {
        // 首先判断Activity内Fragment是否消费了后退事件，然后再消费Activity自身的后退事件
        if (mBackHandledFragment == null || mBackHandledFragment.onBackPressed()) {
            int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
            if (backStackEntryCount == 0) {
                close();
            } else {
                if (backStackEntryCount > 1) {
                    mFragmentManager.popBackStack();
                    Fragment currentFragment = mFragmentManager.getFragments().get(backStackEntryCount - 2); // 获取弹出栈顶Fragment后的当前Fragment
                    String fragmentName = currentFragment.getClass().getSimpleName();
                    changeTopbarTitleStr(fragmentNameAndTitleMap.get(fragmentName));
                    changeTopbarRightBtnVisiable(fragmentNameAndIsShowRightBtnMap.get(fragmentName));
                } else {
                    close();
                }
            }
        }
    }


    private void close() {
        clearMap();
        finish();
    }

    private void clearMap() {
        if (fragmentNameAndTitleMap != null && fragmentNameAndTitleMap.size() > 0) {
            fragmentNameAndTitleMap.clear();
        }
        if (fragmentNameAndIsShowRightBtnMap != null && fragmentNameAndIsShowRightBtnMap.size() > 0) {
            fragmentNameAndIsShowRightBtnMap.clear();
        }
    }

}
