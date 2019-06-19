package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.PaySetupModelWebChat;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WebChatListModle extends SetDefaultPayBaseModle {

    /**
     * 获取自己的收款信息
     * 微信
     * @param type
     * @return
     */
    public Observable<PaySetupModelWebChat> paysetupWebChat(int type){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .paysetupWebChat(type)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<PaySetupModelWebChat>())
                .onErrorResumeNext(new ModelExceptionMap<PaySetupModelWebChat>());
    }
}
