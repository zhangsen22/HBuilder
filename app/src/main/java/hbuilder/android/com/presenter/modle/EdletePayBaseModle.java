package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class EdletePayBaseModle {

    /**
     * 删除收款设置
     * @param type
     * @param id
     * @param financePwd
     * @param time
     * @return
     */
    public Observable<BaseBean> detelePay(int type,long id,String financePwd,long time){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .detelePay(type,id,financePwd,time)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
