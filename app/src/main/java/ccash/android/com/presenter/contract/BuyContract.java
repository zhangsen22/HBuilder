package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BuyResponse;

public interface BuyContract {
    interface Presenter extends IBasePresenter {
        void getBuyRefresh(long minId);
        void getBuyLoadMore(long minId);
    }
    interface View extends IBaseView<Presenter> {
        void getBuyRefreshSuccess(BuyResponse buyResponse);
        void getBuyRefreshError();
        void getBuyLoadMoreSuccess(BuyResponse buyResponse);
        void getBuyLoadMoreError();
    }
}
