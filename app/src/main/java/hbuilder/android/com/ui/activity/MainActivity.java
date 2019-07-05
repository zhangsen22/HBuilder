package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import butterknife.BindView;
import butterknife.OnClick;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.presenter.MainPresenter;
import hbuilder.android.com.presenter.contract.MainContract;
import hbuilder.android.com.presenter.modle.MainModle;
import hbuilder.android.com.ui.adapter.MainViewPagerAdapter;
import hbuilder.android.com.ui.fragment.CenterFragment;
import hbuilder.android.com.ui.fragment.OrderFragment;
import hbuilder.android.com.ui.fragment.PropertyFragment;
import hbuilder.android.com.ui.widget.NoScrollViewPager;
import hbuilder.android.com.util.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements MainContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

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
        String hostAddress = MyApplication.getHostAddress();
        GALogger.d(TAG,"hostAddress   "+hostAddress);
    }

    @Override
    protected void initData() {
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                updateApp();
            }
        },3000);
        //初始化presenter
        new MainPresenter(this, new MainModle());
        mainPresenter.usdtPrice();
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
            }else if(requestCode == Constants.REQUESTCODE_12){
                OrderFragment orderFragment = mainViewPagerAdapter.getOrderFragment();
                if(orderFragment != null){
                    orderFragment.onActivityResultOrder(requestCode);
                }
            }
        }
    }

    @Override
    public void usdtPriceSuccess(UsdtPriceResponse usdtPriceResponse) {
        if(usdtPriceResponse != null){
            SharedPreferencesUtils.putString(Constants.USDTPRICE,GsonUtil.getInstance().objTojson(usdtPriceResponse));
        }
    }

    @Override
    public void usdtPriceError() {
        UsdtPriceResponse mUsdtPriceResponse = new UsdtPriceResponse(6.90,6.90);
        SharedPreferencesUtils.putString(Constants.USDTPRICE,GsonUtil.getInstance().objTojson(mUsdtPriceResponse));
    }

    private void updateApp(){
        //带确认和取消按钮的弹窗
        new XPopup.Builder(this)
//                         .dismissOnTouchOutside(false)
                // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        Log.e("tag", "onShow");
                    }

                    @Override
                    public void onDismiss() {
                        Log.e("tag", "onDismiss");
                    }
                }).asConfirm("发现新版本,是否升级?", "",
                "下次再说", "升级",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                    }
                }, null, false)
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            initExetTiShi();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initExetTiShi() {
        //带确认和取消按钮的弹窗
        new XPopup.Builder(this)
//                         .dismissOnTouchOutside(false)
                // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        Log.e("tag", "onShow");
                    }

                    @Override
                    public void onDismiss() {
                        Log.e("tag", "onDismiss");
                    }
                }).asConfirm("提示", "确认退出吗?",
                "否", "是",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        MainActivity.this.finish();
                    }
                }, null, false)
                .show();
    }
}
