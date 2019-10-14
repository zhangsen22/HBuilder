package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.WalletResponse;

public interface EntrustSaleContract {

    interface Presenter extends IBasePresenter {
        //委托出售
        void putUpSell(double price, double minNum, double maxNum, boolean supporAli, boolean supportWechat, boolean supportBank,boolean supportCloud,boolean supportLakala, String financePwd, long time);
        //资产查询
        void getInfo();
    }
    interface View extends IBaseView<Presenter> {
        //委托出售成功
        void putUpSellSuccess(BaseBean baseBean);
        //资产查询成功
        void getInfoSuccess(WalletResponse walletResponse);
    }
}
