package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelWebChat;

public interface WebChatEditContract {

    interface Presenter extends IBasePresenter {
        //微信收款设置
        void wechat(long id,String name,String account,String base64Img,String empBase64Img,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //微信收款设置成功
        void wechatSuccess(String name, String account, String base64Img);
    }
}
