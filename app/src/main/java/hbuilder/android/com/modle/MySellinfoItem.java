package hbuilder.android.com.modle;

public class MySellinfoItem {

    private long id;//:12312        //挂单id
    private String tradeid;//:"fdsafds"    //订单号
    private double price;//:12.21            //单价
    private double num;//:2.1                //数量
    private int status;//:1            //订单状态,见下面表格
    private int type;//:1                //收款方式,1为支付宝，2为微信，3为银行账户
    private long createTime;//:1        //订单创建时间
    private long payTime;//:            //付款时间
    private int payCode;//:1234            //付款参考码

    public String getTradeId() {
        return tradeid;
    }

    public double getPrice() {
        return price;
    }

    public double getNum() {
        return num;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public int getPayCode() {
        return payCode;
    }

    @Override
    public String toString() {
        return "MySellinfoItem{" +
                "tradeId='" + tradeid + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", status=" + status +
                ", type=" + type +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", payCode=" + payCode +
                '}';
    }
}
