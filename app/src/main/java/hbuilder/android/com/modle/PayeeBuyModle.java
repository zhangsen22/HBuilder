package hbuilder.android.com.modle;

import android.os.Parcel;
import android.os.Parcelable;

public class PayeeBuyModle implements Parcelable {
    private String name;//
    private String account;//
    private String base64Img;//
    private String bankName;//
    private String subName;//
    private long dailyLimit;
    public String id;//


    protected PayeeBuyModle(Parcel in) {
        name = in.readString();
        account = in.readString();
        base64Img = in.readString();
        bankName = in.readString();
        subName = in.readString();
        dailyLimit = in.readLong();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(account);
        dest.writeString(base64Img);
        dest.writeString(bankName);
        dest.writeString(subName);
        dest.writeLong(dailyLimit);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayeeBuyModle> CREATOR = new Creator<PayeeBuyModle>() {
        @Override
        public PayeeBuyModle createFromParcel(Parcel in) {
            return new PayeeBuyModle(in);
        }

        @Override
        public PayeeBuyModle[] newArray(int size) {
            return new PayeeBuyModle[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public String getBankName() {
        return bankName;
    }

    public String getSubName() {
        return subName;
    }

    public long getDailyLimit() {
        return dailyLimit;
    }

    public String getId() {
        return id;
    }
}
