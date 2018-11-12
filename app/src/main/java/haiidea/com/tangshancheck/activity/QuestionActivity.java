package haiidea.com.tangshancheck.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haiidea.com.tangshancheck.R;
import haiidea.com.tangshancheck.adapter.BaseFragmentAdapter;

import static haiidea.com.tangshancheck.R.id.viewPager;

/**
 * Created by Administrator on 2018/11/11.
 */

public class QuestionActivity extends FragmentActivity {
    @Bind(viewPager)
    ViewPager mViewPager;

    private List<SelectFragment> fragments = new ArrayList<>();
    private List<String> title = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        Bundle paramBundle = new Bundle();
        Bundle bundle = new Bundle();
        bundle.putString("type", "1");
        bundle.putBundle("param",paramBundle);
        fragments.add(SelectFragment.newInstance(bundle));
        bundle = new Bundle();
        bundle.putString("type", "2");
        bundle.putBundle("param",paramBundle);
        fragments.add(SelectFragment.newInstance(bundle));
        bundle = new Bundle();
        bundle.putString("type", "3");
        bundle.putBundle("param",paramBundle);
        fragments.add(SelectFragment.newInstance(bundle));
        bundle = new Bundle();
        bundle.putString("type", "4");
        bundle.putBundle("param",paramBundle);
        fragments.add(SelectFragment.newInstance(bundle));
        bundle = new Bundle();
        bundle.putString("type", "5");
        bundle.putBundle("param",paramBundle);
        fragments.add(SelectFragment.newInstance(bundle));

        PagerAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private int mIndex;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIndex == 3) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mViewPager.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mViewPager.requestDisallowInterceptTouchEvent(false);
                    break;
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}
