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
import hwd.kuworuye.bean.QueryAllOrderBean;
import hwd.kuworuye.utils.Util;


/**
 * Created by Administrator on 2017/3/4.
 */
public class OrderSortAdapter extends BaseAdapter {

    private List<QueryAllOrderBean.OrderListBean.ListBean> list;
    private Context context;

    public OrderSortAdapter(Context context, List<QueryAllOrderBean.OrderListBean.ListBean> list) {
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
    public View getView(int i, View conview, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (conview == null) {
            conview = View.inflate(context, R.layout.item_oder_sort, null);
            viewHolder = new ViewHolder(conview);
            conview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) conview.getTag();
        }
        viewHolder.mTvProductState.setText(Util.getOrderState(list.get(i).getOrderStatus(),list.get(i).getApprovalType()));
        viewHolder.mTvProductAddress.setText(list.get(i).getUserAddress());
        viewHolder.mTvItemBox.setText("共" + list.get(i).getTotalBox() + "件商品");
        viewHolder.mTvTotalPrice.setText("¥"+list.get(i).getTotalPrice() + "元");
        viewHolder.mTvOrderTimeX.setText(list.get(i).getCreateDate());
        return conview;
    }


    class ViewHolder {
        @BindView(R.id.tv_order_time)
        TextView mTvOrderTime;
        @BindView(R.id.tv_order_time_x)
        TextView mTvOrderTimeX;
        @BindView(R.id.tv_product_state)
        TextView mTvProductState;
        @BindView(R.id.tv_product_address)
        TextView mTvProductAddress;
        @BindView(R.id.tv_item_box)
        TextView mTvItemBox;
        @BindView(R.id.tv_total_price)
        TextView mTvTotalPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
