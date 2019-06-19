package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.RewardDetailResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RewardDetailModle {

    public Observable<RewardDetailResponse> rewardDetail(int type, long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .rewardDetail(type,minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<RewardDetailResponse>())
                .onErrorResumeNext(new ModelExceptionMap<RewardDetailResponse>());
    }
}
