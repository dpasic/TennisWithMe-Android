package hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;

/**
 * Created by edjapas on 16.1.2017..
 */

public interface UserInfoView extends RestView {

    void acceptFriendship();
    void friendshipRequested();
}
