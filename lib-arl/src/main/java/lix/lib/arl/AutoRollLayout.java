package lix.lib.arl;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 */
public class AutoRollLayout extends FrameLayout {

    private ViewPager mViewPager;
    private TextView mBannerTitle;
//    private LinearLayout mDotContainer;
    private OnItemClickListener mOnItemClickListener;

    private GestureDetector mGestureDetector;
    private TextView mBannerContent;
    ;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    // 在代码中常用
    public AutoRollLayout(Context context) {
        // 一定是调用自己的构造方法，不要调用super
        this(context, null);
//        init();
    }

    // 在xml中写，会导致此方法调用
    public AutoRollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

//        init();
    }

    // 一般此方法是给我们自定义控件用，比如说Button
    // 我们可以在父控件的基础上，添加一些默认的属性，做法是在2参数的构造方法中调用三参数的方法
    // 第三个参数 ，传入默认样式的 id
    // http://www.cnblogs.com/angeldevil/p/3479431.html
    public AutoRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*
    用户手指在触摸屏幕
     */
    boolean mIsTouching = false;

    private void init() {
        View.inflate(getContext(), R.layout.arl_layout_arl, this);
        mViewPager = (ViewPager) findViewById(R.id.arl_arl_vp);
        mBannerTitle = (TextView) findViewById(R.id.arl_banner_title);
        mBannerContent = ((TextView) findViewById(R.id.arl_banner_contnet));
//        mDotContainer = (LinearLayout) findViewById(R.id.arl_arl_dot_container);
        mViewPager.setOnPageChangeListener(mPageListener);

        mGestureDetector = new GestureDetector(getContext(), mOnGestureListener);

        mViewPager.setOnTouchListener(mOnTouchListener);

    }

    static Handler handler = new Handler();
    List<? extends IRollItem> items;

    public <T extends IRollItem> void setItems(List<T> items) {
        this.items = items;
        // viewpager
        mViewPager.setAdapter(mAdapter);
        // textview
//        监听
        mBannerTitle.setText(null);
        // 点
//        addDots();

        mPageListener.onPageSelected(0);
    }

    boolean autoRoll = false;

    public void setAutoRoll(boolean autoRoll) {
        Log.e("AutoRollLayout", "setAutoRoll " + autoRoll);
        if (this.autoRoll == autoRoll) {
            return;
        }
        this.autoRoll = autoRoll;

        if (autoRoll) {
//            handler.removeCallbacks(mGonNextPageRunnable);
            handler.postDelayed(mGonNextPageRunnable, 3000);
        } else {
            handler.removeCallbacks(mGonNextPageRunnable);
        }
    }

    private void addDots() {
//        mDotContainer.removeAllViews();
        if (items == null || items.isEmpty()) {
            return;
        }
        int dotWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        for (IRollItem item : items) {
            View dot = new View(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dotWidth, dotWidth);
            lp.setMargins(0, 0, dotWidth, 0);
            dot.setLayoutParams(lp);
            dot.setBackgroundResource(R.drawable.arl_dot_selector);
//            mDotContainer.addView(dot);
            dot.setOnClickListener(mGoPageListener);
        }
    }


    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        List<ImageView> cache = new ArrayList<>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (cache.isEmpty()) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                cache.add(imageView);
//                imageView.setOnClickListener(mImageViewOcl);
            }
            ImageView imageView = cache.remove(0);
            // 用Picasso imageView 显示图片
            Glide.with(container.getContext()).load(items.get(position).getRollItemPicUrl()).placeholder(R.mipmap.place).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            cache.add((ImageView) object);
        }
    };

    private ViewPager.OnPageChangeListener mPageListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (items == null || items.isEmpty()) {
                return;
            }

            mBannerTitle.setText(items.get(position).getRollItemTitle());

            mBannerContent.setText(items.get(position).getRollItemContent());
            for (int i = 0; i < mAdapter.getCount(); i++) {
//                if(i == position){
//                    mDotContainer.getChildAt(i).setEnabled(false);
//                }else{
//                    mDotContainer.getChildAt(i).setEnabled(true);
//                }

//                mDotContainer.getChildAt(i).setEnabled(i != position);
            }

        }
    };
    private OnClickListener mGoPageListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
