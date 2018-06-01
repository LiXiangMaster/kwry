package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.NowReperToryBean;
import hwd.kuworuye.interf.IChangeCoutCallback;
import hwd.kuworuye.weight.CounterView;

/**
 * Created by Administrator on 2017/3/9.
 */

public class SubmitReperToryAdapter extends BaseAdapter {
    private List<NowReperToryBean.DataBean.InventoryProductListBean> list;
    private Context context;
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    public SubmitReperToryAdapter(Context context, List<NowReperToryBean.DataBean.InventoryProductListBean> inventoryProductList) {
        this.context = context;
        this.list = inventoryProductList;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_submit_repertory, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvComPic);
        viewHolder.mTvCommDrinkType.setText(list.get(i).getProductName());
        viewHolder.mTvCommDrinkSmell.setText(list.get(i).getTasteName());
        viewHolder.mTvCommDrinkNorms.setText(list.get(i).getSpecificationsName());
        viewHolder.mTvCommDrinkPrice.setText(list.get(i).getPrice());
        viewHolder.mCvAddDown.setMaxValue(Integer.parseInt(list.get(i).getTotalBox()));
        viewHolder.mCvAddDown.setDefultValue(list.get(i).getTotalBox());
        map.put(i, Integer.parseInt(list.get(i).getTotalBox()));
        viewHolder.mCvAddDown.setCallback(new IChangeCoutCallback() {
            @Override
            public void change(int count) {
                int totalBox = Integer.parseInt(list.get(i).getTotalBox());
                map.put(i, count);
                if (count >= totalBox) {
//                    Util.toast(context, "最多不能超过" + list.get(i).getTotalBox());
                    return;
                }
            }
        });
        return view;
    }

    public Map<Integer, Integer> getCountMap() {
        return map;
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
        @BindView(R.id.cv_add_down)
        CounterView mCvAddDown;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
