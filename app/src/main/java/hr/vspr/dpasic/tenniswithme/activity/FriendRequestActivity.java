package hr.vspr.dpasic.tenniswithme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.friend_request_mvp.FriendRequestPresenter;
import hr.vspr.dpasic.tenniswithme.activity.friend_request_mvp.FriendRequestPresenterImpl;
import hr.vspr.dpasic.tenniswithme.activity.friend_request_mvp.FriendRequestView;
import hr.vspr.dpasic.tenniswithme.adapter.PlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

public class FriendRequestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        FriendRequestView, OnPeopleListFragmentInteractionListener {

    private static final String USER = "user";
    private static final String ACTION_TYPE = "actionType";

    private FriendRequestPresenter friendRequestPresenter;

    @BindView(R.id.search_people)
    SearchView searchPeople;
    @BindView(R.id.list_people)
    RecyclerView recyclerView;
    @BindView(R.id.activity_friend_request)
    LinearLayout friendRequestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        ButterKnife.bind(this);

        friendRequestPresenter = new FriendRequestPresenterImpl(this);

        searchPeople.setOnQueryTextListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        friendRequestPresenter.sendFriendsQuery(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void updateListViewAdapter(List<Player> players) {
        recyclerView.setAdapter(new PlayerRecyclerViewAdapter(players, UserActionType.REQUEST_FRIENDSHIP, this));
    }

    @Override
    public void notifyRequestError(String msg) {

    }

    @Override
    public void onListFragmentInteraction(Player item, UserActionType actionType) {
        Intent userInfoActivity = new Intent(this, UserInfoActivity.class);
        userInfoActivity.putExtra(USER, item);
        userInfoActivity.putExtra(ACTION_TYPE, actionType);

        startActivity(userInfoActivity);
    }
}
