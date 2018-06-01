package hwd.kuworuye.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ShowSiteListBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.AddAdressSuccessEvent;
import hwd.kuworuye.utils.TipUtil;
import hwd.kuworuye.utils.Util;
import hwd.kuworuye.weight.MyDialog;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/3/4.
 */
public class GetGoodsAreaAdapter extends BaseAdapter {


    private List<ShowSiteListBean.DataBean> data;
    private Context context;

    public GetGoodsAreaAdapter(Context context, List<ShowSiteListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_history_area, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String isDefault = data.get(i).getIsDefault();
        if (isDefault.equals("0")) {
            //不是默认地址；
            viewHolder.mCbAreaManager.setChecked(false);
        } else if (isDefault.equals("1")) {
            viewHolder.mCbAreaManager.setChecked(true);
        }
        viewHolder.mCbAreaManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestNet2SetDefault(data.get(i).getId(), data.get(i).getUserId());
            }
        });
        viewHolder.mTvGetgoodsName.setText(data.get(i).getName());
        viewHolder.mTvGetgoodsPhone.setText(data.get(i).getPhone());
        viewHolder.mTvLocationArea.setText(data.get(i).getRegion() + data.get(i).getAddress());
        viewHolder.mTvDeleteAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDeleteTip(data.get(i).getId());
            }
        });
        viewHolder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowSiteListBean.DataBean dataBean = data.get(i);
                String name = dataBean.getName();
                String phone = dataBean.getPhone();
                String region = dataBean.getRegion();
                String address = dataBean.getAddress();
                String addressId = dataBean.getId();
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(Constant.GET_GOODS_NAME, name);
                intent.putExtra(Constant.GET_GOODS_PHONE, phone);
                intent.putExtra(Constant.GET_GOODS_REGION, region);
                intent.putExtra(Constant.GET_GOODS_ADDRESS_ID, addressId);
                intent.putExtra(Constant.GET_ADDRESS, address);

                intent.putExtra(Constant.CONTENT_TYPE, Constant.GET_GOODS_AREA_UPDATE);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private void requestNet2SetDefault(String addressId, String userId) {
        Util.showProgressDialog(context, "请稍后....");
        OkHttpUtils.post(HttpConstant.SET_DEFAULT_ADDRESS + "id=" + addressId + "&userId=" + userId).tag(context).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                Util.dismissProgressDialog();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    TipUtil.operationSuccessTip(context, !success);
                    EventBus.getDefault().post(new AddAdressSuccessEvent(success));
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(context, failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.dismissProgressDialog();

            }
        });
    }


    private void popupDeleteTip(final String addressId) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_tip, null);
        final MyDialog dialog = new MyDialog(context, view, R.style.dialog);
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
                requestNet2DeleteAddress(addressId);
                dialog.dismiss();
            }
        });
    }

    private void requestNet2DeleteAddress(String addressId) {
        Util.showProgressDialog(context, "正在删除...");
        OkHttpUtils.post(HttpConstant.DELETE_ADDRESS + "id=" + addressId).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                Util.dismissProgressDialog();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                boolean success = successBean.isSuccess();
                if (success) {
                    TipUtil.operationSuccessTip(context, !success);
                    EventBus.getDefault().post(new AddAdressSuccessEvent(success));
                } else {
                    Util.toast(context, "删除失败");
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.dismissProgressDialog();

            }
        });
    }

    static class ViewHolder {
        @BindView(R.id.cb_area_manager)
        CheckBox mCbAreaManager;
        @BindView(R.id.tv_getgoods_name)
        TextView mTvGetgoodsName;
        @BindView(R.id.tv_getgoods_phone)
        TextView mTvGetgoodsPhone;
        @BindView(R.id.tv_location_area)
        TextView mTvLocationArea;
        @BindView(R.id.iv_edit)
        ImageView mIvEdit;
        @BindView(R.id.tv_delete_address)
        TextView mTvDeleteAdress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
