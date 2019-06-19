package hbuilder.android.com.modle;

public class MyBuyinfoItem {

    private long id;//:12312        //挂单id
    private String tradeId;//:"fdsafds"    //订单号
    private double price;//:12.21            //单价
    private double num;//:2.1                //数量
    private int status;//:1            //订单状态,见下面表格
    private int type;//:1                //收款方式,1为支付宝，2为微信，3为银行账户
    private long createTime;//:1        //订单创建时间
    private long payTime;//:            //付款时间
    private int payCode;//:1234            //付款参考码

    private PayeeBuyModle payee;



    public long getId() {
        return id;
    }

    public String getTradeId() {
        return tradeId;
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

    public long getCreateTime() {
        return createTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public int getPayCode() {
        return payCode;
    }

    public PayeeBuyModle getPayee() {
        return payee;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }

    public void setPayee(PayeeBuyModle payee) {
        this.payee = payee;
    }
}
