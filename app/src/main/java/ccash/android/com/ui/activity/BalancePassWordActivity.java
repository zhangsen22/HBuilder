package ccash.android.com.ui.activity;

import android.content.Intent;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import ccash.android.com.BaseActivity;
import ccash.android.com.R;
import ccash.android.com.ui.fragment.BalancePassWordFragment;

public class BalancePassWordActivity extends BaseActivity {
    private static final String TAG = BalancePassWordActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, BalancePassWordActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_content;
    }

    @Override
    protected void initView(View mRootView) {
    }

    @Override
    protected void initData() {
        BalancePassWordFragment balancePassWordFragment = (BalancePassWordFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (balancePassWordFragment == null) {
            balancePassWordFragment = BalancePassWordFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    balancePassWordFragment, R.id.contentFrame);
        }
    }
}
