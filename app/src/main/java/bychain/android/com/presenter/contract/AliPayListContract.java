package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.PaySetupModelAliPay;

public interface AliPayListContract {

    interface Presenter extends IBasePresenter {
        void aliPayListRefresh(int type);
        void aliPayListLoadMore(int type);
        void setDefaultPayAliPay(int type, long id, String financePwd, long time);
        void detelePay(int type,long id,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        void aliPayListRefreshSuccess(PaySetupModelAliPay paySetupModelAliPay);
        void aliPayListRefreshError();
        void aliPayListLoadMoreSuccess(PaySetupModelAliPay paySetupModelAliPay);
        void aliPayListLoadMoreError();
        void setDefaultPayAliPaySuccess(BaseBean baseBean);
        void detelePayAliPaySuccess(BaseBean baseBean);
    }
}