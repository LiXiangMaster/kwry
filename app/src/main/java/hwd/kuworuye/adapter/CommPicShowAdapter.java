package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.BackPicBean;

/**
 * Created by Administrator on 2017/3/30.
 */
public class CommPicShowAdapter extends BaseAdapter {

    private List<BackPicBean.PageListBean.ListBean> list;
    private Context context;

    public CommPicShowAdapter(Context context, List<BackPicBean.PageListBean.ListBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_comm_pic_show, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getImgs()).into(viewHolder.mIvActivitySiteManage);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_activity_site_manage)
        ImageView mIvActivitySiteManage;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
