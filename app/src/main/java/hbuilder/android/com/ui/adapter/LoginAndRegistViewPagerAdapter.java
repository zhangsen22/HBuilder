package hbuilder.android.com.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.ui.fragment.LoginFragment;
import hbuilder.android.com.ui.fragment.RegistFragment;

public class LoginAndRegistViewPagerAdapter extends FragmentPagerAdapter {

    private BaseFragment mCurrentPrimaryItem = null;
    private LoginFragment loginFragment;
    private RegistFragment registFragment;
    private List<Fragment> fragmentList;
    private String[] mTitles;

    public LoginAndRegistViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance("");
        }
        fragmentList.add(loginFragment);
        if(registFragment == null){
            registFragment = RegistFragment.newInstance("");
        }
        fragmentList.add(registFragment);
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
            mCurrentPrimaryItem = loginFragment;
        }else if(position == 1){
            mCurrentPrimaryItem = registFragment;
        }
        return mCurrentPrimaryItem;
    }
}
