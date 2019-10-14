package bychain.android.com.presenter;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.MyEntrustinfoResponse;
import bychain.android.com.modle.MySellOrBuyinfoResponse;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.OrderItemDetailsContract;
import bychain.android.com.presenter.modle.OrderItemDetailsModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class OrderItemDetailsPresenter implements OrderItemDetailsContract.Presenter{

    private OrderItemDetailsContract.View mView;
    private OrderItemDetailsModle mModel;

    public OrderItemDetailsPresenter(OrderItemDetailsContract.View view, OrderItemDetailsModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void mySellinfoRefresh(int type, long minId) {
//        mView.showLoading();
        mModel.mySellinfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MySellOrBuyinfoResponse>() {
                    @Override
                    public void onSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse) {
                        mView.mySellinfoRefreshSuccess(mySellOrBuyinfoResponse);
//                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.mySellinfoRefreshError();
//                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void mySellinfoLoadMore(int type, long minId) {
        mModel.mySellinfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MySellOrBuyinfoResponse>() {
                    @Override
                    public void onSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse) {
                        mView.mySellinfoLoadMoreSuccess(mySellOrBuyinfoResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.mySellinfoLoadMoreError();
                    }
                });
    }

    @Override
    public void myBuyinfoRefresh(int type, long minId) {
//        mView.showLoading();
        mModel.myBuyinfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MySellOrBuyinfoResponse>() {
                    @Override
                    public void onSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse) {
                        mView.myBuyinfoRefreshSuccess(mySellOrBuyinfoResponse);
//                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.myBuyinfoRefreshError();
//                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void myBuyinfoLoadMore(int type, long minId) {
        mModel.myBuyinfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MySellOrBuyinfoResponse>() {
                    @Override
                    public void onSuccess(MySellOrBuyinfoResponse mySellOrBuyinfoResponse) {
                        mView.myBuyinfoLoadMoreSuccess(mySellOrBuyinfoResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.myBuyinfoLoadMoreError();
                    }
                });
    }

    @Override
    public void myBillInfoRefresh(int type, long minId) {
//        mView.showLoading();
        mModel.myBillInfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MyEntrustinfoResponse>() {
                    @Override
                    public void onSuccess(MyEntrustinfoResponse myEntrustinfoResponse) {
                        mView.myBillInfoRefreshSuccess(myEntrustinfoResponse);
//                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.myBillInfoRefreshError();
//                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void myBillInfoLoadMore(int type, long minId) {
        mModel.myBillInfo(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<MyEntrustinfoResponse>() {
                    @Override
                    public void onSuccess(MyEntrustinfoResponse myEntrustinfoResponse) {
                        mView.myBillInfoLoadMoreSuccess(myEntrustinfoResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.myBillInfoLoadMoreError();
                    }
                });
    }

    @Override
    public void cancel(long billId, int type) {
        mView.showLoading();
        mModel.cancel(billId,type).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.cancelSuccess(baseBean);
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
                        super.onFailure(ex);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
