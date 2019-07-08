package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.RegistPresenter;
import hbuilder.android.com.presenter.modle.RegistModle;
import hbuilder.android.com.ui.fragment.RegistFragment;

public class RegistActivity extends BaseActivity {
    private static final String TAG = RegistActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, RegistActivity.class));
    }

    @Override
    protected void initView(View mRootView) {

    }

    @Override
    protected int getRootView() {
        return R.layout.activity_content;
    }

    @Override
    protected void initData() {
        RegistFragment registFragment = (RegistFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (registFragment == null) {
            registFragment = RegistFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registFragment, R.id.contentFrame);
        }
        //初始化presenter
        new RegistPresenter(registFragment, new RegistModle());
    }
}
