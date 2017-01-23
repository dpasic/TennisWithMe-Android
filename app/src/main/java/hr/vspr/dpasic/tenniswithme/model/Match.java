package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dpasic on 1/21/17.
 */

public class Match implements Parcelable {

    @SerializedName("Id")
    private int id;

    @SerializedName("PlayerOneId")
    private String playerOneId;

    @SerializedName("PlayerOneName")
    private String playerOneName;

    @SerializedName("PlayerTwoId")
    private String playerTwoId;

    @SerializedName("PlayerTwoName")
    private String playerTwoName;

    @SerializedName("IsConfirmed")
    private boolean isConfirmed;

    @SerializedName("IsPlayed")
    private boolean isPlayed;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("Result")
    private String result;

    @SerializedName("Rating")
    private String rating;

    @SerializedName("PlayerOneComment")
    private String playerOneComment;

    @SerializedName("PlayerTwoComment")
    private String playerTwoComment;

    @SerializedName("DatePlayed")
    private Date datePlayed;

    @SerializedName("CityPlayed")
    private String cityPlayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(String playerOneId) {
        this.playerOneId = playerOneId;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(String playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlayerOneComment() {
        return playerOneComment;
    }

    public void setPlayerOneComment(String playerOneComment) {
        this.playerOneComment = playerOneComment;
    }

    public String getPlayerTwoComment() {
        return playerTwoComment;
    }

    public void setPlayerTwoComment(String playerTwoComment) {
        this.playerTwoComment = playerTwoComment;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }

    public String getCityPlayed() {
        return cityPlayed;
    }

    public void setCityPlayed(String cityPlayed) {
        this.cityPlayed = cityPlayed;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss", Locale.getDefault());

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.cityPlayed);
        dest.writeString(this.comment);
        dest.writeString(this.playerOneComment);
        dest.writeString(this.playerOneId);
        dest.writeString(this.playerOneName);
        dest.writeString(this.playerTwoComment);
        dest.writeString(this.playerTwoId);
        dest.writeString(this.playerTwoName);
        dest.writeString(this.rating);
        dest.writeString(this.result);
        dest.writeString(sdf.format(this.datePlayed));
        dest.writeString(Boolean.toString(isConfirmed));
        dest.writeString(Boolean.toString(isPlayed));
    }

    protected Match(Parcel in) {
        this.cityPlayed = in.readString();
        this.comment = in.readString();
        this.playerOneComment = in.readString();
        this.playerOneId = in.readString();
        this.playerOneName = in.readString();
        this.playerTwoComment = in.readString();
        this.playerTwoId = in.readString();
        this.playerTwoName = in.readString();
        this.rating = in.readString();
        this.result = in.readString();
        try {
            this.datePlayed = sdf.parse(in.readString());
        } catch (ParseException e) {
            Log.d("PARSE", e.getMessage());
        }
        this.isConfirmed = Boolean.valueOf(in.readString());
        this.isPlayed = Boolean.valueOf(in.readString());
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
