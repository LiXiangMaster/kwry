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
import hwd.kuworuye.bean.SaleActivityXiaoHeDetailBean;

/**
 * Created by Administrator on 2017/3/29.
 */
public class ApplyProductListHeXiaoAdapter extends BaseAdapter {
    private List<SaleActivityXiaoHeDetailBean.ItemListBean> itemList;
    private Context context;

    public ApplyProductListHeXiaoAdapter(Context context, List<SaleActivityXiaoHeDetailBean.ItemListBean> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList == null ? null : itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_apply_product_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(itemList.get(i).getKwProductAttribute().getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvComPic);
        SaleActivityXiaoHeDetailBean.ItemListBean.KwProductAttributeBean.ProductBean product = itemList.get(i).getKwProductAttribute().getProduct();
        if (product == null) {
            viewHolder.mTvCommDrinkType.setText("哇哈哈~");
        } else {
            viewHolder.mTvCommDrinkType.setText(itemList.get(i).getKwProductAttribute().getProduct().getProductName());
        }
        viewHolder.mTvCommDrinkSmell.setText(itemList.get(i).getKwProductAttribute().getTasteStr());
        viewHolder.mTvCommDrinkNorms.setText(itemList.get(i).getKwProductAttribute().getSpecificationsStr());
        viewHolder.mTvCommDrinkPrice.setText(itemList.get(i).getKwProductAttribute().getPrice());
        viewHolder.mTvProductNum.setText("X" + itemList.get(i).getQuantity());

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
