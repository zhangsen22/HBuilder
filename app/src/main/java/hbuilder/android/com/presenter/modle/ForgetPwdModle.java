package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPwdModle extends GetSmsCodeModle{

    /**
     * 忘记密码
     * @param pwd
     * @param phoneNum
     * @param imageCode
     * @param smsCode
     * @return
     */
    public Observable<BaseBean> forgetPwd(String pwd, String phoneNum, String imageCode, String smsCode){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .forgetPwd(pwd,phoneNum,imageCode,smsCode)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
