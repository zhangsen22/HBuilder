package bychain.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bychain.android.com.BaseFragment;
import bychain.android.com.MyApplication;
import bychain.android.com.R;
import bychain.android.com.app.Constants;
import bychain.android.com.modle.WalletResponse;
import bychain.android.com.presenter.PropertyPresenter;
import bychain.android.com.presenter.contract.PropertyContract;
import bychain.android.com.presenter.modle.PropertyModle;
import bychain.android.com.ui.activity.MainActivity;
import bychain.android.com.ui.activity.RansferOfFundsActivity;
import bychain.android.com.ui.adapter.TradingViewPagerAdapter;

public class TradingAccountFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener, PropertyContract.View {
    private static final String TAG = TradingAccountFragment.class.getSimpleName();
    @BindView(R.id.tv_bco_money)
    TextView tvBcoMoney;
    @BindView(R.id.tv_used_usdt)
    TextView tvUsedUsdt;
    @BindView(R.id.tv_freed_usdt)
    TextView tvFreedUsdt;
    @BindView(R.id.ll_duihuabco)
    LinearLayout llDuihuabco;
    @BindView(R.id.ll_huazhuan)
    LinearLayout llHuazhuan;
    @BindView(R.id.cb_jyjl)
    CheckBox cbJyjl;
    @BindView(R.id.cb_tgjl)
    CheckBox cbTgjl;
    @BindView(R.id.cb_dljl)
    CheckBox cbDljl;
    @BindView(R.id.trading_viewPager)
    ViewPager tradingViewPager;
    @BindView(R.id.cb_gdjl)
    CheckBox cbGdjl;
    private MainActivity mainActivity;
    private TradingViewPagerAdapter tradingViewPagerAdapter;
    private PropertyPresenter propertyPresenter;

    public static TradingAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        TradingAccountFragment fragment = new TradingAccountFragment();
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
        return R.layout.trading_account_fragment;
    }

    @Override
    protected void initView(View root) {
        final String[] guadanTitle = mainActivity.getResources().getStringArray(R.array.trading_title);
        tradingViewPager.setOffscreenPageLimit(guadanTitle.length - 1);
        tradingViewPagerAdapter = new TradingViewPagerAdapter(getChildFragmentManager(), guadanTitle);
        tradingViewPager.setAdapter(tradingViewPagerAdapter);
        cbJyjl.setOnCheckedChangeListener(this);
        cbTgjl.setOnCheckedChangeListener(this);
        cbDljl.setOnCheckedChangeListener(this);
        cbGdjl.setOnCheckedChangeListener(this);
        tradingViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "TradingAccountFragment   is    lazyLoadData");
        setLoadDataWhenVisible();
        //初始化presenter
        new PropertyPresenter(this, new PropertyModle());
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                propertyPresenter.getInfo();
            }
        }, 1000);
    }

    @OnClick({R.id.ll_duihuabco, R.id.ll_huazhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_duihuabco:
                break;
            case R.id.ll_huazhuan:
                RansferOfFundsActivity.startThis(mainActivity, 2, Constants.REQUESTCODE_11);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_jyjl:
                if (b) {
                    cbTgjl.setChecked(false);
                    cbDljl.setChecked(false);
                    cbGdjl.setChecked(false);
                    tradingViewPager.setCurrentItem(0, false);
                }
                break;
            case R.id.cb_tgjl:
                if (b) {
                    cbJyjl.setChecked(false);
                    cbDljl.setChecked(false);
                    cbGdjl.setChecked(false);
                    tradingViewPager.setCurrentItem(1, false);
                }
                break;
            case R.id.cb_dljl:
                if (b) {
                    cbJyjl.setChecked(false);
                    cbTgjl.setChecked(false);
                    cbGdjl.setChecked(false);
                    tradingViewPager.setCurrentItem(2, false);
                }
                break;
            case R.id.cb_gdjl:
                if (b) {
                    cbJyjl.setChecked(false);
                    cbTgjl.setChecked(false);
                    cbDljl.setChecked(false);
                    tradingViewPager.setCurrentItem(3, false);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            cbJyjl.setChecked(true);
            cbTgjl.setChecked(false);
            cbDljl.setChecked(false);
        } else if (i == 1) {
            cbJyjl.setChecked(false);
            cbTgjl.setChecked(true);
            cbDljl.setChecked(false);
        } else if (i == 2) {
            cbJyjl.setChecked(false);
            cbTgjl.setChecked(false);
            cbDljl.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void getInfoSuccess(WalletResponse walletResponse) {
        if (walletResponse != null) {
            tvBcoMoney.setText((walletResponse.getHotNum() + walletResponse.getHotFreezeNum()) + "");
            tvUsedUsdt.setText(walletResponse.getHotNum() + "");
            tvFreedUsdt.setText(walletResponse.getHotFreezeNum() + "");
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
