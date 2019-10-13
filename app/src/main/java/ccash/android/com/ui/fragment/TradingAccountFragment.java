package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.RansferOfFundsActivity;
import ccash.android.com.ui.adapter.TradingViewPagerAdapter;

public class TradingAccountFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
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
    private MainActivity mainActivity;
    private TradingViewPagerAdapter tradingViewPagerAdapter;

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
        tradingViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "TradingAccountFragment   is    lazyLoadData");
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
        switch (compoundButton.getId()){
            case R.id.cb_jyjl:
                if(b){
                    cbTgjl.setChecked(false);
                    cbDljl.setChecked(false);
                    tradingViewPager.setCurrentItem(0,false);
                }
                break;
            case R.id.cb_tgjl:
                if(b){
                    cbJyjl.setChecked(false);
                    cbDljl.setChecked(false);
                    tradingViewPager.setCurrentItem(1,false);
                }
                break;
            case R.id.cb_dljl:
                if(b){
                    cbJyjl.setChecked(false);
                    cbTgjl.setChecked(false);
                    tradingViewPager.setCurrentItem(2,false);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if(i == 0){
            cbJyjl.setChecked(true);
            cbTgjl.setChecked(false);
            cbDljl.setChecked(false);
        }else if(i == 1){
            cbJyjl.setChecked(false);
            cbTgjl.setChecked(true);
            cbDljl.setChecked(false);
        }else if(i == 2){
            cbJyjl.setChecked(false);
            cbTgjl.setChecked(false);
            cbDljl.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
