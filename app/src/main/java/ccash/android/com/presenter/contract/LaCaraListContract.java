package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.PaySetupModelLaCara;
import ccash.android.com.modle.PaySetupModelYunShanFu;

public interface LaCaraListContract {

    interface Presenter extends IBasePresenter {
        void laCaraListRefresh(int type);
        void laCaraListLoadMore(int type);
        void setDefaultPaylaCara(int type, long id, String financePwd, long time);
        void detelePay(int type, long id, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        void laCaraListRefreshSuccess(PaySetupModelLaCara paySetupModelLaCara);
        void laCaraListRefreshError();
        void laCaraListLoadMoreSuccess(PaySetupModelLaCara paySetupModelLaCara);
        void laCaraListLoadMoreError();
        void setDefaultPayLaCaraSuccess(BaseBean baseBean);
        void deteleLaCaraSuccess(BaseBean baseBean);
    }
}

