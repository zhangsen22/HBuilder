package hbuilder.android.com.modle;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDetailsModle implements Parcelable {

    private String tradeId;//:"fdsafds"    //订单号
    private int status;//:1            //订单状态,见下面表格
    private double price;//:12.21            //单价
    private double num;//:2.1                //数量
    private long createTime;//:1        //订单创建时间
    private int payCode;//:1234            //付款参考码
    private int type;//:1                //收款方式,1为支付宝，2为微信，3为银行账户
    private String name;//微信名，支付宝名,银行卡真实姓名
    private String account;//微信号，支付宝账号,银行卡号
    private String base64Img;//微信收款二维码,支付宝收款二维码

    public OrderDetailsModle(String tradeId, int status, double price, double num, long createTime, int payCode, String name, String account, String base64Img,int type) {
        this.tradeId = tradeId;
        this.status = status;
        this.price = price;
        this.num = num;
        this.createTime = createTime;
        this.payCode = payCode;
        this.name = name;
        this.account = account;
        this.base64Img = base64Img;
        this.type = type;
    }

    protected OrderDetailsModle(Parcel in) {
        tradeId = in.readString();
        status = in.readInt();
        price = in.readDouble();
        num = in.readDouble();
        createTime = in.readLong();
        payCode = in.readInt();
        type = in.readInt();
        name = in.readString();
        account = in.readString();
        base64Img = in.readString();
    }

    public static final Creator<OrderDetailsModle> CREATOR = new Creator<OrderDetailsModle>() {
        @Override
        public OrderDetailsModle createFromParcel(Parcel in) {
            return new OrderDetailsModle(in);
        }

        @Override
        public OrderDetailsModle[] newArray(int size) {
            return new OrderDetailsModle[size];
        }
    };

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tradeId);
        dest.writeInt(status);
        dest.writeDouble(price);
        dest.writeDouble(num);
        dest.writeLong(createTime);
        dest.writeInt(payCode);
        dest.writeInt(type);
        dest.writeString(name);
        dest.writeString(account);
        dest.writeString(base64Img);
    }
}
