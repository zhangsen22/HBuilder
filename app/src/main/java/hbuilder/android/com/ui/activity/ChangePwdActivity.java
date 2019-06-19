package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.fragment.ChangePwdFragment;

public class ChangePwdActivity extends BaseActivity {
    private static final String TAG = ChangePwdActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ChangePwdActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        ChangePwdFragment changePwdFragment = (ChangePwdFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (changePwdFragment == null) {
            changePwdFragment = ChangePwdFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    changePwdFragment, R.id.contentFrame);
        }
    }
}
