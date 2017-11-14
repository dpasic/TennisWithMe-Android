package hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp;

import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.PlayersRating;

/**
 * Created by edjapas on 16.1.2017..
 */

public interface UserInfoPresenter {

    void confirmFriendship(Player player);

    void requestFriendship(Player player);

    void signOut();

    void getPlayersRating(Player player);

    void createOrUpdatePlayersRating(PlayersRating playersRating);
}
