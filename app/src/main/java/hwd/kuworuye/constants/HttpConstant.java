package hwd.kuworuye.constants;

/**
 * Created by Administrator on 2017/3/14.
 */

public class HttpConstant {
    /**
     * 正式服务器地址
     */
    public static final String BASE_IP = "http://119.23.79.117:8080";

    /**
     * 250  花生壳测试接口；
     */
//    public static final String BASE_IP = "http://1507k7g973.51mypc.cn";
//    public static final String BASE_IP = "http://lihaohaoli.picp.io";
    /**
     * 内网测试接口
     */
//    public static final String BASE_IP = "http://192.168.0.250:8080";
    /**
     * 公共部分
     */
    public static final String COMM = "/jeesite/interface/";

    /**
     * 产品列表
     **/
    public static final String PRODUCT_LIST = BASE_IP + COMM + "appProduct/productList";
    /**
     * 新闻资讯列表：
     */
    public static final String NEWS_LIST = BASE_IP + COMM + "appNews/list?delFlag=0";
    /**
     * 轮播图的接口
     */
    public static final String BANNER = BASE_IP + COMM + "appNews/list?isHome=1&delFlag=0";
    /**
     * 用户登录接口
     */
    public static final String LOGIN_USER = BASE_IP + COMM + "appLogin/login?";
    /**
     * 修改用户密码接口
     */
    public static final String UPDATE_PWD = BASE_IP + COMM + "appLogin/updatePassword?";

