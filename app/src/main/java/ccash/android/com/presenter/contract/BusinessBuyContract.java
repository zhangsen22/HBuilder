package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BuyBusinessResponse;

public interface BusinessBuyContract {

    interface Presenter extends IBasePresenter {
        //购买
        void buy(long billId, double num, int type);
    }
    interface View extends IBaseView<Presenter> {
        //购买成功
        void buySuccess(BuyBusinessResponse buyBusinessResponse,int type);
    }
}
