package ccash.android.com.presenter.modle;

import ccash.android.com.modle.InvitationResponse;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
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
