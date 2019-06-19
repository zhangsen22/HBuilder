package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelWebChat;

public interface WebChatListContract {

    interface Presenter extends IBasePresenter {
        void webChatListRefresh(int type);
        void webChatListLoadMore(int type);
        void setDefaultPayWebChat(int type, long id, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        void webChatListRefreshSuccess(PaySetupModelWebChat paySetupModelWebChat);
        void webChatListRefreshError();
        void webChatListLoadMoreSuccess(PaySetupModelWebChat paySetupModelWebChat);
        void webChatListLoadMoreError();
        void setDefaultPayWebChatSuccess(BaseBean baseBean);
    }
}

