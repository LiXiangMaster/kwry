package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.bean.NewsListBean;
import hwd.kuworuye.utils.Util;

/**
 * Created by Administrator on 2017/3/3.
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsListBean.DataBean> beanList;

    public NewsListAdapter(Context context, List<NewsListBean.DataBean> beanList) {
        this.beanList = beanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beanList == null ? 0 : beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList == null ? null : beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View conview, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (conview == null) {
            viewHolder = new ViewHolder();
            conview = View.inflate(context, R.layout.item_news_list, null);
            viewHolder.mImageView = ((ImageView) conview.findViewById(R.id.iv_news_pic));
            viewHolder.mContent = ((TextView) conview.findViewById(R.id.tv_news_content));
            viewHolder.mDate = ((TextView) conview.findViewById(R.id.tv_news_time));
            conview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) conview.getTag();
        }
        viewHolder.mContent.setText(beanList.get(i).getTitle());
        String createDate = beanList.get(i).getCreateDate();
        String mdhmByYMDHMS = Util.getMDHMByYMDHMS(createDate);
        viewHolder.mDate.setText(mdhmByYMDHMS);

        Glide.with(context).load(beanList.get(i).getImgUrl()).placeholder(R.drawable.place).into(viewHolder.mImageView);

        return conview;
    }

    static class ViewHolder {
        ImageView mImageView;
        TextView mContent;
        TextView mDate;
    }
}
