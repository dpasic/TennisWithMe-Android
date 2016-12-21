package hr.vspr.dpasic.tenniswithme.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edjapas on 19.12.2016..
 */

public class RegisterViewModel {

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    @SerializedName("ConfirmPassword")
    private String confirmPassword;

    public RegisterViewModel() {
    }

    public RegisterViewModel(String email, String password, String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
