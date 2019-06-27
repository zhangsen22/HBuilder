package hbuilder.android.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.growalong.util.util.Md5Utils;

import hbuilder.android.com.BaseFragment;
import hbuilder.android.com.MyApplication;
import hbuilder.android.com.R;
import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.presenter.LoginPresenter;
import hbuilder.android.com.presenter.contract.LoginContract;
import hbuilder.android.com.ui.activity.LoginActivity;
import hbuilder.android.com.ui.activity.MainActivity;
import hbuilder.android.com.ui.activity.SplashActivity;

public class SplashFragment extends BaseFragment implements LoginContract.View {
    private static final String TAG = SplashFragment.class.getSimpleName();
    private SplashActivity splashActivity;
    private LoginPresenter splashPresenter;

    public static SplashFragment newInstance(@Nullable String taskId) {
        Bundle arguments = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    @Override
    protected int getRootView() {
        return R.layout.splash_fragment;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        if(AccountManager.getInstance().isLogin()){
            /**
             * 掉登录接口  成功了去首页
             */
            long currentTime = System.currentTimeMillis();
            String phoneNumber = AccountManager.getInstance().getPhoneNumber();
            String passWord = AccountManager.getInstance().getPassWord();
            splashPresenter.login(phoneNumber,passWord,currentTime);

        }else {
            MyApplication.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.startThis(splashActivity);
                    splashActivity.finish();
                }
            },3000);
        }
    }

    @Override
    public void loginSuccess(AccountInfo accountInfo) {
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.startThis(splashActivity);
                splashActivity.finish();
            }
        },3000);
    }

    @Override
    public void loginError() {
        MyApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                LoginActivity.startThis(splashActivity);
                splashActivity.finish();
            }
        },3000);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        splashPresenter = (LoginPresenter) presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }
}
