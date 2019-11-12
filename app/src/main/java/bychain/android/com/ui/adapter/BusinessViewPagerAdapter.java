package bychain.android.com.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import bychain.android.com.BaseFragment;
import bychain.android.com.ui.fragment.BuyFragment;
import bychain.android.com.ui.fragment.LargeAmountFragment;
import bychain.android.com.ui.fragment.OrderItemFragment;

public class BusinessViewPagerAdapter extends FragmentPagerAdapter {

    private BaseFragment mCurrentPrimaryItem = null;
    private BuyFragment buyFragment;
    private LargeAmountFragment largeAmountFragment;
    private OrderItemFragment orderItemFragment;
    private List<Fragment> fragmentList;
    private String[] mTitles;

    public BusinessViewPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.mTitles = titles;
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        if(buyFragment == null){
            buyFragment = BuyFragment.newInstance("");
        }
        fragmentList.add(buyFragment);
        if(largeAmountFragment == null){
            largeAmountFragment = LargeAmountFragment.newInstance("");
        }
        fragmentList.add(largeAmountFragment);
        if(orderItemFragment == null){
           orderItemFragment = OrderItemFragment.newInstance(3);
        }
        fragmentList.add(orderItemFragment);
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

    public BaseFragment getCurrentFragment(int position){
        if(position == 0){
            mCurrentPrimaryItem = buyFragment;
        }
        else if(position == 1){
            mCurrentPrimaryItem = largeAmountFragment;
        }

        else if(position == 2){
            mCurrentPrimaryItem = orderItemFragment;
        }
        return mCurrentPrimaryItem;
    }
}
