package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelWebChat;
import hbuilder.android.com.modle.WeChatPayeeItemModelPayee;
import hbuilder.android.com.modle.WebChatEditModle;

public interface WebChatListContract {

    interface Presenter extends IBasePresenter {
        void webChatListRefresh(int type);
        void webChatListLoadMore(int type);
        void setDefaultPayWebChat(int type, long id, String financePwd, long time);
        void detelePay(int type,long id,String financePwd,long time);
        //微信收款设置
        void reWechat(long id, WeChatPayeeItemModelPayee payee);
    }
    interface View extends IBaseView<Presenter> {
        void webChatListRefreshSuccess(PaySetupModelWebChat paySetupModelWebChat);
        void webChatListRefreshError();
        void webChatListLoadMoreSuccess(PaySetupModelWebChat paySetupModelWebChat);
        void webChatListLoadMoreError();
        void setDefaultPayWebChatSuccess(BaseBean baseBean);
        void deteleWebChatSuccess(BaseBean baseBean);
        //微信收款设置成功
        void reWechatSuccess(WebChatEditModle webChatEditModle, WeChatPayeeItemModelPayee payee);
    }
}

