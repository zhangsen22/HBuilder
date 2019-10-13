package ccash.android.com.presenter;

import ccash.android.com.modle.RewardDetailResponse;
import ccash.android.com.modle.RewardLogResponse;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.RewardDetailContract;
import ccash.android.com.presenter.modle.RewardDetailModle;
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
    public void rewardDetailRefresh(int type, long minId) {
//        mView.showLoading();
        mModel.rewardDetail(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<RewardDetailResponse>() {
                    @Override
                    public void onSuccess(RewardDetailResponse rewardDetailResponse) {
//                        mView.hideLoading();
                        mView.rewardDetailRefreshSuccess(rewardDetailResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
//                        mView.hideLoading();
                        mView.rewardDetailRefreshError();
                    }
                });
    }

    @Override
    public void rewardDetailLoadMore(int type, long minId) {
        mModel.rewardDetail(type,minId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<RewardDetailResponse>() {
                    @Override
                    public void onSuccess(RewardDetailResponse rewardDetailResponse) {
                        mView.rewardDetailLoadMoreSuccess(rewardDetailResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.rewardDetailLoadMoreError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
