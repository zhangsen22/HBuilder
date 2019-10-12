package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.growalong.util.util.GALogger;
import butterknife.BindView;
import butterknife.OnClick;
import ccash.android.com.BaseFragment;
import ccash.android.com.R;
import ccash.android.com.app.Constants;
import ccash.android.com.ui.activity.ChongBiActivity;
import ccash.android.com.ui.activity.MainActivity;
import ccash.android.com.ui.activity.RansferOfFundsActivity;
import ccash.android.com.ui.activity.TiBiActivity;

public class WalletAccountFragment extends BaseFragment {

    private static final String TAG = WalletAccountFragment.class.getSimpleName();
    @BindView(R.id.tv_used_usdt)
    TextView tvUsedUsdt;
    @BindView(R.id.tv_freed_usdt)
    TextView tvFreedUsdt;
    @BindView(R.id.ll_chongbi)
    LinearLayout llChongbi;
    @BindView(R.id.ll_tibi)
    LinearLayout llTibi;
    @BindView(R.id.ll_huazhuan)
    LinearLayout llHuazhuan;
    private MainActivity mainActivity;

    public static WalletAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        WalletAccountFragment fragment = new WalletAccountFragment();
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
        return R.layout.wallet_account_fragment;
    }

    @Override
    protected void initView(View root) {
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        GALogger.d(TAG, "WalletAccountFragment   is    lazyLoadData");
    }

    @OnClick({R.id.ll_chongbi, R.id.ll_tibi, R.id.ll_huazhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_chongbi:
                ChongBiActivity.startThis(mainActivity);
                break;
            case R.id.ll_tibi:
                TiBiActivity.startThis(mainActivity);
                break;
            case R.id.ll_huazhuan:
                RansferOfFundsActivity.startThis(mainActivity, 1, Constants.REQUESTCODE_11);
                break;
        }
    }
}
