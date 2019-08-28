package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.FinanceLogResponse;

public interface FinancialRecordsContract {
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
