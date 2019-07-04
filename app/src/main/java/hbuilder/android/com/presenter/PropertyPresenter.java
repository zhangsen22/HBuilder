package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.PropertyContract;
import hbuilder.android.com.presenter.modle.PropertyModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class PropertyPresenter implements PropertyContract.Presenter{

    private PropertyContract.View mView;
    private PropertyModle mModel;

    public PropertyPresenter(PropertyContract.View view, PropertyModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void usdtPrice() {
        mView.showLoading();
        mModel.usdtPrice().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<UsdtPriceResponse>() {
                    @Override
                    public void onSuccess(UsdtPriceResponse usdtPriceResponse) {
                        mView.usdtPriceSuccess(usdtPriceResponse);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.usdtPriceError();
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getInfo() {
        mView.showLoading();
        mModel.getInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<WalletResponse>() {
                    @Override
                    public void onSuccess(WalletResponse walletResponse) {
                        mView.getInfoSuccess(walletResponse);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
