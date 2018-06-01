package hwd.kuworuye.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.activity.PhotoViewPagerActivity;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.constants.Constant;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CommBackBigAdapter extends BaseAdapter {
    private List<ImageDataUrlBean> list;
    private Context context;
    public CommBackBigAdapter(Context context, List<ImageDataUrlBean> photoListExtra) {
        this.context = context;
        this.list = photoListExtra;
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
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_sitpic_manager, null);
            viewHolder.ivPhoto = ((ImageView) view.findViewById(R.id.comm_accessory_item_photo));
            viewHolder.ivDelete = ((ImageView) view.findViewById(R.id.comm_accessory_item_delete));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(list.get(i).getImgs()).placeholder(R.drawable.place).into(viewHolder.ivPhoto);

        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoViewPagerActivity.class);
                intent.putParcelableArrayListExtra(Constant.COMM_PIC_URL_TITLE, (ArrayList<? extends Parcelable>) list);
                intent.putExtra(Constant.PIC_INDEX,i);
                context.startActivity(intent);
            }
        });

        return view;
    }


    public static class ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivDelete;

    }
}
