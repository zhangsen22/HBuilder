package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.YnShanFuEditModle;

public interface YunShanFuLoginContract {

    interface Presenter extends IBasePresenter {
        //云闪付登陆成功上传参数
        void cloudLogin(long paymentId, String cookieUser, String username);
    }
    interface View extends IBaseView<Presenter> {
        //云闪付登陆成功上传参数成功
        void cloudLoginSuccess(YnShanFuEditModle ynShanFuEditModle);
    }
}
