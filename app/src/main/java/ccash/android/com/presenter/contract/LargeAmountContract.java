package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.LargeAmountResponse;

public interface LargeAmountContract {

    interface Presenter extends IBasePresenter {
        void getHugeBillinfoRefresh(long minId);
        void getHugeBillinfoLoadMore(long minId);
    }
    interface View extends IBaseView<Presenter> {
        void getHugeBillinfoRefreshSuccess(LargeAmountResponse largeAmountResponse);
        void getHugeBillinfoRefreshError();
        void getHugeBillinfoLoadMoreSuccess(LargeAmountResponse largeAmountResponse);
        void getHugeBillinfoLoadMoreError();
    }
}
