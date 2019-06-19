package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.SellResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BusinessSellModle {

    /**
     * 出售
     * @param billId
     * @param num
     * @param type
     * @param financePwd
     * @param time
     * @return
     */
    public Observable<SellResponse> sell(long billId,double num,int type,String financePwd,long time){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .sell(billId,num,type,financePwd,time)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<SellResponse>())
                .onErrorResumeNext(new ModelExceptionMap<SellResponse>());
    }
}
