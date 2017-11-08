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
    @SerializedName("SkillDescription")
    private String skillDescription;

    @Column
    @SerializedName("GenderDescription")
    private String genderDescription;

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

    // TODO: check if this is ok data type
    @Column
    @SerializedName("OverallRating")
    private String overallRating;

    @Column
    @SerializedName("PlayedGames")
    private int playedGames;

    @Column
    @SerializedName("WonGames")
    private int wonGames;

    @Column
    @SerializedName("LostGames")
    private int lostGames;

    @Column
    @SerializedName("Points")
    private int points;

    // Badges
    @SerializedName("IsFavoritePlayer")
    private boolean isFavoritePlayer;

    @SerializedName("HasBronzeBadge")
    private boolean hasBronzeBadge;

    @SerializedName("HasSilverBadge")
    private boolean hasSilverBadge;

    @SerializedName("HasGoldBadge")
    private boolean hasGoldBadge;

    @SerializedName("HasPlatinumBadge")
    private boolean hasPlatinumBadge;

    @SerializedName("HasWinnerRookieBadge")
    private boolean hasWinnerRookieBadge;

    @SerializedName("HasWinnerChallengerBadge")
    private boolean hasWinnerChallengerBadge;

    @SerializedName("HasWinnerMasterBadge")
    private boolean hasWinnerMasterBadge;


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

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public String getGenderDescription() {
        return genderDescription;
    }

    public void setGenderDescription(String genderDescription) {
        this.genderDescription = genderDescription;
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

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isFavoritePlayer() {
        return isFavoritePlayer;
    }

    public void setFavoritePlayer(boolean favoritePlayer) {
        isFavoritePlayer = favoritePlayer;
    }

    public boolean isHasBronzeBadge() {
        return hasBronzeBadge;
    }

    public void setHasBronzeBadge(boolean hasBronzeBadge) {
        this.hasBronzeBadge = hasBronzeBadge;
    }

    public boolean isHasSilverBadge() {
        return hasSilverBadge;
    }

    public void setHasSilverBadge(boolean hasSilverBadge) {
        this.hasSilverBadge = hasSilverBadge;
    }

    public boolean isHasGoldBadge() {
        return hasGoldBadge;
    }

    public void setHasGoldBadge(boolean hasGoldBadge) {
        this.hasGoldBadge = hasGoldBadge;
    }

    public boolean isHasPlatinumBadge() {
        return hasPlatinumBadge;
    }

    public void setHasPlatinumBadge(boolean hasPlatinumBadge) {
        this.hasPlatinumBadge = hasPlatinumBadge;
    }

    public boolean isHasWinnerRookieBadge() {
        return hasWinnerRookieBadge;
    }

    public void setHasWinnerRookieBadge(boolean hasWinnerRookieBadge) {
        this.hasWinnerRookieBadge = hasWinnerRookieBadge;
    }

    public boolean isHasWinnerChallengerBadge() {
        return hasWinnerChallengerBadge;
    }

    public void setHasWinnerChallengerBadge(boolean hasWinnerChallengerBadge) {
        this.hasWinnerChallengerBadge = hasWinnerChallengerBadge;
    }

    public boolean isHasWinnerMasterBadge() {
        return hasWinnerMasterBadge;
    }

    public void setHasWinnerMasterBadge(boolean hasWinnerMasterBadge) {
        this.hasWinnerMasterBadge = hasWinnerMasterBadge;
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
        dest.writeString(this.overallRating);
        dest.writeInt(this.playedGames);
        dest.writeInt(this.wonGames);
        dest.writeInt(this.lostGames);
        dest.writeInt(this.points);
        dest.writeString(this.genderDescription);
        dest.writeString(this.summary);
        dest.writeString(this.userName);
        dest.writeString(this.skillDescription);

        // booleans
        dest.writeByte((byte) (this.isFavoritePlayer ? 1 : 0));
        dest.writeByte((byte) (this.hasBronzeBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasSilverBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasGoldBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasPlatinumBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasWinnerRookieBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasWinnerChallengerBadge ? 1 : 0));
        dest.writeByte((byte) (this.hasWinnerMasterBadge ? 1 : 0));
        dest.writeByte((byte) (this.isFriendshipReceived ? 1 : 0));
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
        this.overallRating = in.readString();
        this.playedGames = in.readInt();
        this.wonGames = in.readInt();
        this.lostGames = in.readInt();
        this.points = in.readInt();
        this.genderDescription = in.readString();
        this.summary = in.readString();
        this.userName = in.readString();
        this.skillDescription = in.readString();

        // booleans
        this.isFavoritePlayer = in.readByte() != 0;
        this.hasBronzeBadge = in.readByte() != 0;
        this.hasSilverBadge = in.readByte() != 0;
        this.hasGoldBadge = in.readByte() != 0;
        this.hasPlatinumBadge = in.readByte() != 0;
        this.hasWinnerRookieBadge = in.readByte() != 0;
        this.hasWinnerChallengerBadge = in.readByte() != 0;
        this.hasWinnerMasterBadge = in.readByte() != 0;
        this.isFriendshipReceived = in.readByte() != 0;
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
