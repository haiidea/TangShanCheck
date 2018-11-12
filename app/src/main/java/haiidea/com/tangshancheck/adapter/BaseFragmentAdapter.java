package haiidea.com.tangshancheck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import haiidea.com.tangshancheck.activity.SelectFragment;

/**
 * Created by Administrator on 2018/11/11.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<SelectFragment> mFragments;

    public BaseFragmentAdapter(FragmentManager fm, List<SelectFragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (mFragments!=null){
            return mFragments.size();
        } else {
            return 0;
        }
    }
}
