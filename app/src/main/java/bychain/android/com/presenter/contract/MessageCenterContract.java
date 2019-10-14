package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.MessageCenterResponse;

public interface MessageCenterContract {

    interface Presenter extends IBasePresenter {
        void msgCenterRefresh(long minId);
        void msgCenterLoadMore(long minId);
    }
    interface View extends IBaseView<Presenter> {
        void msgCenterRefreshSuccess(MessageCenterResponse messageCenterResponse);
        void msgCenterRefreshError();
        void msgCenterLoadMoreSuccess(MessageCenterResponse messageCenterResponse);
        void msgCenterLoadMoreError();
    }
}
