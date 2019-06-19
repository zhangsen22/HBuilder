package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BuyResponse;

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
