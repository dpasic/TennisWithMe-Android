package hr.vspr.dpasic.tenniswithme.fragment.friends_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.User;

/**
 * Created by dpasic on 1/15/17.
 */

public interface FriendsView extends RestView {

    void updateListViewAdapter(List<User> users);
}
