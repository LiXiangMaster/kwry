package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.CommApplyListBean;
import hwd.kuworuye.constants.Constant;


/**
 * Created by Administrator on 2017/3/4.
 */
public class CommApplyListAdapter extends BaseAdapter {

    private List<CommApplyListBean> list;
    private Context context;

    public CommApplyListAdapter(Context context, List<CommApplyListBean> list) {
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


        int joinType = list.get(i).getJoinType();
        switch (joinType) {
            case Constant.SALE_APPLY:
            case Constant.DISPLAY_APPLY:
                String salesVolume = list.get(i).getSalesVolume();
                double salesVolumeDouble = Double.parseDouble(salesVolume);
                DecimalFormat df = new DecimalFormat("#.00");
                String saleVolumeStr = df.format(salesVolumeDouble);

                String ratio = list.get(i).getRatio();
                double ratioDouble = Double.parseDouble(ratio);
                String ratioStr = df.format(ratioDouble);

                viewHolder.mTvActivityTime.setText(list.get(i).getCreateDate());
                viewHolder.mTvState.setText(list.get(i).getApprovalStatus());
                viewHolder.mTvActivityStyle.setText("活动：" + list.get(i).getName());
                viewHolder.mTvActivityCost.setText("费效比率：" + ratioStr + "%");
                if (salesVolumeDouble == 0) {
                    viewHolder.mTvSale.setText("销售额：¥" + 0);
                } else {
                    viewHolder.mTvSale.setText("销售额：¥" + saleVolumeStr);
                }
                break;
            case Constant.JOINSITE_APPLY:
                String salesVolume1 = list.get(i).getSalesVolume();
                double salesVolumeDouble1 = Double.parseDouble(salesVolume1);
                DecimalFormat df1 = new DecimalFormat("#.00");
                String saleVolumeStr1 = df1.format(salesVolumeDouble1);

                String ratio1 = list.get(i).getRatio();
                double ratio1Double1 = Double.parseDouble(ratio1);
                String ratio1Str = df1.format(ratio1Double1);

                viewHolder.mTvActivityTime.setText(list.get(i).getCreateDate());
                viewHolder.mTvState.setText(list.get(i).getApprovalStatus());
                viewHolder.mTvActivityStyle.setText("活动：" + list.get(i).getName());
                viewHolder.mTvActivityCost.setText("活动费用：¥" + ratio1Str);
                viewHolder.mTvSale.setText("销售额：¥" + saleVolumeStr1);
                break;
            case Constant.OTHER_APPLY:
                String salesVolume2 = list.get(i).getSalesVolume();
                viewHolder.mTvActivityTime.setText(list.get(i).getCreateDate());
                viewHolder.mTvState.setText(list.get(i).getApprovalStatus());
                viewHolder.mTvActivityStyle.setText("主旨：" + list.get(i).getName());
                viewHolder.mTvActivityCost.setText("费用：¥" + list.get(i).getRatio());
                viewHolder.mTvSale.setText("说明：" + salesVolume2);
                break;
        }

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
