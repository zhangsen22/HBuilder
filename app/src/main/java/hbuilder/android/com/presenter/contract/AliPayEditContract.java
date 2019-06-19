package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;

public interface AliPayEditContract {

    interface Presenter extends IBasePresenter {
        //支付宝收款设置
        void ali(String name, String account, String base64Img, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        //支付宝收款设置成功
        void aliSuccess(String name, String account, String base64Img);
    }
}
