package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.AwardDetailsPresenter;
import hbuilder.android.com.presenter.modle.AwardDetailsModle;
import hbuilder.android.com.ui.fragment.AwardDetailsFragment;

public class AwardDetailsActivity extends BaseActivity {
    private static final String TAG = AwardDetailsActivity.class.getSimpleName();

    public static void startThis(BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, AwardDetailsActivity.class));
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
        AwardDetailsFragment awardDetailsFragment = (AwardDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (awardDetailsFragment == null) {
            awardDetailsFragment = AwardDetailsFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    awardDetailsFragment, R.id.contentFrame);
        }
        //初始化presenter
        new AwardDetailsPresenter(awardDetailsFragment, new AwardDetailsModle());
    }
}
