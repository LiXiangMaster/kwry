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
import hwd.kuworuye.bean.OrderDetailBean;

/**
 * Created by Administrator on 2017/5/19.
 */
public class GiftAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderDetailBean.GiftlistBean> giftList;

    public GiftAdapter(Context context, List<OrderDetailBean.GiftlistBean> giftList) {
        this.mContext = context;
        this.giftList = giftList;
    }

    @Override
    public int getCount() {
        return giftList == null ? 0 : giftList.size();
    }

    @Override
    public Object getItem(int i) {
        return giftList == null ? null : giftList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_gift, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int quantity = giftList.get(i).getQuantity();
        viewHolder.mTvNum.setText("x"+quantity);
        viewHolder.mTvSmell.setText(giftList.get(i).getKwProductAttribute().getTasteStr());
        viewHolder.mTvBrand.setText(giftList.get(i).getKwProductAttribute().getProduct().getProductName());
        viewHolder.mTvType.setText(giftList.get(i).getKwProductAttribute().getSpecificationsStr());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_smell)
        TextView mTvSmell;
        @BindView(R.id.tv_brand)
        TextView mTvBrand;
        @BindView(R.id.tv_num)
        TextView mTvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
