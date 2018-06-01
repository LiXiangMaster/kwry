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
import hwd.kuworuye.bean.JoinSiteCostApplyDetailBean;


/**
 * Created by Administrator on 2017/3/31.
 */

public class JoinSiteAyDetailStateAdapter extends BaseAdapter {

    private Context context;
    private List<JoinSiteCostApplyDetailBean.DataBean.ApprovalListBean> approvalList;

    public JoinSiteAyDetailStateAdapter(Context context, List<JoinSiteCostApplyDetailBean.DataBean.ApprovalListBean> approvalList) {
        this.context = context;
        this.approvalList = approvalList;
    }

    @Override
    public int getCount() {
        return approvalList == null ? 0 : approvalList.size();
    }

    @Override
    public Object getItem(int i) {
        return approvalList == null ? null : approvalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_joinsite_detail_state, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvUserName.setText(approvalList.get(i).getUserName() + ",");
        viewHolder.mTvOrderState.setText(approvalList.get(i).getApprovalMemo());
        viewHolder.mTvOrderTime.setText(approvalList.get(i).getApprovalTime() + ",");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_order_time)
        TextView mTvOrderTime;
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
