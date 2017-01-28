package hr.vspr.dpasic.tenniswithme.fragment.friends_mvp;

import java.util.List;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by dpasic on 1/15/17.
 */

public interface FriendsView extends RestView {

    void updateViewAdapter(List<Player> players);
}
