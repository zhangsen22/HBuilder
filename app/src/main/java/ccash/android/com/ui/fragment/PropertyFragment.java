package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import com.growalong.util.util.GsonUtil;
import java.text.DecimalFormat;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.modle.UsdtPriceResponse;
import ccash.android.com.modle.WalletResponse;
import ccash.android.com.presenter.PropertyPresenter;
import ccash.android.com.presenter.contract.PropertyContract;
import ccash.android.com.presenter.modle.PropertyModle;
import ccash.android.com.ui.activity.ChongBiActivity;
import ccash.android.com.ui.activity.FinancialRecordsActivity;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.RansferOfFundsActivity;
import ccash.android.com.ui.activity.TiBiActivity;
import ccash.android.com.util.SharedPreferencesUtils;

public class PropertyFragment extends BaseFragment implements PropertyContract.View {
    private static final String TAG = PropertyFragment.class.getSimpleName();
    @BindView(R.id.iv_financial_records)
    ImageView ivFinancialRecords;
    @BindView(R.id.fl_title_comtent)
    FrameLayout flTitleComtent;
    @BindView(R.id.tv_wallet_usdt)
    TextView tvWalletUsdt;
    @BindView(R.id.tv_wallet_cny)
    TextView tvWalletCny;
    @BindView(R.id.ll_wallet_cb)
    LinearLayout llWalletCb;
    @BindView(R.id.ll_wallet_ti)
    LinearLayout llWalletTi;
    @BindView(R.id.ll_wallet_zjhz)
    LinearLayout llWalletZjhz;
    @BindView(R.id.tv_wallet_dj_usdt)
    TextView tvWalletDjUsdt;
    @BindView(R.id.tv_wallet_dj_cny)
    TextView tvWalletDjCny;
    @BindView(R.id.tv_account_usdt)
    TextView tvAccountUsdt;
    @BindView(R.id.tv_account_cny)
    TextView tvAccountCny;
    @BindView(R.id.ll_account_zjhz)
    LinearLayout llAccountZjhz;
    @BindView(R.id.tv_account_dj_usdt)
    TextView tvAccountDjUsdt;
    @BindView(R.id.tv_account_dj_cny)
    TextView tvAccountDjCny;
    private PropertyPresenter propertyPresenter;
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
            tvWalletUsdt.setText(new DecimalFormat("0.00").format(walletNum));
            tvWalletCny.setText(new DecimalFormat("0.00").format((walletNum  * minSellPrice)));
            tvWalletDjUsdt.setText(new DecimalFormat("0.00").format(walletFreezeNum));
            tvWalletDjCny.setText(new DecimalFormat("0.00").format((walletFreezeNum  * minSellPrice)));
            tvAccountUsdt.setText(new DecimalFormat("0.00").format(hotNum));
            tvAccountCny.setText(new DecimalFormat("0.00").format((hotNum)));
            tvAccountDjUsdt.setText(new DecimalFormat("0.00").format(hotFreezeNum));
            tvAccountDjCny.setText(new DecimalFormat("0.00").format((hotFreezeNum)));
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

    @OnClick({R.id.iv_financial_records,R.id.ll_wallet_cb, R.id.ll_wallet_ti, R.id.ll_wallet_zjhz, R.id.ll_account_zjhz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_financial_records:
                FinancialRecordsActivity.startThis(mainActivity);
                break;
            case R.id.ll_wallet_cb:
                ChongBiActivity.startThis(mainActivity);
                break;
            case R.id.ll_wallet_ti:
                TiBiActivity.startThis(mainActivity);
                break;
            case R.id.ll_wallet_zjhz:
                RansferOfFundsActivity.startThis(mainActivity, 1, Constants.REQUESTCODE_11);
                break;
            case R.id.ll_account_zjhz:
                RansferOfFundsActivity.startThis(mainActivity, 2, Constants.REQUESTCODE_11);
                break;
        }
    }
}
