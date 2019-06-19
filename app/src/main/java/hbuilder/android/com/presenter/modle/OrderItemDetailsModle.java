package hbuilder.android.com.presenter.modle;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.modle.MyBuyinfoResponse;
import hbuilder.android.com.modle.MyEntrustinfoResponse;
import hbuilder.android.com.modle.MySellinfoResponse;
import hbuilder.android.com.net.retrofit.BaseRetrofitClient;
import hbuilder.android.com.net.retrofit.exception.ModelExceptionMap;
import hbuilder.android.com.net.retrofit.exception.ServerExceptionMap;
import hbuilder.android.com.net.retrofit.service.ApiServices;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class OrderItemDetailsModle extends CancelOrderModle{

    /**
     * 获取我的卖出交易信息
     * @param type
     * @param minId
     * @return
     */
    public Observable<MySellinfoResponse> mySellinfo(int type, long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .mySellinfo(type,minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<MySellinfoResponse>())
                .onErrorResumeNext(new ModelExceptionMap<MySellinfoResponse>());
    }

    /**
     * 获取我的买入交易信息
     * @param type
     * @param minId
     * @return
     */
    public Observable<MyBuyinfoResponse> myBuyinfo(int type, long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .myBuyinfo(type,minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<MyBuyinfoResponse>())
                .onErrorResumeNext(new ModelExceptionMap<MyBuyinfoResponse>());
    }

    /**
     * 获取我的买入交易信息
     * @param type
     * @param minId
     * @return
     */
    public Observable<MyEntrustinfoResponse> myBillInfo(int type, long minId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .myBillInfo(type,minId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<MyEntrustinfoResponse>())
                .onErrorResumeNext(new ModelExceptionMap<MyEntrustinfoResponse>());
    }

    /**
     * 撤销委托
     * @param billId
     * @param type
     * @return
     */
    public Observable<BaseBean> cancel(long billId, int type){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .cancel(billId,type)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }

    /**
     * 订单申诉
     * @param tradeId
     * @return
     */
    public Observable<BaseBean> appeal(String tradeId){
        return BaseRetrofitClient.getInstance().create(ApiServices.class)
                .appeal(tradeId)
                .subscribeOn(Schedulers.io())
                .map(new ServerExceptionMap<BaseBean>())
                .onErrorResumeNext(new ModelExceptionMap<BaseBean>());
    }
}
