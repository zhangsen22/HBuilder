package hbuilder.android.com.modle;

import android.os.Parcel;
import android.os.Parcelable;

public class BulletinListItem implements Parcelable {
    private String bodyUrl;
    private String releaseTime;
    private String title;

    protected BulletinListItem(Parcel in) {
        bodyUrl = in.readString();
        releaseTime = in.readString();
        title = in.readString();
    }

    public static final Creator<BulletinListItem> CREATOR = new Creator<BulletinListItem>() {
        @Override
        public BulletinListItem createFromParcel(Parcel in) {
            return new BulletinListItem(in);
        }

        @Override
        public BulletinListItem[] newArray(int size) {
            return new BulletinListItem[size];
        }
    };

    public String getBodyUrl() {
        return bodyUrl;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "BulletinListItem{" +
                "bodyUrl='" + bodyUrl + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bodyUrl);
        dest.writeString(releaseTime);
        dest.writeString(title);
    }
}
