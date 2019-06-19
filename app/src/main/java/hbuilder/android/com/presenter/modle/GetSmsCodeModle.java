package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.ImageCodeResponse;
import hbuilder.android.com.modle.SmsCodeResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GetSmsCodeModle {

    /**
     * 获取图片验证码
     * @param phoneNum
     * @return
     */
    public Observable<ImageCodeResponse> getImageCode(String phoneNum){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getImageCode(phoneNum)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<ImageCodeResponse>())
                .onErrorResumeNext(new ModelExceptionMap<ImageCodeResponse>());
    }


    /**
     * 发送验证码接口
     * @param phoneNum
     * @return
     */
    public Observable<SmsCodeResponse> senSenSmsCode(String phoneNum){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .senSenSmsCode(phoneNum)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<SmsCodeResponse>())
                .onErrorResumeNext(new ModelExceptionMap<SmsCodeResponse>());
    }
}
