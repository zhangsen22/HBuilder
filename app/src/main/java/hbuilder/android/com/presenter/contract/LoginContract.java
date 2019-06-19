package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.app.AccountInfo;

public interface LoginContract {
    interface Presenter extends IBasePresenter {
        //登录
        void login(String phoneNum,String pwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //登录  成功
        void loginSuccess(AccountInfo accountInfo);
        //登录  失败
        void loginError();
    }
}
