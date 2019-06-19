package hbuilder.android.com.modle;

import hbuilder.android.com.ui.adapter.poweradapter.AbsSelect;

public class AliPayPayeeItemModel extends AbsSelect {

    private long id;//:3                //id
    private String name;//："放大",        //姓名，
    private String account;//:"ewrwre",        //支付宝账号，
    private String base64Img;//:"rewr"        //支付宝收款二维码

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getBase64Img() {
        return base64Img;
    }

    @Override
    public String toString() {
        return "AliPayPayeeItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", base64Img='" + base64Img + '\'' +
                '}';
    }
}
