package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;

public interface MainContract {
    interface Presenter extends IBasePresenter {
        //查看USDT最新价格
        void usdtPrice();
    }
    interface View extends IBaseView<Presenter> {
        //查看USDT最新价格成功
        void usdtPriceSuccess(UsdtPriceResponse usdtPriceResponse);
        void usdtPriceError();
    }
}
