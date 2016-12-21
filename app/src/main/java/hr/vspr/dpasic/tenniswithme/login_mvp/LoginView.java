package hr.vspr.dpasic.tenniswithme.login_mvp;

import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;

/**
 * Created by edjapas on 19.12.2016..
 */

public interface LoginView {

    LoginViewModel getLoginViewModel();
    void goToApp();
    void showLoginError(String msg);
}
