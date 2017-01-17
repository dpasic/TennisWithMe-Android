package hr.vspr.dpasic.tenniswithme.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.ActiveFriendsFragment;
import hr.vspr.dpasic.tenniswithme.fragment.RequestedFriendsFragment;
import hr.vspr.dpasic.tenniswithme.fragment.UserInfoFragment;
import hr.vspr.dpasic.tenniswithme.activity.main_mvp.MainPresenterImpl;
import hr.vspr.dpasic.tenniswithme.activity.main_mvp.MainView;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnFriendsListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.User;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView,
        UserInfoFragment.OnFragmentInteractionListener,
        OnFriendsListFragmentInteractionListener {

    private MainPresenterImpl mainPresenter;
    private User user;

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

        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.setUserInfo();
    }

    private void commitUserInfoFragment(User user, UserActionType actionType) {
        Fragment fragment = UserInfoFragment.newInstance(user, actionType);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, UserInfoFragment.class.getName()).commit();

        setTitle(R.string.user_info);
    }

    private void commitActiveFriendsFragment() {
        Fragment fragment = ActiveFriendsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, ActiveFriendsFragment.class.getName()).commit();

        setTitle(R.string.friends);
    }

    private void commitRequestedFriendsFragment() {
        Fragment fragment = RequestedFriendsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, RequestedFriendsFragment.class.getName()).commit();

        setTitle(R.string.friend_requests);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            /*drawer.closeDrawer(GravityCompat.START);*/
            super.onBackPressed();
        } else {
            /*super.onBackPressed();*/
            drawer.openDrawer(GravityCompat.START);
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

        if (id == R.id.nav_active_friends) {
            commitActiveFriendsFragment();

        } else if (id == R.id.nav_requested_friends) {
            commitRequestedFriendsFragment();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openUserInfo(View view) {
        commitUserInfoFragment(user, UserActionType.VIEW_AND_EDIT);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setNavigationUserInfo(User user) {
        navigationName.setText(user.getFullName());
        navigationEmail.setText(user.getEmail());

        this.user = user;
        commitUserInfoFragment(user, UserActionType.VIEW_AND_EDIT);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(User item, UserActionType actionType) {
        commitUserInfoFragment(item, actionType);
    }

    @Override
    public void notifyRequestError(String msg) {
        Snackbar.make(drawer, msg, Snackbar.LENGTH_LONG).show();
    }
}
