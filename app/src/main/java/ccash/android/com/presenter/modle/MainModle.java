package ccash.android.com.presenter.modle;

import ccash.android.com.modle.UsdtPriceResponse;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
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
