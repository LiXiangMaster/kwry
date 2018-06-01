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
import hwd.kuworuye.bean.CommProductAllNatureBean;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AskGoodsPlanShowAdapter extends BaseAdapter {
    private List<CommProductAllNatureBean> list;
    private Context context;

    public AskGoodsPlanShowAdapter(Context context, List<CommProductAllNatureBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_ask_goods_plan_show, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getPicUrl()).placeholder(R.drawable.place).into(viewHolder.mIvComPic);
        viewHolder.mTvCommDrinkSmell.setText(list.get(i).getSmellStr());
        viewHolder.mTvCommDrinkPrice.setText(list.get(i).getPrice());
        viewHolder.mTvProductNum.setText("X"+list.get(i).getNumber());
        viewHolder.mTvCommDrinkType.setText(list.get(i).getProductName());
        viewHolder.mTvCommDrinkNorms.setText(list.get(i).getNormals());
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
