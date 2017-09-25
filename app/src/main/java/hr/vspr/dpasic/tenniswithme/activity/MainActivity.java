package hr.vspr.dpasic.tenniswithme.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.ActiveFriendsFragment;
import hr.vspr.dpasic.tenniswithme.fragment.ActiveMatchesFragment;
import hr.vspr.dpasic.tenniswithme.fragment.MatchInfoFragment;
import hr.vspr.dpasic.tenniswithme.fragment.PlayerInfoFragment;
import hr.vspr.dpasic.tenniswithme.fragment.RequestedMatchesFragment;
import hr.vspr.dpasic.tenniswithme.fragment.SearchPartnersFragment;
import hr.vspr.dpasic.tenniswithme.fragment.RequestedFriendsFragment;
import hr.vspr.dpasic.tenniswithme.activity.main_mvp.MainPresenterImpl;
import hr.vspr.dpasic.tenniswithme.activity.main_mvp.MainView;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnMatchListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView,
        PlayerInfoFragment.OnFragmentInteractionListener,
        OnPeopleListFragmentInteractionListener,
        OnMatchListFragmentInteractionListener {

    public static final String PLAYER = "player";
    public static final String MATCH = "match";
    public static final String ACTION_TYPE = "actionType";

    private MainPresenterImpl mainPresenter;
    private Player player;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private TextView navigationName;
    private TextView navigationEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        View headerLayout = navigationView.getHeaderView(0);
        navigationName = (TextView) headerLayout.findViewById(R.id.name_view);
        navigationEmail = (TextView) headerLayout.findViewById(R.id.email_view);

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInfo(view);
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        commitFindPartnerFragment();
        navigationView.setCheckedItem(R.id.nav_find_partner);

        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.setUserInfo();
    }

    private void commitUserInfoFragment(Player player, ActionType actionType) {
        Fragment fragment = PlayerInfoFragment.newInstance(player, actionType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, PlayerInfoFragment.class.getName())
                .addToBackStack(PlayerInfoFragment.class.getName()).commit();
    }

    private void commitFindPartnerFragment() {
        Fragment fragment = SearchPartnersFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, SearchPartnersFragment.class.getName())
                .addToBackStack(SearchPartnersFragment.class.getName()).commit();
    }

    private void commitActiveFriendsFragment() {
        Fragment fragment = ActiveFriendsFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, ActiveFriendsFragment.class.getName())
                .addToBackStack(ActiveFriendsFragment.class.getName()).commit();
    }

    private void commitRequestedFriendsFragment() {
        Fragment fragment = RequestedFriendsFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, RequestedFriendsFragment.class.getName())
                .addToBackStack(RequestedFriendsFragment.class.getName()).commit();
    }

    private void commitActiveMatchesFragment() {
        Fragment fragment = ActiveMatchesFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, ActiveMatchesFragment.class.getName())
                .addToBackStack(ActiveMatchesFragment.class.getName()).commit();
    }

    private void commitRequestedMatchesFragment() {
        Fragment fragment = RequestedMatchesFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, RequestedMatchesFragment.class.getName())
                .addToBackStack(RequestedMatchesFragment.class.getName()).commit();
    }

    private void clearFragmentManagerStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStackImmediate();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStackImmediate();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find_partner) {
            clearFragmentManagerStack();
            commitFindPartnerFragment();

        } else if (id == R.id.nav_active_friends) {
            clearFragmentManagerStack();
            commitActiveFriendsFragment();

        } else if (id == R.id.nav_requested_friends) {
            clearFragmentManagerStack();
            commitRequestedFriendsFragment();

        } else if (id == R.id.nav_active_matches) {
            clearFragmentManagerStack();
            commitActiveMatchesFragment();

        } else if (id == R.id.nav_requested_matches) {
            clearFragmentManagerStack();
            commitRequestedMatchesFragment();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openUserInfo(View view) {
        commitUserInfoFragment(player, ActionType.VIEW_AND_EDIT);
        drawer.closeDrawer(GravityCompat.START);

        // Uncheck all items in navigation
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void setNavigationUserInfo(Player player) {
        navigationName.setText(player.getFullName());
        navigationEmail.setText(player.getEmail());

        this.player = player;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Player player, ActionType actionType) {
        commitUserInfoFragment(player, actionType);
    }

    @Override
    public void onListFragmentInteraction(Match match, ActionType actionType) {
        Fragment fragment = MatchInfoFragment.newInstance(match, player, actionType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, MatchInfoFragment.class.getName())
                .addToBackStack(MatchInfoFragment.class.getName()).commit();
    }

    @Override
    public void notifyRequestError(String msg) {
        Snackbar.make(drawer, msg, Snackbar.LENGTH_LONG).show();
    }
}
