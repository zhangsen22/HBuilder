package hbuilder.android.com.modle;

public class LargeAmountItem {

    private long id;//挂单id
    private long userId;//挂单用户id
    private String nickname;//挂单用户昵称
    private long hugeSellId;//大额提现id,同一id的挂单属于一次提现拆分出来的
    private double price;//挂单价格
    private double money;//挂单金额
    private double num;//数量
    private boolean supportAli;//：true    //是否支持支付宝
    private boolean supportWechat;//:false    //是否支持微信支付
    private boolean supportBank;//:false    //是否支持银行卡支付
    private long puttime;//：21313131    //挂单时间

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public long getHugeSellId() {
        return hugeSellId;
    }

    public double getPrice() {
        return price;
    }

    public double getMoney() {
        return money;
    }

    public double getNum() {
        return num;
    }

    public boolean isSupportAli() {
        return supportAli;
    }

    public boolean isSupportWechat() {
        return supportWechat;
    }

    public boolean isSupportBank() {
        return supportBank;
    }

    public long getPuttime() {
        return puttime;
    }

    @Override
    public String toString() {
        return "LargeAmountItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", hugeSellId=" + hugeSellId +
                ", price=" + price +
                ", money=" + money +
                ", num=" + num +
                ", supportAli=" + supportAli +
                ", supportWechat=" + supportWechat +
                ", supportBank=" + supportBank +
                ", puttime=" + puttime +
                '}';
    }
}
