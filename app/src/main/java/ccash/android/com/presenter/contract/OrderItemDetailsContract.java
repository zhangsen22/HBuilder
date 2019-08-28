package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.MyEntrustinfoResponse;
import ccash.android.com.modle.MySellOrBuyinfoResponse;

public interface OrderItemDetailsContract {

    interface Presenter extends IBasePresenter {
        void mySellinfoRefresh(int type, long minId);
        void mySellinfoLoadMore(int type, long minId);

        void myBuyinfoRefresh(int type, long minId);
        void myBuyinfoLoadMore(int type, long minId);

        void myBillInfoRefresh(int type, long minId);
        void myBillInfoLoadMore(int type, long minId);

        void cancel(long billId, int type);

        void appeal(String tradeId);

        void ordercancel(String tradeId);
    }
    interface View extends IBaseView<Presenter> {
        void mySellinfoRefreshSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse);
        void mySellinfoRefreshError();
        void mySellinfoLoadMoreSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse);
        void mySellinfoLoadMoreError();

        void myBuyinfoRefreshSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse);
        void myBuyinfoRefreshError();
        void myBuyinfoLoadMoreSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse);
        void myBuyinfoLoadMoreError();

        void myBillInfoRefreshSuccess(MyEntrustinfoResponse myEntrustinfoResponse);
        void myBillInfoRefreshError();
        void myBillInfoLoadMoreSuccess(MyEntrustinfoResponse myEntrustinfoResponse);
        void myBillInfoLoadMoreError();

        void cancelSuccess(BaseBean baseBean);

        void appealSuccess(BaseBean baseBean);

        void ordercancelSuccess(BaseBean baseBean);
    }
}
