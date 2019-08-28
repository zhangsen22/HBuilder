package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.InvitationResponse;

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
