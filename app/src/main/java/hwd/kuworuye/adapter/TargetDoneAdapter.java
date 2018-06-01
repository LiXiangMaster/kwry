package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.bean.TargetDoneInfo;

/**
 * Created by Administrator on 2017/3/4.
 */
public class TargetDoneAdapter extends BaseAdapter {
    private List<TargetDoneInfo> list;
    private Context context;

    public TargetDoneAdapter(Context context, List<TargetDoneInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_target_done, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mTvMonthNum.setText(list.get(i).getMonth());
        viewHolder.mTvTargetBackMoney.setText("目标回款：" + list.get(i).getTargetBackMoney() + "元");
        viewHolder.mTvReallyBackMoney.setText("实际回款：" + list.get(i).getReallyBackMoney() + "元");
        double percent = list.get(i).getPercent();
        DecimalFormat df=new DecimalFormat("#.00");
        String saleVolumeStr = df.format(percent);
        if (percent == 0.0) {
            viewHolder.mTvPrecent.setText(0 + "%");
        } else {
            viewHolder.mTvPrecent.setText(saleVolumeStr + "%");
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_month_num)
        TextView mTvMonthNum;
        @BindView(R.id.tv_target_back_money)
        TextView mTvTargetBackMoney;
        @BindView(R.id.tv_really_back_money)
        TextView mTvReallyBackMoney;
        @BindView(R.id.tv_precent)
        TextView mTvPrecent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
