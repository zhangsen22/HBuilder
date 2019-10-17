package bychain.android.com.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import bychain.android.com.BaseFragment;
import bychain.android.com.ui.fragment.RewardDetailFragment;

public class TradingViewPagerAdapter extends FragmentPagerAdapter {
    /**
     * 1为交易奖励，2推广分红，3代理奖励，4挂单奖励
     */
    private BaseFragment mCurrentPrimaryItem = null;
    private RewardDetailFragment rewardDetailFragment;
    private List<Fragment> fragmentList;
    private String[] mTitles;

    public TradingViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        for (int i = 0; i < titles.length; i++) {
            if(i < 2){
                rewardDetailFragment = RewardDetailFragment.newInstance(i+1);
            }else if(i == 2){
                rewardDetailFragment = RewardDetailFragment.newInstance(4);
            }
            fragmentList.add(rewardDetailFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0:fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public BaseFragment getCurrentFragment(int position) {
        mCurrentPrimaryItem = (BaseFragment) fragmentList.get(position);
        return mCurrentPrimaryItem;
    }
}
