package hbuilder.android.com.modle;

import java.util.List;

public class DomainModel extends BaseBean{
//    {
//        "gateway": ["http://app.infbank.com:8080/GateServer",
//            "http://app.infbank.com:8080/GateServer",
//            "http://app.infbank.com/GateServer",
//            "http://app.infbank.com/GateServer"],
//        "version": "V5.0.2"
//    }

    private List<String> gateway;
    private String version;
    private boolean wxPayLock;//是否锁定微信支付  true:锁定  不让看  不显示  false:相反

    public List<String> getGateway() {
        return gateway;
    }

    public String getVersion() {
        return version;
    }

    public boolean isWxPayLock() {
        return wxPayLock;
    }

    @Override
    public String toString() {
        return "DomainModel{" +
                "gateway=" + gateway +
                ", version='" + version + '\'' +
                ", wxPayLock=" + wxPayLock +
                '}';
    }
}
