package ccash.android.com.presenter.modle;

import ccash.android.com.modle.PaySetupModelLaCara;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LaCaraListModle extends SetDefaultPayBaseModle {


    /**
     * 获取自己的收款信息
     * 拉卡拉
     * @param type
     * @return
     */
    public Observable<PaySetupModelLaCara> paysetupLaCara(int type){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .paysetupLaCara(type)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<PaySetupModelLaCara>())
                .onErrorResumeNext(new ModelExceptionMap<PaySetupModelLaCara>());
    }

}
