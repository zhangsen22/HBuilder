package bychain.android.com.presenter.modle;

import bychain.android.com.modle.UsdtPriceResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainModle {
    /**
     * 查看USDT最新价格
     * @return
     */
    public Observable<UsdtPriceResponse> usdtPrice(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .usdtPrice()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<UsdtPriceResponse>())
                .onErrorResumeNext(new ModelExceptionMap<UsdtPriceResponse>());
    }
}
