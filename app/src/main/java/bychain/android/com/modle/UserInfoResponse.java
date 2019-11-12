package bychain.android.com.modle;

public class UserInfoResponse extends BaseBean{
    private int aliOpenFlag;

    public int getAliOpenFlag() {
        return aliOpenFlag;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "aliOpenFlag=" + aliOpenFlag +
                '}';
    }
}
