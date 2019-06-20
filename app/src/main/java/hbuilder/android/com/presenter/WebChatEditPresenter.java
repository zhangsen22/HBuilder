package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.BaseBean;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.WebChatEditContract;
import hbuilder.android.com.presenter.modle.PaySettingModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WebChatEditPresenter implements WebChatEditContract.Presenter{

    private WebChatEditContract.View mView;
    private PaySettingModle mModel;

    public WebChatEditPresenter(WebChatEditContract.View view, PaySettingModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void starLoadData() {

    }

    @Override
    public void wechat(long id,final String name, final String account, final String base64Img, String empBase64Img, String financePwd, long time) {
        mView.showLoading();
        mModel.wechat(id,name,account,base64Img,empBase64Img,financePwd,time).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.wechatSuccess(name,account,base64Img);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                    }
                });
    }
}
