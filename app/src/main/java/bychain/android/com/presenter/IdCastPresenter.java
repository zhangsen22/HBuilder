package bychain.android.com.presenter;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.LaCaraWenChatListModle;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.IdCastContract;
import bychain.android.com.presenter.modle.PaySettingModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class IdCastPresenter implements IdCastContract.Presenter{

    private IdCastContract.View mView;
    private PaySettingModle mModel;

    public IdCastPresenter(IdCastContract.View view, PaySettingModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void bank(long id,long wechatPaymentId,String bankName, String subName, final String name, final String account, double dailyLimit, String financePwd, long time) {
        mView.showLoading();
        mModel.bank(id,wechatPaymentId,bankName,subName,name,account,dailyLimit,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.bankSuccess(name,account);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getWechatList() {
        mModel.getWechatList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<LaCaraWenChatListModle>() {
                    @Override
                    public void onSuccess(LaCaraWenChatListModle laCaraWenChatListModle) {
                        mView.getWechatListSuccess(laCaraWenChatListModle);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
