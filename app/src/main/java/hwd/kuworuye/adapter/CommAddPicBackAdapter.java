package hwd.kuworuye.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.activity.PhotoViewPagerActivity;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommPicShowEvent;
import hwd.kuworuye.event.HideDeleteIvEvent;
import hwd.kuworuye.event.ShowDeleteIvEvent;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CommAddPicBackAdapter extends BaseAdapter {
    private List<ImageDataUrlBean> list;
    private Context context;
    private boolean mIsShowDelete;
    private boolean mIsHideDelete = true;

    public CommAddPicBackAdapter(Context context, List<ImageDataUrlBean> photoListExtra) {
        EventBus.getDefault().register(this);
        this.context = context;
        this.list = photoListExtra;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_sitpic_manager, null);
            viewHolder.ivPhoto = ((ImageView) view.findViewById(R.id.comm_accessory_item_photo));
            viewHolder.ivDelete = ((ImageView) view.findViewById(R.id.comm_accessory_item_delete));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getImgs()).placeholder(R.drawable.place).into(viewHolder.ivPhoto);
        if (mIsShowDelete) {
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
            viewHolder.ivPhoto.setFocusable(false);
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        deletePicByUrl(list.get(i).getImgs());
                    }
            });
        } else if (!mIsHideDelete) {
            viewHolder.ivDelete.setVisibility(View.INVISIBLE);
        }
        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoViewPagerActivity.class);
                intent.putParcelableArrayListExtra(Constant.COMM_PIC_URL_TITLE, (ArrayList<? extends Parcelable>) list);
                intent.putExtra(Constant.PIC_INDEX,i);
                context.startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 删除图片根据图片url
     * @param imgs
     */
    private void deletePicByUrl(String imgs) {
        Util.showProgressDialog(context,"正在删除...");
        OkHttpUtils.get(HttpConstant.DELETE_PIC_BY_URL+"imgs="+imgs).tag(context).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                Util.dismissProgressDialog();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    EventBus.getDefault().post(new CommPicShowEvent(successBean.isSuccess()));
                    Util.toast(context,"删除成功");
                }else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(context,failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.dismissProgressDialog();
            }
        });
    }


    @Subscribe
    public void onShowDelete(ShowDeleteIvEvent event) {
        if (event != null && (event.getIsDelete() == true)) {
            mIsShowDelete = event.getIsDelete();
            notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onHideDelete(HideDeleteIvEvent event) {
        if (event != null && (event.getIsHideDeleIv() == false)) {
            mIsHideDelete = event.getIsHideDeleIv();
            mIsShowDelete = false;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivDelete;

    }
}
