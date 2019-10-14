package bychain.android.com.presenter.modle;

import bychain.android.com.modle.WalletResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WalletModle {
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
