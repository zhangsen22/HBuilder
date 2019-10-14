package bychain.android.com.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.growalong.util.util.DensityUtil;
import com.growalong.util.util.GALogger;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import butterknife.BindView;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.ui.activity.GuaDanActivity;
import bychain.android.com.ui.activity.MainActivity;
import bychain.android.com.ui.activity.OrderActivity;
import bychain.android.com.ui.adapter.BusinessViewPagerAdapter;

public class BusinessContainerFragment extends BaseFragment {
    private static final String TAG = BusinessContainerFragment.class.getSimpleName();
    @BindView(R.id.business_magicindicator)
    MagicIndicator businessMagicindicator;
    @BindView(R.id.business_viewPager)
    ViewPager businessViewPager;
    @BindView(R.id.ff_business_content)
    LinearLayout ffBusinessContent;
    @BindView(R.id.tv_guadan)
    ImageView tvGuadan;
    private MainActivity mainActivity;
    private BusinessViewPagerAdapter baseFragmentPagerAdapter;

    public static BusinessContainerFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        BusinessContainerFragment fragment = new BusinessContainerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GALogger.d(TAG, "onCreate ......");
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.business_container_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "BusinessContainerFragment   is    initView");
        setRootViewPaddingTop(ffBusinessContent);
        final String[] businessTitle = mainActivity.getResources().getStringArray(R.array.business_title);
        businessViewPager.setOffscreenPageLimit(businessTitle.length - 1);
        baseFragmentPagerAdapter = new BusinessViewPagerAdapter(getChildFragmentManager(), businessTitle);
        businessViewPager.setAdapter(baseFragmentPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(mainActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return businessTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(businessTitle[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#4CB86C"));
//                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = businessViewPager.getCurrentItem();
                        if (currentItem != index) {
                            if(index == 3){
                                OrderActivity.startThis(mainActivity);
                            }else {
                                businessViewPager.setCurrentItem(index);
                            }
                        } else {

                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setColors(Color.parseColor("#4CB86C"));
                indicator.setYOffset(UIUtil.dip2px(context, 8));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
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
        GALogger.d(TAG, "lazyLoadData ......");
        int currentItem = businessViewPager.getCurrentItem();
        if (baseFragmentPagerAdapter != null) {
            BaseFragment currentFragment = baseFragmentPagerAdapter.getCurrentFragment(currentItem);
            if (currentFragment != null && currentFragment.isVisible()) {
                currentFragment.lazyLoadData();
            }
        }
    }

    @OnClick({R.id.tv_guadan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_guadan:
                GuaDanActivity.startThis(mainActivity);
                break;
        }
    }

    @Override
    public void onPause() {
        GALogger.d(TAG, "onPause ......");
        super.onPause();
    }

    @Override
    public void onResume() {
        GALogger.d(TAG, "onResume ......");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
