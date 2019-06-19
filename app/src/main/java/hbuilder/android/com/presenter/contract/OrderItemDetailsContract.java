package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.MyBuyinfoResponse;
import hbuilder.android.com.modle.MyEntrustinfoResponse;
import hbuilder.android.com.modle.MySellinfoResponse;

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
        void mySellinfoRefreshSuccess(MySellinfoResponse mySellinfoResponse);
        void mySellinfoRefreshError();
        void mySellinfoLoadMoreSuccess(MySellinfoResponse mySellinfoResponse);
        void mySellinfoLoadMoreError();

        void myBuyinfoRefreshSuccess(MyBuyinfoResponse myBuyinfoResponse);
        void myBuyinfoRefreshError();
        void myBuyinfoLoadMoreSuccess(MyBuyinfoResponse myBuyinfoResponse);
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
