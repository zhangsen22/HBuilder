package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BalancePassWordModle extends GetSmsCodeModle{

    /**
     * 修改资金密码
     * @param financePwd
     * @param smsCode
     * @return
     */
    public Observable<BaseBean> changeFinancePwd(String financePwd,String smsCode){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .changeFinancePwd(financePwd,smsCode)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
