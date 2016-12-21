package hr.vspr.dpasic.tenniswithme.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edjapas on 19.12.2016..
 */

public class LoginViewModel {

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    public LoginViewModel() {
    }

    public LoginViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
