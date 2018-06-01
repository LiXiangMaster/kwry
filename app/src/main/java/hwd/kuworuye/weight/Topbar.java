package hwd.kuworuye.weight;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import hwd.kuworuye.bean.TopbarBean;
import hwd.kuworuye.interf.TopBarClickListener;

/**
 * 功能：顶部导航栏
 */
public class Topbar {

    private ImageView mLeftBtn;
    private TextView mTitleTV;
    private ImageView mRightBtn;
    private TopBarClickListener mTopBarClickListener;

    public void setTopBarClickListener(TopBarClickListener topBarClickListener){
        mTopBarClickListener = topBarClickListener;
    }

    public Topbar(Context context, View view){
        init(context,view);
        initClickListener();
    }

    private void init(Context context,View view){
        TopbarBean topbarBean = new TopbarBean(context,view);
        mLeftBtn = topbarBean.getLeftBtn();
        mTitleTV = topbarBean.getTitleTV();
        mRightBtn = topbarBean.getRightBtn();
    }

    private void initClickListener() {
        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopBarClickListener.leftClick();
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopBarClickListener.rightClick();
            }
        });
    }

    public void setTopbarTitleStr(String titleStr){
        mTitleTV.setText(titleStr);
    }

    public void setRightBtnVisiable(boolean isShow){
        if (isShow){
            mRightBtn.setVisibility(View.VISIBLE);
        }else {
            mRightBtn.setVisibility(View.INVISIBLE);
        }
    }

}
