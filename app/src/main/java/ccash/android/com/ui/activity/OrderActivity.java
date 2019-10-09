package ccash.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import ccash.android.com.BaseActivity;
import ccash.android.com.R;
import ccash.android.com.ui.fragment.OrderFragment;

public class OrderActivity extends BaseActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();

    public static void startThis(BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, OrderActivity.class));
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
        OrderFragment orderFragment = (OrderFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (orderFragment == null) {
            orderFragment = OrderFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    orderFragment, R.id.contentFrame);
        }
    }
}