    /**
     * 修改个人资料接口；
     */
    public static final String UPDATE_MY_DATA = BASE_IP + COMM + "appUserinfo/updateKwUserinfo?";
    /**
     * 展示个人资料接口
     */
    public static final String SHOW_PERSION_DATAS = BASE_IP + COMM + "appUserinfo/findKwUserinfo?";
    /**
     * 根据用户信息Id修改用户签名接口
     */
    public static final String SHOW_SIGN_STATE = BASE_IP + COMM + "appUserinfo/updateAutograph?";
    /**
     * 获取用户所有地址接口
     */
    public static final String GET_SITE_LIST = BASE_IP + COMM + "appUserAddress/list?";
    /**
     * 添加用户地址接口
     */
    public static final String ADD_USER_ADDRESS = BASE_IP + COMM + "appUserAddress/add?";
    /**
     * 修改用户地址接口
     */
    public static final String UPDATE_ADDRESS = BASE_IP + COMM + "appUserAddress/edit?";
    /**
     * 删除用户地址接口
     */
    public static final String DELETE_ADDRESS = BASE_IP + COMM + "appUserAddress/delete?";
    /**
     * 设置用户默认地址
     */
    public static final String SET_DEFAULT_ADDRESS = BASE_IP + COMM + "appUserAddress/isDefault?";
    /**
     * 获取版本数据接口
     */
    public static final String APP_VERSION_UPDATE = BASE_IP + COMM + "appVersion/details?";
    /**
     * 根据用户信息Id修改用户签名接口
     */
    public static final String UPDATE_SIGN = BASE_IP + COMM + "appUserinfo/updateAutograph?";
    /**
     * 获取用户进销数据接口
     */
    public static final String GET_USER_JXC = BASE_IP + COMM + "appInvoicing/list?";
    /**
     * 根据进销Id查询进销详情
     */
    public static final String QUERY_JX_DATAIL = BASE_IP + COMM + "appInvoicing/findKwInvoicingByInvoicingId?";
    /**
     * 判断是否可以更新库存接口
     */
    public static final String IS_CAN_UPDAT_REPERTORY = BASE_IP + COMM + "appInvoicing/isSubmit?";
    /**
     * 根据用户Id获取当前库存产品
     */
    public static final String GET_NOW_REPERTORY = BASE_IP + COMM + "appInventory/findCurrentInventoryByUserId?";
    /**
     * 初始化库存接口
     */
    public static final String INIT_REPERTORY = BASE_IP + COMM + "appInventory/initializeInventory?";
    /**
     * 获取用户要货计划列表接口
     */
    public static final String GET_USER_ASKGOODS_PLAN = BASE_IP + COMM + "/appCargoPlan/list?";
    /**
     * 根据用户Id查询下级所有经销商
     */
    public static final String QUERY_ALL_JXS = BASE_IP + COMM + "appCargoPlan/findALLSubordinateDealers?";
    /**
     * 更新库存接口
     */
    public static final String UPDATE_REPERTORY = BASE_IP + COMM + "appInventory/updateTheInventory?";
    /**
     * 根据用户Id获取目标计划
     */
    public static final String TARGET_PLAN = BASE_IP + COMM + "appPlan/planSituation?";
    /**
     * 添加要货计划
     */
    public static final String ADD_SUBMIT_PLAN = BASE_IP + COMM + "appCargoPlan/addCargoPlan?";
    /**
     * 要货计划明细
     */
    public static final String ASK_GOODS_DETAIL = BASE_IP + COMM + "appCargoPlan/cargoPlanDetail?";
    /**
     * 查询所有订单
     */
    public static final String QUERY_ALL_ORDER = BASE_IP + COMM + "appOrder/findOrderList?";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = BASE_IP + COMM + "appOrder/orderForm?";
    /**
     * 根据要货计划Id删除要货计划
     */
    public static final String DELETE_ASK_PLAN = BASE_IP + COMM + "appCargoPlan/deleteKwCargoPlan?";
    /**
     * 根据userId查询用户所有的订单
     */
    public static final String TARGET_DONE_DETAIL = BASE_IP + COMM + "appPlan/findAllByUserId?";
    /**
     * 根据用户Id查询所有下级经销商总销售情况
     */
    public static final String MY_TEAM_ONE = BASE_IP + COMM + "appPlan/findLowerSalesTotalLines?";
    /**
     * 根据用户Id查询所有下级经销商销售情况明细ios
     */
    public static final String MY_TEAM_DETAIL = BASE_IP + COMM + "/appPlan/findLowerSalesLinesList?";
    /**
     * 提交订单
     */
    public static final String SUBMIT_ORDER = BASE_IP + COMM + "appOrder/submitOrder?";
    /**
     * 删除订单
     */
    public static final String DELETE_ORDER = BASE_IP + COMM + "appOrder/deleteOrder?";
    /**
     * 查询所有促销活动申请
     */
    public static final String QUERY_ALL_APPLY_SALE_ACTIVITY = BASE_IP + COMM + "promotionA/findPromotionAList?";
    /**
     * 提交促销活动申请
     */
    public static final String SUBMIT_APPLY_SALE_ACTIVITY = BASE_IP + COMM + "promotionA/submitPromotionA?";
    /**
     * 修改订单
     */
    public static final String UPDATE_ORDER = BASE_IP + COMM + "appOrder/editOrder?";
    /**
     * 促销活动申请详情
     */
    public static final String SALE_APPLY_DETAIL = BASE_IP + COMM + "promotionA/promotionAForm?";
    /**
     * 删除促销活动申请
     */
    public static final String DELETE_ACTIVITY_APPLY = BASE_IP + COMM + "promotionA/deletePromotionA?";
    /**
     * 修改促销活动申请
     */
    public static final String UPDATE_APPLY_ACTIVITY = BASE_IP + COMM + "promotionA/editPromotionA?";
    /**
     * 查询所有促销活动核销
     */
    public static final String QUERY_SALE_XIAOHE_LIST = BASE_IP + COMM + "promotionE/findPromotionEList?";
    /**
     * 促销活动申请审批
     */
    public static final String APPLY_APPROVE = BASE_IP + COMM + "promotionA/passPromotionA?";
    /**
     * 促销活动核销详情
     */
    public static final String SALE_XIAOHE_DETAIL = BASE_IP + COMM + "promotionE/PromotionEForm?";
    /**
     * 查询图片
     */
    public static final String QUERY_ALL_PIC = BASE_IP + COMM + "appPhoto/findOrderList?";
    /**
     * 修改促销活动核销
     */
    public static final String UPDATE_SALE_HEXIAO = BASE_IP + COMM + "promotionE/editPromotionE?";
    /**
     * 查询所有产品陈列申请
     */
    public static final String PRODUCT_DISPLAY_COST_APPLY = BASE_IP + COMM + "displayA/findDisplayAList?";
    /**
     * 查询产品陈列申请详情
     */
    public static final String PRODUCT_DISPLAY_COST_DETAIL = BASE_IP + COMM + "displayA/displayAForm?";
    /**
     * 提交产品陈列申请
     */
    public static final String SUBMIT_PRODUCT_DISPLAY_APPLY = BASE_IP + COMM + "displayA/submitDisplayA?";
    /**
     * 进场费列表接口
     */
    public static final String JOIN_SITE_COST_APPLY = BASE_IP + COMM + "appApproachApplication/approachApplicationList?";
    /**
     * 进场费修改接口
     */
    public static final String JOIN_SITE_COST_UPDATE = BASE_IP + COMM + "appApproachApplication/updateApproachApplication?";
    /**
     * 进场费详情接口
     */
    public static final String JOIN_SITE_COST_APPLY_DETAIL = BASE_IP + COMM + "appApproachApplication/approachApplicationDetails?";
    /**
     * 进场费提交接口
     */
    public static final String JOIN_COST_SUBMIT = BASE_IP + COMM + "appApproachApplication/addApproachApplication?";
    /**
     * 进场费删除接口
     */
    public static final String JOIN_SITE_COST_DELETE = BASE_IP + COMM + "appApproachApplication/deleteApproachApplication?";
    /**
     * 进场费审批接口
     */
    public static final String JOIN_SITE_COST_APPLY_APPROVE = BASE_IP + COMM + "appApproachApplication/passApproachApplication?";

