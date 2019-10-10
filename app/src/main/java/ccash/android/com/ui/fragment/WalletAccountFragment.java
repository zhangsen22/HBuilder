package ccash.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.growalong.util.util.GALogger;
import ccash.android.com.BaseFragment;
import ccash.android.com.R;

public class WalletAccountFragment extends BaseFragment {

    private static final String TAG = WalletAccountFragment.class.getSimpleName();

    public static WalletAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        WalletAccountFragment fragment = new WalletAccountFragment();
        fragment.setArguments(arguments);
        return fragment;
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
}
