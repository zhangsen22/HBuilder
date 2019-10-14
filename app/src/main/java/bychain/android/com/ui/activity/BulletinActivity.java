package bychain.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import java.util.ArrayList;
import bychain.android.com.BaseActivity;
import bychain.android.com.R;
import bychain.android.com.modle.BulletinListItem;
import bychain.android.com.ui.fragment.BulletinFragment;

public class BulletinActivity extends BaseActivity {

    public static void startThis(BaseActivity activity, ArrayList<BulletinListItem> bulletinList) {
        Intent intent = new Intent(activity, BulletinActivity.class);
        intent.putParcelableArrayListExtra("bulletinList", bulletinList);
        activity.startActivity(intent);
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
        ArrayList<BulletinListItem> bulletinList = getIntent().getParcelableArrayListExtra("bulletinList");
        BulletinFragment bulletinFragment = (BulletinFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (bulletinFragment == null) {
            bulletinFragment = BulletinFragment.newInstance(bulletinList);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    bulletinFragment, R.id.contentFrame);
        }
    }
}
