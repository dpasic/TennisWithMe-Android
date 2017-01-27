package hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp;

import hr.vspr.dpasic.tenniswithme.common.AccessTokenRefresher;
import hr.vspr.dpasic.tenniswithme.common.RestPublisher;
import hr.vspr.dpasic.tenniswithme.common.RestSubscriber;
import hr.vspr.dpasic.tenniswithme.model.AccessToken;
import hr.vspr.dpasic.tenniswithme.model.Player;

/**
 * Created by dpasic on 1/8/17.
 */

public class EditUserInfoPresenterImpl implements EditUserInfoPresenter {

    private EditUserInfoView editUserInfoView;

    public EditUserInfoPresenterImpl(EditUserInfoView editUserInfoView) {
        this.editUserInfoView = editUserInfoView;
    }

    @Override
    public void updateProfile(final Player player) {
        AccessTokenRefresher refresher = new AccessTokenRefresher();

        refresher.registerSubscriber(new RestSubscriber() {
            @Override
            public void doRequest(RestPublisher publisher, AccessToken token) {
                updateProfileRequest(token, player);
            }
        });

        refresher.refreshTokenIfNecessary();
    }

    private void updateProfileRequest(AccessToken token, Player player) {

    }
}
