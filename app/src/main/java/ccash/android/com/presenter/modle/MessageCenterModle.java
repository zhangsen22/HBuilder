package ccash.android.com.presenter.modle;

import ccash.android.com.modle.MessageCenterResponse;
import ccash.android.com.net.retrofit.BaseRetrofitClient;
import ccash.android.com.net.retrofit.exception.ModelExceptionMap;
import ccash.android.com.net.retrofit.exception.ServerExceptionMap;
import ccash.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MessageCenterModle {

    /**
     * 获取消息中心消息
     * @param minId
     * @return
     */
    public Observable<MessageCenterResponse> msgCenter(long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .msgCenter(minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<MessageCenterResponse>())
                .onErrorResumeNext(new ModelExceptionMap<MessageCenterResponse>());
    }
}
