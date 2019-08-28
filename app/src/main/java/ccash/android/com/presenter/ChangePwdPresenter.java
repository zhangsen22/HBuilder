package ccash.android.com.presenter;

import ccash.android.com.modle.BaseBean;
import ccash.android.com.modle.SmsCodeResponse;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.ChangePwdContract;
import ccash.android.com.presenter.modle.ChangePwdModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangePwdPresenter implements ChangePwdContract.Presenter{

    private ChangePwdContract.View mView;
    private ChangePwdModle mModel;

    public ChangePwdPresenter(ChangePwdContract.View view, ChangePwdModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void senSenSmsCode(String phoneNum) {
        mView.showLoading();
        mModel.senSenSmsCode(phoneNum).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<SmsCodeResponse>() {
                    @Override
                    public void onSuccess(SmsCodeResponse smsCodeResponse) {
                        mView.hideLoading();
                        mView.senSenSmsCodeSuccess(smsCodeResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void changePwd(String financePwd, String smsCode) {
        mView.showLoading();
        mModel.changePwd(financePwd,smsCode).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.hideLoading();
                        mView.changePwdSuccess(baseBean);
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
