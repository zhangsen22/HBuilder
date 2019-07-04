package hbuilder.android.com.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.growalong.util.util.DensityUtil;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.presenter.PropertyPresenter;
import hbuilder.android.com.presenter.contract.PropertyContract;
import hbuilder.android.com.presenter.modle.PropertyModle;
import hbuilder.android.com.ui.adapter.PropertyViewPagerAdapter;
import hbuilder.android.com.util.SharedPreferencesUtils;

public class PropertyFragment extends BaseFragment implements ViewPager.OnPageChangeListener, PropertyContract.View {
    private static final String TAG = PropertyFragment.class.getSimpleName();

    @BindView(R.id.property_magicindicator)
    MagicIndicator propertyMagicindicator;
    @BindView(R.id.property_viewPager)
    ViewPager propertyViewPager;
    @BindView(R.id.tv_available_monery)
    TextView tvAvailableMonery;
    @BindView(R.id.tv_available_rmb_monery)
    TextView tvAvailableRmbMonery;
    @BindView(R.id.tv_freeze_monery)
    TextView tvFreezeMonery;
    @BindView(R.id.tv_freeze_rmb_monery)
    TextView tvFreezeRmbMonery;
    @BindView(R.id.iv_waller_iamge)
    ImageView ivWallerIamge;
    @BindView(R.id.iv_waller_iamge1)
    ImageView ivWallerIamge1;
    private PropertyViewPagerAdapter propertyViewPagerAdapter;
    private PropertyPresenter propertyPresenter;
    private Context mContext;
    private WalletResponse mWalletResponse = null;

    public static PropertyFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        PropertyFragment fragment = new PropertyFragment();
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
        return R.layout.property_fragment;
    }

    @Override
    protected void initView(View root) {
        GALogger.d(TAG, "PropertyFragment   is    initView");
        setRootViewPaddingTop(root);
        final String[] propertyTitle = mContext.getResources().getStringArray(R.array.property_title);
        propertyViewPager.setOffscreenPageLimit(propertyTitle.length - 1);
        propertyViewPagerAdapter = new PropertyViewPagerAdapter(getChildFragmentManager(), propertyTitle);
        propertyViewPager.setAdapter(propertyViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return propertyTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#808080"));
                colorTransitionPagerTitleView.setTextSize(14);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#4e6bcf"));
                colorTransitionPagerTitleView.setText(propertyTitle[index]);
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
                indicator.setLineHeight(DensityUtil.dip2px(MyApplication.appContext, 1));
                indicator.setColors(R.color.color_afadad);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                return indicator;
            }
        });

        propertyMagicindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(propertyMagicindicator, propertyViewPager);
        // do this in a runnable to make sure the viewPager's views are already instantiated before triggering the onPageSelected call
        propertyViewPager.addOnPageChangeListener(this);
        /**
         * 此代码解决进来不调用onPageSelected
         */
//        propertyViewPager.post(new Runnable() {
//            @Override
//            public void run() {
//                onPageSelected(propertyViewPager.getCurrentItem());
//            }
//        });
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "PropertyFragment   is    lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new PropertyPresenter(PropertyFragment.this, new PropertyModle());
        propertyPresenter.getInfo();
        int currentItem = propertyViewPager.getCurrentItem();
        if (propertyViewPagerAdapter != null) {
            BaseFragment currentFragment = propertyViewPagerAdapter.getCurrentFragment(currentItem);
            GALogger.d(TAG, "currentFragment.isVisible()   " + currentFragment.isVisible());
            if (currentFragment != null && currentFragment.isVisible()) {
                MyApplication.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        currentFragment.setEnableLazyLoad(true);
                        currentFragment.lazyLoadData();
                    }
                }, 1000);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        GALogger.d(TAG, "i    " + i);
        UsdtPriceResponse usdtPriceResponse = GsonUtil.getInstance().getServerBean(SharedPreferencesUtils.getString(Constants.USDTPRICE), UsdtPriceResponse.class);
        if (mWalletResponse != null && usdtPriceResponse != null) {
            if (i == 0) {
                double walletNum = mWalletResponse.getWalletNum();
                double walletFreezeNum = mWalletResponse.getWalletFreezeNum();
                double minSellPrice = usdtPriceResponse.getMinSellPrice();
                tvAvailableMonery.setText(new DecimalFormat("0.00").format(walletNum));
                tvFreezeMonery.setText(new DecimalFormat("0.00").format(walletFreezeNum));
                tvAvailableRmbMonery.setText(MyApplication.appContext.getResources().getString(R.string.rmb) + new DecimalFormat("0.00").format(walletNum * minSellPrice));
                tvFreezeRmbMonery.setText(MyApplication.appContext.getResources().getString(R.string.rmb) + new DecimalFormat("0.00").format(walletFreezeNum * minSellPrice));
                ivWallerIamge.setImageResource(R.mipmap.p);
                ivWallerIamge1.setImageResource(R.mipmap.p);
            } else if (i == 1) {
                double hotNum = mWalletResponse.getHotNum();
                double hotFreezeNum = mWalletResponse.getHotFreezeNum();
                tvAvailableMonery.setText(new DecimalFormat("0.00").format(hotNum));
                tvFreezeMonery.setText(new DecimalFormat("0.00").format(hotFreezeNum));
                tvAvailableRmbMonery.setText(MyApplication.appContext.getResources().getString(R.string.rmb) + new DecimalFormat("0.00").format(hotNum));
                tvFreezeRmbMonery.setText(MyApplication.appContext.getResources().getString(R.string.rmb) + new DecimalFormat("0.00").format(hotFreezeNum));
                ivWallerIamge.setImageResource(R.mipmap.bt);
                ivWallerIamge1.setImageResource(R.mipmap.bt);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void onActivityResultProperty(int requestCode) {
        GALogger.d(TAG, "requestCode == " + requestCode);
        propertyPresenter.getInfo();
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            this.mWalletResponse = walletResponse;
            onPageSelected(propertyViewPager.getCurrentItem());
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
