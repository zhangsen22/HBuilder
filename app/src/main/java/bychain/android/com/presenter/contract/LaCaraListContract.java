package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.PaySetupModelLaCara;

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

