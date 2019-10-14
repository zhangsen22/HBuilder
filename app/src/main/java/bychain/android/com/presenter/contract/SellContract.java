package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BuyResponse;

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
