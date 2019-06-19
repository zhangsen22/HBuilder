package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.RewardLogResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TradingAccountModle {

    /**
     * 获取奖励记录
     * @return
     */
    public Observable<RewardLogResponse> rewardLog(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .rewardLog()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<RewardLogResponse>())
                .onErrorResumeNext(new ModelExceptionMap<RewardLogResponse>());
    }
}
