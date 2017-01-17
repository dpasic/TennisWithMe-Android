package hr.vspr.dpasic.tenniswithme.activity.register_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;

/**
 * Created by edjapas on 19.12.2016..
 */

public interface RegisterView extends RestView {

    RegisterViewModel getRegisterViewModel();
    void goToApp();
}
