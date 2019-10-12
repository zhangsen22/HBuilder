package ccash.android.com.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.DensityUtil;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
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
import ccash.android.com.BaseFragment;
import ccash.android.com.MyApplication;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.modle.UsdtPriceResponse;
import ccash.android.com.modle.WalletResponse;
import ccash.android.com.presenter.PropertyPresenter;
import ccash.android.com.presenter.contract.PropertyContract;
import ccash.android.com.presenter.modle.PropertyModle;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.adapter.PropertyViewPagerAdapter;
import ccash.android.com.util.SharedPreferencesUtils;

public class PropertyFragment extends BaseFragment implements PropertyContract.View {
    private static final String TAG = PropertyFragment.class.getSimpleName();
    @BindView(R.id.fl_title_comtent)
    LinearLayout flTitleComtent;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_total_moneycny)
    TextView tvTotalMoneycny;
    @BindView(R.id.property_magicindicator)
    MagicIndicator propertyMagicindicator;
    @BindView(R.id.property_viewPager)
    ViewPager propertyViewPager;
    private PropertyPresenter propertyPresenter;
    private PropertyViewPagerAdapter propertyViewPagerAdapter;
    private MainActivity mainActivity;

    public static PropertyFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        PropertyFragment fragment = new PropertyFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.property_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "PropertyFragment   is    initView");
        setRootViewPaddingTop(flTitleComtent);
        final String[] guadanTitle = mainActivity.getResources().getStringArray(R.array.property_title);
        propertyViewPager.setOffscreenPageLimit(guadanTitle.length - 1);
        propertyViewPagerAdapter = new PropertyViewPagerAdapter(getChildFragmentManager(), guadanTitle);
        propertyViewPager.setAdapter(propertyViewPagerAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(mainActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return guadanTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#333333"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#506EE6"));
                colorTransitionPagerTitleView.setText(guadanTitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentItem = propertyViewPager.getCurrentItem();
                        if (currentItem != index) {
                            propertyViewPager.setCurrentItem(index);
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
                indicator.setColors(Color.parseColor("#506EE6"));
                indicator.setYOffset(UIUtil.dip2px(context, 8));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });

        propertyMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(propertyMagicindicator, propertyViewPager);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "PropertyFragment   is    lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new PropertyPresenter(PropertyFragment.this, new PropertyModle());
        propertyPresenter.getInfo();
    }

    public void onActivityResultProperty(int requestCode) {
        GALogger.d(TAG, "requestCode == " + requestCode);
        if (propertyPresenter != null) {
            propertyPresenter.getInfo();
        }
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        UsdtPriceResponse usdtPriceResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.USDTPRICE), UsdtPriceResponse.class);
        if (walletResponse != null && usdtPriceResponse != null) {
            double minSellPrice = usdtPriceResponse.getMinSellUsdtPrice();
            double walletNum = walletResponse.getWalletNum();
            double walletFreezeNum = walletResponse.getWalletFreezeNum();
            double hotNum = walletResponse.getHotNum();
            double hotFreezeNum = walletResponse.getHotFreezeNum();
            tvTotalMoney.setText((walletNum+walletFreezeNum+hotNum+hotFreezeNum)+"");
            tvTotalMoneycny.setText((walletNum+walletFreezeNum+hotNum+hotFreezeNum)*minSellPrice+"");
        }
    }

    @Override
    public void setPresenter(PropertyContract.Presenter presenter) {
        this.propertyPresenter = (PropertyPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
