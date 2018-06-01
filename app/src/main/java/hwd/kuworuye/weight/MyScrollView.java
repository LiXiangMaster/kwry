package hwd.kuworuye.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
    private OnScrollToBottom callBack;

    public void setCallBack(OnScrollToBottom callBack){
        this.callBack = callBack;
    }

    public MyScrollView(Context context)
    {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        View view = (View)getChildAt(getChildCount()-1);
        int d = view.getBottom();

        int x = getHeight();
        int y = getScrollY();

        d = d-x-y;

        if(d<10){
            if (callBack!=null){
                callBack.OnScroll();
            }
        }

            super.onScrollChanged(l,t,oldl,oldt);
    }

    public interface OnScrollToBottom{
        void OnScroll();
    }
}