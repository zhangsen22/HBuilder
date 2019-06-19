package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import hbuilder.android.com.presenter.modle.CancelOrderModle;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BusinessBuyDetailsModle extends CancelOrderModle {

    /**
     * 玩家点击我已付款
     * @param tradeId
     * @return
     */
    public Observable<BaseBean> manualPay(String tradeId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .manualPay(tradeId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
