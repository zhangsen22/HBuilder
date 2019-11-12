package bychain.android.com.presenter.modle;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.UserInfoResponse;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CenterModle {

    /**
     * 设置昵称
     * @param nickname
     * @return
     */
    public Observable<BaseBean> changeNickname(String nickname){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .changeNickname(nickname)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }

    /**
     * 支付宝收款设置开关
     * @param aliOpenFlag
     * @return
     */
    public Observable<BaseBean> setAliOpenFlag(int aliOpenFlag){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .setAliOpenFlag(aliOpenFlag)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }

    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoResponse> getUserInfo(){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<UserInfoResponse>())
                .onErrorResumeNext(new ModelExceptionMap<UserInfoResponse>());
    }
}
