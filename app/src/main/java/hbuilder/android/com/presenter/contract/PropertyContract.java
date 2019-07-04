package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;

public interface PropertyContract {
    interface Presenter extends IBasePresenter {
        //资产查询
        void getInfo();
    }
    interface View extends IBaseView<Presenter> {

        //资产查询成功
        void getInfoSuccess(WalletResponse walletResponse);
    }
}
