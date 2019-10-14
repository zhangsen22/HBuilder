package bychain.android.com.presenter.modle;

import bychain.android.com.modle.RewardDetailResponse;
import bychain.android.com.modle.RewardLogResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RewardDetailModle {

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

    public Observable<RewardDetailResponse> rewardDetail(int type, long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .rewardDetail(type,minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<RewardDetailResponse>())
                .onErrorResumeNext(new ModelExceptionMap<RewardDetailResponse>());
    }
}
