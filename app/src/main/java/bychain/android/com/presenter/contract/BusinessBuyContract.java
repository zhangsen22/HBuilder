package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BuyBusinessResponse;

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
