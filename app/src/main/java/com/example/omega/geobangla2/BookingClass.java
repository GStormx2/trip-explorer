package com.example.omega.geobangla2;

import android.os.Parcel;
import android.os.Parcelable;

public class BookingClass implements Parcelable {
    private String name;
    private String tag;
    private String stars;
    private String bedType;
    private String roomCount;
    private String checkInDate;
    private String checkOutDate;
    private String totalPrice;
    private String uid;

    public BookingClass(){

    }

    public BookingClass(String Name, String Tag, String Stars, String BedType, String RoomCount, String CheckInDate, String CheckOutDate, String TotalPrice, String Uid) {
        this.name = Name;
        this.tag = Tag;
        this.stars = Stars;
        this.bedType = BedType;
        this.roomCount = RoomCount;
        this.checkInDate = CheckInDate;
        this.checkOutDate = CheckOutDate;
        this.totalPrice = TotalPrice;
        this.uid = Uid;
    }

    protected BookingClass(Parcel in) {
        name = in.readString();
        tag = in.readString();
        stars = in.readString();
        bedType = in.readString();
        roomCount = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        totalPrice = in.readString();
        uid = in.readString();
    }

    public static final Creator<BookingClass> CREATOR = new Creator<BookingClass>() {
        @Override
        public BookingClass createFromParcel(Parcel in) {
            return new BookingClass(in);
        }

        @Override
        public BookingClass[] newArray(int size) {
            return new BookingClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getStars() {
        return stars;
    }

    public String getBedType() {
        return bedType;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(tag);
        dest.writeString(stars);
        dest.writeString(bedType);
        dest.writeString(roomCount);
        dest.writeString(checkInDate);
        dest.writeString(checkOutDate);
        dest.writeString(totalPrice);
        dest.writeString(uid);
    }
}
