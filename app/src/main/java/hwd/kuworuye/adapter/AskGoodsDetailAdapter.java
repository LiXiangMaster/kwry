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
import hwd.kuworuye.bean.AskGoodsDetailBean;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AskGoodsDetailAdapter extends BaseAdapter {

    private Context context;
    private List<AskGoodsDetailBean.DataBean> dataList;
    public AskGoodsDetailAdapter(Context context, List<AskGoodsDetailBean.DataBean> data) {
        this.dataList = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList == null ? null : dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_ask_goods_details, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(dataList.get(i).getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvAskGoodsDetail);
        viewHolder.mTvCommDrinkNorms.setText(dataList.get(i).getSpecificationsName());
        viewHolder.mTvCommDrinkSmell.setText(dataList.get(i).getTasteName());
        viewHolder.mTvCommDrinkType.setText(dataList.get(i).getProductName());
        viewHolder.mTvItemBox.setText(dataList.get(i).getQuantity()+"箱");
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.iv_ask_goods_detail)
        ImageView mIvAskGoodsDetail;
        @BindView(R.id.tv_comm_drink_type)
        TextView mTvCommDrinkType;
        @BindView(R.id.tv_comm_drink_smell)
        TextView mTvCommDrinkSmell;
        @BindView(R.id.tv_comm_drink_norms)
        TextView mTvCommDrinkNorms;
        @BindView(R.id.tv_item_box)
        TextView mTvItemBox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
