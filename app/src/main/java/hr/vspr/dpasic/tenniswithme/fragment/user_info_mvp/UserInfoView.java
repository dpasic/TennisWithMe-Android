package hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.PlayersRating;

/**
 * Created by edjapas on 16.1.2017..
 */

public interface UserInfoView extends RestView {

    void acceptedFriendship();
    void friendshipRequested();
    void signedOut();
    void updatePlayer(Player player);
    void setPlayersRating(PlayersRating playersRating);
    void onUpdatePlayersRating(PlayersRating playersRating);
}
