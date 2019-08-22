package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.YnShanFuEditModle;

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
