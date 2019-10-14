package bychain.android.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import bychain.android.com.BaseActivity;
import bychain.android.com.R;
import bychain.android.com.modle.BuyItem;
import bychain.android.com.presenter.BusinessSellPresenter;
import bychain.android.com.presenter.modle.BusinessSellModle;
import bychain.android.com.ui.fragment.BusinessSellFragment;

public class BusinessSellActivity extends BaseActivity {
    private static final String TAG = BusinessSellActivity.class.getSimpleName();
    public static void startThis(Context context,BuyItem buyItem) {
        Intent intent = new Intent(context, BusinessSellActivity.class);
        intent.putExtra("buyItem",buyItem);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        BusinessSellFragment businessSellFragment = (BusinessSellFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (businessSellFragment == null) {
            businessSellFragment = BusinessSellFragment.newInstance(buyItem);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    businessSellFragment, R.id.contentFrame);
        }
        //初始化presenter
        new BusinessSellPresenter(businessSellFragment, new BusinessSellModle());
    }
}
