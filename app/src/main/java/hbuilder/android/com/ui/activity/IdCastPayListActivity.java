package hbuilder.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.presenter.IdCastPayListPresenter;
import hbuilder.android.com.presenter.modle.IdCastPayListModle;
import hbuilder.android.com.ui.fragment.IdCastPayListFragment;

public class IdCastPayListActivity extends BaseActivity {
    private static final String TAG = IdCastPayListActivity.class.getSimpleName();
    private IdCastPayListFragment idCastPayListFragment;

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, IdCastPayListActivity.class));
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
        idCastPayListFragment = (IdCastPayListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (idCastPayListFragment == null) {
            idCastPayListFragment = IdCastPayListFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    idCastPayListFragment, R.id.contentFrame);
        }
        //初始化presenter
        new IdCastPayListPresenter(idCastPayListFragment, new IdCastPayListModle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == Constants.REQUESTCODE_16){
                idCastPayListFragment.onActivityResultF();
            }
        }
    }
}
