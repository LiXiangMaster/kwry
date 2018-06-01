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
import hwd.kuworuye.bean.OrderDetailBean;

/**
 * Created by Administrator on 2017/3/24.
 */

public class OrderInfoAdapter extends BaseAdapter {
    private List<OrderDetailBean.RecordListBean> recordList;
    private Context context;

    public OrderInfoAdapter(Context context, List<OrderDetailBean.RecordListBean> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList == null ? 0 : recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList == null ? null : recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_order_info, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvUserName.setText(recordList.get(i).getUser().getName() + ",");
        viewHolder.mTvOrderTime.setText(recordList.get(i).getApproverTime() + ",");
        viewHolder.mTvOrderState.setText(recordList.get(i).getApproverMemo());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_order_time)
        TextView mTvOrderTime;
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
