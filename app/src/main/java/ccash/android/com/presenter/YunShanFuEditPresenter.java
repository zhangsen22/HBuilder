package ccash.android.com.presenter;

import ccash.android.com.modle.YnShanFuEditModle;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.YunShanFuEditContract;
import ccash.android.com.presenter.modle.PaySettingModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class YunShanFuEditPresenter implements YunShanFuEditContract.Presenter{

    private YunShanFuEditContract.View mView;
    private PaySettingModle mModel;

    public YunShanFuEditPresenter(YunShanFuEditContract.View view, PaySettingModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void yunshanfu(long id, String name, String account, String base64Img, String financePwd, long time) {
        mView.showLoading();
        mModel.cloud(id,name,account,base64Img,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<YnShanFuEditModle>() {
                    @Override
                    public void onSuccess(YnShanFuEditModle ynShanFuEditModle) {
                        mView.yunShanFuSuccess(ynShanFuEditModle);
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
    public void starLoadData() {

    }

}
