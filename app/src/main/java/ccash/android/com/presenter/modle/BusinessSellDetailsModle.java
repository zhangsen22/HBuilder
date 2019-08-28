package ccash.android.com.presenter.modle;

import ccash.android.com.modle.BaseBean;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BusinessSellDetailsModle{

    /**
     * 订单申诉
     * @param tradeId
     * @return
     */
    public Observable<BaseBean> appeal(String tradeId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .appeal(tradeId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }

    /**
     * 玩家点击放币
     * @param tradeId
     * @return
     */
    public Observable<BaseBean> fb_transfer(String tradeId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .fb_transfer(tradeId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
