package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.modle.RewardLogResponse;
import hbuilder.android.com.presenter.RewardDetailPresenter;
import hbuilder.android.com.presenter.modle.RewardDetailModle;
import hbuilder.android.com.ui.fragment.RewardDetailFragment;

public class RewardDetailActivity extends BaseActivity {

    public static void startThis(BaseActivity activity, int formType, RewardLogResponse rewardLogResponse) {
        Intent intent = new Intent(activity, RewardDetailActivity.class);
        intent.putExtra("formType",formType);
        intent.putExtra("rewardLogResponse",rewardLogResponse);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        int formType = getIntent().getIntExtra("formType", 1);
        RewardLogResponse rewardLogResponse = getIntent().getParcelableExtra("rewardLogResponse");
        RewardDetailFragment rewardDetailFragment = (RewardDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (rewardDetailFragment == null) {
            rewardDetailFragment = RewardDetailFragment.newInstance(formType,rewardLogResponse);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    rewardDetailFragment, R.id.contentFrame);
        }
        //初始化presenter
        new RewardDetailPresenter(rewardDetailFragment, new RewardDetailModle());
    }
}
