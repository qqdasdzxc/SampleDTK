package com.dmitrijkuzmin.sampledtk.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class User implements Parcelable{

    @PrimaryKey
    @NonNull
    private Integer id;

    @NonNull
    public Integer getId() { return this.id; }

    public void setId(@NonNull Integer id) { this.id = id; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String username;

    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }

    private String email;

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    @Ignore
    private Address address;

    public Address getAddress() { return this.address; }

    public void setAddress(Address address) { this.address = address; }

    private String phone;

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    private String website;

    public String getWebsite() { return this.website; }

    public void setWebsite(String website) { this.website = website; }

    @Ignore
    private Company company;

    public Company getCompany() { return this.company; }

    public void setCompany(Company company) { this.company = company; }

    public class Geo
    {
        private String lat;

        public String getLat() { return this.lat; }

        public void setLat(String lat) { this.lat = lat; }

        private String lng;

        public String getLng() { return this.lng; }

        public void setLng(String lng) { this.lng = lng; }
    }

    public class Address
    {
        private String street;

        public String getStreet() { return this.street; }

        public void setStreet(String street) { this.street = street; }

        private String suite;

        public String getSuite() { return this.suite; }

        public void setSuite(String suite) { this.suite = suite; }

        private String city;

        public String getCity() { return this.city; }

        public void setCity(String city) { this.city = city; }

        private String zipcode;

        public String getZipcode() { return this.zipcode; }

        public void setZipcode(String zipcode) { this.zipcode = zipcode; }

        private Geo geo;

        public Geo getGeo() { return this.geo; }

        public void setGeo(Geo geo) { this.geo = geo; }
    }

    public class Company
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String catchPhrase;

        public String getCatchPhrase() { return this.catchPhrase; }

        public void setCatchPhrase(String catchPhrase) { this.catchPhrase = catchPhrase; }

        private String bs;

        public String getBs() { return this.bs; }

        public void setBs(String bs) { this.bs = bs; }
    }

    public User(){}

    public User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(website);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
