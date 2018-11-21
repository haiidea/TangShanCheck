package haiidea.com.tangshancheck.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haiidea.com.tangshancheck.R;
import haiidea.com.tangshancheck.adapter.BaseFragmentAdapter;

import static haiidea.com.tangshancheck.R.id.viewPager;

/**
 * Created by Administrator on 2018/11/11.
 */

public class QuestionActivity extends FragmentActivity {
    @Bind(R.id.back_iv)
    ImageView mBackIV;
    @Bind(viewPager)
    ViewPager mViewPager;
    @Bind(R.id.time_title_tv)
    TextView mTimeTV;
    @Bind(R.id.title_tv)
    TextView mTitleTV;
    @Bind(R.id.index_tv)
    TextView mIndexTV;
    @Bind(R.id.start_tv)
    TextView mStartTV;

    private List<SelectFragment> fragments = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private AnswerPopuwindow mPopuwindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        String name =getIntent().getStringExtra("name");
        mTitleTV.setText(name);

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
                mIndexTV.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTimeTV.setText(getCurTime());
        mPopuwindow = new AnswerPopuwindow(this);
    }
    @SuppressLint("SimpleDateFormat")
    public static String getCurTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String curDateStr = formatter.format(curDate);
        return curDateStr;
    }
    @OnClick({R.id.back_iv,R.id.start_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.start_tv:
                mPopuwindow.showAsDropDown(mIndexTV,200,400);
                break;
        }
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
