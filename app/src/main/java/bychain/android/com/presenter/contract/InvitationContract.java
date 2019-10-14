package bychain.android.com.presenter.contract;

import bychain.android.com.IBasePresenter;
import bychain.android.com.IBaseView;
import bychain.android.com.modle.InvitationResponse;

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
