package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.InvitationResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class InvitationModle {

    /**
     * 推荐奖励
     * @param upUserId
     * @return
     */
    public Observable<InvitationResponse> recommendReward(long upUserId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .recommendReward(upUserId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<InvitationResponse>())
                .onErrorResumeNext(new ModelExceptionMap<InvitationResponse>());
    }
}
