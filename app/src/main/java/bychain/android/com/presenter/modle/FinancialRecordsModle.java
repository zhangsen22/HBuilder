package bychain.android.com.presenter.modle;

import bychain.android.com.modle.FinanceLogResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class FinancialRecordsModle {

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
