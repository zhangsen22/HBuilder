package hbuilder.android.com.modle;

import java.util.List;

public class BankPayeeModel {

    private long defalut;
    private List<BankPayeeItemModel> list;

    public long getDefalut() {
        return defalut;
    }

    public List<BankPayeeItemModel> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "BankPayeeModel{" +
                "defalut=" + defalut +
                ", list=" + list +
                '}';
    }
}
