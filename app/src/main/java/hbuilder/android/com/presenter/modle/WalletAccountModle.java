package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.FinanceLogResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WalletAccountModle {

    /**
     * 获取财务记录
     * @param minId
     * @return
     */
    public Observable<FinanceLogResponse> financeLog(long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .financeLog(minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<FinanceLogResponse>())
                .onErrorResumeNext(new ModelExceptionMap<FinanceLogResponse>());
    }
}
