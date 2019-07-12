package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.ui.fragment.AddMakeStyleFragment;

public class AddMakeStyleActivity extends BaseActivity {
    private static final String TAG = AddMakeStyleActivity.class.getSimpleName();
    public static void startThis(BaseActivity activity) {
        Intent intent = new Intent(activity, AddMakeStyleActivity.class);
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
        AddMakeStyleFragment addMakeStyleFragment = (AddMakeStyleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (addMakeStyleFragment == null) {
            addMakeStyleFragment = AddMakeStyleFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addMakeStyleFragment, R.id.contentFrame);
        }
    }
}
