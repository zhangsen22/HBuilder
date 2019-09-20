package ccash.android.com.presenter.modle;

import ccash.android.com.modle.BulletinListResponse;
import ccash.android.com.modle.LargeAmountResponse;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BusinessContainerModle {

    /**
     * 公告
     * @return
     */
    public Observable<BulletinListResponse> bulletinList(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .bulletinList()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BulletinListResponse>())
                .onErrorResumeNext(new ModelExceptionMap<BulletinListResponse>());
    }

    /**
     * 获取大额提现订单
     * @param minId
     * @return
     */
    public Observable<LargeAmountResponse> getHugeBillinfo(long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getHugeBillinfo(minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<LargeAmountResponse>())
                .onErrorResumeNext(new ModelExceptionMap<LargeAmountResponse>());
    }
}
