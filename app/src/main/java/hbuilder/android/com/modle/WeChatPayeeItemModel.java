package hbuilder.android.com.modle;

import hbuilder.android.com.ui.adapter.poweradapter.AbsSelect;

public class WeChatPayeeItemModel extends AbsSelect{

    private long id;//:1                //id
    private String name;//："放大",        //微信名，
    private String account;//:"ewrwre",        //微信号，
    private String base64Img;//:"rewr"        //微信收款二维码
    private String empBase64Img;//:"rere"        //店员收款二维码

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

    public String getEmpBase64Img() {
        return empBase64Img;
    }

    @Override
    public String toString() {
        return "WeChatPayeeItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", base64Img='" + base64Img + '\'' +
                ", empBase64Img='" + empBase64Img + '\'' +
                '}';
    }
}
