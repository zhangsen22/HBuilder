package bychain.android.com.presenter;

import bychain.android.com.modle.BuyResponse;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.SellContract;
import bychain.android.com.presenter.modle.BuyModle;
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
        mModel.getBuyinfo(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BuyResponse>() {
                    @Override
                    public void onSuccess(BuyResponse buyResponse) {
                        mView.getSellRefreshSuccess(buyResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.getSellRefreshError();
                    }
                });
    }

    @Override
    public void getSellLoadMore(long minId) {
        mModel.getBuyinfo(minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BuyResponse>() {
                    @Override
                    public void onSuccess(BuyResponse buyResponse) {
                        mView.getSellLoadMoreSuccess(buyResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.getSellLoadMoreError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
