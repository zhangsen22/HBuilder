package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.UsdtPriceResponse;

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
