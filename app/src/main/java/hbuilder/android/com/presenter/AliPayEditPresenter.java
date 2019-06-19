package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.AliPayEditContract;
import hbuilder.android.com.presenter.modle.PaySettingModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AliPayEditPresenter implements AliPayEditContract.Presenter{

    private AliPayEditContract.View mView;
    private PaySettingModle mModel;

    public AliPayEditPresenter(AliPayEditContract.View view, PaySettingModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void ali(final String name, final String account, final String base64Img, String financePwd, long time) {
        mView.showLoading();
        mModel.ali(name,account,base64Img,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.aliSuccess(name,account,base64Img);
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
