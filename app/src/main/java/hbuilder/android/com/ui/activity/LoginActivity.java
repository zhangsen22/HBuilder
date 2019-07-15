package hbuilder.android.com.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import hbuilder.android.com.BaseActivity;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.app.AppManager;
import hbuilder.android.com.app.Constants;
import hbuilder.android.com.presenter.LoginPresenter;
import hbuilder.android.com.presenter.modle.LoginModle;
import hbuilder.android.com.ui.fragment.LoginFragment;
import hbuilder.android.com.util.SharedPreferencesUtils;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_content;
    }

    @Override
    protected void initView(View mRootView) {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {

                        } else {
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        if(AccountManager.getInstance().isLogin()){
            AccountManager.getInstance().logout();
        }
        if(SharedPreferencesUtils.has(Constants.SESSIONID)){
            SharedPreferencesUtils.remove(Constants.SESSIONID);
        }
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }
        //初始化presenter
        new LoginPresenter(loginFragment, new LoginModle());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //退出程序
            AppManager.getInstance().appExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
