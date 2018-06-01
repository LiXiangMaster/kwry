package hwd.kuworuye.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.JxcManagerBean;

/**
 * Created by Administrator on 2017/3/4.
 */
public class JXCAdapter extends BaseAdapter {
    private List<JxcManagerBean.DataBean> dataBeanList;
    private Context context;

    public JXCAdapter(Context context, List<JxcManagerBean.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @Override
    public int getCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataBeanList == null ? null : dataBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHoder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_jxc_manager, null);
            viewHoder = new ViewHolder(view);
            view.setTag(viewHoder);
        } else {
            viewHoder = (ViewHolder) view.getTag();
        }
        String invoicingType = dataBeanList.get(i).getInvoicingType();
        if (invoicingType.equals("1")) {
            viewHoder.mTvInvoicingType.setText("进");
            viewHoder.mTvTotalBox.setText("+" + dataBeanList.get(i).getTotalBox());

        } else if (invoicingType.equals("2")) {
            viewHoder.mTvTotalBox.setText("-" + dataBeanList.get(i).getTotalBox());
            viewHoder.mTvInvoicingType.setText("售");
            viewHoder.mTvTotalBox.setTextColor(Color.parseColor("#fe8b8b"));
            viewHoder.mTvInvoicingType.setBackgroundResource(R.drawable.jxc_state_sale_shape);
        }
        viewHoder.mTvSurplusBox.setText(dataBeanList.get(i).getSurplusBox());
        viewHoder.mTvInvoicingDate.setText(dataBeanList.get(i).getCreateDate());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_invoicingType)
        TextView mTvInvoicingType;
        @BindView(R.id.tv_total_box)
        TextView mTvTotalBox;
        @BindView(R.id.tv_invoicingDate)
        TextView mTvInvoicingDate;
        @BindView(R.id.tv_surplusBox)
        TextView mTvSurplusBox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
