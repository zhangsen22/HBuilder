package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BulletinListResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
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
}
