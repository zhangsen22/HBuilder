package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.PaySetupModelYunShanFu;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class YunShanFuListModle extends SetDefaultPayBaseModle {


    /**
     * 获取自己的收款信息
     * 云闪付
     * @param type
     * @return
     */
    public Observable<PaySetupModelYunShanFu> paysetupYunShanFu(int type){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .paysetupYunShanFu(type)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<PaySetupModelYunShanFu>())
                .onErrorResumeNext(new ModelExceptionMap<PaySetupModelYunShanFu>());
    }

}
