package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dpasic on 1/8/17.
 */

public class PlayersRating implements Parcelable {

    @SerializedName("Id")
    private int id;

    @SerializedName("ReviewerId")
    private String reviewerId;

    @SerializedName("RatedId")
    private String ratedId;

    @SerializedName("RatingDescription")
    private String ratingDescription;

    public PlayersRating() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getRatedId() {
        return ratedId;
    }

    public void setRatedId(String ratedId) {
        this.ratedId = ratedId;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.reviewerId);
        dest.writeString(this.ratedId);
        dest.writeString(this.ratingDescription);
    }

    protected PlayersRating(Parcel in) {
        this.reviewerId = in.readString();
        this.ratedId = in.readString();
        this.ratingDescription = in.readString();
    }

    public static final Creator<PlayersRating> CREATOR = new Creator<PlayersRating>() {
        @Override
        public PlayersRating createFromParcel(Parcel in) {
            return new PlayersRating(in);
        }

        @Override
        public PlayersRating[] newArray(int size) {
            return new PlayersRating[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
