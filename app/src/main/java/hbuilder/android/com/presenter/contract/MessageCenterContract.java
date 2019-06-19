package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.MessageCenterResponse;

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
