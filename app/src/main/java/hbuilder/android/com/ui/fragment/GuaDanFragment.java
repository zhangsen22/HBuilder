package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.growalong.util.util.DensityUtil;
import com.growalong.util.util.GALogger;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import butterknife.BindView;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.adapter.GuaDanViewPagerAdapter;

public class GuaDanFragment extends BaseFragment {
    private static final String TAG = GuaDanFragment.class.getSimpleName();
    @BindView(R.id.guadan_magicindicator)
    MagicIndicator guadanMagicindicator;
    @BindView(R.id.guadan_viewPager)
    ViewPager guadanViewPager;
    private GuaDanViewPagerAdapter guaDanViewPagerAdapter;
    private Context mContext;

    public static GuaDanFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        GuaDanFragment fragment = new GuaDanFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    protected int getRootView() {
        return R.layout.guadan_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG,"GuaDanFragment   is    initView");
        setRootViewPaddingTop(root);
        final String[] guadanTitle = mContext.getResources().getStringArray(R.array.guadan_title);
        guadanViewPager.setOffscreenPageLimit(guadanTitle.length-1);
        guaDanViewPagerAdapter = new GuaDanViewPagerAdapter(getChildFragmentManager(),guadanTitle);
        guadanViewPager.setAdapter(guaDanViewPagerAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return guadanTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#808080"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#4e6bcf"));
                colorTransitionPagerTitleView.setText(guadanTitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = guadanViewPager.getCurrentItem();
                        if (currentItem != index) {
                            guadanViewPager.setCurrentItem(index);
                        } else {

                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext,1));
                indicator.setColors(R.color.color_afadad);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                return indicator;
            }
        });

        guadanMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(guadanMagicindicator, guadanViewPager);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG,"GuaDanFragment   is    lazyLoadData");
        int currentItem = guadanViewPager.getCurrentItem();
        if(guaDanViewPagerAdapter != null){
            BaseFragment currentFragment = guaDanViewPagerAdapter.getCurrentFragment(currentItem);
            GALogger.d(TAG,"currentFragment.isVisible()   "+currentFragment.isVisible());
            if(currentFragment != null && currentFragment.isVisible()){
                currentFragment.setEnableLazyLoad(true);
                currentFragment.lazyLoadData();
            }
        }
    }
}
