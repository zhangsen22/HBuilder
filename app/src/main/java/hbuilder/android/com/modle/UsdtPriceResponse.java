package hbuilder.android.com.modle;

public class UsdtPriceResponse extends BaseBean{

    private double maxBuyPrice;//:6.77    //最高买价
    private double minBuyPrice;//:6.75    //最低买价
    private double maxSellPrice;//:6.76    //最高卖价
    private double minSellPrice;//:6.75    //最低卖价

    public UsdtPriceResponse(double maxSellPrice, double minSellPrice) {
        this.maxSellPrice = maxSellPrice;
        this.minSellPrice = minSellPrice;
    }

    public double getMaxBuyPrice() {
        return maxBuyPrice;
    }

    public double getMinBuyPrice() {
        return minBuyPrice;
    }

    public double getMaxSellPrice() {
        return maxSellPrice;
    }

    public double getMinSellPrice() {
        return minSellPrice;
    }
}
