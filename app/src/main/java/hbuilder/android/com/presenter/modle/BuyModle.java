package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BuyResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BuyModle {

    /**
     * 获取挂取的卖单信息
     * @param minId
     * @return
     */
    public Observable<BuyResponse> getSellinfo(long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getSellinfo(minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BuyResponse>())
                .onErrorResumeNext(new ModelExceptionMap<BuyResponse>());
    }

    /**
     * 获取挂取的卖单信息
     * @param minId
     * @return
     */
    public Observable<BuyResponse> getBuyinfo(long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getBuyinfo(minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BuyResponse>())
                .onErrorResumeNext(new ModelExceptionMap<BuyResponse>());
    }
}
