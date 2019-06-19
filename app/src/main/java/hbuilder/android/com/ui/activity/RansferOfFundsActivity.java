package hbuilder.android.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.fragment.RansferOfFundsFragment;

public class RansferOfFundsActivity extends BaseActivity {

    public static void startThis(Context activity, int formType) {
        Intent intent = new Intent(activity, RansferOfFundsActivity.class);
        intent.putExtra("formType",formType);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_ransfer_of_funds;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        int formType = getIntent().getIntExtra("formType", 1);
        RansferOfFundsFragment ransferOfFundsFragment = (RansferOfFundsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (ransferOfFundsFragment == null) {
            ransferOfFundsFragment = RansferOfFundsFragment.newInstance(formType);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ransferOfFundsFragment, R.id.contentFrame);
        }

    }
}
