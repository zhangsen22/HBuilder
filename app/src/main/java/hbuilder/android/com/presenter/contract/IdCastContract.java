package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelBank;

public interface IdCastContract {

    interface Presenter extends IBasePresenter {
        //银行卡收款设置
        void bank(long id,String bankName,String subName,String name,String account,double dailyLimit,String financePwd,long time);
    }
    interface View extends IBaseView<Presenter> {
        //银行卡收款设置成功
        void bankSuccess(String name, String account);
    }
}
