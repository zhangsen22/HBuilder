package hbuilder.android.com.modle;

import java.util.List;

public class AliPayPayeeModel {
    private long defalut;
    private List<AliPayPayeeItemModel> list;

    public long getDefalut() {
        return defalut;
    }

    public List<AliPayPayeeItemModel> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "AliPayPayeeModel{" +
                "defalut=" + defalut +
                ", list=" + list +
                '}';
    }
}
