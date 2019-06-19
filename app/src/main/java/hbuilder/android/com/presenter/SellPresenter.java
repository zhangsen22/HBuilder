package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.BuyResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.SellContract;
import hbuilder.android.com.presenter.modle.BuyModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SellPresenter implements SellContract.Presenter{

    protected SellContract.View mView;
    private BuyModle mModel;

    public SellPresenter(SellContract.View view, BuyModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void getSellRefresh(long minId) {
        mView.showLoading();
        mModel.getBuyinfo(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BuyResponse>() {
                    @Override
                    public void onSuccess(BuyResponse buyResponse) {
                        mView.getSellRefreshSuccess(buyResponse);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.getSellRefreshError();
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getSellLoadMore(long minId) {
        mView.showLoading();
        mModel.getBuyinfo(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BuyResponse>() {
                    @Override
                    public void onSuccess(BuyResponse buyResponse) {
                        mView.getSellLoadMoreSuccess(buyResponse);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.getSellLoadMoreError();
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
