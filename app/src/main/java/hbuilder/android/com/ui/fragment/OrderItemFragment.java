package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.growalong.util.util.DensityUtil;

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
import hbuilder.android.com.ui.adapter.OrderItemViewPagerAdapter;

/**
 * 1: 我的卖出
 * 2: 我的买入
 * 3: 我的委托单
 */
public class OrderItemFragment extends BaseFragment {

    @BindView(R.id.order_item_magicindicator)
    MagicIndicator orderItemMagicindicator;
    @BindView(R.id.order_item_viewPager)
    ViewPager orderItemViewPager;
    private Context mContext;
    private String[] orderItemTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    public static OrderItemFragment newInstance(@Nullable int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type",type);
        OrderItemFragment fragment = new OrderItemFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.order_item_fragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        int type = getArguments().getInt("type");
        if(type == 1 || type == 2){
            orderItemTitle = mContext.getResources().getStringArray(R.array.order_item_title);
        }else if(type == 3){
            orderItemTitle = mContext.getResources().getStringArray(R.array.order_item_title_entrust);
        }

        orderItemViewPager.setOffscreenPageLimit(orderItemTitle.length - 1);
        OrderItemViewPagerAdapter orderViewPagerAdapter = new OrderItemViewPagerAdapter(getChildFragmentManager(), orderItemTitle,type);
        orderItemViewPager.setAdapter(orderViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return orderItemTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#808080"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#4e6bcf"));
                colorTransitionPagerTitleView.setText(orderItemTitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = orderItemViewPager.getCurrentItem();
                        if (currentItem != index) {
                            orderItemViewPager.setCurrentItem(index);
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

        orderItemMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(orderItemMagicindicator, orderItemViewPager);
    }
}
