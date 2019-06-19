package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.AliPayListPresenter;
import hbuilder.android.com.presenter.modle.AliPayListModle;
import hbuilder.android.com.ui.fragment.AliPayListFragment;

public class AliPayListActivity extends BaseActivity {
    private static final String TAG = AliPayListActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, AliPayListActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_ali_pay_list;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        AliPayListFragment aliPayListFragment = (AliPayListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (aliPayListFragment == null) {
            aliPayListFragment = AliPayListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    aliPayListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new AliPayListPresenter(aliPayListFragment, new AliPayListModle());
    }
}
