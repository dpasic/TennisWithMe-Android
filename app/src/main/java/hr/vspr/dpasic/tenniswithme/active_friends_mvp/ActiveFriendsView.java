package hr.vspr.dpasic.tenniswithme.active_friends_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.model.User;

/**
 * Created by dpasic on 1/15/17.
 */

public interface ActiveFriendsView {

    void updateListViewAdapter(List<User> users);
}
