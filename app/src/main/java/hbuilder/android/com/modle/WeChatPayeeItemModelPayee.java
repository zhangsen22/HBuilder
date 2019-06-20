package hbuilder.android.com.modle;

public class WeChatPayeeItemModelPayee {

    private long id;//:1                //id
    private String name;//："放大",        //微信名，
    private String account;//:"ewrwre",        //微信号，
    private String base64Img;//:"rewr"        //微信收款二维码
    private boolean locked;
    private boolean watchStop;

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

    public boolean isLocked() {
        return locked;
    }

    public boolean isWatchStop() {
        return watchStop;
    }

    @Override
    public String toString() {
        return "WeChatPayeeItemModelPayee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", base64Img='" + base64Img + '\'' +
                ", locked=" + locked +
                ", watchStop=" + watchStop +
                '}';
    }
}
