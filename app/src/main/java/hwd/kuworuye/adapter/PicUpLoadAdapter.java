package hwd.kuworuye.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import hwd.kuworuye.R;
import hwd.kuworuye.activity.SitePicUpLoadActivity;
import hwd.kuworuye.bean.ImageData;

/**
 * Created by HP on 2016/9/3.
 */
public class PicUpLoadAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ImageData> beanList;

    public PicUpLoadAdapter(Context context, List<ImageData> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.comm_accessory_item, null);
            viewHolder.ivPhoto = (ImageView) view.findViewById(R.id.comm_accessory_item_photo);
            viewHolder.ivDelete = (ImageView) view.findViewById(R.id.comm_accessory_item_delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(beanList.get(i).getImgs()).placeholder(R.drawable.place).into(viewHolder.ivPhoto);

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beanList.remove(i);
                notifyDataSetChanged();
                if (beanList.size() == 0) {
                    RelativeLayout rl = (RelativeLayout) ((SitePicUpLoadActivity) context).findViewById(R.id.rl_site_pic_update);
                    rl.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    public static class ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivDelete;

    }
}
