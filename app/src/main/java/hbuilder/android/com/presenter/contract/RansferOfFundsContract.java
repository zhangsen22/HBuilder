package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.WalletResponse;

public interface RansferOfFundsContract {
    interface Presenter extends IBasePresenter {
        //资金划转
        void transfer(int type,double num,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //资金划转成功
        void transferSuccess(WalletResponse walletResponse);
    }
}
