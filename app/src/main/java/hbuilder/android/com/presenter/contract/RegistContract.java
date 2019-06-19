package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.modle.ImageCodeResponse;
import hbuilder.android.com.modle.SmsCodeResponse;

public interface RegistContract {
    interface Presenter extends IBasePresenter {
        //获取图片验证码
        void getImageCode(String phoneNum);
        //发送验证码
        void senSenSmsCode(String phoneNum);
        //注册  登录
        void registerAndLogin(String phoneNum
                , String invitedCode
                , String imageCode
                , String smsCode
                , String pwd
                , long time, String password);
    }
    interface View extends IBaseView<Presenter> {
        //获取图片验证码成功
        void getImageCodeSuccess(ImageCodeResponse imageCodeResponse);
        //发送验证码成功
        void senSenSmsCodeSuccess(SmsCodeResponse smsCodeResponse);
        //注册  登录  成功
        void registerAndLoginSuccess(AccountInfo accountInfo);
    }
}
