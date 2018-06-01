package hwd.kuworuye.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/3.
 */

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.comm_back)
    ImageView mCommBack;
    @BindView(R.id.tv_comm_title_bar_two)
    TextView mTvCommTitleBarTwo;
    @BindView(R.id.wv_news_detail)
    WebView mWvNewsDetail;
    private String mStringExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        inintData();
    }

    private void inintData() {
        Intent intent = getIntent();
        if (intent != null) {
            mStringExtra = intent.getStringExtra("home");
        }
        mTvCommTitleBarTwo.setText(R.string.news_detail_title);
        mCommBack.setOnClickListener(this);
        mTvCommTitleBarTwo.setOnClickListener(this);
        mWvNewsDetail.loadDataWithBaseURL(null, mStringExtra.replace("{{FONTSIZE}}", "15px"), "text/html", "UTF-8", null);
        mWvNewsDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWvNewsDetail.getSettings().setLoadWithOverviewMode(true);
        mWvNewsDetail.getSettings().setDefaultTextEncodingName("utf-8");

        mWvNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comm_back:
                finish();
                break;

            default:
                break;
        }
    }

}
