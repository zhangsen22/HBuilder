package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.ForgetPwdPresenter;
import hbuilder.android.com.presenter.modle.ForgetPwdModle;
import hbuilder.android.com.ui.fragment.ForgetPwdFragment;

public class ForgetPwdActivity extends BaseActivity {
    private static final String TAG = ForgetPwdActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ForgetPwdActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.forgetpwd_activity;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        ForgetPwdFragment forgetPwdFragment = (ForgetPwdFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (forgetPwdFragment == null) {
            forgetPwdFragment = ForgetPwdFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    forgetPwdFragment, R.id.contentFrame);
        }
        //初始化presenter
        new ForgetPwdPresenter(forgetPwdFragment, new ForgetPwdModle());
    }
}
