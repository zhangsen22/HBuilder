package bychain.android.com.presenter;

import bychain.android.com.modle.SellResponse;
import bychain.android.com.modle.WalletResponse;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.BusinessSellContract;
import bychain.android.com.presenter.modle.BusinessSellModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BusinessSellPresenter implements BusinessSellContract.Presenter{

    private BusinessSellContract.View mView;
    private BusinessSellModle mModel;

    public BusinessSellPresenter(BusinessSellContract.View view, BusinessSellModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void sell(long billId, double num, int type, String financePwd, long time) {
        mView.showLoading();
        mModel.sell(billId,num,type,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<SellResponse>() {
                    @Override
                    public void onSuccess(SellResponse sellResponse) {
                        mView.sellSuccess(sellResponse);
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
    public void getInfo() {
//        mView.showLoading();
        mModel.getInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<WalletResponse>() {
                    @Override
                    public void onSuccess(WalletResponse walletResponse) {
                        mView.getInfoSuccess(walletResponse);
//                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
//                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
