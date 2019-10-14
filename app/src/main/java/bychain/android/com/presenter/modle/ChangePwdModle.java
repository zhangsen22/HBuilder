package bychain.android.com.presenter.modle;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.net.retrofit.BaseRetrofitClient;
import bychain.android.com.net.retrofit.exception.ModelExceptionMap;
import bychain.android.com.net.retrofit.exception.ServerExceptionMap;
import bychain.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ChangePwdModle extends GetSmsCodeModle{

    /**
     * 修改密码
     * @param financePwd
     * @param smsCode
     * @return
     */
    public Observable<BaseBean> changePwd(String financePwd, String smsCode){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .changePwd(financePwd,smsCode)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
