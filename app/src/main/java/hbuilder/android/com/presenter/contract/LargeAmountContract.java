package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.LargeAmountResponse;

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
