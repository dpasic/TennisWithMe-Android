package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dpasic on 1/21/17.
 */

public class Match implements Parcelable {

    protected Match(Parcel in) {
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
