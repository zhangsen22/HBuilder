package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.WalletResponse;

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
