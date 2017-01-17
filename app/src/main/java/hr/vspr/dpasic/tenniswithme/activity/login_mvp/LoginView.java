package hr.vspr.dpasic.tenniswithme.activity.login_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;

/**
 * Created by edjapas on 19.12.2016..
 */

public interface LoginView extends RestView {

    LoginViewModel getLoginViewModel();
    void goToApp();
}
