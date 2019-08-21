package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.WebChatEditModle;
import hbuilder.android.com.modle.WechatLoginModle;
import hbuilder.android.com.modle.YnShanFuEditModle;

public interface YunShanFuEditContract {

    interface Presenter extends IBasePresenter {
        //云闪付收款设置
        void yunshanfu(long id, String name, String account, String base64Img, String financePwd, long time);
        //云闪付登陆成功上传参数
        void cloudLogin(long paymentId,String cookieUser,String username);
    }
    interface View extends IBaseView<Presenter> {
        //微信收款设置成功
        void yunShanFuSuccess(YnShanFuEditModle ynShanFuEditModle);

        //云闪付登陆成功上传参数成功
        void cloudLoginSuccess(YnShanFuEditModle ynShanFuEditModle);
    }
}
