package ccash.android.com.modle;

import android.os.Parcel;
import android.os.Parcelable;

public class LaCaraPayeeItemModelPayee implements Parcelable {

    private long id;//:3                //id
    private String name;//："放大",        //姓名，
    private String account;//:"ewrwre",        //支付宝账号，
    private String accountid;
    private long wechatPaymentId;
    private String base64Img;//:"rewr"        //支付宝收款二维码
    private boolean locked;//: false,
    private boolean watchStop;//: false
    private boolean watchUnbind;// 是否已经解除监控

      /**
       * 所在地区省代码
       */
    private String provinceCode;

    /**
     * 所在地区市代码
     */
    private String cityCode;

    protected LaCaraPayeeItemModelPayee(Parcel in) {
        id = in.readLong();
        wechatPaymentId = in.readLong();
        name = in.readString();
        account = in.readString();
        accountid = in.readString();
        base64Img = in.readString();
        locked = in.readByte() != 0;
        watchStop = in.readByte() != 0;
        watchUnbind = in.readByte() != 0;
        provinceCode = in.readString();
        cityCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(wechatPaymentId);
        dest.writeString(name);
        dest.writeString(account);
        dest.writeString(accountid);
        dest.writeString(base64Img);
        dest.writeByte((byte) (locked ? 1 : 0));
        dest.writeByte((byte) (watchStop ? 1 : 0));
        dest.writeByte((byte) (watchUnbind ? 1 : 0));
        dest.writeString(provinceCode);
        dest.writeString(cityCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaCaraPayeeItemModelPayee> CREATOR = new Creator<LaCaraPayeeItemModelPayee>() {
        @Override
        public LaCaraPayeeItemModelPayee createFromParcel(Parcel in) {
            return new LaCaraPayeeItemModelPayee(in);
        }

        @Override
        public LaCaraPayeeItemModelPayee[] newArray(int size) {
            return new LaCaraPayeeItemModelPayee[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getAccountid() {
        return accountid;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isWatchStop() {
        return watchStop;
    }

    public boolean isWatchUnbind() {
        return watchUnbind;
    }

    public long getWechatPaymentId() {
        return wechatPaymentId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    @Override
    public String toString() {
        return "LaCaraPayeeItemModelPayee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", accountid='" + accountid + '\'' +
                ", wechatPaymentId=" + wechatPaymentId +
                ", base64Img='" + base64Img + '\'' +
                ", locked=" + locked +
                ", watchStop=" + watchStop +
                ", watchUnbind=" + watchUnbind +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}
