package hbuilder.android.com.presenter;

import com.growalong.util.util.Md5Utils;

import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.app.AccountManager;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.LoginContract;
import hbuilder.android.com.presenter.modle.LoginModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginPresenter implements LoginContract.Presenter{

    protected LoginContract.View mView;

    private LoginModle mModel;

    public LoginPresenter(LoginContract.View view, LoginModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void login(final String phoneNum, final String pwd, long time) {
        mView.showLoading();
        mModel.login(phoneNum,Md5Utils.getMD5(pwd+time),time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<AccountInfo>() {
                    @Override
                    public void onSuccess(AccountInfo accountInfo) {
                        mView.hideLoading();
                        accountInfo.setPhoneNumber(phoneNum);
                        accountInfo.setPassword(pwd);
                        AccountManager.getInstance().saveAccountInfoFormModel(accountInfo);
                        mView.loginSuccess(accountInfo);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                        mView.loginError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
