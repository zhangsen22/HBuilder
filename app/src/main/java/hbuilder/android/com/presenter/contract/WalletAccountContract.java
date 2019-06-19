package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.FinanceLogResponse;

public interface WalletAccountContract {
    interface Presenter extends IBasePresenter {
        void financeLogRefresh(long minId);
        void financeLogLoadMore(long minId);
    }
    interface View extends IBaseView<Presenter> {
        void financeLogRefreshSuccess(FinanceLogResponse financeLogResponse);
        void financeLogRefreshError();
        void financeLogLoadMoreSuccess(FinanceLogResponse financeLogResponse);
        void financeLogLoadMoreError();
    }
}
