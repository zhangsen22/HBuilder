package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BuyBusinessResponse;

public interface BusinessBuyContract {

    interface Presenter extends IBasePresenter {
        //购买
        void buy(long billId, double num, int type);
    }
    interface View extends IBaseView<Presenter> {
        //购买成功
        void buySuccess(BuyBusinessResponse buyBusinessResponse);
    }
}
