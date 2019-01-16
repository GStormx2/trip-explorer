package com.example.omega.geobangla2;

import java.util.ArrayList;

public class StoredResources {
    private static ArrayList<String> storedImageUrls = new ArrayList<>();
    private static ArrayList<String> storedNames = new ArrayList<>();
    private static int pos;
    private static String clickedDivision;
    private static int resortPosition;
    private static int packOne, packTwo, pricePerDay;
    private static String resortTag, resortStars;
    private static String coverPhotoUrl, profilePhotoUrl;

    public static String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public static void setCoverPhotoUrl(String coverPhotoUrl) {
        StoredResources.coverPhotoUrl = coverPhotoUrl;
    }

    public static String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public static void setProfilePhotoUrl(String profilePhotoUrl) {
        StoredResources.profilePhotoUrl = profilePhotoUrl;
    }

    public static String getResortTag() {
        return resortTag;
    }

    public static void setResortTag(String resortTag) {
        StoredResources.resortTag = resortTag;
    }

    public static String getResortStars() {
        return resortStars;
    }

    public static void setResortStars(String resortStars) {
        StoredResources.resortStars = resortStars;
    }

    public static String getResortName() {
        return resortName;
    }

    public static void setResortName(String resortName) {
        StoredResources.resortName = resortName;
    }

    private static String resortName;

    public static int getPackOne() {
        return packOne;
    }

    public static void setPackOne(int packOne) {
        StoredResources.packOne = packOne;
    }

    public static int getPackTwo() {
        return packTwo;
    }

    public static void setPackTwo(int packTwo) {
        StoredResources.packTwo = packTwo;
    }

    public static int getPricePerDay() {
        return pricePerDay;
    }

    public static void setPricePerDay(int pricePerDay) {
        StoredResources.pricePerDay = pricePerDay;
    }


    public static int getResortPosition() {
        return resortPosition;
    }

    public static void setResortPosition(int resortPosition) {
        StoredResources.resortPosition = resortPosition;
    }

    public void setStoredResources(ArrayList<String> imageUrls, ArrayList<String> imageNames){
        storedImageUrls = imageUrls;
        storedNames = imageNames;

    }
    public String getStoredImageUrls(int i){
        return storedImageUrls.get(i);
    }
    public String getStoredNames(int i){
        return storedNames.get(i);
    }

    public static void setPos(int pos) {
        StoredResources.pos = pos;
    }

    public static int getPos() {
        return pos;
    }
    public static void setClickedDivision(String clickedDivision){
        StoredResources.clickedDivision = clickedDivision;
    }
    public static String getClickedDivision(){
        return StoredResources.clickedDivision;
    }
}