    /**
     * 查询图片
     */
    public static final String QUEERY_PIC_BY_TYPE = BASE_IP + COMM + "appPhoto/findOrderList?";
    /**
     * 进场费用核销列表接口
     */
    public static final String QUERY_JOIN_SITE_HEXIAO_LIST = BASE_IP + COMM + "appApproachExamine/approachExamineList?";
    /**
     * 进场费用核销详情接口
     */
    public static final String QUERY_JOIN_SITE_DETAIL = BASE_IP + COMM + "appApproachExamine/approachExamineDetails?";
    /**
     * 其他费用申请列表接口
     */
    public static final String OTHER_COST_APPLY_LIST = BASE_IP + COMM + "appOtherApplication/otherApplicationList?";
    /**
     * 其他费用申请详情接口
     */
    public static final String OTHER_COST_APPLY_DETAIL = BASE_IP + COMM + "appOtherApplication/otherApplicationDetails?";
    /**
     * 其他费用申请提交接口
     */
    public static final String OTHER_COST_APPLY_SUBMIT = BASE_IP + COMM + "appOtherApplication/addOtherApplication?";
    /**
     * 其他费用申请删除接口
     */
    public static final String OTHER_COST_APPLY_DELETE = BASE_IP + COMM + "appOtherApplication/deleteOtherApplication?";
    /**
     * 其他费用申请修改接口
     */
    public static final String OTHER_COST_APPLY_UPDATE = BASE_IP + COMM + "appOtherApplication/updateOtherApplication?";
    /**
     * 其他费用申请审批接口
     */
    public static final String OTHER_COST_APPLY_APPROVE = BASE_IP + COMM + "appOtherApplication/passOtherApplication?";
    /**
     * 其他费用核销列表接口
     */
    public static final String OTHER_COST_HEXIAO_LIST = BASE_IP + COMM + "appOtherExamine/otherExamineList?";
    /**
     * 提交产品陈列申请
     */
    public static final String SUBMIT_DISPLAY_APPLY = BASE_IP + COMM + "displayA/submitDisplayA?";
    /**
     * 产品陈列申请修改
     */
    public static final String PRODUCT_DISPLAY_UPDATE = BASE_IP + COMM + "displayA/editDisplayA?";
    /**
     * 产品陈列审批
     */
    public static final String PRODUCT_DISPLAY_APPLY_APPROVE = BASE_IP + COMM + "displayA/passDisplayA?";
    /**
     * 产品陈列申请删除
     */
    public static final String PRODUCT_DISPLAY_APPLY_DELETE = BASE_IP + COMM + "displayA/deleteDisplayA?";
    /**
     * 查询所有产品陈列核销
     */
    public static final String QUERY_DISPLAY_HEXIAO_LIST = BASE_IP + COMM + "DisplayE/findDisplayEList?";
    /**
     * 查询产品陈列核销详情
     */
    public static final String DISPLAY_HEXIAO_DETAIL = BASE_IP + COMM + "DisplayE/displayEForm?";
    /**
     * 产品陈列核销审批
     */
    public static final String DISPLAY_HEXIAO_APPROVE = BASE_IP + COMM + "DisplayE/passDisplayE?";
    /***
     * 修改产品陈列核销
     */
    public static final String DISPLAY_HEXIAO_UPDATE = BASE_IP + COMM + "DisplayE/editDisplayE?";
    /**
     * 订单审核提交
     */
    public static final String ORDER_APPROVE_SUBMIT = BASE_IP + COMM + "appOrder/passOrder?";
    /**
     * 其他费用核销详情接口
     */
    public static final String OTHER_COST_HEXIAO_DETAIL = BASE_IP + COMM + "appOtherExamine/otherExamineDetails?";
    /**
     * 其他费用核销修改接口
     */
    public static final String OTHER_COST_HEXIAO_UPDATE = BASE_IP + COMM + "appOtherExamine/updateOtherExamine?";
    /**
     * 其他费用核销审批接口
     */
    public static final String OTHER_COST_HEXIAO_APPROVE = BASE_IP + COMM + "appOtherExamine/passOtherExamine?";
    /**
     * 进场费用核销修改接口
     */
    public static final String JOIN_SITE_COST_HEXIAO_UPDATE = BASE_IP + COMM + "appApproachExamine/updateApproachExamine?";
    /**
     * 修改/添加全部照片
     */
    public static final String UPDATE_AND_ADD_ALL_PIC = BASE_IP + COMM + "appPhoto/saveAll?";
    /**
     * 删除单张照片
     */
    public static final String DELETE_PIC_BY_URL = BASE_IP + COMM + "appPhoto/deleteByimg?";
    /**
     * 单个添加图片
     */
    public static final String SINGLE_ADD_PIC = BASE_IP + COMM + "appPhoto/saveOnly?";
    /**
     * 促销活动核销审批
     */
    public static final String SALE_HEXIAO_APPROVE = BASE_IP + COMM + "promotionE/passPromotionE?";
    /**
     * 进场费用核销审批接口
     */
    public static final String JOIN_SITE_HEXIAO_APPROVE = BASE_IP + COMM + "appApproachExamine/passApproachExamine?";
    /**
     * 安卓获取团队
     */
    public static final String SECOND_GET_TEAM = BASE_IP + COMM + "appPlan/findLowerSalesLinesAllList?";
}
