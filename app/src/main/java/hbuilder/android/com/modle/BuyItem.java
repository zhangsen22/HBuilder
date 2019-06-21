package hbuilder.android.com.modle;

import android.os.Parcel;
import android.os.Parcelable;

public class BuyItem implements Parcelable {
    private long id;//:12312        //挂单id
    private long userid;//:3131     //挂单用户id
    private String nickname;//:"fer" //挂单用户昵称
    private int tradeTimes;//:1231    //累计订单数
    private double tradeSuccRate;//:32.32    //成单率
    private double price;//：6.78    //挂单价格
    private double minNum;//：100    //最小数量
    private double maxNum;//：200    //最大数量
    private boolean supportAli;//：true    //是否支持支付宝
    private boolean supportWechat;//:false    //是否支持微信支付
    private boolean supportBank;//:false    //是否支持银行卡支付
    private long puttime;//：21313131    //挂单时间

    public BuyItem(long id, String nickname, double price, double minNum, double maxNum, boolean supportAli, boolean supportWechat, boolean supportBank) {
        this.id = id;
        this.nickname = nickname;
        this.price = price;
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.supportAli = supportAli;
        this.supportWechat = supportWechat;
        this.supportBank = supportBank;
    }

    protected BuyItem(Parcel in) {
        id = in.readLong();
        userid = in.readLong();
        nickname = in.readString();
        tradeTimes = in.readInt();
        tradeSuccRate = in.readDouble();
        price = in.readDouble();
        minNum = in.readDouble();
        maxNum = in.readDouble();
        supportAli = in.readByte() != 0;
        supportWechat = in.readByte() != 0;
        supportBank = in.readByte() != 0;
        puttime = in.readLong();
    }

    public static final Creator<BuyItem> CREATOR = new Creator<BuyItem>() {
        @Override
        public BuyItem createFromParcel(Parcel in) {
            return new BuyItem(in);
        }

        @Override
        public BuyItem[] newArray(int size) {
            return new BuyItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public long getUserid() {
        return userid;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTradeTimes() {
        return tradeTimes;
    }

    public double getTradeSuccRate() {
        return tradeSuccRate;
    }

    public double getPrice() {
        return price;
    }

    public double getMinNum() {
        return minNum;
    }

    public double getMaxNum() {
        return maxNum;
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
        return "BuyItem{" +
                "id=" + id +
                ", userid=" + userid +
                ", nickname='" + nickname + '\'' +
                ", tradeTimes=" + tradeTimes +
                ", tradeSuccRate=" + tradeSuccRate +
                ", price=" + price +
                ", minNum=" + minNum +
                ", maxNum=" + maxNum +
                ", supportAli=" + supportAli +
                ", supportWechat=" + supportWechat +
                ", supportBank=" + supportBank +
                ", puttime=" + puttime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userid);
        dest.writeString(nickname);
        dest.writeInt(tradeTimes);
        dest.writeDouble(tradeSuccRate);
        dest.writeDouble(price);
        dest.writeDouble(minNum);
        dest.writeDouble(maxNum);
        dest.writeByte((byte) (supportAli ? 1 : 0));
        dest.writeByte((byte) (supportWechat ? 1 : 0));
        dest.writeByte((byte) (supportBank ? 1 : 0));
        dest.writeLong(puttime);
    }
}
