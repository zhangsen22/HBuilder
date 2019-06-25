package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import com.growalong.util.util.GsonUtil;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.presenter.MainPresenter;
import hbuilder.android.com.presenter.contract.MainContract;
import hbuilder.android.com.presenter.modle.MainModle;
import hbuilder.android.com.ui.adapter.MainViewPagerAdapter;
import hbuilder.android.com.ui.fragment.CenterFragment;
import hbuilder.android.com.ui.fragment.PropertyFragment;
import hbuilder.android.com.ui.widget.NoScrollViewPager;
import hbuilder.android.com.util.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.noscrollViewPager)
    NoScrollViewPager noscrollViewPager;
    @BindView(R.id.rb_business)
    RadioButton rbBusiness;
    @BindView(R.id.rb_guadan)
    RadioButton rbGuadan;
    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_property)
    RadioButton rbProperty;
    @BindView(R.id.rb_center)
    RadioButton rbCenter;

    private MainPresenter mainPresenter;
    private MainViewPagerAdapter mainViewPagerAdapter;

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View mRootView) {
    }

    @Override
    protected void initData() {
        //初始化presenter
        new MainPresenter(this, new MainModle());
        MyApplication.applicationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainPresenter.usdtPrice();
                MyApplication.applicationHandler.postDelayed(this,5*60*1000);
            }
        },0);
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.getInfo();
            }
        },1000);
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        noscrollViewPager.setAdapter(mainViewPagerAdapter);
        noscrollViewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @OnClick({R.id.rb_business, R.id.rb_guadan, R.id.rb_order, R.id.rb_property, R.id.rb_center})
    public void onViewClicked(View view) {
        int currentItem = noscrollViewPager.getCurrentItem();
        switch (view.getId()) {
            case R.id.rb_business:
                if (currentItem != 0) {
                    noscrollViewPager.setCurrentItem(0,false);
                }
                break;
            case R.id.rb_guadan:
                if (currentItem != 1) {
                    noscrollViewPager.setCurrentItem(1,false);
                }
                break;
            case R.id.rb_order:
                if (currentItem != 2) {
                    noscrollViewPager.setCurrentItem(2,false);
                }
                break;
            case R.id.rb_property:
                if (currentItem != 3) {
                    noscrollViewPager.setCurrentItem(3,false);
                }
                break;
            case R.id.rb_center:
                if (currentItem != 4) {
                    noscrollViewPager.setCurrentItem(4,false);
                }
                break;
        }
    }

    @Override
    public void usdtPriceSuccess(UsdtPriceResponse usdtPriceResponse) {
        if(usdtPriceResponse != null){
            SharedPreferencesUtils.putString(Constants.USDTPRICE,GsonUtil.getInstance().objTojson(usdtPriceResponse));
        }
    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if(walletResponse != null){
            SharedPreferencesUtils.putString(Constants.WALLET_BALANCE,GsonUtil.getInstance().objTojson(walletResponse));
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mainPresenter = (MainPresenter) presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == Constants.REQUESTCODE_10){
                CenterFragment centerFragment = mainViewPagerAdapter.getCenterFragment();
                if(centerFragment != null){
                    centerFragment.onActivityResultCenter(requestCode);
                }
            }else if(requestCode == Constants.REQUESTCODE_11){
                PropertyFragment propertyFragment = mainViewPagerAdapter.getPropertyFragment();
                if(propertyFragment != null){
                    propertyFragment.onActivityResultProperty(requestCode);
                }
            }
        }
    }
}
