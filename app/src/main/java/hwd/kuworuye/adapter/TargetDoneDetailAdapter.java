package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.TargetDoneDetailBean;

/**
 * Created by Administrator on 2017/3/27.
 */
public class TargetDoneDetailAdapter extends BaseAdapter {
    private List<TargetDoneDetailBean.DataBean> dataBean;
    private Context context;

    public TargetDoneDetailAdapter(Context context, List<TargetDoneDetailBean.DataBean> dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    @Override
    public int getCount() {
        return dataBean == null ? 0 : dataBean.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBean == null ? null : dataBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_target_done_detail, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvTargetDonePrice.setText(dataBean.get(i).getTotalPrice()+"元");
        viewHolder.mTvTargetDoneDate.setText(dataBean.get(i).getCreateDate());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_target_done_price)
        TextView mTvTargetDonePrice;
        @BindView(R.id.tv_target_done_date)
        TextView mTvTargetDoneDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
