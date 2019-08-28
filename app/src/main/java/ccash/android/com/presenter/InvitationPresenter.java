package ccash.android.com.presenter;

import ccash.android.com.modle.InvitationResponse;
import ccash.android.com.net.retrofit.ModelResultObserver;
import ccash.android.com.net.retrofit.exception.ModelException;
import ccash.android.com.presenter.contract.InvitationContract;
import ccash.android.com.presenter.modle.InvitationModle;
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
