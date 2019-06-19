package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.UsdtPriceResponse;

public interface EntrustSaleContract {

    interface Presenter extends IBasePresenter {
        //委托出售
        void putUpSell(double price, double minNum, double maxNum, boolean supporAli, boolean supportWechat, boolean supportBank, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        //委托出售成功
        void putUpSellSuccess(BaseBean baseBean);
    }
}
