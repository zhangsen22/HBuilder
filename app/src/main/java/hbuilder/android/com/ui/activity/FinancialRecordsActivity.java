package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.FinancialRecordsPresenter;
import hbuilder.android.com.presenter.modle.FinancialRecordsModle;
import hbuilder.android.com.ui.fragment.FinancialRecordsFragment;

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
        setRootViewPaddingTop();
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
