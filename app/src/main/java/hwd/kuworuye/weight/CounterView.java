package hwd.kuworuye.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hwd.kuworuye.R;
import hwd.kuworuye.interf.IChangeCoutCallback;
import hwd.kuworuye.utils.Util;

public class CounterView extends LinearLayout implements View.OnClickListener, TextWatcher {

    /**
     * 最小的数量
     **/
    public static final int MIN_VALUE = 0;
    private int maxValue;

    //    private int countValue = 1;//数量
    private int countValue = 0;//数量

    private ImageView ivAdd, ivMinu;

    private EditText etCount;

    private IChangeCoutCallback callback;
    private String defultNum = "0";


    public void setCallback(IChangeCoutCallback c) {
        if (c != null) {
            this.callback = c;
        }
    }

    private Context mContext;

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs);
    }

    /**
     * 功能描述：设置最大数量
     * 作者： hg_liuzl@qq.com
     * 时间：2016/12/3 18:33
     * 参数：
     */
    public void setMaxValue(int max) {
        this.maxValue = max;
    }

    public int getMaxValue() {
        return maxValue;
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.my_counter_size);

        int maxValue = a.getInt(R.styleable.my_counter_size_count_max, 10000);

        setMaxValue(maxValue);

        LayoutInflater.from(mContext).inflate(R.layout.model_count_view_kc, this);

        ivMinu = (ImageView) findViewById(R.id.iv_count_minus);
        ivMinu.setOnClickListener(this);

        ivAdd = (ImageView) findViewById(R.id.iv_count_add);
        ivAdd.setOnClickListener(this);

        etCount = (EditText) findViewById(R.id.et_count);

        etCount.addTextChangedListener(this);
        a.recycle();
    }

    public void setDefultValue(String num) {
        this.defultNum = num;
        if (TextUtils.isEmpty(defultNum)) {
            etCount.setText("0");
        } else {
            etCount.setText(defultNum);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_count_add:
                addAction();
                break;
            case R.id.iv_count_minus:
                minuAction();
                break;


        }
    }

    /**
     * 添加操
     */
    private void addAction() {
        if (Integer.parseInt(defultNum) >=maxValue) {
            ivAdd.setEnabled(false);
        } else {
            ivAdd.setEnabled(true);
            int i = Integer.parseInt(defultNum);
            i += 1;
            defultNum = String.valueOf(i);
            countValue += 1;
        }
        btnChangeWord();
    }


    /**
     * 减少操作
     */
    private void minuAction() {
        countValue -= 1;
        if (countValue < 0) {
            Util.toast(getContext(), "数量不能小于0");
            return;
        }
        int i = Integer.parseInt(defultNum);
        i -= 1;
        defultNum = String.valueOf(i);
        btnChangeWord();
    }

    /**
     * 功能描述：
     * 作者： hg_liuzl@qq.com
     * 时间：2016/12/12 10:29
     * 参数：boolean 是否需要重新赋值
     */
    private void changeWord(boolean needUpdate) {
        if (needUpdate) {
            etCount.removeTextChangedListener(this);
            if (!TextUtils.isEmpty(etCount.getText().toString().trim())) {  //不为空的时候才需要赋值
                etCount.setText(String.valueOf(countValue));
            }
            etCount.addTextChangedListener(this);
        }
        etCount.setSelection(etCount.getText().toString().trim().length());
        if (callback != null) {
            callback.change(countValue);
        }
    }

    private void btnChangeWord() {
        ivMinu.setEnabled(countValue > MIN_VALUE);
        ivAdd.setEnabled(countValue < maxValue);
        etCount.setText(String.valueOf(countValue));
        etCount.setSelection(etCount.getText().toString().trim().length());
        if (callback != null) {
            callback.change(countValue);
        }
    }

    public String getCountValue() {
        return String.valueOf(countValue);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            countValue = Integer.valueOf(s.toString());
            changeWord(true);
//            EventBus.getDefault().post(new ProductNumEvent(countValue));
        }else {
            countValue =0;
            changeWord(true);
        }
    }
}
