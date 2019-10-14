package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.FinanceLogResponse;

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
