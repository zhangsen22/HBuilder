package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelBank;

public interface IdCastPayListContract {

    interface Presenter extends IBasePresenter {
        //获取自己的收款信息  银行卡
        void paysetupBankRefresh(int type);
        void paysetupBankLoadMore(int type);
        void setDefaultPayIdCast(int type, long id, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        //获取自己的收款信息  银行卡  成功
        void paysetupBankRefreshSuccess(PaySetupModelBank paySetupModelBank);
        //获取自己的收款信息  银行卡  失败
        void paysetupBankRefreshError();
        //获取自己的收款信息  银行卡  成功
        void paysetupBankLoadMoreSuccess(PaySetupModelBank paySetupModelBank);
        //获取自己的收款信息  银行卡  失败
        void paysetupBankLoadMoreError();
        void setDefaultPayIdCastSuccess(BaseBean baseBean);
    }
}
