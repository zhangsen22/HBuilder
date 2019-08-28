package ccash.android.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import ccash.android.com.BaseActivity;
import ccash.android.com.R;
import ccash.android.com.modle.BuyItem;
import ccash.android.com.presenter.BusinessBuyPresenter;
import ccash.android.com.presenter.modle.BusinessBuyModle;
import ccash.android.com.ui.fragment.BusinessBuyFragment;

public class BusinessBuyActivity extends BaseActivity {
    private static final String TAG = BusinessBuyActivity.class.getSimpleName();
    public static void startThis(Context context, BuyItem buyItem) {
        Intent intent = new Intent(context, BusinessBuyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("buyItem",buyItem);
        context.startActivity(intent);
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
        BuyItem buyItem = getIntent().getParcelableExtra("buyItem");
        BusinessBuyFragment businessBuyFragment = (BusinessBuyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (businessBuyFragment == null) {
            businessBuyFragment = BusinessBuyFragment.newInstance(buyItem);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    businessBuyFragment, R.id.contentFrame);
        }
        //初始化presenter
        new BusinessBuyPresenter(businessBuyFragment, new BusinessBuyModle());
    }
}
