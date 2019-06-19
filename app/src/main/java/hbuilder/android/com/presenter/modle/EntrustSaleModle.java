package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class EntrustSaleModle {

    /**
     * 委托出售
     * @param price
     * @param minNum
     * @param maxNum
     * @param supporAli
     * @param supportWechat
     * @param supportBank
     * @param financePwd
     * @param time
     * @return
     */
    public Observable<BaseBean> putUpSell(double price, double minNum, double maxNum, boolean supporAli, boolean supportWechat, boolean supportBank, String financePwd, long time){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .putUpSell(price,minNum,maxNum,supporAli,supportWechat,supportBank,financePwd,time)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
