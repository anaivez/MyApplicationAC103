package com.gdu.myapplicationac103;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:
 * Created by ZhDu on 2021/4/15 14:17.
 */
public class DUser implements Parcelable {

    int id;
    String Name;

    public DUser(int id, String name) {
        this.id = id;
        Name = name;
    }

    protected DUser(Parcel in) {
        id = in.readInt();
        Name = in.readString();
    }

    public static final Creator<DUser> CREATOR = new Creator<DUser>() {
        @Override
        public DUser createFromParcel(Parcel in) {
            return new DUser(in);
        }

        @Override
        public DUser[] newArray(int size) {
            return new DUser[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name == null ? "" : Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Name);
    }
}
