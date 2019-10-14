package bychain.android.com.presenter.modle;

import bychain.android.com.modle.InvitationResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
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
