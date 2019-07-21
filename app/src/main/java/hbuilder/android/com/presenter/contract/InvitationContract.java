package hbuilder.android.com.presenter.contract;

import hbuilder.android.com.IBasePresenter;
import hbuilder.android.com.IBaseView;
import hbuilder.android.com.modle.InvitationResponse;

public interface InvitationContract {
    interface Presenter extends IBasePresenter {
        //推荐奖励
        void recommendReward(long upUserId);
    }
    interface View extends IBaseView<Presenter> {
        //推荐奖励成功
        void recommendRewardSuccess(InvitationResponse invitationResponse);
        //推荐奖励失败
        void recommendRewardError();
    }
}
