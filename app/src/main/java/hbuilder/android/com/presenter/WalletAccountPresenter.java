package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.FinanceLogResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.WalletAccountContract;
import hbuilder.android.com.presenter.modle.WalletAccountModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WalletAccountPresenter implements WalletAccountContract.Presenter{

    private WalletAccountContract.View mView;
    private WalletAccountModle mModel;

    public WalletAccountPresenter(WalletAccountContract.View view, WalletAccountModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void financeLogRefresh(long minId) {
        mView.showLoading();
        mModel.financeLog(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<FinanceLogResponse>() {
                    @Override
                    public void onSuccess(FinanceLogResponse financeLogResponse) {
                        mView.financeLogRefreshSuccess(financeLogResponse);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.financeLogRefreshError();
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void financeLogLoadMore(long minId) {
        mModel.financeLog(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<FinanceLogResponse>() {
                    @Override
                    public void onSuccess(FinanceLogResponse financeLogResponse) {
                        mView.financeLogLoadMoreSuccess(financeLogResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.financeLogLoadMoreError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
