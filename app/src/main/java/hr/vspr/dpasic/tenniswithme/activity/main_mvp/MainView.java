package hr.vspr.dpasic.tenniswithme.activity.main_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by edjapas on 3.1.2017..
 */

public interface MainView extends RestView {

    void setNavigationUserInfo(Player player);
}
