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
import hwd.kuworuye.bean.ImageDataUrlBean;

/**
 * Created by Administrator on 2017/3/31.
 */
public class JoinSiteAyPicMangeAdapter extends BaseAdapter {
    private List<ImageDataUrlBean> photoList;
    private Context context;

    public JoinSiteAyPicMangeAdapter(Context context, List<ImageDataUrlBean> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return photoList == null ? 0 : photoList.size();
    }

    @Override
    public Object getItem(int i) {
        return photoList == null ? null : photoList.get(i);
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
        Glide.with(context).load(photoList.get(i).getImgs()).placeholder(R.drawable.place).into(viewHolder.mIvActivitySiteManage);
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
