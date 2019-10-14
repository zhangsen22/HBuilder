package bychain.android.com.presenter.modle;

import bychain.android.com.app.AccountInfo;
import bychain.android.com.modle.DomainModel;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LoginModle {

    /**
     * 登录
     * @param phoneNum
     * @param pwd
     * @param time
     * @return
     */
    public Observable<AccountInfo> login(String phoneNum,String pwd,long time){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .login(phoneNum,pwd,time)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<AccountInfo>())
                .onErrorResumeNext(new ModelExceptionMap<AccountInfo>());
    }

    /**
     * 获取多域名
     * @return
     */
    public Observable<DomainModel> getDomainName(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getDomainName()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<DomainModel>())
                .onErrorResumeNext(new ModelExceptionMap<DomainModel>());
    }
}
