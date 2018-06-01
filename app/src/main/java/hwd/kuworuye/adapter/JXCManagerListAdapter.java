package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.JxDetailBean;

/**
 * Created by Administrator on 2017/3/9.
 */

public class JXCManagerListAdapter extends BaseAdapter {
    private List<JxDetailBean.DataBean> dataBean;
    private Context context;

    public JXCManagerListAdapter(Context context, List<JxDetailBean.DataBean> dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    @Override
    public int getCount() {
        return dataBean == null ? 0 : dataBean.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBean == null ? null : dataBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_jxc_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        JxDetailBean.DataBean.ProductAttributeBean productAttribute = dataBean.get(i).getProductAttribute();
        JxDetailBean.DataBean.ProductBean product = dataBean.get(i).getProduct();

        Glide.with(context).load(productAttribute.getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvJxdetailPic);
        viewHolder.mTvDrinkType.setText(product.getProductName());
        viewHolder.mTvDrinkSmell.setText(productAttribute.getTasteStr());
        viewHolder.mTvDrinkNorms.setText(productAttribute.getSpecificationsStr());
        viewHolder.mTvDrinkBoxNum.setText(dataBean.get(i).getQuantity() + "箱");
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.iv_jxdetail_pic)
        ImageView mIvJxdetailPic;
        @BindView(R.id.tv_drink_type)
        TextView mTvDrinkType;
        @BindView(R.id.tv_drink_smell)
        TextView mTvDrinkSmell;
        @BindView(R.id.tv_drink_norms)
        TextView mTvDrinkNorms;
        @BindView(R.id.tv_drink_box_num)
        TextView mTvDrinkBoxNum;
        @BindView(R.id.ll_jxc_list)
        LinearLayout mLlJxcList;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
