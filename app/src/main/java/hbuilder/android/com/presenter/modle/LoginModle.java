package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.app.AccountInfo;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LoginModle {

    public Observable<AccountInfo> login(String phoneNum,String pwd,long time){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .login(phoneNum,pwd,time)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<AccountInfo>())
                .onErrorResumeNext(new ModelExceptionMap<AccountInfo>());
    }
}
