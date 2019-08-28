package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BuyResponse;

public interface SellContract {

    interface Presenter extends IBasePresenter {
        void getSellRefresh(long minId);
        void getSellLoadMore(long minId);
    }
    interface View extends IBaseView<Presenter> {
        void getSellRefreshSuccess(BuyResponse buyResponse);
        void getSellRefreshError();
        void getSellLoadMoreSuccess(BuyResponse buyResponse);
        void getSellLoadMoreError();
    }
}
