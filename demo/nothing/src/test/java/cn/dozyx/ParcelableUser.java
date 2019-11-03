package cn.dozyx;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author dozyx
 * @date 2019-11-02
 */
public class ParcelableUser implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
    }

    public ParcelableUser() {
    }

    protected ParcelableUser(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.isMale = in.readByte() != 0;
    }

    public static final Creator<ParcelableUser> CREATOR = new Creator<ParcelableUser>() {
        @Override
        public ParcelableUser createFromParcel(Parcel source) {
            return new ParcelableUser(source);
        }

        @Override
        public ParcelableUser[] newArray(int size) {
            return new ParcelableUser[size];
        }
    };
}
