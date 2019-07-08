package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;

public interface EntrustBuyContract {

    interface Presenter extends IBasePresenter {
        //委托购买
        void putUpBuy(double price, double minNum, double maxNum, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        //委托购买
        void putUpBuySuccess(BaseBean baseBean);
    }
}
