package hr.vspr.dpasic.tenniswithme.activity.edit_user_info_mvp;

import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by dpasic on 1/8/17.
 */

public interface UserInfoPublisher {

    void subscribe(UserInfoSubscriber subscriber);
    void notifySubscribers(Player player);
}
