package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BuyResponse;

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
