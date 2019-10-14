package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.YnShanFuEditModle;

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
