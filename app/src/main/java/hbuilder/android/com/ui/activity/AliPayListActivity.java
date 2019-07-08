package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.AliPayListPresenter;
import hbuilder.android.com.presenter.modle.AliPayListModle;
import hbuilder.android.com.ui.fragment.AliPayListFragment;

public class AliPayListActivity extends BaseActivity {
    private static final String TAG = AliPayListActivity.class.getSimpleName();
    private AliPayListFragment aliPayListFragment;

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, AliPayListActivity.class));
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
        aliPayListFragment = (AliPayListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (aliPayListFragment == null) {
            aliPayListFragment = AliPayListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    aliPayListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new AliPayListPresenter(aliPayListFragment, new AliPayListModle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 100){
                aliPayListFragment.onActivityResultF();
            }
        }
    }
}
