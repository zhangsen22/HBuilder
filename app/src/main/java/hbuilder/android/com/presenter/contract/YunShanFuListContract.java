package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.PaySetupModelYunShanFu;

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

