package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.LaCaraWenChatListModle;

public interface IdCastContract {

    interface Presenter extends IBasePresenter {
        //银行卡收款设置
        void bank(long id,long wechatPaymentId,String bankName,String subName,String name,String account,double dailyLimit,String financePwd,long time);
        //拉卡拉下拉框获取微信列表
        void getWechatList();
    }
    interface View extends IBaseView<Presenter> {
        //银行卡收款设置成功
        void bankSuccess(String name, String account);
        //拉卡拉下拉框获取微信列表
        void getWechatListSuccess(LaCaraWenChatListModle laCaraWenChatListModle);
    }
}
