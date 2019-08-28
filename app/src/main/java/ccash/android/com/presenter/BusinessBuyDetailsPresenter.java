package ccash.android.com.presenter;

import ccash.android.com.modle.BaseBean;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.BusinessBuyDetailsContract;
import ccash.android.com.presenter.modle.BusinessBuyDetailsModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BusinessBuyDetailsPresenter implements BusinessBuyDetailsContract.Presenter{

    private BusinessBuyDetailsContract.View mView;
    private BusinessBuyDetailsModle mModel;

    public BusinessBuyDetailsPresenter(BusinessBuyDetailsContract.View view, BusinessBuyDetailsModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void ordercancel(String tradeId) {
        mView.showLoading();
        mModel.ordercancel(tradeId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.ordercancelSuccess(baseBean);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        int mCode = ex.mCode;
                        if(mCode == 190){
                            mView.goOrderMySellComplete();
                        }else {
                            super.onFailure(ex);
                        }
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void manualPay(String tradeId) {
        mView.showLoading();
        mModel.manualPay(tradeId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.manualPaySuccess(baseBean);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        int mCode = ex.mCode;
                        if(mCode == 190){
                            mView.goOrderMySellComplete();
                        }else {
                            super.onFailure(ex);
                        }
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
