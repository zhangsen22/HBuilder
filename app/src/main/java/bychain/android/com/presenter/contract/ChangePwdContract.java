package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.SmsCodeResponse;

public interface ChangePwdContract {

    interface Presenter extends IBasePresenter {
        //发送验证码
        void senSenSmsCode(String phoneNum);
        //修改密码
        void changePwd(String financePwd, String smsCode);
    }
    interface View extends IBaseView<Presenter> {
        //发送验证码成功
        void senSenSmsCodeSuccess(SmsCodeResponse smsCodeResponse);
        //修改密码
        void changePwdSuccess(BaseBean baseBean);
    }
}
