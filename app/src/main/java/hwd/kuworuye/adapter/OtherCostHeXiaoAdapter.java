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
import hwd.kuworuye.bean.OtherCostHexiaoListBean;
import hwd.kuworuye.utils.Util;


/**
 * Created by Administrator on 2017/3/4.
 */
public class OtherCostHeXiaoAdapter extends BaseAdapter {

    private List<OtherCostHexiaoListBean.DataBean> list;
    private Context context;

    public OtherCostHeXiaoAdapter(Context context, List<OtherCostHexiaoListBean.DataBean> list) {
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
        viewHolder.mTvState.setText(Util.getHxApproveState(list.get(i).getExamineStatus(),list.get(i).getApprovalType()));
        viewHolder.mTvActivityStyle.setText("主旨：" + list.get(i).getTitle());
        viewHolder.mTvActivityCost.setText("费用：¥" + list.get(i).getCosts());
        viewHolder.mTvSale.setText("说明：¥" + list.get(i).getExplains());
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
