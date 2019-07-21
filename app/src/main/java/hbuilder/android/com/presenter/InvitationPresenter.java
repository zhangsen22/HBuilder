package hbuilder.android.com.presenter;

import hbuilder.android.com.modle.InvitationResponse;
import hbuilder.android.com.net.retrofit.ModelResultObserver;
import hbuilder.android.com.net.retrofit.exception.ModelException;
import hbuilder.android.com.presenter.contract.InvitationContract;
import hbuilder.android.com.presenter.modle.InvitationModle;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class InvitationPresenter implements InvitationContract.Presenter{

    private InvitationContract.View mView;
    private InvitationModle mModel;

    public InvitationPresenter(InvitationContract.View view, InvitationModle model){
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void recommendReward(long upUserId) {
        mModel.recommendReward(upUserId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelResultObserver<InvitationResponse>() {
                    @Override
                    public void onSuccess(InvitationResponse invitationResponse) {
                        mView.recommendRewardSuccess(invitationResponse);
                    }

                    @Override
                    public void onFailure(ModelException ex) {
                        super.onFailure(ex);
                        mView.recommendRewardError();
                    }
                });
    }

    @Override
    public void starLoadData() {

    }
}
