package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.InvitationPresenter;
import hbuilder.android.com.presenter.modle.InvitationModle;
import hbuilder.android.com.ui.fragment.InvitationRewardFirstFragment;

public class InvitationRewardFirstActivity extends BaseActivity {
    private static final String TAG = InvitationRewardFirstActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, InvitationRewardFirstActivity.class));
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
        InvitationRewardFirstFragment invitationRewardFirstFragment = (InvitationRewardFirstFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (invitationRewardFirstFragment == null) {
            invitationRewardFirstFragment = InvitationRewardFirstFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    invitationRewardFirstFragment, R.id.contentFrame);
        }
        //初始化presenter
        new InvitationPresenter(invitationRewardFirstFragment, new InvitationModle());
    }
}
