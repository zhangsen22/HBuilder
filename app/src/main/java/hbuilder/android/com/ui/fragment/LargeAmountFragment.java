package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.R;

public class LargeAmountFragment extends BaseFragment {

    public static LargeAmountFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        LargeAmountFragment fragment = new LargeAmountFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getRootView() {
        return R.layout.large_amount_ragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }
}
