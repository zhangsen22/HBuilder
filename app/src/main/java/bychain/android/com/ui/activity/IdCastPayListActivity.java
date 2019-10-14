package bychain.android.com.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.growalong.util.util.ActivityUtils;

import bychain.android.com.BaseActivity;
import bychain.android.com.R;
import bychain.android.com.app.Constants;
import bychain.android.com.presenter.IdCastPayListPresenter;
import bychain.android.com.presenter.modle.IdCastPayListModle;
import bychain.android.com.ui.fragment.IdCastPayListFragment;

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
