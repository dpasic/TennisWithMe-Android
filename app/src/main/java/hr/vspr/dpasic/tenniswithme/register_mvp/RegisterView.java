package hr.vspr.dpasic.tenniswithme.register_mvp;

import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;

/**
 * Created by edjapas on 19.12.2016..
 */

public interface RegisterView {

    RegisterViewModel getRegisterViewModel();
    void goToApp();
    void showRegisterError(String msg);
}
