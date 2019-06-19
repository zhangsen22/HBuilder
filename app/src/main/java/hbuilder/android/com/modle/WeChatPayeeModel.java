package hbuilder.android.com.modle;

import java.util.List;

public class WeChatPayeeModel {
    private long defalut;
    private List<WeChatPayeeItemModel> list;

    public long getDefalut() {
        return defalut;
    }

    public List<WeChatPayeeItemModel> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "WeChatPayeeModel{" +
                "defalut=" + defalut +
                ", list=" + list +
                '}';
    }
}
