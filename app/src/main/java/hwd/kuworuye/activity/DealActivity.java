package hwd.kuworuye.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.weight.JustifyTextView;

/**
 * Created by Administrator on 2017/5/8.
 */

public class DealActivity extends BaseActivity {

    @BindView(R.id.comm_three_back)
    ImageView mCommThreeBack;
    @BindView(R.id.tv_comm_title_bar_three)
    TextView mTvCommTitleBarThree;
    @BindView(R.id.comm_right_three)
    ImageView mCommRightThree;
    @BindView(R.id.main_tv)
    JustifyTextView mMainTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        ButterKnife.bind(this);
        // 此处为子类JustifyTextView对象,使用方法与原生TextView相同
        TextView tv = (TextView) findViewById(R.id.main_tv);
        tv.setText(getAssetsString(this, "deal.txt"));
        initData();
    }

    private void initData() {
        mCommThreeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvCommTitleBarThree.setText("酷我用户协议和法律协议");

        mCommRightThree.setVisibility(View.INVISIBLE);

    }

    public String getAssetsString(Context context, String fileName) {
        StringBuffer sb = new StringBuffer();
        // 根据语言选择加载
        try {
            AssetManager am = context.getAssets();
            InputStream in = am.open(fileName);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                line += ("\n");
                sb.append(line);
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
