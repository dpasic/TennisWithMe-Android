package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dpasic on 1/21/17.
 */

public class Match implements Parcelable {

    @SerializedName("Id")
    private int id;

    @SerializedName("ChallengerId")
    private String challengerId;

    @SerializedName("ChallengerName")
    private String challengerName;

    @SerializedName("OpponentId")
    private String opponentId;

    @SerializedName("OpponentName")
    private String opponentName;

    @SerializedName("WinnerId")
    private String winnerId;

    @SerializedName("IsConfirmed")
    private boolean isConfirmed;

    @SerializedName("IsPlayed")
    private boolean isPlayed;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("Result")
    private String result;

    @SerializedName("RatingDescription")
    private String ratingDescription;

    @SerializedName("ChallengerComment")
    private String challengerComment;

    @SerializedName("OpponentComment")
    private String opponentComment;

    @SerializedName("TimestampPlayed")
    private long timestampPlayed;

    @SerializedName("CityPlayed")
    private String cityPlayed;

    @SerializedName("IsMatchReceived")
    private boolean isMatchReceived;

    public Match() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChallengerId() {
        return challengerId;
    }

    public void setChallengerId(String challengerId) {
        this.challengerId = challengerId;
    }

    public String getChallengerName() {
        return challengerName;
    }

    public void setChallengerName(String challengerName) {
        this.challengerName = challengerName;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
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

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    public String getChallengerComment() {
        return challengerComment;
    }

    public void setChallengerComment(String challengerComment) {
        this.challengerComment = challengerComment;
    }

    public String getOpponentComment() {
        return opponentComment;
    }

    public void setOpponentComment(String opponentComment) {
        this.opponentComment = opponentComment;
    }

    public Date getDatePlayed() {
        return new Date(timestampPlayed);
    }

    public void setDatePlayed(Date datePlayed) {
        this.timestampPlayed = datePlayed.getTime();
    }

    public String getCityPlayed() {
        return cityPlayed;
    }

    public void setCityPlayed(String cityPlayed) {
        this.cityPlayed = cityPlayed;
    }

    public boolean isMatchReceived() {
        return isMatchReceived;
    }

    public void setMatchReceived(boolean matchReceived) {
        isMatchReceived = matchReceived;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.cityPlayed);
        dest.writeString(this.comment);
        dest.writeString(this.challengerComment);
        dest.writeString(this.challengerId);
        dest.writeString(this.challengerName);
        dest.writeString(this.winnerId);
        dest.writeString(this.opponentComment);
        dest.writeString(this.opponentId);
        dest.writeString(this.opponentName);
        dest.writeString(this.ratingDescription);
        dest.writeString(this.result);
        dest.writeString(Long.toString(this.timestampPlayed));
        dest.writeString(Boolean.toString(isConfirmed));
        dest.writeString(Boolean.toString(isPlayed));
    }

    protected Match(Parcel in) {
        this.cityPlayed = in.readString();
        this.comment = in.readString();
        this.challengerComment = in.readString();
        this.challengerId = in.readString();
        this.challengerName = in.readString();
        this.winnerId = in.readString();
        this.opponentComment = in.readString();
        this.opponentId = in.readString();
        this.opponentName = in.readString();
        this.ratingDescription = in.readString();
        this.result = in.readString();
        this.timestampPlayed = Long.valueOf(in.readString());
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
