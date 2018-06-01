package hwd.kuworuye.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.bean.tree.FileBean;
import hwd.kuworuye.bean.tree.Node;
import hwd.kuworuye.bean.tree.TreeListViewAdapter;
import hwd.kuworuye.constants.Constant;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {


    private final List<T> mDatas;
    private Context context;

    public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
                             int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        this.context = context;
        this.mDatas = datas;
    }

    @Override
    public View getConvertView(final Node node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.mLlOnlySaleHave.setTag(position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FileBean fileBean = (FileBean) mDatas.get(position);
        if (position == 0) {
            viewHolder.mIvLeft.setVisibility(View.INVISIBLE);
        }
        if (node.getIcon() == -1) {
            viewHolder.mIvArrowOne.setVisibility(View.INVISIBLE);
            viewHolder.mLlOnlySaleHave.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mLlOnlySaleHave.setVisibility(View.GONE);
            viewHolder.mIvArrowOne.setVisibility(View.VISIBLE);
            viewHolder.mIvArrowOne.setImageResource(node.getIcon());
        }

        if (!node.getItemType().equals("1")) {
            viewHolder.mLlOnlySaleHave.setVisibility(View.GONE);
        }
        if ("2".equals(node.getItemType())) {
            viewHolder.mTvMyteamCity.setTextColor(Color.parseColor("#40AA5F"));
            viewHolder.mTvSaleJob.setTextColor(Color.parseColor("#40AA5F"));
        } else if ("3".equals(node.getItemType())) {
            viewHolder.mTvMyteamCity.setTextColor(Color.parseColor("#F2AB2D"));
            viewHolder.mTvSaleJob.setTextColor(Color.parseColor("#F2AB2D"));
        } else if ("1".equals(node.getItemType())) {
            viewHolder.mTvMyteamCity.setTextColor(Color.parseColor("#55AEE8"));
            viewHolder.mTvSaleJob.setTextColor(Color.parseColor("#55AEE8"));
        } else if ("4".equals(node.getItemType())) {
            viewHolder.mTvMyteamCity.setTextColor(Color.parseColor("#F56912"));
            viewHolder.mTvSaleJob.setTextColor(Color.parseColor("#F56912"));
        }

        viewHolder.mTvMyteamCity.setText(node.getAreaName());
        viewHolder.mTvSaleJob.setText(node.getPeopleName());
        viewHolder.mTvSaleBox.setText(node.getSaleNum() + "箱");
        viewHolder.mTvBackMoney.setText(node.getSaleCost() + "元");

        viewHolder.mBtJxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.JXC_MANAGER);
                intent.putExtra(Constant.USERID, node.getUserId());
                intent.putExtra(Constant.ISSHOW_UPDATE_BT, "noshow");
                context.startActivity(intent);
            }
        });
        viewHolder.mBtTargetDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(Constant.CONTENT_TYPE, Constant.TARGET_DONE);
                intent.putExtra(Constant.USERID, node.getUserId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_left)
        ImageView mIvLeft;
        @BindView(R.id.tv_myteam_city)
        TextView mTvMyteamCity;
        @BindView(R.id.tv_sale_job)
        TextView mTvSaleJob;
        @BindView(R.id.iv_arrow_one)
        ImageView mIvArrowOne;
        @BindView(R.id.tv_sale_box)
        TextView mTvSaleBox;
        @BindView(R.id.tv_back_money)
        TextView mTvBackMoney;
        @BindView(R.id.ll_only_sale_have)
        LinearLayout mLlOnlySaleHave;
        @BindView(R.id.bt_jxc)
        Button mBtJxc;
        @BindView(R.id.bt_target_done)
        Button mBtTargetDone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
