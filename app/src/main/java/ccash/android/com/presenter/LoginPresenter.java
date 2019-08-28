package ccash.android.com.presenter;

import com.growalong.util.util.Md5Utils;

import ccash.android.com.app.AccountInfo;
import ccash.android.com.app.AccountManager;
import ccash.android.com.modle.DomainModel;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.LoginContract;
import ccash.android.com.presenter.modle.LoginModle;
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
    public void login(final String phoneNum, final String pwd, long time,boolean isLoading) {
        if(isLoading) {
            mView.showLoading();
        }
        mModel.login(phoneNum,Md5Utils.getMD5(pwd+time),time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<AccountInfo>() {
                    @Override
                    public void onSuccess(AccountInfo accountInfo) {
                        if(isLoading) {
                            mView.hideLoading();
                        }
                        accountInfo.setPhoneNumber(phoneNum);
                        accountInfo.setPassword(pwd);
                        AccountManager.getInstance().saveAccountInfoFormModel(accountInfo);
                        mView.loginSuccess(accountInfo);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        if(isLoading) {
                            mView.hideLoading();
                        }
                        mView.loginError();
                    }
                });
    }

    @Override
    public void getDomainName() {
        mModel.getDomainName().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<DomainModel>() {
                    @Override
                    public void onSuccess(DomainModel domainModel) {
                        mView.getDomainNameSuccess(domainModel);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
