package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;

public interface TiBiContract {

    interface Presenter extends IBasePresenter {
        //提币
        void withdraw(String addr,double num,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //提币  成功
        void withdrawSuccess(BaseBean baseBean);
    }
}
