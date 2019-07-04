package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
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
import butterknife.OnClick;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.ui.activity.WebViewActivity;
import hbuilder.android.com.ui.adapter.BusinessViewPagerAdapter;

public class BusinessContainerFragment extends BaseFragment {
    private static final String TAG = BusinessContainerFragment.class.getSimpleName();
    @BindView(R.id.business_magicindicator)
    MagicIndicator businessMagicindicator;
    @BindView(R.id.business_viewPager)
    ViewPager businessViewPager;
    @BindView(R.id.ll_notry_click)
    LinearLayout llNotryClick;
    private Context mContext;
    private BusinessViewPagerAdapter baseFragmentPagerAdapter;

    public static BusinessContainerFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        BusinessContainerFragment fragment = new BusinessContainerFragment();
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
        return R.layout.business_container_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG,"BusinessContainerFragment   is    initView");
        setRootViewPaddingTop(root);
        final String[] businessTitle = mContext.getResources().getStringArray(R.array.business_title);
        businessViewPager.setOffscreenPageLimit(businessTitle.length - 1);
        baseFragmentPagerAdapter = new BusinessViewPagerAdapter(getChildFragmentManager(), businessTitle);
        businessViewPager.setAdapter(baseFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return businessTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#808080"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#4e6bcf"));
                colorTransitionPagerTitleView.setText(businessTitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = businessViewPager.getCurrentItem();
                        if (currentItem != index) {
                            businessViewPager.setCurrentItem(index);
                        } else {

                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext, 1));
                indicator.setColors(R.color.color_afadad);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                return indicator;
            }
        });

        businessMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(businessMagicindicator, businessViewPager);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        setLoadDataWhenVisible();
        int currentItem = businessViewPager.getCurrentItem();
        if(baseFragmentPagerAdapter != null){
            BaseFragment currentFragment = baseFragmentPagerAdapter.getCurrentFragment(currentItem);
            if(currentFragment != null && currentFragment.isVisible()){
                currentFragment.lazyLoadData();
            }
        }
    }

    @OnClick(R.id.ll_notry_click)
    public void onViewClicked() {
        WebViewActivity.launchVerifyCode(MyApplication.appContext, Constants.NOTIFYCLICK, true);
    }
}
