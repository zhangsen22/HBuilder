package bychain.android.com.presenter.modle;

import bychain.android.com.modle.BulletinListResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
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
