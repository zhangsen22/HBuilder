package bychain.android.com.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.R;
import bychain.android.com.ui.activity.OrderActivity;
import bychain.android.com.ui.adapter.OrderViewPagerAdapter;
import bychain.android.com.ui.widget.NoScrollViewPager;

public class OrderFragment extends BaseFragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    @BindView(R.id.order_magicindicator)
    MagicIndicator orderMagicindicator;
    @BindView(R.id.order_viewPager)
    NoScrollViewPager orderViewPager;
    @BindView(R.id.ff_order_content)
    LinearLayout ffOrderContent;
    @BindView(R.id.iv_order_finish)
    ImageView ivOrderFinish;
    private OrderViewPagerAdapter orderViewPagerAdapter;
    private OrderActivity orderActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderActivity = (OrderActivity) getActivity();
    }

    public static OrderFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.order_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "OrderFragment   is    initView");
        setRootViewPaddingTop(ffOrderContent);
        orderMagicindicator.setBackgroundResource(R.drawable.round_indicator_bg);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        final String[] orderTitle = orderActivity.getResources().getStringArray(R.array.order_title);
        orderViewPager.setOffscreenPageLimit(orderTitle.length - 1);
        orderViewPagerAdapter = new OrderViewPagerAdapter(getChildFragmentManager(), orderTitle);
        orderViewPager.setAdapter(orderViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(orderActivity);
//        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return orderTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#506EE6"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setText(orderTitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = orderViewPager.getCurrentItem();
                        if (currentItem != index) {
                            orderViewPager.setCurrentItem(index);
                        } else {

                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.y60);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius((float) (lineHeight / 4.6));
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#506EE6"));
                return indicator;
            }
        });

        orderMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(orderMagicindicator, orderViewPager);
    }

    @OnClick(R.id.iv_order_finish)
    public void onViewClicked() {
        orderActivity.finish();
    }
}
