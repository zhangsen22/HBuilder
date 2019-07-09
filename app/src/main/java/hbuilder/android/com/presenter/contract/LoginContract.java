package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.modle.DomainModel;

public interface LoginContract {
    interface Presenter extends IBasePresenter {
        //登录
        void login(String phoneNum,String pwd,long time,boolean isLoading);
        //获取多域名
        void getDomainName();
    }
    interface View extends IBaseView<Presenter> {
        //登录  成功
        void loginSuccess(AccountInfo accountInfo);
        //登录  失败
        void loginError();
        //获取多域名失败
        void getDomainNameSuccess(DomainModel domainModel);
    }
}
