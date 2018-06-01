package hwd.kuworuye.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.bean.AskGoodsPlanListBean;
import hwd.kuworuye.bean.FailMsgBean;
import hwd.kuworuye.bean.SuccessBean;
import hwd.kuworuye.constants.HttpConstant;
import hwd.kuworuye.event.CommRefreshListEvent;
import hwd.kuworuye.utils.Util;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/4.
 */
public class AskGoodsPlanAdapter extends BaseSwipeAdapter {
    private List<AskGoodsPlanListBean.DataBean> list;
    private Context context;

    public AskGoodsPlanAdapter(Context context, List<AskGoodsPlanListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? null : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        // TODO Auto-generated method stub
        final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        TextView mTvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView mTvNumYearMonthDay = (TextView) convertView.findViewById(R.id.tv_num_year_month_day);
        TextView mTvAskGoodsSecWeeks = (TextView) convertView.findViewById(R.id.tv_ask_goods_sec_weeks);
        TextView mTvYmrFm = (TextView) convertView.findViewById(R.id.tv_ymr_fm);
        TextView mTvBoxNum = (TextView) convertView.findViewById(R.id.tv_box_num);
        mTvName.setText(list.get(position).getDistributorName());
        mTvNumYearMonthDay.setText(list.get(position).getCargoDate());
        String currentTimeYYYYMMDD = Util.getCurrentTimeYYYYMMDD();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(currentTimeYYYYMMDD);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
            String secondWeek = "";
            switch (weekOfMonth) {
                case 1:
                    secondWeek = "第一周";
                    break;
                case 2:
                    secondWeek = "第二周";
                    break;
                case 3:
                    secondWeek = "第三周";
                    break;
                case 4:
                    secondWeek = "第四周";
                    break;
                case 5:
                    secondWeek = "第五周";
                    break;
            }
            mTvAskGoodsSecWeeks.setText(secondWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mTvYmrFm.setText(list.get(position).getCreateDate());
        mTvBoxNum.setText(list.get(position).getTotalBox() + "箱");


        final TextView delete = (TextView) convertView.findViewById(R.id.delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int pos = (Integer) delete.getTag();
                Util.toast(context, "点击删除");
                deleteAskPlanList(position);
                notifyDataSetChanged();
                sl.close();
            }
        });

    }

    /**
     * 删除要货计划列表；
     *
     * @param position
     */
    private void deleteAskPlanList(int position) {
        String id = list.get(position).getId();
        Util.showProgressDialog(context, "正在加载...");
        OkHttpUtils.post(HttpConstant.DELETE_ASK_PLAN + "id=" + id).tag(context).execute(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                Util.dismissProgressDialog();
                Gson gson = new Gson();
                SuccessBean successBean = gson.fromJson(s, SuccessBean.class);
                if (successBean.isSuccess()) {
                    Util.toast(context, "删除成功");
                    // 刷新列表；
                    EventBus.getDefault().post(new CommRefreshListEvent(true));
                } else {
                    FailMsgBean failMsgBean = gson.fromJson(s, FailMsgBean.class);
                    Util.toast(context, failMsgBean.getMsg());
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Util.dismissProgressDialog();
            }
        });
    }


    @Override
    public View generateView(int position, ViewGroup arg1) {
        // TODO Auto-generated method stub
        Log.e("generateView", "position = " + position);
        View v = LayoutInflater.from(context).inflate(R.layout.item_ask_goods_plan, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {//当隐藏的删除menu被打开的时候的回调函数
                // TODO Auto-generated method stub
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));

            }
        });

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout,
                                      boolean surface) {
                // 双击条目 监听；
            }
        });

        return v;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        // TODO Auto-generated method stub
        return R.id.swipe;
    }

}
