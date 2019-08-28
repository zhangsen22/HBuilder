package ccash.android.com.ui.activity;

import android.content.Intent;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import ccash.android.com.BaseActivity;
import ccash.android.com.R;
import ccash.android.com.presenter.FinancialRecordsPresenter;
import ccash.android.com.presenter.modle.FinancialRecordsModle;
import ccash.android.com.ui.fragment.FinancialRecordsFragment;

public class FinancialRecordsActivity extends BaseActivity {
    private static final String TAG = FinancialRecordsActivity.class.getSimpleName();

    public static void startThis(BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, FinancialRecordsActivity.class));
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
        FinancialRecordsFragment financialRecordsFragment = (FinancialRecordsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (financialRecordsFragment == null) {
            financialRecordsFragment = FinancialRecordsFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    financialRecordsFragment, R.id.contentFrame);
        }
        //初始化presenter
        new FinancialRecordsPresenter(financialRecordsFragment, new FinancialRecordsModle());
    }
}
