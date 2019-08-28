package ccash.android.com.presenter.contract;

import ccash.android.com.IBasePresenter;
import ccash.android.com.IBaseView;
import ccash.android.com.modle.RewardLogResponse;

public interface AwardDetailsContract {
    interface Presenter extends IBasePresenter {
        //获取奖励记录
        void rewardLog();
    }
    interface View extends IBaseView<Presenter> {
        //获取奖励记录成功
        void rewardLogSuccess(RewardLogResponse rewardLogResponse);
    }
}
