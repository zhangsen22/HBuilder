package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.presenter.YunShanFuListPresenter;
import hbuilder.android.com.presenter.modle.YunShanFuListModle;
import hbuilder.android.com.ui.fragment.YunShanFuListFragment;

public class YunShanFuListActivity extends BaseActivity {
    private static final String TAG = YunShanFuListActivity.class.getSimpleName();
    private YunShanFuListFragment yunShanFuListFragment;

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, YunShanFuListActivity.class));
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
        yunShanFuListFragment = (YunShanFuListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (yunShanFuListFragment == null) {
            yunShanFuListFragment = YunShanFuListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    yunShanFuListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new YunShanFuListPresenter(yunShanFuListFragment, new YunShanFuListModle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == Constants.REQUESTCODE_19){
                yunShanFuListFragment.onActivityResultF();
            }
        }
    }
}
