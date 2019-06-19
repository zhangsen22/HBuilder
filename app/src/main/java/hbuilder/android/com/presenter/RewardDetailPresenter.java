package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.RewardDetailResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.RewardDetailContract;
import hbuilder.android.com.presenter.modle.RewardDetailModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RewardDetailPresenter implements RewardDetailContract.Presenter{

    private RewardDetailContract.View mView;

    private RewardDetailModle mModel;

    public RewardDetailPresenter(RewardDetailContract.View view, RewardDetailModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void rewardDetailRefresh(int type, long minId) {
        mView.showLoading();
        mModel.rewardDetail(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<RewardDetailResponse>() {
                    @Override
                    public void onSuccess(RewardDetailResponse rewardDetailResponse) {
                        mView.hideLoading();
                        mView.rewardDetailRefreshSuccess(rewardDetailResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                        mView.rewardDetailRefreshError();
                    }
                });
    }

    @Override
    public void rewardDetailLoadMore(int type, long minId) {
        mView.showLoading();
        mModel.rewardDetail(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<RewardDetailResponse>() {
                    @Override
                    public void onSuccess(RewardDetailResponse rewardDetailResponse) {
                        mView.hideLoading();
                        mView.rewardDetailLoadMoreSuccess(rewardDetailResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.hideLoading();
                        mView.rewardDetailLoadMoreError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
