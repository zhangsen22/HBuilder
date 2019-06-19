package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.SellResponse;

public interface BusinessSellContract {
    interface Presenter extends IBasePresenter {
        //出售
        void sell(long billId,double num,int type,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //出售成功
        void sellSuccess(SellResponse sellResponse);
    }
}
