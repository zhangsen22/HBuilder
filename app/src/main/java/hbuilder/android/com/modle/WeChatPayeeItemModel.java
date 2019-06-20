package hbuilder.android.com.modle;

import hbuilder.android.com.ui.adapter.poweradapter.AbsSelect;

public class WeChatPayeeItemModel extends AbsSelect{

    private WeChatPayeeItemModelPayee payee;
    private double leftMoney;
    private long leftTimes;

    public WeChatPayeeItemModelPayee getPayee() {
        return payee;
    }

    public double getLeftMoney() {
        return leftMoney;
    }

    public long getLeftTimes() {
        return leftTimes;
    }

    @Override
    public String toString() {
        return "WeChatPayeeItemModel{" +
                "payee=" + payee +
                ", leftMoney=" + leftMoney +
                ", leftTimes=" + leftTimes +
                '}';
    }
}
