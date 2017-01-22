package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edjapas on 3.1.2017..
 */

public class Player implements Parcelable {

    @SerializedName("Id")
    private String id;

    @SerializedName("FirstName")
    public String firstName;

    @SerializedName("LastName")
    public String lastName;

    @SerializedName("Age")
    public String age;

    @SerializedName("City")
    public String city;

    @SerializedName("Skill")
    public String skill;

    @SerializedName("Gender")
    public String gender;

    @SerializedName("MobileNumber")
    public String mobileNumber;

    @SerializedName("Club")
    public String club;

    @SerializedName("Summary")
    public String summary;

    @SerializedName("Email")
    public String email;

    @SerializedName("UserName")
    public String userName;

    //public Skill Skill;
    @SerializedName("Rating")
    public String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return ((firstName == null) ? "" : firstName) + " " + ((lastName == null) ? "" : lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.age);
        dest.writeString(this.city);
        dest.writeString(this.club);
        dest.writeString(this.email);
        dest.writeString(this.firstName);
        dest.writeString(this.id);
        dest.writeString(this.lastName);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.rating);
        dest.writeString(this.gender);
        dest.writeString(this.summary);
        dest.writeString(this.userName);
    }

    protected Player(Parcel in) {
        this.age = in.readString();
        this.city = in.readString();
        this.club = in.readString();
        this.email = in.readString();
        this.firstName = in.readString();
        this.id = in.readString();
        this.lastName = in.readString();
        this.mobileNumber = in.readString();
        this.rating = in.readString();
        this.gender = in.readString();
        this.summary = in.readString();
        this.userName = in.readString();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
