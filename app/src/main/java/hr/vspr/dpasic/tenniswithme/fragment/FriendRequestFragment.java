package hr.vspr.dpasic.tenniswithme.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.fragment.friend_request_mvp.FriendRequestPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.friend_request_mvp.FriendRequestPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.friend_request_mvp.FriendRequestView;
import hr.vspr.dpasic.tenniswithme.adapter.PlayerRecyclerViewAdapter;
import hr.vspr.dpasic.tenniswithme.fragment.interaction_listener.OnPeopleListFragmentInteractionListener;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

public class FriendRequestFragment extends Fragment implements SearchView.OnQueryTextListener,
        FriendRequestView, OnPeopleListFragmentInteractionListener {

    private FriendRequestPresenter friendRequestPresenter;

    @BindView(R.id.search_people)
    SearchView searchPeople;
    @BindView(R.id.list_people)
    RecyclerView recyclerView;
    @BindView(R.id.activity_friend_request)
    LinearLayout friendRequestView;

    public FriendRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerInfoFragment.
     */
    public static FriendRequestFragment newInstance() {
        FriendRequestFragment fragment = new FriendRequestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);
        ButterKnife.bind(this, view);

        friendRequestPresenter = new FriendRequestPresenterImpl(this);

        searchPeople.setOnQueryTextListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

        return view;
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
        recyclerView.setAdapter(new PlayerRecyclerViewAdapter(players, ActionType.REQUEST_FRIENDSHIP, this));
    }

    @Override
    public void notifyRequestError(String msg) {

    }

    @Override
    public void onListFragmentInteraction(Player player, ActionType actionType) {
        Fragment fragment = PlayerInfoFragment.newInstance(player, actionType);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, PlayerInfoFragment.class.getName())
                .addToBackStack(PlayerInfoFragment.class.getName()).commit();

        getActivity().setTitle(R.string.title_user_info);
    }
}