//            mViewPager.setCurrentItem(mDotContainer.indexOfChild(v));

        }
    };

//    private OnClickListener mImageViewOcl = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
////            Toast.makeText(getContext(), "mImageViewOcl", Toast.LENGTH_SHORT).show();
//            if (mOnItemClickListener != null) {
////                mViewPager.indexOfChild(v);
//                mOnItemClickListener.onItemClick(mViewPager.getCurrentItem());
//            }
//        }
//    };
    /*
   套路 防止间隔执行的任务 多次执行的标准写法
     */

    private Runnable mGonNextPageRunnable = new Runnable() {
        @Override
        public void run() {
            //！ 只移除为执行的任务，不会影响已经执行的
            // 如果队列中有多个 ==  ，会移除多个
            // 如果队列中没有 ==  ，没有任何影响
            handler.removeCallbacks(this);
            if (!mIsTouching) {
                goNextPage();

            }
            handler.postDelayed(this, 3000);
        }
    };

    boolean fromLeftToRight = true;

    private void goNextPage() {
        // ViewPager的setCurrentItem 方法中进行了 容错处理，接受负值和超出count的数值，
        // 我们发现了，还是自己处理一下，不要交给它错误的数据了
        if (mAdapter.getCount() <= 1) {
            return;
        }

        int currentIndex = mViewPager.getCurrentItem();
        if (currentIndex == 0) {
            fromLeftToRight = true;
        } else if (currentIndex == mAdapter.getCount() - 1) {
            fromLeftToRight = false;
        }
        int targetIndex = 0;
        if (fromLeftToRight) {
            targetIndex = currentIndex + 1;
        } else {
            targetIndex = currentIndex - 1;
        }
        mViewPager.setCurrentItem(targetIndex);

//        Log.e("AutoRollLayout", "goNextPage " + items.get(targetIndex).getRollItemTitle());
    }

    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        /**

         @param v       设置了监听的View
         @param event   v    dispatchTouchEvent方法中 收到的触摸事件( 在自己的消费触摸事件前 的)
         @return
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean match = mGestureDetector.onTouchEvent(event);
//            Log.d("OnGestureListener", "onTouchEvent " + match);
            String action = event.getAction() == MotionEvent.ACTION_DOWN ? "DOWN "
                    : event.getAction() == MotionEvent.ACTION_MOVE ? "MOVE "
                    : event.getAction() == MotionEvent.ACTION_UP ? "UP " : "OTHER";
//            Log.d("onTouch", action);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    mIsTouching = true;
                    if (autoRoll) {
                        handler.removeCallbacks(mGonNextPageRunnable);
                    }

                    break;

                case MotionEvent.ACTION_CANCEL:
                    Log.w("onTouch", "CANCEL");
//                    break;
                case MotionEvent.ACTION_UP:
                    mIsTouching = false;
                    if (autoRoll) {
                        handler.postDelayed(mGonNextPageRunnable, 3000);
                    }
                    break;
            }
            return false;
        }
    };
    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
        // 手指按下
        @Override
        public boolean onDown(MotionEvent e) {
//            Log.d("OnGestureListener", "onDown");
            return false;
        }

        // 按下后一小会 没动
        @Override
        public void onShowPress(MotionEvent e) {
//            Log.d("OnGestureListener", "onShowPress");

        }

        // 单机
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            Log.d("OnGestureListener", "onSingleTapUp");
            if (mOnItemClickListener != null) {
//                mViewPager.indexOfChild(v);
                mOnItemClickListener.onItemClick(mViewPager.getCurrentItem());
            }

            return true;
        }

        // 手指在移动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.d("OnGestureListener", "onScroll");
            return false;
        }

        // 长按
        @Override
        public void onLongPress(MotionEvent e) {
//            Log.d("OnGestureListener", "onLongPress");

        }

        // 手指离开屏幕还有速度
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            Log.d("OnGestureListener", "onFling");
            return true;
        }
    };

}
