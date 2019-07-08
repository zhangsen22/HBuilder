package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.EntrustBuyContract;
import hbuilder.android.com.presenter.modle.EntrustBuyModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class EntrustBuyPresenter implements EntrustBuyContract.Presenter{

    private EntrustBuyContract.View mView;
    private EntrustBuyModle mModel;

    public EntrustBuyPresenter(EntrustBuyContract.View view, EntrustBuyModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void putUpBuy(double price, double minNum, double maxNum, String financePwd, long time) {
        mView.showLoading();
        mModel.putUpBuy(price,minNum,maxNum,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.putUpBuySuccess(baseBean);
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
