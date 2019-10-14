package bychain.android.com.presenter;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.BusinessSellDetailsContract;
import bychain.android.com.presenter.modle.BusinessSellDetailsModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BusinessSellDetailsPresenter  implements BusinessSellDetailsContract.Presenter{
    private BusinessSellDetailsContract.View mView;
    private BusinessSellDetailsModle mModel;

    public BusinessSellDetailsPresenter(BusinessSellDetailsContract.View view, BusinessSellDetailsModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void appeal(String tradeId) {
        mView.showLoading();
        mModel.appeal(tradeId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.appealSuccess(baseBean);
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
    public void fb_transfer(String tradeId) {
        mView.showLoading();
        mModel.fb_transfer(tradeId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.fb_transferSuccess(baseBean);
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
