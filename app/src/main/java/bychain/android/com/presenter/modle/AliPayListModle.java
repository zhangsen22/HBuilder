package bychain.android.com.presenter.modle;

import bychain.android.com.modle.PaySetupModelAliPay;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AliPayListModle extends SetDefaultPayBaseModle {


    /**
     * 获取自己的收款信息
     * 支付宝
     * @param type
     * @return
     */
    public Observable<PaySetupModelAliPay> paysetupAliPay(int type){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .paysetupAliPay(type)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<PaySetupModelAliPay>())
                .onErrorResumeNext(new ModelExceptionMap<PaySetupModelAliPay>());
    }

}
