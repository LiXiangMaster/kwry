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
import hwd.kuworuye.bean.SaleApplyDetailBean;

/**
 * Created by Administrator on 2017/3/29.
 */
public class CommApproveInfoAdapter extends BaseAdapter {
    private List<SaleApplyDetailBean.RecordListBean> list;
    private Context context;

    public CommApproveInfoAdapter(Context context, List<SaleApplyDetailBean.RecordListBean> recordList) {
        this.context = context;
        this.list = recordList;
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
            view = View.inflate(context, R.layout.item_comm_approve_info, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvUserName.setText(list.get(i).getUser().getName()+",");
        viewHolder.mTvOrderState.setText(list.get(i).getApprovalMemo());
        viewHolder.mTvOrderTime.setText(list.get(i).getApprovalTime()+",");
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
