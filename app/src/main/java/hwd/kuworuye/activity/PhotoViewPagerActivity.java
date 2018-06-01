/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package hwd.kuworuye.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import hwd.kuworuye.R;
import hwd.kuworuye.base.BaseActivity;
import hwd.kuworuye.bean.ImageDataUrlBean;
import hwd.kuworuye.constants.Constant;
import hwd.kuworuye.weight.HackyViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewPagerActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private List<ImageDataUrlBean> mImageUrlDataList = new ArrayList<>();
    private int mIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_pager);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        if (getIntent() != null) {
            if (getIntent().getParcelableArrayListExtra(Constant.COMM_PIC_URL_TITLE) != null) {
                mImageUrlDataList = getIntent().getParcelableArrayListExtra(Constant.COMM_PIC_URL_TITLE);
                mIndex = getIntent().getIntExtra(Constant.PIC_INDEX,0);
            }
            String solaPicUrl = getIntent().getStringExtra(Constant.COMM_SOLA_PIC_URL);
            if (solaPicUrl != null) {
                mImageUrlDataList.add(new ImageDataUrlBean(solaPicUrl, ""));
            }
        }
        ViewPager mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setCurrentItem(mIndex);
        mImageUrlDataList.get(mIndex).getTitle();
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageUrlDataList == null ? 0 : mImageUrlDataList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            if (mImageUrlDataList != null) {
                Glide.with(PhotoViewPagerActivity.this).load(mImageUrlDataList.get(position).getImgs()).placeholder(R.drawable.place).into(photoView);
                mTvContent.setText(mImageUrlDataList.get(position).getTitle());
            }


            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
