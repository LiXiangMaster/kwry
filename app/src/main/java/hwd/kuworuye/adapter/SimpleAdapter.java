package hwd.kuworuye.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.bean.JxsNameBean;

/**
 * Created by Administrator on 2017/3/21.
 */

public class SimpleAdapter extends BaseAdapter {
    private Context context;
    private List<JxsNameBean.DataBean> data;

    public SimpleAdapter(Context context, List<JxsNameBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_simple, null);
            viewHolder.mTextView = ((TextView) view.findViewById(R.id.tv_jxs_name));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(data.get(i).getUserName());
        return view;
    }

    static class ViewHolder {
        TextView mTextView;
    }

}
