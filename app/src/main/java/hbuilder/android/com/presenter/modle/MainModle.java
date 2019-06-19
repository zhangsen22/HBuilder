package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.UsdtPriceResponse;
import hbuilder.android.com.modle.WalletResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
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

    /**
     * 资产查询
     * @return
     */
    public Observable<WalletResponse> getInfo(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getInfo()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<WalletResponse>())
                .onErrorResumeNext(new ModelExceptionMap<WalletResponse>());
    }
}
