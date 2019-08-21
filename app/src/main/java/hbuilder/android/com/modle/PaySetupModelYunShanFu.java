package hbuilder.android.com.modle;

public class PaySetupModelYunShanFu extends BaseBean{
    private YunShanFuPayeeModel cloudPayeeObj;//云闪付收款信息,没设置如下均为""

    public YunShanFuPayeeModel getCloudPayee() {
        return cloudPayeeObj;
    }

    @Override
    public String toString() {
        return "PaySetupModelYunShanFu{" +
                "cloudPayee=" + cloudPayeeObj +
                '}';
    }
}
