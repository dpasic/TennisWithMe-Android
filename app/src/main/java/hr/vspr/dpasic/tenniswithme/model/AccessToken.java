package hr.vspr.dpasic.tenniswithme.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edjapas on 19.12.2016..
 */

@Table(database = LocalDb.class)
public class AccessToken extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    @SerializedName("access_token")
    private String accessToken;

    @Column
    @SerializedName("refresh_token")
    private String refreshToken;

    @Column
    @SerializedName("token_type")
    private String tokenType;

    @Column
    @SerializedName(".expires")
    private String expires;

    public AccessToken() {
    }

    public AccessToken(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if (!Character.isUpperCase(tokenType.charAt(0))) {
            tokenType =
                    Character
                            .toString(tokenType.charAt(0))
                            .toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Date getExpiresDate() {
        SimpleDateFormat parser = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");

        try {
            return parser.parse(expires);
        } catch (ParseException e) {
            Log.d("DEBUG", e.getMessage());
            return new Date();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeString(this.tokenType);
        dest.writeString(this.refreshToken);
        dest.writeString(this.expires);
    }

    protected AccessToken(Parcel in) {
        this.accessToken = in.readString();
        this.tokenType = in.readString();
        this.refreshToken = in.readString();
        this.expires = in.readString();
    }

    public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator<AccessToken>() {
        @Override
        public AccessToken createFromParcel(Parcel source) {
            return new AccessToken(source);
        }

        @Override
        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };
}
