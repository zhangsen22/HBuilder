package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.PaySetupModelYunShanFu;

public interface YunShanFuListContract {

    interface Presenter extends IBasePresenter {
        void yunShanFuListRefresh(int type);
        void yunShanFuListLoadMore(int type);
        void setDefaultPayyunShanFu(int type, long id, String financePwd, long time);
        void detelePay(int type, long id, String financePwd, long time);
    }
    interface View extends IBaseView<Presenter> {
        void yunShanFuListRefreshSuccess(PaySetupModelYunShanFu paySetupModelYunShanFu);
        void yunShanFuListRefreshError();
        void yunShanFuListLoadMoreSuccess(PaySetupModelYunShanFu paySetupModelYunShanFu);
        void yunShanFuListLoadMoreError();
        void setDefaultPayYunShanFuSuccess(BaseBean baseBean);
        void deteleYunShanFuSuccess(BaseBean baseBean);
    }
}

