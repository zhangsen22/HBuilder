package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.presenter.IdCastPayListPresenter;
import hbuilder.android.com.presenter.modle.IdCastPayListModle;
import hbuilder.android.com.ui.fragment.IdCastPayListFragment;

public class IdCastPayListActivity extends BaseActivity {
    private static final String TAG = IdCastPayListActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, IdCastPayListActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_id_cast_pay_list;
    }

    @Override
    protected void initView(View mRootView) {
        setRootViewPaddingTop();
    }

    @Override
    protected void initData() {
        IdCastPayListFragment idCastPayListFragment = (IdCastPayListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (idCastPayListFragment == null) {
            idCastPayListFragment = IdCastPayListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    idCastPayListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new IdCastPayListPresenter(idCastPayListFragment, new IdCastPayListModle());
    }
}
