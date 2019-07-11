package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;

public class TradingAccountFragment extends BaseFragment{
    private static final String TAG = TradingAccountFragment.class.getSimpleName();

    public static TradingAccountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        TradingAccountFragment fragment = new TradingAccountFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.trading_account_fragment;
    }

    @Override
    protected void initView(View root) {

    }
}
