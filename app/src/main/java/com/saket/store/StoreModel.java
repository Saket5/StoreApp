package com.saket.store;

import android.os.Parcel;
import android.os.Parcelable;

class StoreModel implements Parcelable {
    private String shopName;
    private String Address;
    private double Lat;
    private double Longitute;
    private String items;
    private String offers;

    public StoreModel(){

    }
    public StoreModel(String shopName, String address, double lat, double longitute, String items, String offers) {
        this.shopName = shopName;
        Address = address;
        Lat = lat;
        Longitute = longitute;
        this.items = items;
        this.offers = offers;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLongitute() {
        return Longitute;
    }

    public void setLongitute(double longitute) {
        Longitute = longitute;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    protected StoreModel(Parcel in) {
        shopName = in.readString();
        Address = in.readString();
        Lat = in.readDouble();
        Longitute = in.readDouble();
        items = in.readString();
        offers = in.readString();
    }

    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel(Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray(int size) {
            return new StoreModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopName);
        dest.writeString(Address);
        dest.writeDouble(Lat);
        dest.writeDouble(Longitute);
        dest.writeString(items);
        dest.writeString(offers);
    }
}
