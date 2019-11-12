package bychain.android.com.presenter;

import bychain.android.com.modle.BaseBean;
import bychain.android.com.modle.UserInfoResponse;
import bychain.android.com.net.retrofit.ModelResultObserver;
import bychain.android.com.net.retrofit.exception.ModelException;
import bychain.android.com.presenter.contract.CenterContract;
import bychain.android.com.presenter.modle.CenterModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CenterPresenter implements CenterContract.Presenter{

    private CenterContract.View mView;
    private CenterModle mModel;

    public CenterPresenter(CenterContract.View view, CenterModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void changeNickname(final String nickname) {
        mView.showLoading();
        mModel.changeNickname(nickname).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.changeNicknameSuccess(nickname);
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
    public void setAliOpenFlag(int aliOpenFlag) {
        mView.showLoading();
        mModel.setAliOpenFlag(aliOpenFlag).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        mView.setAliOpenFlagSuccess(aliOpenFlag);
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
    public void getUserInfo() {
        mModel.getUserInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<UserInfoResponse>() {
                    @Override
                    public void onSuccess(UserInfoResponse baseBean) {
                        mView.getUserInfoSuccess(baseBean);
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
