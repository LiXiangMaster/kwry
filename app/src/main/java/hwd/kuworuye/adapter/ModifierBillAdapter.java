package hwd.kuworuye.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hwd.kuworuye.R;
import hwd.kuworuye.activity.ContentActivity;
import hwd.kuworuye.bean.CommPassBean;
import hwd.kuworuye.bean.KwCommProductBean;
import hwd.kuworuye.bean.ProductListBean;
import hwd.kuworuye.event.TotalNumAndPriceIdEvent;
import hwd.kuworuye.utils.Util;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ModifierBillAdapter extends BaseAdapter {

    private ArrayList<CommPassBean> mPassProductList;
    private int categoryId;
    private Map<Integer, Integer> mMap = new HashMap<>();
    private Context context;
    private boolean isShowChange = true;
    private List<KwCommProductBean> kwProductList = new ArrayList<>();

    public ModifierBillAdapter(Context context, int categoryId, List<ProductListBean.DataBean> beanList, ArrayList<CommPassBean> passProductList) {
        this.context = context;
        this.categoryId = categoryId;
        this.mPassProductList = passProductList;
        ProductListBean.DataBean dataBean = beanList.get(categoryId);
        List<ProductListBean.DataBean.ProductDetailListBean> productList = dataBean.getProductList();
        for (int i = 0; i < productList.size(); i++) {
            String productAttributeId = productList.get(i).getProductAttributeId();
            String tasteName = productList.get(i).getTasteName();
            String price = productList.get(i).getPrice();
            String productName = productList.get(i).getProductName();
            String thumbnail = productList.get(i).getThumbnail();
            String specificationsName = productList.get(i).getSpecificationsName();
            String weight = productList.get(i).getWeight();
            kwProductList.add(new KwCommProductBean(productAttributeId, productName, tasteName, specificationsName, price, "0", thumbnail, weight));
        }
    }

    @Override
    public int getCount() {
        return kwProductList == null ? 0 : kwProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return kwProductList == null ? null : kwProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final int[] mMEtItemNumInt = new int[1];
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_modifier_bill, null);
            viewHolder.mIvProduct = ((ImageView) view.findViewById(R.id.iv_product));
            viewHolder.mTvProductPrice = (TextView) view.findViewById(R.id.tv_product_price);
            viewHolder.mDrinkSmell = ((TextView) view.findViewById(R.id.tv_drink_taste));
            viewHolder.mTvProductNorms = (TextView) view.findViewById(R.id.tv_product_norms);
            viewHolder.mTvProductName = ((TextView) view.findViewById(R.id.tv_drink_smell));
            viewHolder.mIvMin = ((ImageView) view.findViewById(R.id.iv_count_minus));
            viewHolder.mIvAdd = ((ImageView) view.findViewById(R.id.iv_count_add));
            viewHolder.mEtChange = ((EditText) view.findViewById(R.id.et_count));
            viewHolder.mEtChange.setTag(position);
            View currentFocus = ((ContentActivity) context).getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int tag = (int) viewHolder.mEtChange.getTag();
        if (tag == position) {
            String productAttributeId = kwProductList.get(position).getProductAttributeId();
            for (int i = 0; i < mPassProductList.size(); i++) {
                String productId = mPassProductList.get(i).getProductId();
                if (productAttributeId.equals(productId)) {
                    int itemNum = mPassProductList.get(i).getItemNum();
                    mMap.put(position, itemNum);
                    if (isShowChange) {
                        viewHolder.mEtChange.setText(itemNum + "");
                    }
                }
            }
            String mEtItemNum = viewHolder.mEtChange.getText().toString().trim();
            mMEtItemNumInt[0] = Integer.parseInt(mEtItemNum);
            mMap.put(position, mMEtItemNumInt[0]);
        }

        viewHolder.mDrinkSmell.setText(kwProductList.get(position).getTasteName());
        Glide.with(context).load(kwProductList.get(position).getThumbnail()).placeholder(R.drawable.place).into(viewHolder.mIvProduct);
        viewHolder.mTvProductNorms.setText(kwProductList.get(position).getSpecificationsName());
        viewHolder.mTvProductPrice.setText("¥" + kwProductList.get(position).getPrice());
        viewHolder.mTvProductName.setText(kwProductList.get(position).getProductName());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowChange = false;
                mMEtItemNumInt[0] += 10;
                finalViewHolder.mEtChange.setText(String.valueOf(mMEtItemNumInt[0]));
            }
        });

        viewHolder.mIvMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowChange = false;
                if (mMEtItemNumInt[0] > 0) {
                    if ((mMEtItemNumInt[0] -= 10) < 0) {
                        mMEtItemNumInt[0] = 0;
                    }
                } else {
                    Util.toast(context, "数量不能小于0");
                }
                finalViewHolder.mEtChange.setText(String.valueOf(mMEtItemNumInt[0]));
            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.mEtChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int tag = (int) finalViewHolder1.mEtChange.getTag();
                if (position == tag) {
                    if (TextUtils.isEmpty(charSequence)) {
                        finalViewHolder.mEtChange.setText(String.valueOf(0));
                        return;
                    }
                    String textNum = String.valueOf(charSequence);
                    if (textNum.contains("-")) {
                        textNum = "0";
                        finalViewHolder2.mEtChange.setText("0");
                    }
                    int number = Integer.parseInt(textNum);
                    if ((mMEtItemNumInt[0] == number) && isShowChange) {
                        return;
                    }

                    mMEtItemNumInt[0] = number;
                    mMap.put(position, number);
                    String productAttributeId = kwProductList.get(position).getProductAttributeId();
                    String price = kwProductList.get(position).getPrice();
                    double priceDouble = Double.parseDouble(price);
                    double totalPrice = priceDouble * number;
                    Selection.setSelection((Spannable) charSequence, charSequence.length());
                    EventBus.getDefault().post(new TotalNumAndPriceIdEvent(number, totalPrice, productAttributeId));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* String text = editable.toString();
                int length = text.length();
                if (length > 1 && text.startsWith("0")) {
                    editable.clear();
                }*/
            }
        });

        return view;
    }

    public Map<Integer, Integer> getCountNumMap() {

        return mMap;
    }

    static class ViewHolder {
        ImageView mIvProduct;
        TextView mDrinkSmell;
        TextView mTvProductNorms;
        TextView mTvProductPrice;
        TextView mTvProductName;
        ImageView mIvMin;
        ImageView mIvAdd;
        EditText mEtChange;
    }
}
