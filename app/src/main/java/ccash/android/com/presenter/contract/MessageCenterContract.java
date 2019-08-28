package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.MessageCenterResponse;

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
