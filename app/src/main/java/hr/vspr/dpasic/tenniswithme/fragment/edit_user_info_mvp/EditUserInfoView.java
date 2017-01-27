package hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by dpasic on 1/8/17.
 */

public interface EditUserInfoView extends RestView {

    void onUpdatedProfile(Player player);
}
