package hr.vspr.dpasic.tenniswithme.main_mvp;

import hr.vspr.dpasic.tenniswithme.model.User;

/**
 * Created by edjapas on 3.1.2017..
 */

public interface MainView {

    void setNavigationUserInfo(User user);
    void showError(String msg);
}
