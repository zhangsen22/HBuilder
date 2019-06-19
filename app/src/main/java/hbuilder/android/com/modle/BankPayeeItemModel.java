package hbuilder.android.com.modle;

import hbuilder.android.com.ui.adapter.poweradapter.AbsSelect;

public class BankPayeeItemModel extends AbsSelect {
    private long id;//:1                    //id
    private String bankName;//：“”                //银行名称，
    private String subName;//:""                //所在支行，
    private String name;//:""                    //真实姓名，
    private String account;//:""                //银行卡号，
    private double dailyLimit;//:10000        //日收款限额

    public long getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getSubName() {
        return subName;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    @Override
    public String toString() {
        return "BankPayeeItemModel{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", subName='" + subName + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", dailyLimit=" + dailyLimit +
                '}';
    }
}
