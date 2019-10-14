package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BuyResponse;

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
