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

    public List<String> getGateway() {
        return gateway;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "DomainModel{" +
                "gateway=" + gateway +
                ", version='" + version + '\'' +
                '}';
    }
}
