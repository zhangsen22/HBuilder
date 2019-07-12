package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.fragment.HelpAndKefuFragment;

public class HelpAndKefuActivity extends BaseActivity {
    private static final String TAG = HelpAndKefuActivity.class.getSimpleName();

    public static void startThis(BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, HelpAndKefuActivity.class));
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
        HelpAndKefuFragment helpAndKefuFragment = (HelpAndKefuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (helpAndKefuFragment == null) {
            helpAndKefuFragment = HelpAndKefuFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    helpAndKefuFragment, R.id.contentFrame);
        }
        //初始化presenter
//        new AwardDetailsPresenter(awardDetailsFragment, new AwardDetailsModle());
    }
}
