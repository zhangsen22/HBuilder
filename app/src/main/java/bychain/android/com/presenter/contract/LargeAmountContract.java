package bychain.android.com.presenter.contract;


import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.LargeAmountResponse;

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
