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
import hwd.kuworuye.bean.OrderDetailBean;

/**
 * Created by Administrator on 2017/3/7.
 */
public class OrderDetailAdapter extends BaseAdapter {
    private List<OrderDetailBean.ItemListBean> listBeanList;
    private Context context;

    public OrderDetailAdapter(Context context, List<OrderDetailBean.ItemListBean> listBeanList) {
        this.context = context;
        this.listBeanList = listBeanList;
    }

    @Override
    public int getCount() {
        return listBeanList == null ? 0 : listBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeanList == null ? null : listBeanList.get(i).getKwProductAttribute();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_order_detail_next, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //TODO 后台说 产品属性 是一次换回给我的，待解决，现在这事返回一个al 应该是自己截取；
        viewHolder.mTvCommDrinkPrice.setText("" + listBeanList.get(i).getPrice());
        if (listBeanList.get(i).getKwProductAttribute() != null) {
            String thumbnail = listBeanList.get(i).getKwProductAttribute().getThumbnail();
            Glide.with(context).load(thumbnail).placeholder(R.drawable.place).into(viewHolder.mIvComPic);
        }
        viewHolder.mTvProductNum.setText("x" + listBeanList.get(i).getQuantity());
        String productName = listBeanList.get(i).getProductName();
        if (productName != null) {
            String[] split = productName.split(",");
            if (split.length == 3) {
                viewHolder.mTvCommDrinkSmell.setText(split[1]);
                viewHolder.mTvCommDrinkType.setText(split[0]);
                viewHolder.mTvCommDrinkNorms.setText(split[2]);
            }
        }

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
