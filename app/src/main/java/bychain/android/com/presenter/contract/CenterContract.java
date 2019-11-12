package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.UserInfoResponse;

public interface CenterContract {
    interface Presenter extends IBasePresenter {
        //设置昵称
        void changeNickname(String nickname);
        void setAliOpenFlag(int aliOpenFlag);
        void getUserInfo();
    }
    interface View extends IBaseView<Presenter> {
        //设置昵称  成功
        void changeNicknameSuccess(String nickname);
        //支付宝收款设置开关  成功
        void setAliOpenFlagSuccess(int aliOpenFlag);
        //支付宝收款设置开关  失败
        void setAliOpenFlagError(int aliOpenFlag);

        void getUserInfoSuccess(UserInfoResponse userInfoResponse);
    }
}
