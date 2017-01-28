package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by edjapas on 3.1.2017..
 */

@Table(database = LocalDb.class)
public class Player extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey(autoincrement = true)
    private transient int dbId;

    @Column
    @SerializedName("Id")
    private String id;

    @Column
    @SerializedName("FirstName")
    private String firstName;

    @Column
    @SerializedName("LastName")
    private String lastName;

    @Column
    @SerializedName("Age")
    private String age;

    @Column
    @SerializedName("City")
    private String city;

    @Column
    @SerializedName("Skill")
    private String skill;

    @Column
    @SerializedName("Gender")
    private String gender;

    @Column
    @SerializedName("MobileNumber")
    private String mobileNumber;

    @Column
    @SerializedName("Club")
    private String club;

    @Column
    @SerializedName("Summary")
    private String summary;

    @Column
    @SerializedName("Email")
    private String email;

    @Column
    @SerializedName("UserName")
    private String userName;

    @Column
    @SerializedName("Rating")
    private String rating;

    @SerializedName("IsFriendshipReceived")
    private boolean isFriendshipReceived;

    public Player() {
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

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

    public boolean isFriendshipReceived() {
        return isFriendshipReceived;
    }

    public void setFriendshipReceived(boolean friendshipReceived) {
        isFriendshipReceived = friendshipReceived;
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
