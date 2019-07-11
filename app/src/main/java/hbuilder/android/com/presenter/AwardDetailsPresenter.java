package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.RewardLogResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.AwardDetailsContract;
import hbuilder.android.com.presenter.modle.AwardDetailsModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AwardDetailsPresenter implements AwardDetailsContract.Presenter{

    private AwardDetailsContract.View mView;
    private AwardDetailsModle mModel;

    public AwardDetailsPresenter(AwardDetailsContract.View view, AwardDetailsModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void rewardLog() {
//        mView.showLoading();
        mModel.rewardLog().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<RewardLogResponse>() {
                    @Override
                    public void onSuccess(RewardLogResponse rewardLogResponse) {
                        mView.rewardLogSuccess(rewardLogResponse);
//                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
//                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
