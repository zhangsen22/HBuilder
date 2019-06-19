package hbuilder.android.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.BuyItem;
import hbuilder.android.com.presenter.BusinessBuyPresenter;
import hbuilder.android.com.presenter.modle.BusinessBuyModle;
import hbuilder.android.com.ui.fragment.BusinessBuyFragment;

public class BusinessBuyActivity extends BaseActivity {
    private static final String TAG = BusinessBuyActivity.class.getSimpleName();
    public static void startThis(Context context, BuyItem buyItem) {
        Intent intent = new Intent(context, BusinessBuyActivity.class);
        intent.putExtra("buyItem",buyItem);
        context.startActivity(intent);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_business_buy;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
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
