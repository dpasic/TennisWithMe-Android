package hr.vspr.dpasic.tenniswithme.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.UserInfoFragment;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

public class UserInfoActivity extends AppCompatActivity implements UserInfoFragment.OnFragmentInteractionListener {

    private Player player;
    private ActionType actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);

        player = getIntent().getParcelableExtra(MainActivity.PLAYER);
        actionType = (ActionType) getIntent().getSerializableExtra(MainActivity.ACTION_TYPE);

        Fragment fragment = UserInfoFragment.newInstance(player, actionType);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_user_info_container, fragment, UserInfoFragment.class.getName()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
