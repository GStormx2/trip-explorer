package com.example.omega.geobangla2;

public class BookingClass {
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
}
