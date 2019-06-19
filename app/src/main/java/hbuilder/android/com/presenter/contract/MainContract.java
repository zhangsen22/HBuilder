package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;

public interface MainContract {
    interface Presenter extends IBasePresenter {
        //查看USDT最新价格
        void usdtPrice();
        //资产查询
        void getInfo();
    }
    interface View extends IBaseView<Presenter> {
        //查看USDT最新价格成功
        void usdtPriceSuccess(UsdtPriceResponse usdtPriceResponse);
        //资产查询成功
        void getInfoSuccess(WalletResponse walletResponse);
    }
}
