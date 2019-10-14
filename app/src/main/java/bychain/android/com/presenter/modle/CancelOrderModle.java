package bychain.android.com.presenter.modle;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CancelOrderModle {
    /**
     * 取消订单
     * @param tradeId
     * @return
     */
    public Observable<BaseBean> ordercancel(String tradeId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .ordercancel(tradeId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
