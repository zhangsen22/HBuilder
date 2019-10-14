package bychain.android.com.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import com.growalong.util.util.ActivityUtils;
import bychain.android.com.BaseActivity;
import bychain.android.com.R;
import bychain.android.com.app.AccountManager;
import bychain.android.com.app.AppManager;
import bychain.android.com.app.Constants;
import bychain.android.com.ui.fragment.LoginAndRegistFragment;
import bychain.android.com.util.SharedPreferencesUtils;
import io.reactivex.functions.Consumer;

public class LoginAndRegistActivity extends BaseActivity {
    private static final String TAG = LoginAndRegistActivity.class.getSimpleName();

    public static void startThis(BaseActivity activity) {
        activity.startActivity(new Intent(activity, LoginAndRegistActivity.class));
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
        LoginAndRegistFragment loginAndRegistFragment = (LoginAndRegistFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (loginAndRegistFragment == null) {
            loginAndRegistFragment = LoginAndRegistFragment.newInstance("");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginAndRegistFragment, R.id.contentFrame);
        }
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
