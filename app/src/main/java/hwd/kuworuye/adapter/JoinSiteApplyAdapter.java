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
import hwd.kuworuye.bean.JoinSiteCostListBean;
import hwd.kuworuye.utils.Util;


/**
 * Created by Administrator on 2017/3/4.
 */
public class JoinSiteApplyAdapter extends BaseAdapter {

    private List<JoinSiteCostListBean.DataBean> list;
    private Context context;

    public JoinSiteApplyAdapter(Context context, List<JoinSiteCostListBean.DataBean> list) {
        this.list = list;
        this.context = context;
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
    public View getView(int i, View conview, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (conview == null) {
            conview = View.inflate(context, R.layout.item_comm_sale_and_xiaohe, null);
            viewHolder = new ViewHolder(conview);
            conview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) conview.getTag();
        }
        viewHolder.mTvActivityTime.setText(list.get(i).getCreateDate());
        viewHolder.mTvState.setText(Util.getApproveState(list.get(i).getApprovalStatus(),list.get(i).getApprovalType()));
        viewHolder.mTvActivityStyle.setText("活动：" + list.get(i).getName());
        viewHolder.mTvActivityCost.setText("进场费用：¥" + list.get(i).getTotalExpenses());
        viewHolder.mTvSale.setText("销售额：¥" + list.get(i).getAnnualSales());
        return conview;
    }

    static class ViewHolder {
        @BindView(R.id.tv_activity_time)
        TextView mTvActivityTime;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_activity_style)
        TextView mTvActivityStyle;
        @BindView(R.id.tv_activity_cost)
        TextView mTvActivityCost;
        @BindView(R.id.tv_sale)
        TextView mTvSale;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
