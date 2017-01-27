package hr.vspr.dpasic.tenniswithme.fragment.friend_request_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by edjapas on 18.1.2017..
 */

public interface FriendRequestView extends RestView {

    void updateListViewAdapter(List<Player> players);
}
