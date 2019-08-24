package hbuilder.android.com.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
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
import butterknife.BindView;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.activity.LoginAndRegistActivity;
import hbuilder.android.com.ui.adapter.LoginAndRegistViewPagerAdapter;
import hbuilder.android.com.ui.widget.NoScrollViewPager;

public class LoginAndRegistFragment extends BaseFragment {
    private static final String TAG = LoginAndRegistFragment.class.getSimpleName();
    @BindView(R.id.fl_login_head)
    FrameLayout flLoginHead;
    @BindView(R.id.guadan_magicindicator)
    MagicIndicator guadanMagicindicator;
    @BindView(R.id.guadan_viewPager)
    NoScrollViewPager guadanViewPager;
    @BindView(R.id.ll_login_head)
    LinearLayout llLoginHead;
    private LoginAndRegistActivity loginAndRegistActivity;
    private LoginAndRegistViewPagerAdapter loginAndRegistViewPagerAdapter;

    public static LoginAndRegistFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        LoginAndRegistFragment fragment = new LoginAndRegistFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginAndRegistActivity = (LoginAndRegistActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.login_regist_fragment;
    }

    @Override
    protected void initView(View root) {
        setRootViewPaddingTop(flLoginHead);
        setRootViewPaddingTop(llLoginHead);
        GALogger.d(TAG, "GuaDanFragment   is    initView");
        final String[] guadanTitle = loginAndRegistActivity.getResources().getStringArray(R.array.login_title);
        guadanViewPager.setOffscreenPageLimit(guadanTitle.length - 1);
        loginAndRegistViewPagerAdapter = new LoginAndRegistViewPagerAdapter(getChildFragmentManager(), guadanTitle);
        guadanViewPager.setAdapter(loginAndRegistViewPagerAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(loginAndRegistActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return guadanTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#666666"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#333333"));
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
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setColors(Color.parseColor("#FFCF20"));
                indicator.setYOffset(UIUtil.dip2px(context, 8));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });

        guadanMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(guadanMagicindicator, guadanViewPager);
    }
}
