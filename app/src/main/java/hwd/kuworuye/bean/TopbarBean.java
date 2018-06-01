package hwd.kuworuye.bean;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import hwd.kuworuye.R;

public class TopbarBean {

    private Context mContext;
    private ImageView mLeftBtn;
    private TextView mTitleTV;
    private ImageView mRightBtn;

    public TopbarBean(Context context, View view) {
        mContext = context;
        mLeftBtn = (ImageView) view.findViewById(R.id.comm_three_back);
        mTitleTV = (TextView) view.findViewById(R.id.tv_comm_title_bar_three);
        mRightBtn = (ImageView) view.findViewById(R.id.comm_right_three);
    }

    public ImageView getLeftBtn() {
        return mLeftBtn;
    }

    public TextView getTitleTV() {
        return mTitleTV;
    }

    public ImageView getRightBtn() {
        return mRightBtn;
    }

}
