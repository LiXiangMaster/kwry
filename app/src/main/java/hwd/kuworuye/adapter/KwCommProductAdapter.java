package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.CommUpdateProductBean;

/**
 * Created by Administrator on 2017/3/7.
 */
public class KwCommProductAdapter extends BaseAdapter {
    private List<CommUpdateProductBean> list;
    private Context context;

    public KwCommProductAdapter(Context context, List<CommUpdateProductBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null :list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_order_detail, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvComPic);
        viewHolder.mTvCommDrinkSmell.setText(list.get(i).getTasteStr());
        viewHolder.mTvCommDrinkPrice.setText(list.get(i).getPrice());
        viewHolder.mTvProductNum.setText("X"+list.get(i).getQuantity());
        viewHolder.mTvCommDrinkType.setText(list.get(i).getProductName());
        viewHolder.mTvCommDrinkNorms.setText(list.get(i).getSpecificationsStr());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_com_pic)
        ImageView mIvComPic;
        @BindView(R.id.tv_comm_drink_type)
        TextView mTvCommDrinkType;
        @BindView(R.id.tv_comm_drink_smell)
        TextView mTvCommDrinkSmell;
        @BindView(R.id.tv_comm_drink_norms)
        TextView mTvCommDrinkNorms;
        @BindView(R.id.tv_comm_drink_price)
        TextView mTvCommDrinkPrice;
        @BindView(R.id.tv_product_num)
        TextView mTvProductNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
