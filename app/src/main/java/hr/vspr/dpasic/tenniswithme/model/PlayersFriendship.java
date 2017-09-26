package hr.vspr.dpasic.tenniswithme.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dpasic on 1/8/17.
 */

public class PlayersFriendship {

    @SerializedName("Id")
    public int id;

    @SerializedName("RequestSenderId")
    public String requestSenderId;

    @SerializedName("RequestReceiverId")
    public String requestReceiverId;

    @SerializedName("IsConfirmed")
    public boolean isConfirmed;

    @SerializedName("IsActive")
    public boolean isActive;
}
