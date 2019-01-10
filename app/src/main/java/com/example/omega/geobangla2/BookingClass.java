package com.example.omega.geobangla2;

public class BookingClass {
    private String Name;
    private String Tag;
    private String Stars;
    private String BedType;
    private String RoomCount;
    private String CheckInDate;
    private String CheckOutDate;
    private String TotalPrice;
    private String Uid;

    public BookingClass(){

    }

    public BookingClass(String Name, String Tag, String Stars, String BedType, String RoomCount, String CheckInDate, String CheckOutDate, String TotalPrice, String Uid) {
        this.Name = Name;
        this.Tag = Tag;
        this.Stars = Stars;
        this.BedType = BedType;
        this.RoomCount = RoomCount;
        this.CheckInDate = CheckInDate;
        this.CheckOutDate = CheckOutDate;
        this.TotalPrice = TotalPrice;
        this.Uid = Uid;
    }

    public String getName() {
        return Name;
    }

    public String getTag() {
        return Tag;
    }

    public String getStars() {
        return Stars;
    }

    public String getBedType() {
        return BedType;
    }

    public String getRoomCount() {
        return RoomCount;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public String getUid() {
        return Uid;
    }
}
