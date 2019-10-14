package bychain.android.com.presenter.modle;

import bychain.android.com.modle.ImageCodeResponse;
import bychain.android.com.modle.SmsCodeResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
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
